package com.ralba.infocarapp.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ralba.infocarapp.R;
import com.ralba.infocarapp.models.CarEntity;

import java.util.ArrayList;

public class CarAdapter
        extends RecyclerView.Adapter<CarAdapter.CarViewHolder>
        implements View.OnClickListener {

    private ArrayList<CarEntity> items;
    private View.OnClickListener listener;

    public static class CarViewHolder
            extends RecyclerView.ViewHolder {

        private ImageView ImageView_Car;
        private TextView TextView_Brand;
        private TextView TextView_Model;

        public CarViewHolder(View itemView) {
            super(itemView);
            ImageView_Car = (ImageView) itemView.findViewById(R.id.ImageView_Car);
            TextView_Brand = (TextView) itemView.findViewById(R.id.TextView_Brand);
            TextView_Model = (TextView) itemView.findViewById(R.id.TextView_Model);
        }

        public void CarBind(CarEntity item) {
            if(!item.getImage().equals("")){
                byte[] decodedString= Base64.decode(item.getImage(), Base64.DEFAULT);
                Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ImageView_Car.setImageBitmap(decodedByte);
            }else{
                ImageView_Car.setImageDrawable(MyApplication.getContext().getDrawable(R.drawable.no_image));
            }
            TextView_Brand.setText(item.getBrand());
            TextView_Model.setText(item.getModel());
        }
    }

    public CarAdapter(@NonNull ArrayList<CarEntity> items) {
        this.items = items;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list, parent, false);
        row.setOnClickListener(this);

        CarViewHolder avh = new CarViewHolder(row);
        return avh;
    }

    @Override
    public void onBindViewHolder(CarViewHolder viewHolder, int position) {
        CarEntity item = items.get(position);
        viewHolder.CarBind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

}
