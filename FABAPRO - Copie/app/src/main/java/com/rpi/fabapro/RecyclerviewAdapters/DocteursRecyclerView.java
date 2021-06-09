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
import com.rpi.fabapro.Activities.DetailsDocteurs;
import com.rpi.fabapro.Activities.Join;
import com.rpi.fabapro.Pojos.professionnels;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class DocteursRecyclerView extends RecyclerView.Adapter<DocteursRecyclerView.Holder> {

    private Context mContext;
    private ArrayList<professionnels> prof;

    public DocteursRecyclerView(Context mContext, ArrayList<professionnels> prof) {
        this.mContext = mContext;
        this.prof = prof;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.b2c_list_view,viewGroup,false);
        DocteursRecyclerView.Holder holder= new DocteursRecyclerView.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        Glide.with(mContext)
                .asBitmap()
                .load(prof.get(i).getImageurl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.Image);
        holder.ImageName.setText(prof.get(i).getName());
        holder.Proffesion.setText(prof.get(i).getProfession());
        holder.Disponibilite.setText(prof.get(i).getDisponibilite());
        holder.uid.setText(prof.get(i).getId());

        if(holder.ImageName.getText().equals("Rejoindre l'equipe")){
            holder.ParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, Join.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mContext.startActivity(intent);
                }
            });
        }else{
            holder.ParentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(holder.Disponibilite.getText().equals("Indisponible")){
                       Toast.makeText(mContext,"Non Disponible! Re-essayer plutard.",Toast.LENGTH_SHORT).show();
                   }else {
                       Intent intent = new Intent(mContext, DetailsDocteurs.class);
                       intent.putExtra("pkey", prof.get(i).getId());
                       intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                       mContext.startActivity(intent);
                   }
                }
            });}
    }

    @Override
    public int getItemCount() {
        return prof.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView Image;
        TextView ImageName,Proffesion,Disponibilite,uid;
        LinearLayout ParentLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            Image= itemView.findViewById(R.id.image);
            ImageName=itemView.findViewById(R.id.image_name);
            Proffesion=itemView.findViewById(R.id.profession);
            Disponibilite=itemView.findViewById(R.id.disponibilite);
            ParentLayout=itemView.findViewById(R.id.parent);
            uid=itemView.findViewById(R.id.getkey);

        }
}
}
