package muchbeer.raum.com.raumtracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import muchbeer.raum.com.raumtracker.data.RaumContract.RaumEntry;

/**
 * Created by muchbeer on 21/11/2018.
 */

public class RaumDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = RaumDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "raumtrack.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 6;

    /**
     * Constructs a new instance of {@link RaumDbHelper}.
     *
     * @param context of the app
     */
    public RaumDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_TRACK_TABLE =  "CREATE TABLE " + RaumEntry.TABLE_NAME + " ("
                + RaumEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RaumEntry.COLUMN_COORDINATE + " TEXT NOT NULL, "
                + RaumEntry.COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + RaumEntry.COLUMN_DAT + " INTEGER NOT NULL, "
                + RaumEntry.COLUMN_STREET_NAME + " TEXT" +

                ");";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRACK_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.

        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + RaumEntry.TABLE_NAME);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                RaumEntry.TABLE_NAME + "'");

        // re-create database
        //Please learn how to append column name to the column instead of dropping
     //   db.execSQL("DROP TABLE IF EXISTS " + RaumEntry.TABLE_NAME);
        onCreate(db);
    }


}