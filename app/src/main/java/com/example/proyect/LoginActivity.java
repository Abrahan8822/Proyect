package com.example.proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mEtcorreo;
    private EditText mEtpass;
    private Button mbtnLogin;
    private Button mbtnreset;
    //datos a usar para iniciar sesion
    private  String correo;
    private  String pass;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtcorreo=findViewById(R.id.etlCorreo);
        mEtpass=findViewById(R.id.etlpass);
        mbtnLogin=findViewById(R.id.btnLogin);
        mAuth=FirebaseAuth.getInstance();
        mbtnreset=findViewById(R.id.btnSentToReset);

        mbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo=mEtcorreo.getText().toString();
                pass=mEtpass.getText().toString();

                if(!correo.isEmpty()&&!pass.isEmpty())
                {
                    loginUser();
                }else
                {
                    Toast.makeText(LoginActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mbtnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });
    }
    private void loginUser()
    {
        mAuth.signInWithEmailAndPassword(correo,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(LoginActivity.this, "No se pudo iniciar sesion compruebe sus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}