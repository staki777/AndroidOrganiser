package com.example.user.drugsorganiser.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by user on 2017-04-14.
 */
@DatabaseTable
public class User implements Serializable//, Parcelable
{

    private static final long serialVersionUID = -222864131214757024L;
    public static final String ID_FIELD = "user_id";

    public static final String LOGIN_COLUMN = "user_login";
    public static final String PASSWORD_COLUMN = "user_password";
    public static final String MAIL_COLUMN = "user_mail";
    public static final String CONTACT_NAME_COLUMN = "contact_name";
    public static final String CONTACT_NUMBER_COLUMN = "contact_number";



    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int userId;

    @DatabaseField(columnName = LOGIN_COLUMN)
    public  String login;

    //TODO: must be encrypted later
    @DatabaseField(columnName = PASSWORD_COLUMN)
    public String password;

    @DatabaseField(columnName = MAIL_COLUMN)
    public String mail;

    @DatabaseField(columnName = CONTACT_NAME_COLUMN)
    public String contactName;

    @DatabaseField(columnName = CONTACT_NUMBER_COLUMN)
    public String contactNumber;

    @ForeignCollectionField(eager = true)
    public Collection<Drug> drugs = new ArrayList<Drug>();

    public User(){} //default constructor required

    public User(String login, String password, String mail) {
        this.login = login;
        this.password = password;
        this.mail = mail;
    }
//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeSerializable(this);
//    }
//    public static final Parcelable.Creator<User> CREATOR
//            = new Parcelable.Creator<User>() {
//        public User createFromParcel(Parcel in) {
//            return new User(in);
//        }
//
//        public User[] newArray(int size) {
//            return new User[size];
//        }
//    };
//
//    /** recreate object from parcel */
//    private User(Parcel in) {
//        User u = (User) in.readSerializable();
//        this.userId = u.userId;
//        this.drugs = u.drugs;
//        this.login = u.login;
//        this.password = u.password;
//        this.contactName = u.contactName;
//        this.contactNumber = u.contactName;
//        this.mail = u.mail;
//    }



}