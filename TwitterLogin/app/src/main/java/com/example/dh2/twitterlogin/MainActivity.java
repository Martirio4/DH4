package com.example.dh2.twitterlogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.os.IResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

public class MainActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;
    TwitterSession twitterSession;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //HACER EL INITIALIZE ANTES DEL SET CONTENT VIEW
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();

        loginButton = (TwitterLoginButton) findViewById(R.id.loginButton);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(MainActivity.this, result.data.getUserName(), Toast.LENGTH_SHORT).show();
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(MainActivity.this, "Logeo fallido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void composeTweet(View view){

        final TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        final Intent intent = new ComposerActivity.Builder(MainActivity.this)
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
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void handleTwitterSession(TwitterSession session) {

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            //HAGO ALGO EN CASO DE QUE EL USUARIO SE HAYA CREADO EN FIREBASE CORRECTAMENTE
                            irAActivity2();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser!=null){
            irAActivity2();
        }
    }

    private void irAActivity2(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void logoutFirebase(View view){
        FirebaseAuth.getInstance().signOut();
    }
}
