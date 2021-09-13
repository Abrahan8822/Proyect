package com.example.proyect.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyect.MainActivity;
import com.example.proyect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    private Button mbtnsignOut;
    private TextView mtvnombre;
    private TextView mtvcorreo;
    private FirebaseAuth mAuth;
    private DatabaseReference mdataBase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        mbtnsignOut=v.findViewById(R.id.btnfpsignOut);
        mtvnombre=v.findViewById(R.id.tvfpNombre);
        mtvcorreo=v.findViewById(R.id.tvfpCorreo);
        mAuth=FirebaseAuth.getInstance();
        mdataBase= FirebaseDatabase.getInstance().getReference();

        mbtnsignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                //para enviar datos a otra activity en el intent
                getActivity().finish();
                startActivity(intent);
            }
        });
        getUserInfo();
        return v;

    }
    private void getUserInfo()
    {
        String id=mAuth.getCurrentUser().getUid();
        mdataBase.child(("Usuarios")).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String name=snapshot.child("nombre").getValue().toString();
                    String correo=snapshot.child("correo").getValue().toString();
                    mtvnombre.setText(name);
                    mtvcorreo.setText(correo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}