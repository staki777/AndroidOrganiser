package com.example.user.drugsorganiser.ViewModel;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.drugsorganiser.R;

import static android.app.Activity.RESULT_OK;

public class ContactPersonFragment extends Fragment {

    private static final int PICK_CONTACT = 1;

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
        getPermissionToReadUserContacts();
    }

    @TargetApi(23)
    public void getPermissionToReadUserContacts() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},PICK_CONTACT);
        }
    }

    // Callback with the request from calling requestPermissions(...)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PICK_CONTACT) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        nameTextView = (TextView) getView().findViewById(R.id.name_contact);
        phoneTextView = (TextView) getView().findViewById(R.id.phone_contact);
        callButton = (Button) getView().findViewById(R.id.call_contact);
        changeButton = (Button) getView().findViewById(R.id.change_contact);

        nameTextView.setText("Contact person");
        phoneTextView.setText("Phone number");

        callButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phoneTextView.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "Phone number must be specified", Toast.LENGTH_SHORT).show();
                }
                //TODO: Not finished
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phoneTextView.getText().toString().trim()));

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //grantUriPermission();
                Intent pickIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pickIntent, PICK_CONTACT);
            }});
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO: Not finished
        if(requestCode == PICK_CONTACT){
            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor cursor =  getActivity().getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();

                String name = "", number = "";
                String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                if (hasPhone.equals("1")) {
                    Cursor phones = getActivity().getContentResolver().query
                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        number = phones.getString(phones.getColumnIndex
                                (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-+() ]", "");
                        //name =  phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                    }
                    phones.close();
                } else {
                    Toast.makeText(getActivity(), "This contact has no phone number", Toast.LENGTH_LONG).show();
                }

                number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //name =  cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Nickname.NAME));

                nameTextView.setText(name);
                phoneTextView.setText(number);

                cursor.close();

                Toast.makeText(getActivity(), "New contact person has been selected.", Toast.LENGTH_SHORT).show();
            }
            else
                phoneTextView.setText("niee");

        }
    }
}
