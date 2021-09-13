package com.example.proyect.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyect.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class PanelFragment extends Fragment {

    public PanelFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_panel, container, false);
        GraphView graph = (GraphView)v.findViewById(R.id.graph);
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
                new DataPoint(18, 20),
                new DataPoint(19, 20),
                new DataPoint(20, 20),
                new DataPoint(21, 20),
                new DataPoint(22, 20),
                new DataPoint(23, 20)

        });
        graph.addSeries(series);
        series.setColor(Color.WHITE);
        return v;
    }
}