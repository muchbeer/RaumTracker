package muchbeer.raum.com.raumtracker;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import muchbeer.raum.com.raumtracker.data.RaumContract;

/**
 * Created by muchbeer on 21/11/2018.
 */

public class RaumActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int RAUM_LOADER = 0;

    private RaumCursorAdapter mCursorAdapter;
    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raum);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRaum();
               /* Intent intent = new Intent(RaumActivity.this, EditorActivity.class);
                startActivity(intent);*/
            }
        });

        // Find the ListView which will be populated with the raum data
        ListView raumListView = (ListView) findViewById(R.id.list_view);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        raumListView.setEmptyView(emptyView);

        raumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               /* Intent intent = new Intent(getBaseContext(), EditorActivity.class);
                intent.setData(ContentUris.withAppendedId(RaumContract.RaumEntry.CONTENT_URI, id));
                startActivity(intent);*/

                Toast.makeText(getApplicationContext(),"Now you are successful made it", Toast.LENGTH_LONG).show();
            }
        });

        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        ListView listView = (ListView) findViewById(R.id.list_view);
        mCursorAdapter = new RaumCursorAdapter(this, null);
        listView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(RAUM_LOADER, null, RaumActivity.this );

    }

    private void insertRaum() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's Raum attributes are the values.
        ContentValues values = new ContentValues();
        values.put(RaumContract.RaumEntry.COLUMN_COORDINATE, "get");
        values.put(RaumContract.RaumEntry.COLUMN_DATE, "ds");
        values.put(RaumContract.RaumEntry.COLUMN_DAT, 74);
        values.put(RaumContract.RaumEntry.COLUMN_STREET_NAME, "Victorial place");

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link RaumEntry#CONTENT_URI} to indicate that we want to insert
        // into the raum database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(RaumContract.RaumEntry.CONTENT_URI, values);
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
                RaumContract.RaumEntry.COLUMN_DATE,
                RaumContract.RaumEntry.COLUMN_DAT,
                RaumContract.RaumEntry.COLUMN_STREET_NAME
        };
        return new CursorLoader(this, RaumContract.RaumEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursorData) {
        mCursorAdapter.changeCursor(cursorData);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }


}
