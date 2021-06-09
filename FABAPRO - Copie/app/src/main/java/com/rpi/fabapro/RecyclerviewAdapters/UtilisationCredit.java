package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.rpi.fabapro.Pojos.Utilisation;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class UtilisationCredit extends RecyclerView.Adapter<UtilisationCredit.Holder>{
private ArrayList<Utilisation> list= new ArrayList<>();
private Context mContext;

    public UtilisationCredit(ArrayList<Utilisation> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.utilisation_list_view,viewGroup,false);
        UtilisationCredit.Holder holder= new UtilisationCredit.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
             holder.Name.setText(list.get(i).getNom());
             holder.Profession.setText(list.get(i).getProfession());
             holder.prix.setText(list.get(i).getPrix());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView Name,Profession,prix;

        public Holder(@NonNull View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.nom1);
            Profession=itemView.findViewById(R.id.profession1);
            prix=itemView.findViewById(R.id.prix1);


        }
    }
}
