package com.tutor93.tugasfrensky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 26;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, "Employee_DB.sqlite", null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        createTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        resetDatabase();
        createTable();
    }

    private void createTable() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, EmployeEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetDatabase() {
        try {
            TableUtils.dropTable(connectionSource, EmployeEntity.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz)
            throws SQLException {
        return super.getDao(clazz);
    }

    @Override
    public void close() {
        super.close();

    }


}