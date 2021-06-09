package com.rpi.fabapro.RecyclerviewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rpi.fabapro.Pojos.gal;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class DocteursGallery extends RecyclerView.Adapter<DocteursGallery.holder>{

    private ArrayList<gal> liq;
    private Context mContext;

    public DocteursGallery(Context mContext, ArrayList<gal>lie) {
        this.mContext = mContext;
        this.liq=lie;
    }


    @Override
    public holder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.docteurs_gallery_list,viewGroup,false);
        DocteursGallery.holder holder= new DocteursGallery.holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder( holder holder, int i) {
          holder.titre.setText(liq.get(i).getTitre());
          holder.contenu.setText(liq.get(i).getDescription());
          Glide.with(mContext)
                  .asBitmap()
                  .load(liq.get(i).getImageUrl())
                  .apply(RequestOptions.centerInsideTransform())
                  .into(holder.gallery_name);
          holder.le.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Toast.makeText(mContext,"Calmez vous le stress n'est pas bon pour la sante",Toast.LENGTH_SHORT).show();
              }
          });
    }

    @Override
    public int getItemCount() {
        return liq.size();
    }


    class holder extends RecyclerView.ViewHolder{
        ImageView gallery_name;
        TextView titre,contenu;
        RelativeLayout le;
        public holder( View itemView) {
            super(itemView);
            gallery_name=itemView.findViewById(R.id.image2);
            titre=itemView.findViewById(R.id.nom2);
            contenu=itemView.findViewById(R.id.description2);
            le=itemView.findViewById(R.id.parent);

        }

    }
}
