package com.example.proyect;

import static java.lang.Boolean.FALSE;

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

public class AddDeviceActivity extends AppCompatActivity {
    private ImageButton mbtnBack,mbtnAdd;
    private Button mbtnCambiarPrecio;
    private TextView matencion;
    //Datos para agregar dispositivos
    private EditText mNserie,mNombre,mPassd,mPassdR;
    private Spinner mEtcompani;
    private EditText mEtprecio;
    //datos para guardar en firebase
    private FirebaseAuth mAuth ;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    //recuperando datos generales del constructor
    Devices d1=new Devices();
    //recibir datos de btnactulizar (pruebas)




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        mbtnBack=findViewById(R.id.imggtnback);
        mbtnAdd=findViewById(R.id.btnAddregister);
        mNserie=findViewById(R.id.etaddsn);
        mNombre=findViewById(R.id.etaddNombre);
        mEtcompani=findViewById(R.id.spinnerCompani);
        mEtprecio=findViewById(R.id.etPrecio);
        mbtnCambiarPrecio=findViewById(R.id.aaddbtnPrecio);
        matencion=findViewById(R.id.aaddtvMensajeAtencion);
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
        mbtnCambiarPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.aaddbtnPrecio:
                        if(mEtprecio.isEnabled()==FALSE)
                        {
                            mEtprecio.setEnabled(true);
                            matencion.setVisibility(View.VISIBLE);
                        }else
                        {
                            mEtprecio.setEnabled(false);
                            matencion.setVisibility(View.GONE);
                        }
                }
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
                String company=mEtcompani.getSelectedItem().toString();
                double precio=Double.parseDouble(mEtprecio.getText().toString());
                String pass1=mPassd.getText().toString();
                String passs2=mPassdR.getText().toString();


                if(nSerie.equals("")||nombre.equals("")||pass1.equals("")||!passs2.equals(pass1))
                {
                    validacion();

                }else
                {
                    Devices d=new Devices(nSerie,nombre,uidUsuario,pass1,estado,corriente,company,precio, ServerValue.TIMESTAMP,ServerValue.TIMESTAMP);
                    mDatabaseRef.child("Devices").child(d.getnSerie()).setValue(d).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful())
                            {
                                LimpiarCajas();
                                Toast.makeText(AddDeviceActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddDeviceActivity.this,HomeActivity.class));
                                finish();
                            }
                            else
                            {
                                    Toast.makeText(AddDeviceActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }

                            }
                        });
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