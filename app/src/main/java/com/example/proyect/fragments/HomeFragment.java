package com.example.proyect.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.proyect.R;
import com.example.proyect.clases.Devices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    //private FirebaseDatabase mdataBase;
    private DatabaseReference mdatabase,mdatabase1,mdataRef;
    private TextView mtvValue;
    private FirebaseAuth mAuth ;

    private Spinner mspDevice;
    private ValueEventListener eventListener;
    String serieSeleccionado="";
    String nombreSeleccionado;
    int e=0;
    //sector Graphi
    private TextView mprecio;
    LineGraphSeries<DataPoint> series1;
    private double x,y,precio;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        mspDevice=v.findViewById(R.id.spfhDevices);
        mtvValue=v.findViewById(R.id.tvfhValue);
        mprecio=v.findViewById(R.id.fptvPrecio);
        mdatabase=FirebaseDatabase.getInstance().getReference();
        GraphView graph=v.findViewById(R.id.fpgraph);
        mAuth=FirebaseAuth.getInstance();
        mdatabase1=FirebaseDatabase.getInstance().getReference("Consumos");
        //metodo para cargar datos
        loadDevices();
        //sector graphi

        x=0;
        precio=0;
        series1 =new LineGraphSeries<>();

        Query query=mdatabase1.child("4F6SyZD9").orderByChild("fechaHora").startAt(1631160000).endAt(1631246399);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren())
                {
                    double consumo=Double.parseDouble(ds.child("consumo").getValue().toString());
                    y=consumo*1000;
                    precio=precio+consumo;
                    series1.appendData(new DataPoint(x,y),true,25);
                    x=x+1;
                }
                precio=precio*0.82;//colocar el precio trayendo el dato de device precio
                mprecio.setText("Bs"+String.format("%.2f",precio));
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(0);
                graph.getViewport().setMaxX(23);
                series1.setColor(Color.GREEN);
                series1.setDrawDataPoints(true);
                series1.setDataPointsRadius(11);
                series1.setThickness(8);
                graph.addSeries(series1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " W";
                }
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
                    mspDevice.setAdapter(arrayAdapter);
                    mspDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if(i==0&&e==0)
                            {
                                serieSeleccionado=devices.get(i).getnSerie();
                                nombreSeleccionado=devices.get(i).getNombre();
                                
                                //mdataRef.removeEventListener(eventListener);
                                loadCorriente(serieSeleccionado);
                            }else
                            {
                                serieSeleccionado=devices.get(i).getnSerie();
                                nombreSeleccionado=devices.get(i).getNombre();

                                mdataRef.removeEventListener(eventListener);
                                loadCorriente(serieSeleccionado);
                                e=e+i;
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