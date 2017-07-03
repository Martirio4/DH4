package com.craps.myapplication.View.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;


import com.craps.myapplication.ControllerFormato.ControllerUsuario;
import com.craps.myapplication.R;
import com.facebook.Profile;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;


public class ActivityLogin extends AppCompatActivity {


    private ImageButton loginBtn;
    TwitterAuthClient twitterAuthClient;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ShareButton shareButton;
    ProgressDialog progress;
    private String idFacebook, nombreFacebook, nombreMedioFacebook, apellidoFacebook, sexoFacebook, imagenFacebook, nombreCompletoFacebook, emailFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login);

        twitterAuthClient = new TwitterAuthClient();
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (estaLogueadoAFacebook()){
            ingresarAFacebook();
        }

        if(session != null){
            ingresarLogueadoTwitter(ActivityLogin.this, session.getUserName());
        }

        TextView unTextview = (TextView) findViewById(R.id.textViewLogin);
        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        unTextview.setTypeface(roboto);

        final TextInputLayout textInputLayout1 = (TextInputLayout) findViewById(R.id.inputLayout1);
        final TextInputLayout textInputLayout2 = (TextInputLayout) findViewById(R.id.inputLayout2);


        Button botonLogin = (Button) findViewById(R.id.buttonLogin);
        Button botonRegistro = (Button) findViewById(R.id.buttonRegister);
        Button botonLuego = (Button) findViewById(R.id.buttonDespues);
        final EditText editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsuario.setTypeface(roboto);


        //COMPROBAR SI COINCIDE USUARIO Y CONTRASEÑA

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputLayout1.setError(null);
                textInputLayout2.setError(null);
                Integer validar =0;
                if (editTextUsuario.getText().toString().isEmpty()) {
                    textInputLayout1.setError("Por favor, ingrese un usuario valido");
                    validar = validar + 1;
                }

                if (editTextPassword.getText().toString().toLowerCase().isEmpty()) {
                    textInputLayout2.setError("Por favor, ingrese una contraseña valida");
                    validar = validar + 1;
                }

                if (validar > 0) {
                    return;
                }
                ControllerUsuario controllerUsuario = new ControllerUsuario(v.getContext());
                String mail = editTextUsuario.getText().toString().toLowerCase();
                String contraseña = editTextPassword.getText().toString();

                if (controllerUsuario.loguearUsuario(mail, contraseña)){
                   ingresarLogueadoTwitter(ActivityLogin.this, mail);
                }
                else{
                    editTextPassword.setText(null);
                    editTextUsuario.setText(null);
                }

            }
        });

        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unIntent = new Intent(v.getContext(), ActivityRegister.class);
                startActivity(unIntent);

            }
        });

        botonLuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unIntent = new Intent(v.getContext(), ActivityMain.class);
                startActivity(unIntent);
            }
        });





        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                twitterAuthClient.authorize(ActivityLogin.this, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        //success
                        ingresarLogueadoTwitter(ActivityLogin.this, result.data.getUserName());
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        //failure
                        Toast.makeText(ActivityLogin.this, "Logeo fallido", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        idFacebook = nombreFacebook = nombreMedioFacebook = apellidoFacebook = sexoFacebook = nombreCompletoFacebook = emailFacebook ="";
        imagenFacebook="nada";

        //for facebook
        //FACEBOOK
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                ingresarAFacebook();

            }

            @Override
            public void onCancel() {

                Toast.makeText(ActivityLogin.this, "Cancelado", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(ActivityLogin.this, "Hubo un Error", Toast.LENGTH_SHORT).show();
            }
        });

        //shareButton = (ShareButton) findViewById(R.id.shareButton);

       /* ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://www.digitalasdfasdfasdfhouse.com/?gclid=QaAp7m8P8HAQ"))
                .build();

        //Asigno el content al share button
        shareButton.setShareContent(content);
*/


    }


    public void ingresarLogueadoTwitter(Activity unaActivity, String mail){
        Intent unIntent = new Intent(unaActivity, ActivityMain.class);
        Bundle bundle=new Bundle();
        bundle.putString(ActivityMain.USUARIO, mail);
        unIntent.putExtras(bundle);
        finish();
        startActivity(unIntent);
    }

    public void ingresarLogueadoFacebook(Activity unaActivity, String mail, String imagenUsuario){
        Intent unIntent = new Intent(unaActivity, ActivityMain.class);
        Bundle bundle=new Bundle();
        bundle.putString(ActivityMain.USUARIO, mail);
        bundle.putString(ActivityMain.IMAGENUSUARIO, imagenUsuario);
        unIntent.putExtras(bundle);
        finish();
        startActivity(unIntent);
    }

    public void composeTweet(View view){

        final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        final Intent intent = new ComposerActivity.Builder(ActivityLogin.this)
                .session(session)
                .text("Love where you work")
                .hashtags("#twitter")
                .createIntent();

        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result
       twitterAuthClient.onActivityResult(requestCode, resultCode, data);
       callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean estaLogueadoAFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void ingresarAFacebook(){
        Profile unProf=Profile.getCurrentProfile();
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            idFacebook =profile.getId();
            nombreFacebook =profile.getFirstName();
            nombreMedioFacebook =profile.getMiddleName();
            apellidoFacebook =profile.getLastName();
            nombreCompletoFacebook =profile.getName();
            imagenFacebook =profile.getProfilePictureUri(400, 400).toString();
        }

        ingresarLogueadoFacebook(ActivityLogin.this, nombreCompletoFacebook, imagenFacebook);
    }



}

