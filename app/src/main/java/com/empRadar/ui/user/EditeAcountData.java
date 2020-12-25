package com.empRadar.ui.user;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.empRadar.R;
import com.empRadar.ui.User;
import com.empRadar.ui.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditeAcountData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditeAcountData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static View view,returnView;
    private static EditText fullName, emailId, mobileNumber, location, id,password;
    public Button update;
    UserCureentEA cureentEA;

    public Uri image_uri,generatedFilePathURI;
    public FirebaseAuth mAuth;
    public FirebaseUser firebaseUser;
    public UserCureent userCureent;
    public DatabaseReference databaseReference;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef;
    public String nameGet, emailGet,  mobileGet ,  locationGet ,  departmentGet ,  idGet, uriGet , passwordGet;
    public Uri photoUrlGet ;
    public ImageView user_profile;
    public UserProfileChangeRequest profileUpdates;
    public StorageReference storageReference;
    public EditeAcountData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditeAcountData.
     */
    // TODO: Rename and change types and number of parameters
    public static EditeAcountData newInstance(String param1, String param2) {
        EditeAcountData fragment = new EditeAcountData();
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
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        returnView = inflater.inflate(R.layout.fragment_edite_acount_data, container, false);

        getCurrentUser();
        initViews();


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
            Log.d("urlAuthname",name);
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
                    photoUrlGet = firebaseUser.getPhotoUrl();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    userCureent = dataSnapshot.getValue(UserCureent.class);
                    departmentGet = userCureent.getDepartment();
                    locationGet = userCureent.getLocation();
                    mobileGet = userCureent.getPhone();
                    nameGet=userCureent.getName();
                    uriGet = userCureent.getUri();
                    Log.d("urllA", "Value is: " + userCureent.getUri());
                    //Log.d("urllAD", "Value is: " + photoUrl.toString());
                  //  Toast.makeText(getActivity(), userCureent.getUri(),Toast.LENGTH_LONG).show();

                    initViews();

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
        fullName = (EditText) returnView.findViewById(R.id.fullNameEA);
        fullName.setText(nameGet);

        //emailId = (EditText) returnView.findViewById(R.id.userEmailIdEA);
        //emailId.setText( emailGet);


        mobileNumber = (EditText) returnView.findViewById(R.id.mobileNumberEA);
        mobileNumber.setText(mobileGet);


        location = (EditText) returnView.findViewById(R.id.locationEA);
        location.setText(locationGet);

        //password = (EditText) returnView.findViewById(R.id.passwordEA);


        //department = (EditText) returnView.findViewById(R.id.departmentEA);
        //department.setText("Department : " + departmentGet);
        //id = (EditText) returnView.findViewById(R.id.userIDAC);
        //id.setText("Id : " + idGet);
        update = (Button) returnView.findViewById(R.id.updatebtn);
        user_profile = (ImageView) returnView.findViewById(R.id.add_picEA);

        if (isAdded()){
            Glide.with(getActivity())
                    .load(uriGet)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.person_24)
                    .into(user_profile);
        }





        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectProfilePic();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(),"Puuuuuuuuuuuuuuu.",Toast.LENGTH_SHORT);
                Log.d("urlTEST","sssssssssssssssss");
                //uploadImage();
                updateData();
            }
        });


    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
         nameGet = fullName.getText().toString();
         emailGet = emailId.getText().toString();
         mobileGet = mobileNumber.getText().toString();
         locationGet = location.getText().toString();
         passwordGet = password.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(emailGet);

        // Check if all strings are null or not
        if (nameGet.equals("") || nameGet.length() == 0
                || emailGet.equals("") || emailGet.length() == 0
                || mobileGet.equals("") || mobileGet.length() == 0
                || locationGet.equals("") || locationGet.length() == 0
                || passwordGet.equals("") || passwordGet.length() == 0
                ) {
            Toast.makeText(getActivity(),"All fields are required.",Toast.LENGTH_SHORT);

        }

        // Check if email id valid or not
        else if (!m.find()) {
            Toast.makeText(getActivity(),"Your Email Id is Invalid.",Toast.LENGTH_SHORT);
        }
        // Check if pass > 6
        else if (passwordGet.length() < 6) {
            Toast.makeText(getActivity(),"Password should be at least 6 characters.",Toast.LENGTH_SHORT);

        }

        // Else do signup or do your stuff
        else {

           // updateData();
            //createAccount(getEmailId, getPassword);


        }
    }

    public void updateData(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        nameGet=fullName.getText().toString();
       // emailGet=emailId.getText().toString();
        mobileGet=mobileNumber.getText().toString();
        locationGet=location.getText().toString();
        //passwordGet =password.getText().toString();

        Toast.makeText(getActivity(),"Puuuuuuuuuuuuuuu.",Toast.LENGTH_SHORT);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(idGet);


        //myRef.child("email").setValue(emailGet);
        myRef.child("location").setValue(locationGet);
        myRef.child("phone").setValue(mobileGet);
        //myRef.child("pass").setValue(passwordGet);
        myRef.child("name").setValue(nameGet);
        Log.d("urlTEST","fffffffffffff");
        fullName.setText(nameGet);
        profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameGet)
                .build();
        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"User profile data updated." ,Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }


    /*-------For sending verification email on user's registered email------*/
    private void verifyEmailRequest() {
        firebaseUser.sendEmailVerification()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Email verification sent on\n" + emailId.getText().toString(), Toast.LENGTH_LONG).show();
                            //mAuth.signOut();
                            //startActivity(new Intent(getActivity(),MainActivity.class));
                        } else {
                            Toast.makeText(getActivity(), "Sign up Success But Failed to send verification email.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /*-------- Below Code is for selecting image from galary or camera -----------*/

    private void SelectProfilePic() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                                getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission, 1000);
                        } else {
                            openCamera();
                        }
                    } else {
                        openCamera();
                    }
                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }
            }
        });
        builder.show();

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(takePictureIntent, 1);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    //permisiion from pop up was denied.
                    Toast.makeText(getActivity(), "Permission Denied...", Toast.LENGTH_LONG).show();
                }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    //user_profile.setImageURI(image_uri);
                    Glide.with(getActivity())
                            .load(image_uri)
                            //.centerCrop()
                            //.placeholder(R.drawable.person_24)
                            .into(user_profile);
                    uploadImage();
                    //Toast.makeText(getActivity(),"case 1 " ,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //data.getData returns the content URI for the selected Image
                    image_uri = data.getData();
                    //user_profile.setImageURI(image_uri);
                    Glide.with(getActivity())
                            .load(image_uri)
                            //.centerCrop()
                            //.placeholder(R.drawable.person_24)
                            .into(user_profile);
                    uploadImage();
                    //Toast.makeText(getActivity(),"case 2 " ,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    ///////////////////save image url in realtime database /////////////
    public void imageToRealFirebase() {



        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users").child(idGet);
        myRef.child("uri").setValue(uriGet);
    }

    // UploadImage method
    public void uploadImage() {
        if (image_uri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference ref
                    = storageReference
                    .child("images/").child(idGet + "/UserImage");

            // adding listeners on upload
            // or failure of image
            generatedFilePathURI = image_uri;
            Log.d("urlll1",generatedFilePathURI + "");
            UploadTask uploadTask = ref.putFile(image_uri);
            ref.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(
                        UploadTask.TaskSnapshot taskSnapshot) {
                    // Image uploaded successfully
                    // Dismiss dialog
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Now play with downloadPhotoUrl
                            //Store data into Firebase Realtime Database
                            generatedFilePathURI=uri;
                            Log.d("urlll",generatedFilePathURI + "");
                            imageToRealFirebase();
                            profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(image_uri)
                                    .build();
                            firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(),"User profile Image updated." ,Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });



                        }
                    });
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getActivity(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }

}