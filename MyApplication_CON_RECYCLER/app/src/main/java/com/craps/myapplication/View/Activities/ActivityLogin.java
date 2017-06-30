package com.craps.myapplication.View.Activities;

import android.app.Activity;
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


import com.craps.myapplication.ControllerFormato.ControllerUsuario;
import com.craps.myapplication.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;


public class ActivityLogin extends AppCompatActivity {


    private ImageButton loginBtn;
    TwitterAuthClient twitterAuthClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
       setContentView(R.layout.activity_login);

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
                    Intent unIntent = new Intent(v.getContext(), ActivityMain.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(ActivityMain.USUARIO, mail);
                    unIntent.putExtras(bundle);
                    startActivity(unIntent);
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


        twitterAuthClient = new TwitterAuthClient();
        loginBtn = (ImageButton) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                twitterAuthClient.authorize(ActivityLogin.this, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        //success
                        Intent unIntent = new Intent(v.getContext(), ActivityRegister.class);
                        startActivity(unIntent);
                        Toast.makeText(ActivityLogin.this, result.data.getUserName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        //failure
                        Toast.makeText(ActivityLogin.this, "Logeo fallido", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });





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
    }


}

