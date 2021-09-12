package com.example.proyect;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyect.adapters.DevicesAdapter;
import com.example.proyect.clases.Devices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private Button mbtnDelete,mcambiarPrecio;
    //Datos para agregar dispositivos
    private TextView mSN,matencion;
    private EditText mNombre,metPrecio,mPass1,mPass2;
    //datos para guardar en firebase
    private FirebaseAuth mAuth ;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    //recuperando datos generales del constructor
    Devices d1=new Devices();
    //recibir datos de btnactulizar (pruebas)
    private String nSerieUpdate,passAntiguo;

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
        matencion=findViewById(R.id.autvMensajeAtencion);
        mbtnupPass=findViewById(R.id.aubtnUppass);//boton para desglosar elos edit text cambio de pass
        mPass1=findViewById(R.id.auetuppass);
        mPass2=findViewById(R.id.auetuppass2);
        mbtnDelete=findViewById(R.id.aubtnDelete);

        //instanciar firebase
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mDatabaseRef=FirebaseDatabase.getInstance().getReference();
        //recibir datos de btnactulizar
        nSerieUpdate=getIntent().getStringExtra("deviceId");
        obtenerDatosdevice();
        mbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateDeviceActivity.this,HomeActivity.class));
                finish();
            }
        });

        mbtnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nSerie0=mSN.getText().toString();
                String compani0=mspCompani.getSelectedItem().toString();
                String nombre0=mNombre.getText().toString();
                String precio0=metPrecio.getText().toString();
                String pass0=mPass1.getText().toString();
                String pass01=mPass2.getText().toString();

                if(mPass1.getVisibility()==View.GONE&&mPass2.getVisibility()==View.GONE)
                {
                    if(nSerie0.equals("")||nombre0.equals("")||precio0.equals(""))
                    {
                        validacion1();

                    }else
                    {
                        Map<String,Object> deviceMap=new HashMap<>();
                        deviceMap.put("compani",compani0);
                        deviceMap.put("fechaAct",ServerValue.TIMESTAMP);
                        deviceMap.put("nombre",nombre0);
                        deviceMap.put("preciokw",Double.parseDouble(precio0));
                        mDatabaseRef.child("Devices").child(nSerieUpdate).updateChildren(deviceMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                LimpiarCajas();
                                Toast.makeText(UpdateDeviceActivity.this, "Los datos se actualizaron correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateDeviceActivity.this,HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateDeviceActivity.this, "Hubo un error al actualizar los datos", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else
                {
                    if(nSerie0.equals("")||nombre0.equals("")||precio0.equals("")||!pass0.equals(passAntiguo)||pass01.equals(""))
                    {
                        validacion2();

                    }else
                    {
                        Map<String,Object> deviceMap=new HashMap<>();
                        deviceMap.put("compani",compani0);
                        deviceMap.put("fechaAct",ServerValue.TIMESTAMP);
                        deviceMap.put("nombre",nombre0);
                        deviceMap.put("preciokw",Double.parseDouble(precio0));
                        deviceMap.put("pass",pass01);
                        mDatabaseRef.child("Devices").child(nSerieUpdate).updateChildren(deviceMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                LimpiarCajas();
                                Toast.makeText(UpdateDeviceActivity.this, "Los datos se actualizaron correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UpdateDeviceActivity.this,HomeActivity.class));
                                finish();
                            }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UpdateDeviceActivity.this, "Hubo un error al actualizar los datos", Toast.LENGTH_SHORT).show();
                                }
                            });

                    }
                }

            }
        });
        mbtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarDevice();
            }
        });
        mcambiarPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.aubtnPrecio:
                        if(metPrecio.isEnabled()==FALSE)
                        {
                            metPrecio.setEnabled(true);
                            matencion.setVisibility(View.VISIBLE);
                        }else
                        {
                            metPrecio.setEnabled(false);
                            matencion.setVisibility(View.GONE);
                        }
                }
            }
        });
        mbtnupPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.aubtnUppass:
                        if(mPass1.getVisibility()==View.GONE&&mPass2.getVisibility()==View.GONE)
                        {
                            mPass1.setVisibility(View.VISIBLE);
                            mPass2.setVisibility(View.VISIBLE);
                        }else
                        {
                            mPass1.setVisibility(View.GONE);
                            mPass2.setVisibility(View.GONE);
                        }
                }
            }
        });
    }

    private void eliminarDevice()
    {
        Map<String,Object> deviceMap=new HashMap<>();
        deviceMap.put("compani","noCompani");
        deviceMap.put("corriente",0);
        deviceMap.put("estado",0);
        deviceMap.put("fechaAct",ServerValue.TIMESTAMP);
        deviceMap.put("nombre","noNombre");
        deviceMap.put("pass","noPassword");
        deviceMap.put("preciokw",0);
        deviceMap.put("uidUsuario","noUsuario");
        mDatabaseRef.child("Devices").child(nSerieUpdate).updateChildren(deviceMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(UpdateDeviceActivity.this, "Dispositivo eliminado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateDeviceActivity.this,HomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateDeviceActivity.this, "Hubo un error al eliminar el dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LimpiarCajas() {
        mSN.setText("");
        mNombre.setText("");
        metPrecio.setText("");
        mPass1.setText("");
        mPass2.setText("");
    }

    private void validacion1() {
        String nombre1=mNombre.getText().toString();
        String precio1=metPrecio.getText().toString();

        if(nombre1.equals(""))
        {
            mNombre.setError("Campo requerdido");
        }else if(precio1.equals(""))
        {
            metPrecio.setError("Campo requerdido");
        }
    }
    private void validacion2() {
        String nombre1=mNombre.getText().toString();
        String precio1=metPrecio.getText().toString();
        String pass1=mPass1.getText().toString();
        String pass2=mPass2.getText().toString();
        if(nombre1.equals(""))
        {
            mNombre.setError("Campo requerdido");
        }else if(precio1.equals(""))
        {
            metPrecio.setError("Campo requerdido");
        }else if(!pass1.equals(passAntiguo))
        {
            mPass1.setError("Contraseña incorrecta");
        }else if(pass2.equals(""))
        {
            mPass2.setError("Contraseña requerida");
        }
    }
    private void obtenerDatosdevice()
    {
        mDatabaseRef.child("Devices").child(nSerieUpdate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {

                    String compani=snapshot.child("compani").getValue().toString();
                    String nSerie=snapshot.child("nSerie").getValue().toString();
                    String nombre=snapshot.child("nombre").getValue().toString();
                    passAntiguo=snapshot.child("pass").getValue().toString();
                    double preciokw=Double.parseDouble(snapshot.child("preciokw").getValue().toString());
                    mSN.setText(nSerie);
                    mNombre.setText(nombre);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(UpdateDeviceActivity.this, R.array.companias, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mspCompani.setAdapter(adapter);
                    if (compani != null) {
                        int spinnerPosition = adapter.getPosition(compani);
                        mspCompani.setSelection(spinnerPosition);
                    }
                    metPrecio.setText(String.format("%.2f",preciokw));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void actulizarDevice()
    {

    }
}