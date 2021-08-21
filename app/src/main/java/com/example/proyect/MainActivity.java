package com.example.proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText mEtnombre;
    private EditText mEtcorreo;
    private EditText mEtpass;
    private Spinner mEtcompani;
    private EditText mEtprecio;
    private Button mbtnRegister;
    //variable de los datos a registar
    private String nombre="";
    private String correo="";
    private String pass="";
    private String company="";
    private double precio=0;
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
        mEtcompani=findViewById(R.id.spinnerCompani);
        mEtprecio=findViewById(R.id.etPrecio);
        mbtnRegister=findViewById(R.id.btnregister);
        mdataBase= FirebaseDatabase.getInstance().getReference();

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre=mEtnombre.getText().toString();
                correo=mEtcorreo.getText().toString();
                pass=mEtpass.getText().toString();
                company=mEtcompani.getSelectedItem().toString();
                precio=Double.parseDouble(mEtprecio.getText().toString());

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
        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this,LoginActivity.class));
                //finish(); el usuario tiene que poder regresar a esta activity
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
                    map.put("compani",company);
                    map.put("preciokw",precio);
                    String id=mAth.getCurrentUser().getUid();
                    mdataBase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful())
                            {

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
}