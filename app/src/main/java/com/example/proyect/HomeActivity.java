package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.proyect.clases.Devices;
import com.example.proyect.fragments.DevicesFragment;
import com.example.proyect.fragments.HomeFragment;
import com.example.proyect.fragments.PanelFragment;
import com.example.proyect.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {
    //inicilizar variable
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //asignar variable
        bottomNavigation=findViewById(R.id.bottom_navigation);
        //añadir menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        //bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_panel));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_devices));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_profile));
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //iniciar fragment
                Fragment fragment=null;
                //check condicion
                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    /*case 2:
                        fragment = new PanelFragment();
                        break;*/
                    case 2:
                        fragment = new DevicesFragment();
                        break;
                    case 3:
                        fragment = new ProfileFragment();
                        break;
                }
                //cargar fragment
                loadFragment(fragment);
            }
        });

        bottomNavigation.setCount(1,"10");
        //estblecer home como fragment inicial
        bottomNavigation.show(2,true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You clicked "+item.getId(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }
}