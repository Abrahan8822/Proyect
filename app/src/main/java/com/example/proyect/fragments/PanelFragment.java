package com.example.proyect.fragments;

import static java.lang.Math.sin;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.proyect.R;
import com.example.proyect.adapters.DevicesAdapter;
import com.example.proyect.clases.Consumos;
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
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PanelFragment extends Fragment {
    private TextView mprecio;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    LineGraphSeries<DataPoint> series1;
    private double x,y,precio;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_panel, container, false);
        mdatabase=FirebaseDatabase.getInstance().getReference("Consumos");
        mprecio=v.findViewById(R.id.fptvPrecio);

        x=0;
        precio=0;
        GraphView graph=v.findViewById(R.id.fpgraph);
        series1 =new LineGraphSeries<>();


        Query query=mdatabase.child("4F6SyZD9").orderByChild("fechaHora").startAt(1631160000).endAt(1631246399);
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
}



/*
public void  loadCorriente()
    {
        final ArrayList<Consumos> listaConsumo=new ArrayList<>();
        Query query=mdatabase.child("4F6SyZD9").orderByChild("fechaHora").startAt(1631336340).endAt(1631419140);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    double consumo=Double.parseDouble(ds.child("consumo").getValue().toString());
                    String fechaHora=ds.child("fechaHora").getValue().toString();
                    listaConsumo.add(new Consumos(consumo,fechaHora));
                    i++;
                }
                mprecio.setText(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




int numdataPoint=500;
        for(int i=0;i<numdataPoint;i++)
        {
            x=x+0.1;
            y=Math.sin(x);
            series1.appendData(new DataPoint(x,y),true,100);
        }
        graph.addSeries(series1);




LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0,  60),
                new DataPoint(1,  50),
                new DataPoint(2,  70),
                new DataPoint(3,  40),
                new DataPoint(4,  40),
                new DataPoint(5,  60),
                new DataPoint(6,  80),
                new DataPoint(7,  100),
                new DataPoint(8,  80),
                new DataPoint(9,  80),
                new DataPoint(10, 80),
                new DataPoint(11, 80),
                new DataPoint(12, 80),
                new DataPoint(13, 80),
                new DataPoint(14, 80),
                new DataPoint(15, 250),
                new DataPoint(16, 250),
                new DataPoint(17, 100),
                new DataPoint(18, 200),
                new DataPoint(19, 200),
                new DataPoint(20, 200),
                new DataPoint(21, 150),
                new DataPoint(22, 50),
                new DataPoint(23, 20)

        });

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(23);

        series.setColor(Color.GREEN);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(11);
        series.setThickness(8);
        graph.addSeries(series);




 */