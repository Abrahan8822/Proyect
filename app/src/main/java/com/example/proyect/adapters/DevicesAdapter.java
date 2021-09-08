package com.example.proyect.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyect.AddDeviceActivity;
import com.example.proyect.R;
import com.example.proyect.clases.Devices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.core.SnapshotHolder;

import java.util.ArrayList;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DevicesViewHolder> {
    ArrayList<Devices> listaDevices;
    Activity activity;
    public DevicesAdapter(ArrayList<Devices> listaDevices, Activity activity){
        this.listaDevices=listaDevices;
        this.activity=activity;
    }

    @Override
    public DevicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewlista,null,false);
        return new DevicesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DevicesViewHolder holder, int position){


        holder.txtNombre.setText(listaDevices.get(position).getNombre());
        holder.txtNserie.setText(listaDevices.get(position).getnSerie());
        final String idNserie=listaDevices.get(position).getnSerie();
        holder.mbtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, AddDeviceActivity.class);
                intent.putExtra("deviceId",idNserie);
                activity.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {return listaDevices.size();}


    public class DevicesViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtNserie;
        ImageButton mbtnEditar;
        public DevicesViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.tvlvNombre);
            txtNserie = itemView.findViewById(R.id.tvlvNserie);
            mbtnEditar = itemView.findViewById(R.id.btnlvmore);
        }
    }

}
