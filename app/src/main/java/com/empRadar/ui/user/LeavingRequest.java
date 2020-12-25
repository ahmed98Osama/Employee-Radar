package com.empRadar.ui.user;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.empRadar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeavingRequest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeavingRequest extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View view,returnView;
    public String nameGet, messageStatusST , idGet ;
    public EditText messageEditText;
    public Button sendMessage;
    public TextView messageStatus;
    public DatabaseReference databaseReference;
    public String status;
    public FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    public UserCheckInOut userCheckInOut;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef;
    public UserRequest userRequest;
    public String message;

    public LeavingRequest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeavingRequest.
     */
    // TODO: Rename and change types and number of parameters
    public static LeavingRequest newInstance(String param1, String param2) {
        LeavingRequest fragment = new LeavingRequest();
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
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        returnView = inflater.inflate(R.layout.fragment_leaving_request, container, false);
        getCurrentUser();
        return returnView;

    }

    public void getCurrentUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            ProgressDialog progressDialog
                    = new ProgressDialog(getActivity());
            progressDialog.setTitle("GET Information...");

            progressDialog.show();

            idGet = firebaseUser.getUid();

            myRef = database.getReference("Requests").child(idGet);

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Requests").child(idGet);
            //myRef.child("status").setValue("null");
            myRef.child("name").setValue(nameGet);
            myRef.child("id").setValue(idGet);
            //myRef.child("message").setValue(" ");



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

            myRef = database.getReference("Requests").child(idGet);


            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // Name, email address, and profile photo Url
                    nameGet = firebaseUser.getDisplayName();
                    //emailGet = firebaseUser.getEmail();

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    userRequest = dataSnapshot.getValue(UserRequest.class);
                    status = userRequest.getStatus();
                    messageStatusST = userRequest.getStatus();
                    Log.d("urlRequest",userRequest.getStatus() + " ");

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

        messageEditText = (EditText) returnView.findViewById(R.id.messageTxt);
        sendMessage = (Button) returnView.findViewById(R.id.msgBtn);
        messageStatus = (TextView) returnView.findViewById(R.id.requestStatus);

        if (messageStatusST==null && isAdded()){
            messageStatus.setText("null");
            messageStatus.setTextColor(Color.GRAY);
        }
        else if (messageStatusST.equals("1") && isAdded())
        {
            messageStatus.setText("Watting");
            messageStatus.setTextColor(Color.YELLOW);

        }
        else if (messageStatusST.equals("2") && isAdded())
        {
            messageStatus.setText("Aceepted");
            messageStatus.setTextColor(Color.GREEN);

        }
        else if (messageStatusST.equals("3") && isAdded())
        {
            messageStatus.setText("refused");
            messageStatus.setTextColor(Color.RED);

        }

        if (isAdded()){
            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    message = messageEditText.getText().toString();
                    database = FirebaseDatabase.getInstance();
                    myRef = database.getReference("Requests").child(idGet);
                    myRef.child("status").setValue("1");
                    myRef.child("name").setValue(nameGet);
                    myRef.child("id").setValue(idGet);
                    myRef.child("message").setValue(message);

                    Toast.makeText(getActivity(), "message is sent to the admin", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }



}

