package muchbeer.raum.com.raumtracker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by muchbeer on 21/11/2018.
 */

public class RaumProvider extends ContentProvider {

    public static final String LOG_TAG = RaumProvider.class.getSimpleName();

    /** Database helper object */
    private RaumDbHelper mDbHelper;

    public static final int RAUMTRACKER = 100;
    public static final int TRACKER_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(RaumContract.CONTENT_AUTHORITY, RaumContract.PATH_LOCATION, RAUMTRACKER);
        sUriMatcher.addURI(RaumContract.CONTENT_AUTHORITY, RaumContract.PATH_LOCATION + "/#", TRACKER_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new RaumDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case RAUMTRACKER:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table
                cursor = database.query(RaumContract.RaumEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TRACKER_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = RaumContract.RaumEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(RaumContract.RaumEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Send notifications for every listener
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RAUMTRACKER:
                return RaumContract.RaumEntry.CONTENT_LIST_TYPE;
            case TRACKER_ID:
                return RaumContract.RaumEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RAUMTRACKER:
                Uri u = insertPet(uri, contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                return u;
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private void validate(ContentValues values) {
        String coordinate_name = values.getAsString(RaumContract.RaumEntry.COLUMN_COORDINATE);
        if (coordinate_name == null) {
            throw new IllegalArgumentException("Coordinate required");
        }
        String column_date = values.getAsString(RaumContract.RaumEntry.COLUMN_DATE);
        /*if (column_date == null) {
            throw new IllegalArgumentException("Date requires ");
        }*/
        Integer column_dat = values.getAsInteger(RaumContract.RaumEntry.COLUMN_DAT);
        if (column_dat == null) {
            throw new IllegalArgumentException("Dat requires a gender");
        }
        String street_name = values.getAsString(RaumContract.RaumEntry.COLUMN_STREET_NAME);
        if (street_name == null) {
            throw new IllegalArgumentException("street name requires ");
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertPet(Uri uri, ContentValues values) {

        // Sanity check
        validate(values);

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(RaumContract.RaumEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsUpdates;
        switch (match) {
            case RAUMTRACKER:
                // Delete all rows that match the selection and selection args
                rowsUpdates = database.delete(RaumContract.RaumEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TRACKER_ID:
                // Delete a single row given by the ID in the URI
                selection = RaumContract.RaumEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdates = database.delete(RaumContract.RaumEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsUpdates != 0) {
            // Notify listeners about changes
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdates;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case RAUMTRACKER:
                return updatePet(uri, contentValues, selection, selectionArgs);
            case TRACKER_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = RaumContract.RaumEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                int rowsUpdated = updatePet(uri, contentValues, selection, selectionArgs);
                if (rowsUpdated != 0) {
                    // Notify listeners about changes
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsUpdated;
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Update the pets with the given values
        int rows = database.update(RaumContract.RaumEntry.TABLE_NAME, values, selection, selectionArgs);
        // If the rows is -1, then the update failed. Log an error and return null.
        if (rows == -1) {
            Log.e(LOG_TAG, "Failed to update " + uri);
            return 0;
        }

        return rows;
    }
}
