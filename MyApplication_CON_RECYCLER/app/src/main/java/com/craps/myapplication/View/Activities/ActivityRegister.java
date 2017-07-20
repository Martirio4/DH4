package com.craps.myapplication.View.Activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.craps.myapplication.ControllerFormato.ControllerUsuario;
import com.craps.myapplication.Model.Usuario;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.TMDBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.craps.myapplication.R.id.editTextPassword;
import static com.craps.myapplication.R.id.editTextUsuario;
import static com.craps.myapplication.R.id.start;


public class ActivityRegister extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private ControllerUsuario controllerUsuario;

    private EditText editTextUsuario;
    private EditText editTextMail;
    private EditText editTextPassword;
    private EditText editTextPassRepe;

    private TextInputLayout textInputLayout1;
    private TextInputLayout textInputLayout2;
    private TextInputLayout textInputLayout3;
    private TextInputLayout textInputLayout4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        controllerUsuario = new ControllerUsuario(this);


        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);

        textInputLayout1 = (TextInputLayout) findViewById(R.id.inputLayout1);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.inputLayout2);
        textInputLayout3 = (TextInputLayout) findViewById(R.id.inputLayout3);
        textInputLayout4 = (TextInputLayout) findViewById(R.id.inputLayout4);

        editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        editTextMail = (EditText) findViewById(R.id.editTextmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassRepe = (EditText) findViewById(R.id.editTextPasswordRepetir);

        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        editTextUsuario.setTypeface(roboto);
        editTextMail.setTypeface(roboto);
        textViewLogin.setTypeface(roboto);

        Button buttonRegister = (Button) findViewById(R.id.button_register_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer control = 0;
                textInputLayout1.setError(null);
                textInputLayout2.setError(null);
                textInputLayout3.setError(null);

                if (editTextUsuario.getText().toString().isEmpty()) {
                    textInputLayout1.setError("Por favor ingrese un usuario valido");
                    control = control + 1;
                }
                if (editTextMail.getText().toString().isEmpty()) {
                    textInputLayout2.setError("Por favor ingrese una contraseña valida");
                    control = control + 1;
                }
                if (editTextPassword.getText().toString().isEmpty()) {
                    textInputLayout3.setError("Por favor ingrese una contraseña valida");
                    control = control + 1;
                }
                if (editTextPassword.getText().toString().length() < 6) {
                    textInputLayout3.setError("La contraseña debe tener al menos 6 caracteres");
                    control = control + 1;
                }

                if (control > 0) {
                    return;
                }
                if (editTextPassword.getText().toString().equals(editTextPassRepe.getText().toString())) {

                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setNombre(editTextUsuario.getText().toString().toLowerCase());
                    nuevoUsuario.setMail(editTextMail.getText().toString().toLowerCase());
                    nuevoUsuario.setContraseña(editTextPassword.getText().toString().toLowerCase());
                    nuevoUsuario.setPais("Argentina");
                    nuevoUsuario.setIdoma(TMDBHelper.language_SPANISH);
                    nuevoUsuario.setFechanacimiento("02/02/1983");
                    if (controllerUsuario.existeUsuario(nuevoUsuario)) {
                        Toast.makeText(ActivityRegister.this, "El usuario ya existe, utilize otro mail", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent unIntent = new Intent(ActivityRegister.this, ActivityLogin.class);
                        startActivity(unIntent);
                    } else {
                        crearCuentaFirebase(editTextMail.getText().toString().toLowerCase(), editTextPassword.getText().toString());
                    }
                } else {
                    textInputLayout3.setError("Las contraseñas ingresadas no coinciden");
                    editTextPassRepe.setText("");
                }
            }

            ;

        });
    }

    public void crearCuentaFirebase(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Usuario nuevoUsuario = new Usuario();
                            nuevoUsuario.setMail(email);
                            nuevoUsuario.setContraseña(password);
                            controllerUsuario.RegistrarUsuario(nuevoUsuario);

                            Intent unIntent = new Intent(ActivityRegister.this, ActivityLogin.class);
                            startActivity(unIntent);
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            //creo usuario en mi base de datos
                            Toast.makeText(ActivityRegister.this, "Sin Conexion, intente mas tarde.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    public void ingresarLogueadoNuevoUsuario(String mail) {
        Intent unIntent = new Intent(ActivityRegister.this, ActivityMain.class);
        Bundle bundle = new Bundle();
        bundle.putString(ActivityMain.USUARIO, mail);
        unIntent.putExtras(bundle);
        startActivity(unIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent unIntent = new Intent(this, ActivityLogin.class);
        startActivity(unIntent);
    }
}

