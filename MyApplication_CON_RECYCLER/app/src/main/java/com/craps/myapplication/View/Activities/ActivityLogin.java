package com.craps.myapplication.View.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
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
        setContentView(R.layout.activity_login);
        //INICIALIZAR FIREBASE
        mAuth= FirebaseAuth.getInstance();

        mAuthListener=new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if (user!=null){
                    Toast.makeText(ActivityLogin.this, "Bienvenido! "+user.getEmail(), Toast.LENGTH_SHORT).show();
                    String mail;
                    String foto;
                    if (user.getEmail() == null||user.getEmail().isEmpty()){mail=user.getUid();}
                    else{mail=user.getEmail();}

                    if (user.getPhotoUrl()==null){foto="sinFoto";}
                    else{foto=user.getPhotoUrl().toString();}

                    ingresarLogueadoTwitter(ActivityLogin.this, mail, foto);
                }
                else{

                }
            }
        };

        twitterAuthClient = new TwitterAuthClient();
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (estaLogueadoAFacebook()){
            ingresarConFacebook();
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
                    loguearFirebaseManual(mail, contraseña);
                   ingresarLogueadoManual(ActivityLogin.this, mail);
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
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                ingresarConFacebook();

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

    public void ingresarLogueadoManual(Activity unaActivity, String mail){
        Intent unIntent = new Intent(unaActivity, ActivityMain.class);
        Bundle bundle=new Bundle();
        bundle.putString(ActivityMain.USUARIO, mail);
        unIntent.putExtras(bundle);
        finish();
        startActivity(unIntent);
    }

    public void ingresarLogueadoTwitter(Activity unaActivity, String mail, String imagenUsuario){
        Intent unIntent = new Intent(unaActivity, ActivityMain.class);
        Bundle bundle=new Bundle();
        bundle.putString(ActivityMain.USUARIO, mail);
        bundle.putString(ActivityMain.IMAGENUSUARIO, imagenUsuario);
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

    public void ingresarConFacebook(){
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

    public void ingresarConTwitter(){
        Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(true, false,true);
        user.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                String nombreTwitter=result.data.name;
                String emailTwitter=result.data.email;
                String fotoTwitter=result.data.profileImageUrl.replace("_normal","");

                if (emailTwitter==null ||emailTwitter.isEmpty()){
                    ingresarLogueadoTwitter(ActivityLogin.this,nombreTwitter, fotoTwitter);
                }
                else{
                    ingresarLogueadoTwitter(ActivityLogin.this, emailTwitter, fotoTwitter);
                }
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(ActivityLogin.this, "ERROR! Intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        });

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
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            String mail;
                            if (user.getEmail() == null||user.getEmail().isEmpty()){
                                mail=user.getUid();
                            }
                            else{
                                mail=user.getEmail();
                            }

                            //HAGO ALGO EN CASO DE QUE EL USUARIO SE HAYA CREADO EN FIREBASE CORRECTAMENTE
                            ingresarLogueadoTwitter(ActivityLogin.this, mail, user.getPhotoUrl().toString());

                        } else {
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

                        }

                        // ...
                    }
                });
    }


}

