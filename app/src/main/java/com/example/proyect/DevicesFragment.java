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
import com.google.firebase.database.ValueEventListener;

public class DevicesFragment extends Fragment {
    private View mVistaLista;
    private RecyclerView mListaDevices;
    private DatabaseReference mDevicesRef,userRef;
    private FirebaseAuth mAuth;
    private String usuarioActual;
    private FloatingActionButton mbtnfloadd;

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
        mAuth=FirebaseAuth.getInstance();
        usuarioActual=mAuth.getCurrentUser().getUid();
        mDevicesRef= FirebaseDatabase.getInstance().getReference().child("Usuarios");

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
        FirebaseRecyclerOptions options= new FirebaseRecyclerOptions.Builder<Usuarios>().setQuery(mDevicesRef,Usuarios.class).build();
        FirebaseRecyclerAdapter<Usuarios,UsuariosVista>adapter=new FirebaseRecyclerAdapter<Usuarios, UsuariosVista>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsuariosVista usuariosVista, int i, @NonNull Usuarios usuarios)
            {
                String userId=getRef(i).getKey();
                mDevicesRef.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String profileNombre=snapshot.child("nombre").getValue().toString();
                            String profileCorreo=snapshot.child("correo").getValue().toString();
                            String profileCompani=snapshot.child("compani").getValue().toString();
                            usuariosVista.tvnombre.setText(profileNombre);
                            usuariosVista.tvcorreo.setText(profileCorreo);
                            usuariosVista.tvcompani.setText(profileCompani);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public UsuariosVista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewlista,parent,false);
                UsuariosVista viewHolder=new UsuariosVista(view);
                return viewHolder;
            }
        };
        mListaDevices.setAdapter(adapter);
        adapter.startListening();
    }
    public static  class UsuariosVista extends RecyclerView.ViewHolder
    {
        TextView tvnombre,tvcorreo,tvcompani;
        public UsuariosVista(@NonNull View itemView) {
            super(itemView);
            tvnombre=itemView.findViewById(R.id.tvlvNombre);
            tvcorreo=itemView.findViewById(R.id.tvlvCorreo);
            tvcompani=itemView.findViewById(R.id.tvlvCompani);
        }
    }
}