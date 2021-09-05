package com.example.proyect.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyect.MainActivity;
import com.example.proyect.R;
import com.example.proyect.clases.Devices;

import java.util.ArrayList;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DevicesViewHolder> {

    ArrayList<Devices> listaDevices;
    public DevicesAdapter(ArrayList<Devices> listaDevices){
        this.listaDevices=listaDevices;
    }

    @Override
    public DevicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewlista,null,false);
        return new DevicesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(DevicesViewHolder holder, int position){

        Devices dev=listaDevices.get(position);
        holder.txtNombre.setText(listaDevices.get(position).getNombre());
        holder.txtNserie.setText(listaDevices.get(position).getnSerie());
        holder.txtoption.setOnClickListener(v->
        {
            PopupMenu popupmenu=new PopupMenu(v.getContext(), holder.txtoption);
            popupmenu.inflate(R.menu.option_menu);
            popupmenu.setOnMenuItemClickListener(menuItem ->
            {
                switch (menuItem.getItemId())
                {
                    case R.id.imMenuEdit:
                        Intent intent=new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("EDITAR",dev);
                        break;
                }
            });
        });

    }
    @Override
    public int getItemCount() {return listaDevices.size();}


    public class DevicesViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtNserie,txtoption;

        public DevicesViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.tvlvNombre);
            txtNserie = itemView.findViewById(R.id.tvlvNserie);
            txtoption = itemView.findViewById(R.id.tvlvOptions);
        }
    }

}
