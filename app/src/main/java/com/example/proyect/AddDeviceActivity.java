package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyect.clases.Devices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class AddDeviceActivity extends AppCompatActivity {
    private ImageButton mbtnBack;
    private Button mbtnAdd;
    //Datos para agregar dispositivos
    private EditText mNserie,mNombre,mPassd,mPassdR;
    //datos para guardar en firebase
    private FirebaseAuth mAuth ;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    //recuperando datos generales del constructor
    Devices d1=new Devices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        mbtnBack=findViewById(R.id.imggtnback);
        mbtnAdd=findViewById(R.id.btnAddregister);
        mNserie=findViewById(R.id.etaddsn);
        mNombre=findViewById(R.id.etaddNombre);
        mPassd=findViewById(R.id.etAddpass);
        mPassdR=findViewById(R.id.etaddpass2);
        //instanciar firebase
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference();






        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddDeviceActivity.this,HomeActivity.class));
                finish();
            }
        });

        mbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nSerie=mNserie.getText().toString();
                String uidUsuario=mAuth.getCurrentUser().getUid();
                String nombre=mNombre.getText().toString();
                int estado=d1.getEstado();
                double corriente=d1.getCorriente();
                String pass1=mPassd.getText().toString();
                String passs2=mPassdR.getText().toString();
                if(nSerie.equals("")||nombre.equals("")||pass1.equals("")||!passs2.equals(pass1))
                {
                    validacion();

                }else
                {
                            Devices d=new Devices(nSerie,nombre,uidUsuario,pass1,estado,corriente, ServerValue.TIMESTAMP,ServerValue.TIMESTAMP);
                        mDatabaseRef.child("Devices").child(d.getnSerie()).setValue(d);
                    Toast.makeText(AddDeviceActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
                    LimpiarCajas();
                }
            }
        });
    }

    private void LimpiarCajas() {
        mNserie.setText("");
        mNombre.setText("");
        mPassd.setText("");
        mPassdR.setText("");
    }

    private void validacion() {
        String nSerie=mNserie.getText().toString();
        String nombre=mNombre.getText().toString();
        String pass1=mPassd.getText().toString();
        String passs2=mPassdR.getText().toString();
        if(nSerie.equals(""))
        {
            mNserie.setError("Campo requerdido");
        }else if(nombre.equals(""))
        {
            mNombre.setError("Campo requerdido");
        }else if(pass1.equals(""))
        {
            mPassd.setError("Campo requerdido");
        }else if(!passs2.equals(pass1))
        {
            mPassdR.setError("La contraseña debe coincidir");
        }
    }
}