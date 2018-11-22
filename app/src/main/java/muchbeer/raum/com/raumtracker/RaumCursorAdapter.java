package muchbeer.raum.com.raumtracker;

/**
 * Created by muchbeer on 21/11/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static muchbeer.raum.com.raumtracker.data.RaumContract.RaumEntry.COLUMN_DATE;
import static muchbeer.raum.com.raumtracker.data.RaumContract.RaumEntry.COLUMN_COORDINATE;

/**
 * {@link RaumCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class RaumCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link RaumCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public RaumCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.coordinate_point);
        TextView tvSummary = (TextView) view.findViewById(R.id.date_time);

        String coordinate_track = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COORDINATE));
        String coordinate_date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));

        tvName.setText(coordinate_track);
        tvSummary.setText(coordinate_date);
    }
}
