package com.example.proyect.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.proyect.AddDeviceActivity;
import com.example.proyect.R;
import com.example.proyect.adapters.DevicesAdapter;
import com.example.proyect.clases.Devices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DevicesFragment extends Fragment {
    private View v;
    private FloatingActionButton mbtnfloadd;


//pruebas recycler
    private DevicesAdapter madapater;
    private RecyclerView mrvDevices;
    private ArrayList<Devices>listaDevices=new ArrayList<>();
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;

    public DevicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_devices, container, false);
        mbtnfloadd=v.findViewById(R.id.floatingbtnadd);
        mrvDevices=v.findViewById(R.id.rvListaDevices);
        mdatabase=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

//pruebas recycler

        mrvDevices=v.findViewById(R.id.rvListaDevices);
        mrvDevices.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();





//finpruebas
        mbtnfloadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddDeviceActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

   private void llenarLista() {
       String uidUsuario=mAuth.getCurrentUser().getUid();
       mdatabase.child("Devices").orderByChild("uidUsuario").equalTo(uidUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    listaDevices.clear();
                    for(DataSnapshot ds:snapshot.getChildren())
                    {
                        String nSerie=ds.child("nSerie").getValue().toString();
                        String nombre=ds.child("nombre").getValue().toString();
                         listaDevices.add(new Devices(nSerie,nombre));
                    }
                    madapater=new DevicesAdapter(listaDevices,getActivity());
                    mrvDevices.setAdapter(madapater);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}