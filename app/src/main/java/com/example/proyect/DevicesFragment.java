package com.example.proyect;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
        mbtnfloadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddDeviceActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }


    public void loadDevices()
    {
        String uidUsuario=mAuth.getCurrentUser().getUid();
        final List<Devices> devices=new ArrayList<>();
        mdatabase.child("Devices").orderByChild("uidUsuario").equalTo(uidUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}