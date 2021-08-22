package com.example.proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText metCorreo;
    private Button mbtnReset;
    private String correo="";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        metCorreo=findViewById(R.id.etarCorreo);
        mbtnReset=findViewById(R.id.btnarreset);
        mAuth= FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);

        mbtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo=metCorreo.getText().toString();
                if(!correo.isEmpty())
                {
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();

                }else
                {
                    Toast.makeText(ResetPasswordActivity.this, "Debe ingresar su correo", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }
    private void resetPassword()
    {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ResetPasswordActivity.this, "Se ha enviado un correo para restablecer su contraseña", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                }else
                {
                    Toast.makeText(ResetPasswordActivity.this, "No se pudo restablecer la contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}