package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class showImageRecycler extends RecyclerView.Adapter<showImageRecycler.holder>{
    private ArrayList<String> list;
    private Context mcontext;
    public showImageRecycler(ArrayList<String> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewphoto,viewGroup,false);
        showImageRecycler.holder holder= new showImageRecycler.holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        Glide.with(mcontext)
                .asBitmap()
                .load(list.get(i))
                .apply(RequestOptions.centerInsideTransform())
                .into(holder.i1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class holder extends RecyclerView.ViewHolder {
        private ImageView i1;
        public holder(@NonNull View itemView) {
            super(itemView);
            i1=itemView.findViewById(R.id.showimage);
        }
    }

}
