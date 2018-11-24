package muchbeer.raum.com.raumtracker.utilitiy;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import muchbeer.raum.com.raumtracker.R;

import static muchbeer.raum.com.raumtracker.data.RaumContract.RaumEntry.COLUMN_COORDINATE;
import static muchbeer.raum.com.raumtracker.data.RaumContract.RaumEntry.COLUMN_DATE;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    private Context mContext;
    private Cursor mCursor;

    public MyAdapter(Context context, Cursor cursor) {
       mContext = context;
       mCursor = cursor;
    }

    //INITIALIZE VIEWHODER
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view= inflater.inflate(R.layout.raum_list_item, parent, false);

        //HOLDER
        MyHolder holder=new MyHolder(view);

        return holder;
    }

    //BIND VIEW TO DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        if(!mCursor.moveToPosition(position)) {
            return;
        }
      //  holder.img.setImageResource(R.drawable.marker);
        String coordinate_track = mCursor.getString(mCursor.getColumnIndexOrThrow(COLUMN_COORDINATE));
        String coordinate_date = mCursor.getString(mCursor.getColumnIndexOrThrow(COLUMN_DATE));


        holder.coordinate_point.setText(coordinate_track);
        holder.date_time.setText(coordinate_date);

      //  coordinate_date.s


    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    public void swapCursor(Cursor newCursor) {
        if(mCursor!=null) {
            mCursor.close();
        }
        mCursor = newCursor;

        if(newCursor !=null) {
            notifyDataSetChanged();
        }
    }


}