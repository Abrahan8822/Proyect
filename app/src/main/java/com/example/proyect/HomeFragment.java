package com.example.proyect;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    //private FirebaseDatabase mdataBase;
    private DatabaseReference mdatabase,mdataRef;
    private TextView mtvValue;
    private FirebaseAuth mAuth ;
    private TextView mtvDevice;
    private Spinner mspDevice;
    private ValueEventListener eventListener;
    String serieSeleccionado="";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        mtvDevice=v.findViewById(R.id.tvfhDevices);
        mspDevice=v.findViewById(R.id.spfhDevices);
        mtvValue=v.findViewById(R.id.tvfhValue);
        mdatabase=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        //metodo para cargar datos
        loadDevices();

        String uidUsuario=mAuth.getCurrentUser().getUid();

        return v;
    }

    public void loadDevices(){

        final List<Devices> devices=new ArrayList<>();

        mdatabase.child("Devices").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String uidSerie=ds.child("nSerie").getValue().toString();
                        String nombre=ds.child("nombre").getValue().toString();
                        devices.add(new Devices(uidSerie,nombre));
                    }
                    ArrayAdapter<Devices> arrayAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,devices);
                    mspDevice.setAdapter(arrayAdapter);
                    mspDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i>0)
                            {
                                serieSeleccionado=devices.get(i).getnSerie();
                                mtvDevice.setText("El numero de Serie es :"+serieSeleccionado);
                                mdataRef.removeEventListener(eventListener);
                                loadCorriente(serieSeleccionado);
                            }else
                            {
                                serieSeleccionado=devices.get(i).getnSerie();
                                mtvDevice.setText("El numero de Serie es :"+serieSeleccionado);
                                //mdataRef.removeEventListener(eventListener);
                                loadCorriente(serieSeleccionado);
                            }


                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void  loadCorriente(String Serie)
    {
        mdataRef =FirebaseDatabase.getInstance().getReference().child("Devices/"+Serie);

        eventListener = (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {

                String valor=String.format("%.2f",snapshot1.child("corriente").getValue(float.class));
                mtvValue.setText(valor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdataRef.addValueEventListener(eventListener);
    }
}