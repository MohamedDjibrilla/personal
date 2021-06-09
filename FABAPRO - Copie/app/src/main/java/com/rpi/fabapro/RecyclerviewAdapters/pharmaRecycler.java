package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rpi.fabapro.Pojos.pharma;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class pharmaRecycler extends RecyclerView.Adapter<pharmaRecycler.holder> {


    private ArrayList<pharma> list;
    private Context mContext;

    public pharmaRecycler(ArrayList<pharma> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pharmarecycler_list,viewGroup,false);
        pharmaRecycler.holder holder= new pharmaRecycler.holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        holder.nom.setText(list.get(i).getNom());
        holder.lieu.setText(list.get(i).getLocalite());
        holder.date.setText(list.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class holder extends RecyclerView.ViewHolder{
        TextView nom, date, lieu;
        public holder(@NonNull View itemView) {
            super(itemView);
            nom=itemView.findViewById(R.id.two);
            date=itemView.findViewById(R.id.one);
            lieu=itemView.findViewById(R.id.three);
        }
    }
}
