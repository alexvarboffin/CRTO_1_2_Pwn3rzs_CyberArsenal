//package com.walhalla.pcleaner.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.PersistableBundle;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.Toolbar;
//
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//import kgbook.ru.R;
//import com.walhalla.ui.DLog;
//
//
//public class MainActivity extends BaseActivity implements FacebookCallback<LoginResult> {
//
//    private LoginButton loginButton;
//    private CallbackManager callbackManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setTheme(R.style.AppTheme_NoActionBar);
//        setContentView(R.layout.main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        if (savedInstanceState == null) {
////            getSupportFragmentManager().beginTransaction()
////                    .replace(R.id.container, MainFragment8888.newInstance())
////                    .commitNow();
//        }
//        callbackManager = CallbackManager.Factory.create();
//        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions("email");
//        // If using in a fragment
//        //loginButton.setFragment(this);
//
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//            LoginManager.getInstance().logOut();
//        });
//
//
//        LoginManager.getInstance().registerCallback(callbackManager, this);
//        // Callback registration
//        loginButton.registerCallback(callbackManager, this);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        if (accessToken != null) {
//            DLog.d("-->" + accessToken.toString());
//        }
//    }
//
//    @Override
//    public void onSuccess(LoginResult loginResult) {
//        DLog.d(loginResult.toString());
//    }
//
//    @Override
//    public void onCancel() {
//        DLog.d("Canceled");
//    }
//
//    @Override
//    public void onError(FacebookException exception) {
//        DLog.handleException(exception);
//    }
//}
