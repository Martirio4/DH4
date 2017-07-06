package com.craps.myapplication.View.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.craps.myapplication.Utils.HTTPConnectionManager;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import retrofit2.Call;


public class ActivityLogin extends AppCompatActivity {


    private ImageButton loginBtn;
    TwitterAuthClient twitterAuthClient;
    TwitterApiClient twitterApiClient;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private ShareButton shareButton;
    ProgressDialog progress;
    private String idFacebook, nombreFacebook, nombreMedioFacebook, apellidoFacebook, sexoFacebook, imagenFacebook, nombreCompletoFacebook, emailFacebook;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //INICIALIZO TWITTER
        Twitter.initialize(this);
        mAuth= FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        //chequeo si hay internet
        if (!HTTPConnectionManager.isNetworkingOnline(this)){
            TextView unText=(TextView)findViewById(R.id.texto_sin_conexion);
            unText.setVisibility(View.VISIBLE);
        }


        //INICIALIZAR FIREBASE
        twitterAuthClient = new TwitterAuthClient();
        mAuthListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if (user!=null){
                    String mail;
                    String foto;
                    if (user.getEmail() == null||user.getEmail().isEmpty()){mail=user.getDisplayName();}
                    else{mail=user.getEmail();}
                    if (user.getPhotoUrl()==null){foto="sinFoto";}
                    else{foto=user.getPhotoUrl().toString();}
                    Toast.makeText(ActivityLogin.this, "Bienvenido! "+mail, Toast.LENGTH_SHORT).show();
                    ingresarLogueado(ActivityLogin.this, mail, foto);
                    user.getEmail();
                }
                else{

                }
            }
        };

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
                    loguearFirebaseManual(mail, contraseña);

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
                finish();
                startActivity(unIntent);

            }
        });

        botonLuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unIntent = new Intent(v.getContext(), ActivityMain.class);
                finish();
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
                        handleTwitterSession(result.data);

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

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("error", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("error", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("error", "facebook:onError", error);
                // ...
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



    public void ingresarLogueado(Activity unaActivity, String mail, String imagenUsuario){
        Intent unIntent = new Intent(unaActivity, ActivityMain.class);
        Bundle bundle=new Bundle();
        bundle.putString(ActivityMain.USUARIO, mail);
        bundle.putString(ActivityMain.IMAGENUSUARIO, imagenUsuario);
        unIntent.putExtras(bundle);
        finish();
        startActivity(unIntent);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    private void handleTwitterSession(TwitterSession session) {

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {



                            // If sign in fails, display a message to the user.
                            Toast.makeText(ActivityLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void LogoutFirebase(){
        FirebaseAuth.getInstance().signOut();
    }

    public void loguearFirebaseManual(String usuario, String pass){
        mAuth.signInWithEmailAndPassword(usuario, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(ActivityLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }



    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("tag1", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("tag2", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("tag3", "signInWithCredential", task.getException());
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
/*
    private boolean estaLogueadoEnFacebook(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    private boolean estaLogueadoEnTwitter(){
        Session activeSession =TwitterCore.getInstance().getSessionManager().getActiveSession();
        return activeSession!=null;
    }
*/



    }



