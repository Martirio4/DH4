package com.craps.myapplication.View.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.craps.myapplication.ControllerFormato.ControllerUsuario;
import com.craps.myapplication.Model.Usuario;
import com.craps.myapplication.R;
import com.craps.myapplication.Utils.TMDBHelper;


public class ActivityRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);

        final TextInputLayout textInputLayout1= (TextInputLayout)  findViewById(R.id.inputLayout1);
        final TextInputLayout textInputLayout2= (TextInputLayout)  findViewById(R.id.inputLayout2);
        final TextInputLayout textInputLayout3= (TextInputLayout)  findViewById(R.id.inputLayout3);
        final TextInputLayout textInputLayout4= (TextInputLayout)  findViewById(R.id.inputLayout4);

        final EditText editTextUsuario = (EditText) findViewById(R.id.editTextUsuario);
        final EditText editTextMail = (EditText) findViewById(R.id.editTextmail);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        final EditText editTextPassRepe = (EditText) findViewById(R.id.editTextPasswordRepetir);

        Typeface roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        editTextUsuario.setTypeface(roboto);
        editTextMail.setTypeface(roboto);
        textViewLogin.setTypeface(roboto);

        Button buttonRegister = (Button) findViewById(R.id.button_register_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Integer control = 0;
                textInputLayout1.setError(null);
                textInputLayout2.setError(null);
                textInputLayout3.setError(null);

                if (editTextUsuario.getText().toString().isEmpty()){
                    textInputLayout1.setError("Por favor ingrese un usuario valido");
                    control = control +1;
                }
                if (editTextMail.getText().toString().isEmpty()){
                    textInputLayout2.setError("Por favor ingrese una contraseña valida");
                    control = control +1;
                }
                if (editTextPassword.getText().toString().isEmpty()){
                    textInputLayout3.setError("Por favor ingrese una contraseña valida");
                    control = control +1;
                }

                if (control>0){
                    return;
                }
                if (editTextPassword.getText().toString().equals(editTextPassRepe.getText().toString())){
                    ControllerUsuario controllerUsuario = new ControllerUsuario(v.getContext());
                    Usuario unUsuario = new Usuario();
                    unUsuario.setNombre(editTextUsuario.getText().toString().toLowerCase());
                    unUsuario.setMail(editTextMail.getText().toString().toLowerCase());
                    unUsuario.setContraseña(editTextPassword.getText().toString().toLowerCase());
                    unUsuario.setPais("Argentina");
                    unUsuario.setIdoma(TMDBHelper.language_SPANISH);
                    unUsuario.setFechanacimiento("02/02/1983");

                    if (controllerUsuario.RegistrarUsuario(unUsuario)){
                        //Voy al onBoarding
                        Intent unIntent = new Intent(v.getContext(), ActivityOnBoarding.class);
                        Bundle bundle= new Bundle();
                        bundle.putString(ActivityMain.USUARIO, editTextMail.getText().toString());
                        unIntent.putExtras(bundle);
                        startActivity(unIntent);
                    }
                    else{
                        Toast.makeText(v.getContext(), "El mail ingresado ya fue registrado previamente", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    textInputLayout3.setError("Las contraseñas ingresadas no coinciden");
                    editTextPassRepe.setText("");
                }


            }});




    }
}
