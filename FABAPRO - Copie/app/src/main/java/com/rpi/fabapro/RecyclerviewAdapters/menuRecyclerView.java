package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rpi.fabapro.Pojos.menulist;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class menuRecyclerView extends RecyclerView.Adapter<menuRecyclerView.viewHolder>{


    private ArrayList<menulist> mImages;
    private Context mContext;


    public menuRecyclerView(ArrayList<menulist> mImages, Context mContext) {
        this.mImages = mImages;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_list_view,viewGroup,false);
        menuRecyclerView.viewHolder holder= new menuRecyclerView.viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(i).getImageUrl())
                .apply(RequestOptions.centerInsideTransform())
                .into(viewHolder.image);
        viewHolder.t1.setText(mImages.get(i).getTitre());
        viewHolder.t2.setText(mImages.get(i).getCuisson());
        viewHolder.t3.setText(mImages.get(i).getPlatprix());
        viewHolder.t4.setText(mImages.get(i).getTempslivraison());
        viewHolder.t5.setText(mImages.get(i).getPrixlivraison());
        viewHolder.t6.setText(mImages.get(i).getDescription());
        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Calmez vous le stress n'est pas bon pour la sante",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }


    class viewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView t1,t2,t3,t4,t5,t6;
        LinearLayout parent;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3= itemView.findViewById(R.id.t3);
            t4=itemView.findViewById(R.id.t4);
            t5=itemView.findViewById(R.id.t5);
            t6=itemView.findViewById(R.id.t6);
            image=itemView.findViewById(R.id.pnpaccueilrecyclerphoto);
            parent=itemView.findViewById(R.id.parent);
        }
    }
}
