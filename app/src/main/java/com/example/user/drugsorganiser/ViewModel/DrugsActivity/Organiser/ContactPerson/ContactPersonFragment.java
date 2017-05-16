package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.ContactPerson;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.DrugsActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import static android.app.Activity.RESULT_OK;

public class ContactPersonFragment extends Fragment {

    private static final int PICK_CONTACT = 1;
    private static final int CALL = 2;

    private TextView nameTextView, phoneTextView;
    private String name = "Contact person", number = "Phone number";
    private Button callButton, changeButton;

    private Intent callIntent;
    private Intent pickIntent;

    private User user;

    public ContactPersonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_person, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermissions();
    }

    @TargetApi(23)
    public void getPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},PICK_CONTACT);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL);
        }
    }

//    // Callback with the request from calling requestPermissions(...)
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        if (requestCode == PICK_CONTACT) {
//            if (grantResults.length == 1 &&
//                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getActivity(), "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getView().getResources().getString(R.string.contact_person));
        user = ((DrugsActivity)getActivity()).getUser();

        nameTextView = (TextView) getView().findViewById(R.id.name_contact);
        phoneTextView = (TextView) getView().findViewById(R.id.phone_contact);
        callButton = (Button) getView().findViewById(R.id.call_contact);
        changeButton = (Button) getView().findViewById(R.id.change_contact);

        callButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phoneTextView.getText().toString().replace("[(- )]",""))) {
                    Toast.makeText(getActivity(), "Phone number must be specified", Toast.LENGTH_SHORT).show();
                }

                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phoneTextView.getText().toString().replace("[(+-)]","")));

                startActivity(callIntent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //grantUriPermission();
                Uri uri = Uri.parse("content://contacts");
                pickIntent = new Intent(Intent.ACTION_PICK, uri);
                pickIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickIntent, PICK_CONTACT);
            }});
        nameTextView.setText(user.contactName);
        phoneTextView.setText(user.contactNumber);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_CONTACT && resultCode == RESULT_OK){
            Uri uri = data.getData();
            String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

            Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            user.contactNumber = cursor.getString(numberColumnIndex);

            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            user.contactName = cursor.getString(nameColumnIndex);

            try{
                final Dao<User, Integer> userDao = ((DrugsActivity)getActivity()).getHelper().getUserDao();
                userDao.update(user);
            }catch (SQLException e){
                e.printStackTrace();
            }
            onResume();

            cursor.close();
            Toast.makeText(getActivity(), "New contact person has been selected.", Toast.LENGTH_SHORT).show();
        }
    }
}
