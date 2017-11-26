package br.edu.ifsp.sbv.desafiodolook.dao;

/**
 * Created by Adriel on 11/20/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO <T extends Object> extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sgcp.sqlite3";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERINFO = "userInfo";

    protected Context context;
    protected String[] campos;
    protected String tableName;


    private static final String CREATE_TABLE_USERINFO = "CREATE TABLE userInfo ( "
            + " userInfoID Int NOT NULL,"
            + " email Varchar(100) NOT NULL,"
            + " password Varchar(32),"
            + " userName Varchar(20) NOT NULL,"
            + " nickName Varchar(255),"
            + " description Varchar(255),"
            + " urlAvatar Varchar(1000),"
            + " dateBirth Datetime,"
            + " deviceID Varchar(100) NOT NULL,"
            + " status Bit(1) NOT NULL,"
            + " dateCreation Datetime,"
            + " PRIMARY KEY (userInfoID));";

    public DAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(CREATE_TABLE_USERINFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERINFO);

        onCreate(db);
    }

    protected void closeDatabase(SQLiteDatabase db)
    {
        if(db.isOpen())
            db.close();
    }

}