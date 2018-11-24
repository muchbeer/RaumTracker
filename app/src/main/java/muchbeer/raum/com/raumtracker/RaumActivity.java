package muchbeer.raum.com.raumtracker;

import android.content.ContentUris;
import android.content.ContentValues;

import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import muchbeer.raum.com.raumtracker.data.RaumContract;
import muchbeer.raum.com.raumtracker.data.RaumDbHelper;
import muchbeer.raum.com.raumtracker.utilitiy.MyAdapter;
import muchbeer.raum.com.raumtracker.utilitiy.RaumData;

/**
 * Created by muchbeer on 21/11/2018.
 */

public class RaumActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int RAUM_LOADER = 0;
    RecyclerView rv;
    MyAdapter mAdapter;


    RaumDbHelper dbHelper = new RaumDbHelper(this);
    private SQLiteDatabase mDatabase;

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raum);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = dbHelper.getWritableDatabase();
        //recycler
        rv= (RecyclerView) findViewById(R.id.recyclerView);

        //SET PROPS
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setItemAnimator(new DefaultItemAnimator());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertRaum();
          /*      Cursor cursorRead =
                        getContentResolver().query(RaumContract.RaumEntry.CONTENT_URI,
                                new String[]{RaumContract.RaumEntry._ID},
                                null,
                                null,
                                null);
                if (cursorRead.getCount() == 0){
                  //  insertData();
                    insertRaum();
                }*/

               /* Intent intent = new Intent(RaumActivity.this, EditorActivity.class);
                startActivity(intent);*/
            }
        });


        mAdapter = new MyAdapter(this, null);
        rv.setAdapter(mAdapter);
        getLoaderManager().initLoader(RAUM_LOADER, null, RaumActivity.this );

    }

    private void insertRaum() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's Raum attributes are the values.
        ContentValues values = new ContentValues();
        values.put(RaumContract.RaumEntry.COLUMN_COORDINATE, "get");
       // values.put(RaumContract.RaumEntry.COLUMN_DATE, "");
        values.put(RaumContract.RaumEntry.COLUMN_DAT, 74);
        values.put(RaumContract.RaumEntry.COLUMN_STREET_NAME, "Victorial place");

       getContentResolver().insert(RaumContract.RaumEntry.CONTENT_URI, values);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertRaum();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                getContentResolver().delete(RaumContract.RaumEntry.CONTENT_URI, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                RaumContract.RaumEntry._ID,
                RaumContract.RaumEntry.COLUMN_COORDINATE,
                RaumContract.RaumEntry.COLUMN_DATE

        };
        return new CursorLoader(this,
                RaumContract.RaumEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursorData) {

        mAdapter.swapCursor(cursorData);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
       // mCursorAdapter.swapCursor(null);
        mAdapter.swapCursor(null);
    }

    //This is without COntent provider
    public Cursor retriewCoordinateAll() {

        return mDatabase.query(
                RaumContract.RaumEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                RaumContract.RaumEntry.COLUMN_DATE + " DESC");
    }

    //This is with Content Provider

    }


