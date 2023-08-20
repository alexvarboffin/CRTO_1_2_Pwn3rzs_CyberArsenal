package com.pwn3rzs.crto.android.adapter;

import android.content.res.AssetManager;

import com.pwn3rzs.crto.android.R;
import com.pwn3rzs.crto.android.activity.DLog;
import com.pwn3rzs.crto.android.activity.ViewModel;
import com.pwn3rzs.crto.android.activity.appitem.SimpleLine;
import com.pwn3rzs.crto.android.adapter.headerCollapsed.HeaderCollapsedObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdapterUtils {

    public static void treeViewer(AssetManager am, List<ViewModel> data0, String fileName, HeaderCollapsedObject injectHere) throws IOException {
        String[] list0 = am.list(fileName);

        if (list0 != null) {

//            List<String> folderList = new ArrayList<>(Arrays.asList(list0));
//            Collections.sort(folderList, new NaturalOrderComparator());

            Arrays.sort(list0, new NaturalOrderComparator());

            for (String s : list0) {
                String fullPath = ("".equals(fileName)) ? s : fileName + "/" + s;

                try {
                    InputStream inputStream = am.open(fullPath);
                    //AssetFileDescriptor assetFileDescriptor = am.openFd(fullPath);
                    //AssetFileDescriptor assetFileDescriptor = am.openNonAssetFd(fullPath);
                    //DLog.d("{@@@@" + fullPath);

                    if (injectHere == null) {
                        //@@detect typew
                        if (SimpleLine.isImages(fullPath)) {
                            //list.add(new ResItem(fullPath,  ResType.IMAGES));
                        } else {
                            data0.add(new SimpleLine(s, fullPath, ResType.FILE));
                        }
                    } else {
                        //@@detect typew
                        if (SimpleLine.isImages(fullPath)) {
                            //list.add(new ResItem(fullPath,  ResType.IMAGES));
                        } else {
                            injectHere.list.add(new SimpleLine(s, fullPath, ResType.FILE));
                        }
                    }

                    // Полученное содержимое файла
                    //assetFileDescriptor.close();
                } catch (IOException e) {
                    DLog.handleException(e);
                    HeaderCollapsedObject injectHere0 = new HeaderCollapsedObject("" + s, R.drawable.ic_folder_blue_36dp);
                    treeViewer(am, data0, fullPath, injectHere0);
                }
            }

            if (injectHere != null) {
                data0.add(injectHere);
            }
        }
    }

    private static class NaturalOrderComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return compareStringsWindowsStyle(o1, o2);
        }

        private int compareStringsWindowsStyle(String s1, String s2) {
            int len1 = s1.length();
            int len2 = s2.length();
            int i = 0;
            int j = 0;

            while (i < len1 && j < len2) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(j);

                if (Character.isDigit(c1) && Character.isDigit(c2)) {
                    int num1 = 0;
                    int num2 = 0;

                    while (i < len1 && Character.isDigit(s1.charAt(i))) {
                        num1 = num1 * 10 + Character.getNumericValue(s1.charAt(i));
                        i++;
                    }

                    while (j < len2 && Character.isDigit(s2.charAt(j))) {
                        num2 = num2 * 10 + Character.getNumericValue(s2.charAt(j));
                        j++;
                    }

                    if (num1 != num2) {
                        return num1 - num2;
                    }
                } else {
                    if (c1 != c2) {
                        return c1 - c2;
                    }
                    i++;
                    j++;
                }
            }

            return len1 - len2;
        }
    }
}
