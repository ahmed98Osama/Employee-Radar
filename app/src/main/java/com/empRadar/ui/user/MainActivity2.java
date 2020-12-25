package com.empRadar.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.empRadar.R;

import androidx.fragment.app.Fragment;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity2 extends AppCompatActivity  {

    //private MeowBottomNavigation meowBottomNavigation;
    final static int ID_ACOUNTDETAILS =1;
    final static int ID_EDITDATA =4;
    final static int ID_CHECKINOUT =2;
    final static int ID_LEAVEREQUEST =3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        BottomNavigationView bottomNav;
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //myRef.setValue("Hello, World!");

        /*
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new AcountDetails()).commit();
        }

         */


        getSupportFragmentManager().beginTransaction().replace(R.id.container,new AcountDetails()).commit();



        /*
        meowBottomNavigation = findViewById(R.id.bottomNav);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_REQUESTS,R.drawable.edit_profile));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_EDITDATA,R.drawable.ic_baseline_edit_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_EMPLOYEES,R.drawable.ic_baseline_message));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATIONS,R.drawable.ic_baseline_check));




        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {


            }


        });

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {

                    case ID_REQUESTS:
                        fragment = new AcountDetails();
                        break;

                    case ID_EMPLOYEES:
                        fragment = new CheckInAndOut();
                        break;

                    case ID_NOTIFICATIONS:
                        fragment = new LeavingRequest();
                        break;

                    case ID_EDITDATA:
                        fragment = new EditeAcountData();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //  Toast.makeText(MainActivity.this, "reselected item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

         */

    }
    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.information:
                    fragment = new AcountDetails();
                    break;
                case R.id.Check:
                    fragment = new CheckInAndOut();
                    break;
                case R.id.request:
                    fragment = new LeavingRequest();
                    break;
                case R.id.updatebtn:
                    fragment = new EditeAcountData();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    fragment).commit();
            return true;
        }
    };


}
