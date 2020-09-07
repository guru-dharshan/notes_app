package com.example.notesapp;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class noteadapter extends RecyclerView.Adapter<noteadapter.MyViewHolder> {
        private Context context;
        private List<note> notearray;


        public noteadapter( Context context,  List<note> notearray){
            this.context=context;
            this.notearray=notearray;
        }




        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater mInflater= LayoutInflater.from(context);
            View view=mInflater.inflate(R.layout.notecard,parent,false);
            return new MyViewHolder(view);
        }

   

    @Override
        public void onBindViewHolder(@NonNull noteadapter.MyViewHolder holder, final int position) {
            try {



                holder.title.setText(notearray.get(position).getTitle());
                holder.desc.setText(notearray.get(position).getDesc());



            }

            catch (Exception e){
                Log.i("error",e.toString());
            }


        }

        @Override
        public int getItemCount() {
            return notearray.size();
        }
        public static class MyViewHolder extends RecyclerView.ViewHolder{


            TextView title,desc;

            public MyViewHolder(View itemView) {
                super(itemView);

                title=itemView.findViewById(R.id.notetitle);
                desc=itemView.findViewById(R.id.notedesc);


            }
        }
    }


