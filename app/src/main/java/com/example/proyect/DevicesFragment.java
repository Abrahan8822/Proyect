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

public class DevicesFragment extends Fragment {
    private View mVistaLista;
    private RecyclerView mListaDevices;
    private DatabaseReference mDevicesRef,userRef;
    private FirebaseAuth mAuth;
    private String usuarioActual;
    private FloatingActionButton mbtnfloadd;
    private TextView mtitulo;

    public DevicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mVistaLista=inflater.inflate(R.layout.fragment_devices, container, false);
        mListaDevices=mVistaLista.findViewById(R.id.rvListaDevices);
        mListaDevices.setLayoutManager(new LinearLayoutManager(getContext()));
        mbtnfloadd=mVistaLista.findViewById(R.id.floatingbtnadd);
        mtitulo=mVistaLista.findViewById(R.id.tvfdtitulo);
        mAuth=FirebaseAuth.getInstance();
        usuarioActual=mAuth.getCurrentUser().getUid();
        mDevicesRef= FirebaseDatabase.getInstance().getReference("Devices");
        mbtnfloadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddDeviceActivity.class);
                startActivity(intent);
            }
        });
        return mVistaLista;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options= new FirebaseRecyclerOptions.Builder<Devices>().setQuery(mDevicesRef,Devices.class).build();
        String idUser=mAuth.getCurrentUser().getUid();
        FirebaseRecyclerAdapter<Devices,DevicesVista>adapter=new FirebaseRecyclerAdapter<Devices, DevicesVista>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DevicesVista devicesVista, int i, @NonNull Devices devices)
            {
                String serieId=getRef(i).getKey();
                //Query query = myRef.child("cities").orderByChild("zone").equalTo(zona_deseada);
                // query.addListenerForSingleValueEvent(new ValueEventListener() {
                //mDevicesRef.child(userId).addValueEventListener(new ValueEventListener() {
                //Query query = mDevicesRef.orderByChild("uidUsuario").equalTo(idUser);
                //Query query = mDevicesRef.child("Devices").orderByChild("uidUsuario").equalTo(idUser);
                //Query query = mDevicesRef.orderByChild("uidUsuario").equalTo(idUser);

                mDevicesRef.child(serieId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                                String profileSerie=snapshot.child("nSerie").getValue().toString();
                                String profileNombre=snapshot.child("nombre").getValue().toString();
                                String profilesuario=snapshot.child("uidUsuario").getValue().toString();
                                devicesVista.tvnSerie.setText(profileSerie);
                                devicesVista.tvnombre.setText(profileNombre);
                                devicesVista.tvuidUsuario.setText(profilesuario);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public DevicesVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewlista,parent,false);
                DevicesVista  viewHolder=new DevicesVista (view);
                return viewHolder;
            }
        };
        mListaDevices.setAdapter(adapter);
        adapter.startListening();
    }
    public static  class DevicesVista extends RecyclerView.ViewHolder
    {
        TextView tvnombre,tvnSerie,tvuidUsuario;
        public DevicesVista (@NonNull View itemView) {
            super(itemView);
            tvnSerie=itemView.findViewById(R.id.tvlvnSerie);
            tvnombre=itemView.findViewById(R.id.tvlvNombre);
            tvuidUsuario=itemView.findViewById(R.id.tvlvuidUsuario);
        }
    }
}