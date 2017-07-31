package com.example.codebreaker.weatherforecast.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codebreaker.weatherforecast.R;

import java.util.Collections;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolderWeatherData> {

    private final Context context;



    private LayoutInflater layoutInflater;
    List<DATA> data = Collections.emptyList();

    public Adapter (Context context,List<DATA> data)
    {

        layoutInflater = LayoutInflater.from(context);
        this.context = context;
               this.data = data;
    }

    @Override
    public ViewHolderWeatherData onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        /*Toast.makeText(context,"onCreateViewHolder",Toast.LENGTH_SHORT).show();*/

          View view = layoutInflater.inflate(R.layout.data,viewGroup,false);

        ViewHolderWeatherData viewHolderWeatherData= new ViewHolderWeatherData(view);
        return viewHolderWeatherData;
    }

    @Override
    public void onBindViewHolder(ViewHolderWeatherData viewHolder, int position) {

        /*Toast.makeText(context,"onBindViewHolder",Toast.LENGTH_SHORT).show();*/

        DATA current = data.get(position);

        if (position == 0)
        {
            viewHolder.image.setImageResource(current.ItemId);
            viewHolder.textView1.setText(current.string_1);
            viewHolder.textView2.setText(current.string_2 + " mb");
        }
        if (position == 1)
        {
            viewHolder.image.setImageResource(current.ItemId);
            viewHolder.textView1.setText(current.string_1);
            viewHolder.textView2.setText(current.string_2);
        }

        if (position == 2) {
            viewHolder.image.setImageResource(current.ItemId);
            viewHolder.textView1.setText(current.string_1);
            viewHolder.textView2.setText(current.string_2 + " celsius /" + current.max_temp + " celsius");
        }
        if (position == 3)
        {
            viewHolder.image.setImageResource(current.ItemId);
            viewHolder.textView1.setText(current.string_1);
            viewHolder.textView2.setText(current.visibility);
        }



    }

    @Override
    public int getItemCount() {

        /*Toast.makeText(context,"getItemCount",Toast.LENGTH_SHORT).show();*/

        return data.size();
    }

    class ViewHolderWeatherData extends RecyclerView.ViewHolder
    {

        private ImageView image;
        private TextView textView1;
        private TextView textView2;



        public ViewHolderWeatherData(View itemView) {
            super(itemView);

            /*Toast.makeText(context,"ViewHolderWeatherData",Toast.LENGTH_SHORT).show();*/

            image = (ImageView) itemView.findViewById(R.id.image);
            textView1 = (TextView)itemView.findViewById(R.id.textview_1);
            textView2 = (TextView)itemView.findViewById(R.id.textview_2);

         }
    }
}
