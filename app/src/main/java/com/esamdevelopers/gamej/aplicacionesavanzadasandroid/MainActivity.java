package com.esamdevelopers.gamej.aplicacionesavanzadasandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private AdView adView;
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize the Facebook SDK and this Callback Manager
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //Obtain the Hash Key to the proyect
        getHashKey(getString(R.string.hash_key));
        setContentView(R.layout.activity_main);

        //Register de Login Button and his Callback
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast msgInicio = Toast.makeText(MainActivity.this, "Inicio de sesion exitoso!", Toast.LENGTH_SHORT);
                msgInicio.setGravity(0, 0, Gravity.CENTER_HORIZONTAL);
                msgInicio.show();
            }

            @Override
            public void onCancel() {
                Toast msgCancel = Toast.makeText(MainActivity.this, "Inicio de sesion exitoso!", Toast.LENGTH_SHORT);
                msgCancel.show();
            }

            @Override
            public void onError(FacebookException error) {
                AlertDialog.Builder msgError = new AlertDialog.Builder(getApplicationContext());
                msgError.setIcon(android.R.drawable.ic_dialog_info);
                msgError.setTitle("Error");
                msgError.setMessage("Error al iniciar sesion verifique sus credenciales");
                msgError.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                msgError.create();
                msgError.show();
            }
        });

        // Load an ad into the AdMob banner view.
        adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        adView.loadAd(adRequest);
    }

    private void getHashKey(String key) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(key, PackageManager.GET_SIGNATURES);

            for (Signature signature: info.signatures){
                MessageDigest msg = MessageDigest.getInstance("SHA");
                msg.update(signature.toByteArray());
                Log.d("KeyHash: ", Base64.encodeToString(msg.digest(), Base64.DEFAULT));

                System.out.println("KeyHash: " + Base64.encodeToString(msg.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        if(adView != null){
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(adView != null){
            adView.resume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resCode, data);
    }
}