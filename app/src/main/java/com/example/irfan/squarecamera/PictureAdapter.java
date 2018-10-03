package com.example.irfan.squarecamera;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {
    ArrayList<Picture> pictureArrayList;
    Context context;

    public PictureAdapter(ArrayList<Picture> pictureArrayList, Context context){
        this.pictureArrayList = pictureArrayList;
        this.context = context;
    }

    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.picture_cardlist, viewGroup, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder pictureViewHolder, int i) {
        final Picture item = pictureArrayList.get(i);
        pictureViewHolder.textViewId.setText(item.getId());
        pictureViewHolder.textViewWaktuMulai.setText(item.getWaktuMulai());
        pictureViewHolder.textViewWaktuSelesai.setText(item.getWaktuSelesai());
        pictureViewHolder.textViewWaktu.setText(item.getWaktu()+" detik");
        pictureViewHolder.deletePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database database = new Database(context);
                database.deletePictures(item.getId());

                pictureArrayList.clear();
                pictureArrayList.addAll(database.getAll());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictureArrayList.size();
    }
    public class PictureViewHolder extends RecyclerView.ViewHolder{
        TextView textViewId;
        TextView textViewWaktu;
        TextView textViewWaktuMulai;
        TextView textViewWaktuSelesai;
        Button deletePicture;

        public PictureViewHolder(View itemView){
            super(itemView);

            textViewId = itemView.findViewById(R.id.idPicture);
            textViewWaktuMulai =itemView.findViewById(R.id.waktuMulai);
            textViewWaktuSelesai=itemView.findViewById(R.id.waktuSelesai);
            textViewWaktu = itemView.findViewById(R.id.waktuUpload);
            deletePicture = itemView.findViewById(R.id.btnDelete);
        }
    }
}
