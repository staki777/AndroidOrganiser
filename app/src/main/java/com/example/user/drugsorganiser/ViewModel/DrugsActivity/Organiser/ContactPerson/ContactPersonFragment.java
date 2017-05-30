package com.example.user.drugsorganiser.ViewModel.DrugsActivity.Organiser.ContactPerson;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.example.user.drugsorganiser.ViewModel.DrugsActivity.BaseDrugsActivityFragment;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import static android.app.Activity.RESULT_OK;

public class ContactPersonFragment extends BaseDrugsActivityFragment {

    private static final int CONTACT_PERSON_PERMISSIONS = 1;
    private static final int PICK_CONTACT = 2;

    private TextView nameTextView, phoneTextView;
    private Button callButton, changeButton;

    private Intent callIntent;
    private Intent pickIntent;

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

    public void getPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS},CONTACT_PERSON_PERMISSIONS);
        }else {
            Log.i(LogTag(),"Read Contacts, send SMS and Call permissions granted");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LogTag(), "OnStart");
        getActivity().setTitle(getView().getResources().getString(R.string.contact_person));

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

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LogTag(), "OnResume");
        if (activity().getUser().contactName == null || activity().getUser().contactName.isEmpty()){
            Toast.makeText(getActivity(), getActivity().getString(R.string.lack_of_contact_person), Toast.LENGTH_SHORT).show();

        }
        else {
            nameTextView.setText(activity().getUser().contactName);
            phoneTextView.setText(activity().getUser().contactNumber);
        }
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
            activity().getUser().contactNumber = cursor.getString(numberColumnIndex);

            int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            activity().getUser().contactName = cursor.getString(nameColumnIndex);

            try{
                final Dao<User, Integer> userDao = activity().getHelper().getUserDao();
                userDao.update(activity().getUser());
            }catch (SQLException e){
                e.printStackTrace();
            }

            cursor.close();
            Toast.makeText(getActivity(), "New contact person has been selected.", Toast.LENGTH_SHORT).show();
        }
    }
}
