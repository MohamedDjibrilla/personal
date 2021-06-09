package com.rpi.fabapro.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.Activities.MainActivity;
import com.rpi.fabapro.Pojos.Utilisation;
import com.rpi.fabapro.Pojos.crediter;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.R;
import com.rpi.fabapro.RecyclerviewAdapters.AllTransactionRecycler;
import com.rpi.fabapro.RecyclerviewAdapters.UtilisationCredit;

import java.util.ArrayList;

public class AllTransactionsCompte extends Fragment {

    private ArrayList<String> method = new ArrayList<>();
    private ArrayList<String> montant = new ArrayList<>();
    private DatabaseReference databaseReference;
    private ArrayList<crediter> list= new ArrayList<>();
    private ArrayList<Utilisation> list1= new ArrayList<>();
    private ProgressBar progressBar,p2;
    private String usables,username;
    private DatabaseReference r;
    private RecyclerView recyclerview,recyclerview1,recyclerview2;
    public AllTransactionsCompte() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        getcredentials();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rView=inflater.inflate(R.layout.fragment_all_transactions_compte, container, false);
        progressBar= rView.findViewById(R.id.we);
        p2=rView.findViewById(R.id.p2);
        recyclerview= rView.findViewById(R.id.recyclertransaction);
        recyclerview1= rView.findViewById(R.id.recyclerutilisation);

        getcredentials();






        UtilisationCredit adapter1=new UtilisationCredit(list1,getActivity());
        recyclerview.setAdapter(adapter1);
        LinearLayoutManager layoutManager1= new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview1.setLayoutManager(layoutManager1);




        return rView;  }

    private void getinfo1(){
        AllTransactionRecycler adapter=new AllTransactionRecycler(list,getActivity());
        recyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        r= FirebaseDatabase.getInstance().getReference("Crediter").child(username);
        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    list.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        crediter tr = ds.getValue(crediter.class);
                        list.add(tr);
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getinfo2(){
        Query query2 = FirebaseDatabase.getInstance().getReference("Utilisation").orderByChild("username").equalTo(username);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    list1.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Utilisation tr = ds.getValue(Utilisation.class);
                        list1.add(tr);
                        p2.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getcredentials() {
        usables= Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/","");;
        Query query= FirebaseDatabase.getInstance().getReference("Logs")
                .orderByChild("imei").equalTo(usables);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        logger log = sn.getValue(logger.class);
                        if (dataSnapshot.exists()) {
                            if (!log.equals(null)) {
                                username = log.getUserID();
                                getinfo1();
                                getinfo2();
                            }
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
        });
    }
}
