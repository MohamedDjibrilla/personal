package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.rpi.fabapro.Pojos.com;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class PnpAccueilRecyclerCom extends RecyclerView.Adapter<PnpAccueilRecyclerCom.viewHolder> {

    private ArrayList<com> commentaire;
    private Context mContext;

    public PnpAccueilRecyclerCom(ArrayList<com> commentaire, Context mContext) {
        this.commentaire = commentaire;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pnp_discussion_list_view,viewGroup,false);
        PnpAccueilRecyclerCom.viewHolder holder= new PnpAccueilRecyclerCom.viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.Nom.setText(commentaire.get(i).getNom());
        viewHolder.Com.setText(commentaire.get(i).getCommentaire());

    }

    @Override
    public int getItemCount() {
        return commentaire.size();
    }


    class viewHolder extends RecyclerView.ViewHolder{
        TextView Nom,Com;
        LinearLayout parent;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            Nom=itemView.findViewById(R.id.pnpdiscussionrecyclernom);
            Com=itemView.findViewById(R.id.pnpdiscussionrecyclercom);
            parent=itemView.findViewById(R.id.parent);
        }
    }
}
