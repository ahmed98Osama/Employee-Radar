package com.empRadar.ui.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcountDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcountDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View view,returnView;
    private static TextView fullName, emailId, mobileNumber, location, department, id;
    Button signout;

    public FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    public UserCureent userCureent;
    public DatabaseReference databaseReference;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef;
    public String nameGet, emailGet,  mobileGet ,  locationGet ,  departmentGet ,  idGet, uriGet ;
    public Uri photoUrlGet ;
    ImageView user_profile;


    public AcountDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcountDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static AcountDetails newInstance(String param1, String param2) {
        AcountDetails fragment = new AcountDetails();
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
        getCurrentUser();
        returnView = inflater.inflate(R.layout.fragment_acount_details, container, false);
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
            String email = firebaseUser.getEmail();
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
                     emailGet = firebaseUser.getEmail();

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    userCureent = dataSnapshot.getValue(UserCureent.class);
                    departmentGet = userCureent.getDepartment();
                    locationGet = userCureent.getLocation();
                    mobileGet = userCureent.getPhone();
                    uriGet = userCureent.getUri();
                    photoUrlGet = Uri.parse(uriGet);
                    Log.d("urlxxxxxxxxxxxxxxx", "Value is: " + photoUrlGet);
                    Log.d("urloooooooooooooooo", "Value is: " + userCureent.getUri());
                    //Log.d("urllAD", "Value is: " + photoUrl.toString());
                    //Toast.makeText(getActivity(), photoUrlGet.toString(),Toast.LENGTH_LONG).show();
                    initViews();

                  if (isAdded())
                  {
                      Glide.with(getActivity())
                              .load(photoUrlGet)
                              .diskCacheStrategy(DiskCacheStrategy.NONE)
                              .skipMemoryCache(true)
                              .placeholder(R.drawable.person_24)
                              .into(user_profile);

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
        fullName = (TextView) returnView.findViewById(R.id.fullNameAC);
        fullName.setText("Name : " + nameGet);
        emailId = (TextView) returnView.findViewById(R.id.userEmailIdAC);
        emailId.setText("Email : " + emailGet);
        mobileNumber = (TextView) returnView.findViewById(R.id.mobileNumberAC);
        mobileNumber.setText("Mobile : " + mobileGet);
        location = (TextView) returnView.findViewById(R.id.locationAC);
        location.setText("Location : " + locationGet);
        department = (TextView) returnView.findViewById(R.id.departmentAC);
        department.setText("Department : " + departmentGet);
        id = (TextView) returnView.findViewById(R.id.userIDAC);
        id.setText("Id : " + idGet);
        user_profile = (ImageView) returnView.findViewById(R.id.add_picAC);
        signout = (Button)returnView.findViewById(R.id.LogOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(getActivity(),"Logged out!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

    }


}