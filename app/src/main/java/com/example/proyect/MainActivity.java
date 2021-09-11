package com.example.proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText mEtnombre;
    private EditText mEtcorreo;
    private EditText mEtpass;

    private Button mbtnRegister;
    private Button mbtnsendToLogin;
    //variable de los datos a registar
    private String nombre="";
    private String correo="";
    private String pass="";
    FirebaseAuth mAth;
    DatabaseReference mdataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAth= FirebaseAuth.getInstance();
        mEtnombre=findViewById(R.id.etNombre);
        mEtcorreo=findViewById(R.id.etCorreo);
        mEtpass=findViewById(R.id.etpass);

        mbtnRegister=findViewById(R.id.btnregister);
        mbtnsendToLogin=findViewById(R.id.btnlsentTologin);
        mdataBase= FirebaseDatabase.getInstance().getReference();

        mbtnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre=mEtnombre.getText().toString();
                correo=mEtcorreo.getText().toString();
                pass=mEtpass.getText().toString();


                if(!nombre.isEmpty()&&!correo.isEmpty()&&!pass.isEmpty())
                {
                    if(pass.length()>=6)
                    {
                        registerUser();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "El password debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mbtnsendToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                //se podria usar finish() pero el usuario debe poder regresar ala activity anterior si lo desea
            }
        });
    }
    private void registerUser()
    {
        mAth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Map<String,Object> map= new HashMap<>();
                    map.put("nombre",nombre);
                    map.put("correo",correo);
                    map.put("password",pass);
                    map.put("estado",1);
                    map.put("fechaRegistro", ServerValue.TIMESTAMP);
                    map.put("fechaAct", ServerValue.TIMESTAMP);
                    String id=mAth.getCurrentUser().getUid();
                    mdataBase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful())
                            {
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else
                {
                    Toast.makeText(MainActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAth.getCurrentUser()!=null)
        {
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            finish();
        }
    }
}