package com.example.notesapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class recipieAdapter extends RecyclerView.Adapter<recipieAdapter.MyViewHolder> {
    private Context context;
    private List<recipiedata> recipielist;


    public recipieAdapter( Context context,  List<recipiedata> recipielist){
        this.context=context;
        this.recipielist=recipielist;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater= LayoutInflater.from(context);
        View view=mInflater.inflate(R.layout.recipie_card,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recipieAdapter.MyViewHolder holder, final int position) {
        try {
           
            Glide.with(context).load(recipielist.get(position).getImage()).into(holder.imgThumbnail);
            holder.title.setText(recipielist.get(position).getTitle());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(recipielist.get(position).getSource()));
                    context.startActivity(i);


                }
            });

        }

        catch (Exception e){

        }
        //
        // holder.imgThumbnail.setImageResource(R.drawable.ic_banner_foreground);

    }

    @Override
    public int getItemCount() {
        return 10;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imgThumbnail;
        TextView title;
        
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);

            imgThumbnail=itemView.findViewById(R.id.recipieimage);
            title=itemView.findViewById(R.id.recipietitle);
            cardView=itemView.findViewById(R.id.recipiecard);


        }
    }
}


