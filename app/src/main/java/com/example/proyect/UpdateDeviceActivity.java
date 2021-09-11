package com.example.proyect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyect.adapters.DevicesAdapter;
import com.example.proyect.clases.Devices;
import com.google.android.gms.tasks.OnFailureListener;
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

public class UpdateDeviceActivity extends AppCompatActivity {
    private ImageButton mbtnBack,mbtnupdate,mbtnupPass;
    Spinner mspCompani;
    private Button Delete,mcambiarPrecio;
    //Datos para agregar dispositivos
    private TextView mSN;
    private EditText mNombre,metPrecio,mPass1,mPassd2;
    //datos para guardar en firebase
    private FirebaseAuth mAuth ;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    //recuperando datos generales del constructor
    Devices d1=new Devices();
    //recibir datos de btnactulizar (pruebas)
    private String nSerieUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_device);

        mbtnBack=findViewById(R.id.auimbtntnback);
        mbtnupdate=findViewById(R.id.aubtnUpdate);
        mSN=findViewById(R.id.autvsn);
        mNombre=findViewById(R.id.auetNombre);
        mspCompani=findViewById(R.id.auspinnerCompani);
        metPrecio=findViewById(R.id.auetPrecio);
        mcambiarPrecio=findViewById(R.id.aubtnPrecio);
        mbtnupPass=findViewById(R.id.aubtnUppass);//boton para desglosar elos edit text cambio de pass
        mPass1=findViewById(R.id.auetuppass);
        mPassd2=findViewById(R.id.auetuppass2);

        //instanciar firebase
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference();
        //recibir datos de btnactulizar
        nSerieUpdate=getIntent().getStringExtra("deviceId");

        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateDeviceActivity.this,HomeActivity.class));
                finish();
            }
        });

    }
    /*mbtnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nSerie=nSerieUpdate;
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
        });*/


    /*private void llenarLista() {
        String uidUsuario=mAuth.getCurrentUser().getUid();
        mdatabase.child("Devices").orderByChild("nSerie").equalTo(nSerieUpdate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    //listaDevices.clear();

                        String nSerie=snapshot.child("nSerie").getValue().toString();
                        String nombre=snapshot.child("nombre").getValue().toString();

                        listaDevices.add(new Devices(nSerie,nombre));

                    madapater=new DevicesAdapter(listaDevices,getActivity());
                    mrvDevices.setAdapter(madapater);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






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
    }*/
    private void LimpiarCajas() {
        mSN.setText("");
        mNombre.setText("");
        mPass1.setText("");
        mPassd2.setText("");
    }

    private void validacion() {
        String nombre=mNombre.getText().toString();
        String pass1=mPass1.getText().toString();
        String passs2=mPassd2.getText().toString();
        if(nombre.equals(""))
        {
            mNombre.setError("Campo requerdido");
        }else if(pass1.equals(""))
        {
            mPass1.setError("Campo requerdido");
        }else if(!passs2.equals(pass1))
        {
            mPassd2.setError("La contraseña debe coincidir");
        }
    }
    private void actulizarDatosBasicos()
    {
        Map<String,Object> deviceMap=new HashMap<>();
        deviceMap.put("nombre",mNombre.getText().toString());
        deviceMap.put("pass",mPassd2);
        deviceMap.put("compani",mspCompani);
        deviceMap.put("precio",metPrecio);
        deviceMap.put("fechaAct",new Date().getTime());

        mDatabaseRef.child("Devices").child("nSerie").child(nSerieUpdate).updateChildren(deviceMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateDeviceActivity.this, "Los datos se actualizaron correctamente", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateDeviceActivity.this, "Hubo un error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}