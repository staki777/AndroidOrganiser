package com.example.user.drugsorganiser.DataBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.user.drugsorganiser.Model.ConstantIntervalDose;
import com.example.user.drugsorganiser.Model.RegularDose;
import com.example.user.drugsorganiser.Model.Drug;
import com.example.user.drugsorganiser.Model.CustomDose;
import com.example.user.drugsorganiser.Model.RegistryDose;
import com.example.user.drugsorganiser.Model.User;
import com.example.user.drugsorganiser.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by user on 2017-04-14.
 */



public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "drugorganiser.db";
    private static final int DATABASE_VERSION = 8;

    private Dao<User, Integer> userDao;
    private Dao<Drug, Integer> drugDao;
    private Dao<RegistryDose, Integer> registryDao;
    private Dao<RegularDose, Integer> regularDoseDao;
    private Dao<ConstantIntervalDose, Integer> constantIntervalDoseDao;
    private Dao<CustomDose, Integer> customDoseDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {

            // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Drug.class);
            TableUtils.createTable(connectionSource, RegistryDose.class);
            TableUtils.createTable(connectionSource, RegularDose.class);
            TableUtils.createTable(connectionSource, ConstantIntervalDose.class);
            TableUtils.createTable(connectionSource, CustomDose.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {

            // In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked
            //automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
            // existing database etc.

            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Drug.class, true);
            TableUtils.dropTable(connectionSource, RegistryDose.class, true);
            TableUtils.dropTable(connectionSource, RegularDose.class, true);
            TableUtils.dropTable(connectionSource, ConstantIntervalDose.class, true);
            TableUtils.dropTable(connectionSource, CustomDose.class, true);
            onCreate(sqliteDatabase, connectionSource);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs

    public Dao<Drug, Integer> getDrugDao() throws SQLException {
        if (drugDao == null) {
            drugDao = getDao(Drug.class);
        }
        return drugDao;
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public Dao<RegistryDose, Integer> getRegistryDao() throws SQLException {
        if (registryDao == null) {
            registryDao = getDao(RegistryDose.class);
        }
        return registryDao;
    }

    public Dao<CustomDose, Integer> getCustomDoseDao() throws SQLException {
        if (customDoseDao == null)
            customDoseDao = getDao(CustomDose.class);
        return customDoseDao;
    }

    public Dao<RegularDose, Integer> getRegularDoseDao() throws SQLException {
        if (regularDoseDao == null)
            regularDoseDao = getDao(RegularDose.class);
        return regularDoseDao;
    }

    public Dao<ConstantIntervalDose, Integer> getConstantIntervalDoseDao() throws SQLException {
        if (constantIntervalDoseDao == null)
            constantIntervalDoseDao = getDao(ConstantIntervalDose.class);
        return constantIntervalDoseDao;
    }
}


