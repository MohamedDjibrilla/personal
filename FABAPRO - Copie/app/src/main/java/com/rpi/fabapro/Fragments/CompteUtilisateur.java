package com.rpi.fabapro.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.Activities.MainActivity;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.R;


public class CompteUtilisateur extends Fragment {
    private TextView s1,s2;
    private ProgressBar p;
    private String userid;
    private String usables;

    public CompteUtilisateur() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_compte_utilisateur, container, false);
        s1=view.findViewById(R.id.UID);
        p=view.findViewById(R.id.pr);
        usables= Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/","");;
        Query query= FirebaseDatabase.getInstance().getReference("Logs")
                .orderByChild("imei").equalTo(usables);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()) {
                   for (DataSnapshot sn : dataSnapshot.getChildren()) {
                       logger log = sn.getValue(logger.class);
                       if (!log.equals(null)) {
                           userid = log.getUserID();
                           s1.setText(userid);
                           p.setVisibility(View.GONE);
                       }
                   }
               }else {
                   Intent intent= new Intent(getContext(), MainActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   Toast.makeText(getActivity(), "Connectez vous pour avoir access a cette option", Toast.LENGTH_LONG).show();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });  return view;  }

}
