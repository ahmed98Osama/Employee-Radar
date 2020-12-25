package com.empRadar.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.empRadar.R;
import com.empRadar.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckInAndOut#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckInAndOut extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static View view,returnView;
    private static TextView nametxt, lastintxt, onlinetxt, lastouttxt, totaltxt;

    private static Button checkInbtn , checkOutbtn;

    public FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    public UserCheckInOut userCheckInOut;
    public DatabaseReference databaseReference;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef;
    public String nameGet, onlineGet,  timeInStGet ,  timeOutStGet ,  totaltimeStGet ,  idGet ;
    public long timeInD,timeInOutD,timeOutD,totaltimeD;
    public Long estimatedServerTimeFirst,estimatedServerTimeSecond,estimatedServerTimeThird;
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
   // public ZoneId z = ZoneId.of( "Egypt" ) ;
    public Date date1,date2,date3;

    LocalTime ltInTime,ltOutTime,ltInOutTime ;




    public CheckInAndOut() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckInAndOut.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckInAndOut newInstance(String param1, String param2) {
        CheckInAndOut fragment = new CheckInAndOut();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        returnView = inflater.inflate(R.layout.fragment_check_in_and_out, container, false);
        getCurrentUser();

        //initViews();
        // Inflate the layout for this fragment

        return returnView;
    }

    public void getCurrentUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            ProgressDialog progressDialog
                    = new ProgressDialog(getActivity());
            progressDialog.setTitle("GET Information...");
            progressDialog.show();



            // Name, email address, and profile photo Url
            String name = firebaseUser.getDisplayName();
            //String email = firebaseUser.getEmail();
            //Uri photoUrl = firebaseUser.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = firebaseUser.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            idGet = firebaseUser.getUid();

            myRef = database.getReference("Users").child(idGet);

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Name, email address, and profile photo Url
                    nameGet = firebaseUser.getDisplayName();
                    //emailGet = firebaseUser.getEmail();

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    userCheckInOut = dataSnapshot.getValue(UserCheckInOut.class);
                    onlineGet= userCheckInOut.getAvailable();
                    Log.d("urlooooooooo", "Value is: " + userCheckInOut.getAvailable());
                    if (userCheckInOut.getTimein() != null){
                        timeInD = userCheckInOut.getTimein().longValue();
                        estimatedServerTimeFirst = timeInD;
                    }else{
                        timeInD = 0 ;
                    }

                    if (userCheckInOut.getTimeout() != null){
                        timeOutD = userCheckInOut.getTimeout().longValue();
                        estimatedServerTimeSecond = timeOutD;
                    }else{
                        timeOutD = 0 ;
                    }

                    if (userCheckInOut.getTimeintimeout() != null){
                        timeInOutD = userCheckInOut.getTimeintimeout().longValue();
                        estimatedServerTimeThird = timeInOutD;
                    }else{
                        timeInOutD = 0 ;
                    }

                    if (userCheckInOut.getTotaltime() != null){
                        totaltimeD = userCheckInOut.getTotaltime().longValue();
                    }else{
                        totaltimeD = 0 ;
                    }
                    if (userCheckInOut.getTimeinSt() != null){
                        timeInStGet = userCheckInOut.getTimeinSt();
                        Log.d("urlrimeinsT1",userCheckInOut.getTimeinSt() +"");
                    }else{
                        timeInStGet = "none" ;
                    }
                    if (userCheckInOut.getTimeoutSt() != null){
                        timeOutStGet = userCheckInOut.getTimeoutSt();
                        Log.d("urlrimeinsT22",userCheckInOut.getTimeoutSt()+"");
                    }else{
                        timeOutStGet = "none" ;
                        Log.d("urlrimeinsT2",userCheckInOut.getTimeoutSt()+"");
                    }
                    if (userCheckInOut.getTimeinoutSt() != null){
                        totaltimeStGet = userCheckInOut.getTimeinoutSt();
                        Log.d("urlrimeinsT3",userCheckInOut.getTimeinoutSt()+"");
                    }else{
                        totaltimeStGet = "none" ;
                        Log.d("urlrimeinsT3",userCheckInOut.getTimeinoutSt()+"");
                    }




                    //Log.d("urlxxxxxxxxxxxxxxx", "Value is: " + photoUrlGet);
                    Log.d("urltotaltimeOutD", "Value is: " + timeInOutD);
                    //Log.d("urllAD", "Value is: " + photoUrl.toString());
                    //Toast.makeText(getActivity(), photoUrlGet.toString(),Toast.LENGTH_LONG).show();
                    initViews();

                    if (isAdded())
                    {

                    }

                    progressDialog.dismiss();


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    progressDialog.dismiss();
                    Log.w("urllOncanelled", "Failed to read value.", error.toException());
                }
            });

        }
    }


    private void initViews() {
        //String name, String email, String mobile , String location , String password , String id , Uri image
        checkInbtn = (Button) returnView.findViewById(R.id.checkInBtn);
        checkOutbtn = (Button) returnView.findViewById(R.id.checkOutBtn);
        nametxt = (TextView) returnView.findViewById(R.id.welcomNameTxt);
        nametxt.setText(nameGet);
        onlinetxt = (TextView) returnView.findViewById(R.id.onlineTxt);
        if (onlineGet==null){
            onlinetxt.setText("null");
            onlinetxt.setTextColor(Color.YELLOW);
            checkOutbtn.setEnabled(false);
        }
        else if (onlineGet.equals("yes") && isAdded())
        {
            onlinetxt.setText("Online");
            onlinetxt.setTextColor(Color.GREEN);
            checkOutbtn.setEnabled(true);
           checkInbtn.setEnabled(false);
        }
        else if (onlineGet.equals("no") && isAdded())
        {
            onlinetxt.setText("Offline");
            onlinetxt.setTextColor(Color.RED);
            checkOutbtn.setEnabled(false);
            checkInbtn.setEnabled(true);
        }

        if (isAdded()) {
            lastintxt = (TextView) returnView.findViewById(R.id.checkInTxt);
            lastintxt.setText( timeInStGet+ "");

            lastouttxt = (TextView) returnView.findViewById(R.id.checkOutTxt);
            lastouttxt.setText(timeOutStGet + "");

            totaltxt = (TextView) returnView.findViewById(R.id.TotaltimeTxt);
            totaltxt.setText(totaltimeStGet + "");



            //checkOutbtn.setEnabled(false);

            checkInbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    checkinToRealFirebase();

                    //checkOutbtn.setEnabled(true);
                    Toast.makeText(getActivity(), "fffffffffffffff", Toast.LENGTH_SHORT);
                    //checkInbtn.setEnabled(false);

                }
            });
            checkOutbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    checkoutToRealFirebase();
                    //checkInbtn.setEnabled(false);
                    //checkOutbtn.setEnabled(false);


                }
            });
        }

    }

    ///////////////////checkin realtime database /////////////
    public void checkinToRealFirebase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(idGet);
        myRef.child("available").setValue("yes");

        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long offset = snapshot.getValue(Long.class);
                 estimatedServerTimeFirst = System.currentTimeMillis()  ;
                 //ltInTime = LocalTime.now( z ).truncatedTo( ChronoUnit.SECONDS ) ;
                 timeInStGet=changeTimeFormat(estimatedServerTimeFirst,date1);
                 Log.d("urlString1",timeInStGet);
                userCheckInOut.setTimeinSt(timeInStGet);
                userCheckInOut.setTimein(estimatedServerTimeFirst.doubleValue());
                myRef = database.getReference("Users").child(idGet);
                myRef.child("timein").setValue(userCheckInOut.getTimein());
                myRef.child("timeinSt").setValue(userCheckInOut.getTimeinSt());

                //Toast.makeText(getActivity(),ltInTime+ "",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("cancel", "Listener was cancelled");
            }
        });



    }
    ///////////////////checkout realtime database /////////////
    public void checkoutToRealFirebase() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(idGet);
        myRef.child("available").setValue("no");

        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long offset = snapshot.getValue(Long.class);
                 estimatedServerTimeSecond= System.currentTimeMillis() + offset;

                timeOutStGet = changeTimeFormat(estimatedServerTimeSecond,date2);
                Log.d("urlString2",timeOutStGet);
                userCheckInOut.setTimeoutSt(timeOutStGet);


                userCheckInOut.setTimeout(estimatedServerTimeSecond.doubleValue());
                myRef = database.getReference("Users").child(idGet);
                myRef.child("timeout").setValue(userCheckInOut.getTimeout());
                myRef.child("timeoutSt").setValue(userCheckInOut.getTimeoutSt());


                Toast.makeText(getActivity(),estimatedServerTimeSecond + "",Toast.LENGTH_LONG).show();
                Log.d("urlTime",estimatedServerTimeSecond + "");


                estimatedServerTimeThird =  estimatedServerTimeSecond - estimatedServerTimeFirst ;

                totaltimeStGet = changeTimeFormat(estimatedServerTimeThird,date3);
                Log.d("urlString3",totaltimeStGet +"");
                userCheckInOut.setTimeinoutSt(totaltimeStGet);

                userCheckInOut.setTimeintimeout(estimatedServerTimeThird.doubleValue());



                myRef = database.getReference("Users").child(idGet);
                myRef.child("timeintimeout").setValue(userCheckInOut.getTimeintimeout());
                estimatedServerTimeThird = timeInOutD;
                myRef.child("timeinoutSt").setValue(userCheckInOut.getTimeinoutSt());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("cancel", "Listener was cancelled");
            }
        });



    }

//////////////change time formate ///////////////////
    public String  changeTimeFormat(long estimatedServerTime ,Date date){
        date = new Date(estimatedServerTime);
        simpleDateFormat.format(date);
        Log.d("urlTime",simpleDateFormat.format(date) + "");
        return simpleDateFormat.format(date);
    }

}
