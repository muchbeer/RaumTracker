package muchbeer.raum.com.raumtracker.utilitiy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import muchbeer.raum.com.raumtracker.R;

public class MyHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    TextView coordinate_point,date_time;

     Context mContext;

    public MyHolder(View itemView) {
        super(itemView);

        coordinate_point= (TextView) itemView.findViewById(R.id.coordinate_point);
        date_time= (TextView) itemView.findViewById(R.id.date_time);
      //  img= (ImageView) itemView.findViewById(R.id.playerImage);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, "This is a good progress", Toast.LENGTH_SHORT).show();
    }
}