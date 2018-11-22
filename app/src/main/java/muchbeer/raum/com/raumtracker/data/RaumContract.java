package muchbeer.raum.com.raumtracker.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by muchbeer on 21/11/2018.
 */

public final class RaumContract {

    public static final String CONTENT_AUTHORITY = "com.raum.muchbeer.coordinate";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_LOCATION = "locations";

    private RaumContract() {
    }

    public static final class RaumEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_LOCATION);
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        public final static String TABLE_NAME = "coordinate_point";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_COORDINATE = "coordinate_number";
        public final static String COLUMN_DATE = "date_coordinated";
        public final static String COLUMN_DAT = "dat_accuracy";
        public final static String COLUMN_STREET_NAME = "street_name";

        // Gender value constants:
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
