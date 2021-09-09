package com.example.proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyect.clases.Devices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddDeviceActivity extends AppCompatActivity {
    private ImageButton mbtnBack,mbtnAdd;
    private Button Delete;
    //Datos para agregar dispositivos
    private EditText mNserie,mNombre,mPassd,mPassdR;
    //datos para guardar en firebase
    private FirebaseAuth mAuth ;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    //recuperando datos generales del constructor
    Devices d1=new Devices();
    //recibir datos de btnactulizar (pruebas)
    private String nSerieUpdate;
    private TextView mtvSerie;
    public int actividadbandera;

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

        //recibir datos de btnactulizar
        nSerieUpdate=getIntent().getStringExtra("deviceId");
        mtvSerie=findViewById(R.id.tvidNserie);
        mtvSerie.setText(nSerieUpdate);
        //si es bandera==2->actualizar obtener datos
        if(actividadbandera==2)
        {
            mDatabaseRef.child("Devices").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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
                if(actividadbandera==1)
                {
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
                }else if(actividadbandera==2)
                {

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
    private void actulizarDatos()
    {
        Map<String,Object> deviceMap=new HashMap<>();
        deviceMap.put("fechaAct",new Date().getTime());
        deviceMap.put("nombre",mNombre.getText().toString());
        deviceMap.put("pass",mPassd);
        mDatabaseRef.child("Devices").child("nSerie").child(nSerieUpdate).updateChildren(deviceMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddDeviceActivity.this, "Los datos se actualizaron correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddDeviceActivity.this, "Hubo un error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void EliminarDevice()
    {
        Map<String,Object> deviceMap=new HashMap<>();
        deviceMap.put("estado",0);
        deviceMap.put("fechaAct",new Date().getTime());
        deviceMap.put("nombre","sn");
        deviceMap.put("pass","password8822");
        deviceMap.put("uidUsuario","sn");
        mDatabaseRef.child("Devices").child("nSerie").child(nSerieUpdate).updateChildren(deviceMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddDeviceActivity.this, "Dispositivo eliminado", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddDeviceActivity.this, "Hubo un error al eliminar el dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}