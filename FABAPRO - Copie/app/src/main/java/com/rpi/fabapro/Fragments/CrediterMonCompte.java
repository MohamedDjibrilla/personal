package com.rpi.fabapro.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.Activities.MainActivity;
import com.rpi.fabapro.Pojos.crediter;
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.R;


public class CrediterMonCompte extends Fragment {
    private Button crediter;
    private Spinner spinner;
    private EditText a1,a3,a4;
    private TextView a2,a5;
    private LinearLayout airtel,orange,bnif,alizza,moov,nita;
    private String username, usables,status="En cours";
    private DatabaseReference dec;


    public CrediterMonCompte() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rView=inflater.inflate(R.layout.fragment_crediter_mon_compte, container, false);
        airtel=rView.findViewById(R.id.airtelmoney);
        alizza=rView.findViewById(R.id.alizza);
        bnif=rView.findViewById(R.id.bnif);
        moov=rView.findViewById(R.id.moovflooz);
        orange=rView.findViewById(R.id.orangemoney);
        nita=rView.findViewById(R.id.nita);
        spinner=rView.findViewById(R.id.crediterspinner);
        crediter=rView.findViewById(R.id.comptecreditenvoyer);
        a1=rView.findViewById(R.id.recepteur);
        a2=rView.findViewById(R.id.canal);
        a3=rView.findViewById(R.id.transctionid);
        a4=rView.findViewById(R.id.montant);
        a5=rView.findViewById(R.id.first);
        geet();
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(),R.array.crediter,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int b= ((int) adapterView.getItemIdAtPosition(i));
                switch (b){
                    case(0):
                        airtel.setVisibility(View.VISIBLE);
                        alizza.setVisibility(View.INVISIBLE);
                        bnif.setVisibility(View.INVISIBLE);
                        moov.setVisibility(View.INVISIBLE);
                        nita.setVisibility(View.INVISIBLE);
                        orange.setVisibility(View.INVISIBLE);
                        a2.setText("Airtel-Money");
                        a5.setText("Numero");
                        break;
                    case(1):
                        airtel.setVisibility(View.INVISIBLE);
                        alizza.setVisibility(View.VISIBLE);
                        bnif.setVisibility(View.INVISIBLE);
                        moov.setVisibility(View.INVISIBLE);
                        nita.setVisibility(View.INVISIBLE);
                        orange.setVisibility(View.INVISIBLE);
                        a2.setText("Al-Izza");
                        a5.setText("recepteur");
                        break;
                    case(2):
                        airtel.setVisibility(View.INVISIBLE);
                        alizza.setVisibility(View.INVISIBLE);
                        bnif.setVisibility(View.VISIBLE);
                        moov.setVisibility(View.INVISIBLE);
                        nita.setVisibility(View.INVISIBLE);
                        orange.setVisibility(View.INVISIBLE);
                        a2.setText("Bnif");
                        a5.setText("recepteur");
                        break;
                    case(3):
                        airtel.setVisibility(View.INVISIBLE);
                        alizza.setVisibility(View.INVISIBLE);
                        bnif.setVisibility(View.INVISIBLE);
                        moov.setVisibility(View.VISIBLE);
                        nita.setVisibility(View.INVISIBLE);
                        orange.setVisibility(View.INVISIBLE);
                        a2.setText("Moov-Flooz");
                        a5.setText("Numero");
                        break;
                    case(4):
                        airtel.setVisibility(View.INVISIBLE);
                        alizza.setVisibility(View.INVISIBLE);
                        bnif.setVisibility(View.INVISIBLE);
                        moov.setVisibility(View.INVISIBLE);
                        nita.setVisibility(View.VISIBLE);
                        orange.setVisibility(View.INVISIBLE);
                        a2.setText("Nita");
                        a5.setText("recepteur");
                        break;
                    case(5):
                        airtel.setVisibility(View.INVISIBLE);
                        alizza.setVisibility(View.INVISIBLE);
                        bnif.setVisibility(View.INVISIBLE);
                        moov.setVisibility(View.INVISIBLE);
                        nita.setVisibility(View.INVISIBLE);
                        orange.setVisibility(View.VISIBLE);
                        a2.setText("Orange-Money");
                        a5.setText("Numero");
                        break;
                }}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        crediter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
                a1.setText("");
                a2.setText("");
                a3.setText("");
                a4.setText("");
            }
        });

        return rView; }

    private void verify() {
        String numero=a1.getText().toString().trim();
        String tran=a3.getText().toString().trim();
        String mon=a4.getText().toString().trim();
        if(numero.isEmpty()){
            a1.setError("Erreur! Ce champs ne peut etre vide");
            a1.requestFocus();
            return;
        }
        if(tran.isEmpty()){
            a3.setError("Erreur!Ce champs ne peut etre vide");
            a3.requestFocus();
            return;
        }
        if(mon.isEmpty()){
            a4.setError("Erreur!Ce champs ne peut etre vide");
            a4.requestFocus();
            return;
        }

        crediterDialog();
    }

    private void geet() {
        usables= Build.FINGERPRINT.replace("[-\\s+.?!_,:;\"[]()<>/","");;
        Query query= FirebaseDatabase.getInstance().getReference("Logs")
                .orderByChild("imei").equalTo(usables);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()) {
                   for (DataSnapshot d : dataSnapshot.getChildren()) {
                       logger log = d.getValue(logger.class);
                       if (!log.equals(null)) {
                           username = log.getUserID();
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

    private void crediterDialog() {



        dec=FirebaseDatabase.getInstance().getReference("Crediter").child(username);
        String re= dec.push().getKey();
        com.rpi.fabapro.Pojos.crediter cd= new crediter(username,a1.getText().toString(),a2.getText().toString(),a3.getText().toString(),a4.getText().toString(),status);
        dec.child(re).setValue(cd);
        Toast.makeText(getContext(), "votre requete est en traitement", Toast.LENGTH_SHORT).show();
    }




}
