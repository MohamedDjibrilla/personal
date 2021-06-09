package com.rpi.fabapro.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rpi.fabapro.Pojos.credit;
import com.rpi.fabapro.Pojos.utilisateur;
import com.rpi.fabapro.R;

public class Inscription extends AppCompatActivity {
    private ImageButton back;
    private Button ins;
    private FirebaseAuth mAuth;
    private DatabaseReference cred,profs;
    private Integer creditInitial=0;
    private String a1,a2,a3,a4,a5;
    private int a;
    private CheckBox c1;
    private EditText email,util,pass,tel,profe,recom;
    private ProgressBar bar;
    private String l="8h-10h",m="8h-10h",m1="8h-10h",j="8h-10h",v="8h-10h",s1="8h-10h",s2="8h-10h";
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        back=findViewById(R.id.back);email=findViewById(R.id.email);util=findViewById(R.id.utilisateur);
        pass=findViewById(R.id.password);tel=findViewById(R.id.phone);profe=findViewById(R.id.profession);
        recom=findViewById(R.id.recommande);mAuth=FirebaseAuth.getInstance();bar=findViewById(R.id.progress);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        ins=findViewById(R.id.inscrire);
        c1=findViewById(R.id.c1);
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inscri();
            }
        });

        if(savedInstanceState!=null) {
            a = savedInstanceState.getInt("phone");
            tel.setText(a);
            a1 = savedInstanceState.getString("email1");
            email.setText(a1);
            a2 = savedInstanceState.getString("user");
            util.setText(a2);
            a3 = savedInstanceState.getString("pass1");
            pass.setText(a3);
            a4 = savedInstanceState.getString("profession");
            profe.setText(a4);
            a5 = savedInstanceState.getString("reommande");
            recom.setText(a5);
        }
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    recom.setEnabled(false);
                }else{
                    recom.setEnabled(false);
                }
            }
        });

    }

    private void inscri() {



         builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        final View view = getLayoutInflater().inflate(R.layout.verification, null);
        builder.setView(view);
        builder.show();


        String name=util.getText().toString().trim();
        String email1=email.getText().toString();
        String pass1=pass.getText().toString().trim();
        String tel1= String.valueOf(tel.getText());
        String profe1= profe.getText().toString();


        if(name.isEmpty()){
            util.setError("nom d'utilisateur Obligatoire");
            util.requestFocus();
            reset();
            return;
        }
        if(email1.isEmpty()){
            email.setError("Email Obligatoire");
            util.requestFocus();
            reset();
            return;
        }
        if(pass1.isEmpty() && pass1.length()>=6 ){
            pass.setError("Mot de passe doit etre superieur a 6");
            util.requestFocus();
            reset();
            return;}
        if(tel1.isEmpty() && tel1.length()!=8){
            tel.setError("Numero de Telephone Incorrect");
            util.requestFocus();
            reset();
            return;}
        if(profe1.isEmpty() ){
            profe.setError("Profession Incorrect");
            util.requestFocus();
            reset();
            return; }


        chechuser();

    }


    private void chechuser(){
        Query query = FirebaseDatabase.getInstance().getReference("Utilisateurs")
                .orderByChild("username").equalTo(util.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot sn : dataSnapshot.getChildren()) {
                        utilisateur utils = sn.getValue(utilisateur.class);
                        if (utils!=null) {
                            Toast.makeText(Inscription.this, "Cet utilisateur est deja en service", Toast.LENGTH_LONG).show();
                            reset();
                        }else {
                            register();
                        }
                    }}else{
                    register();
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Inscription.this,"Une erreur est survenu",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reset(){
        email.setText("");
        util.setText("");
        pass.setText("");
        tel.setText("");
        profe.setText("");
        recom.setText("");
    }

    private void register(){

        mAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        bar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            profs= FirebaseDatabase.getInstance().getReference("Utilisateurs");
                            cred=FirebaseDatabase.getInstance().getReference("CreditUtilisateurs");
                            credit cre= new credit(FirebaseAuth.getInstance().getCurrentUser().getUid(), util.getText().toString(),creditInitial);
                            cred.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(cre);
                            utilisateur prof= new utilisateur(util.getText().toString(),email.getText().toString(),pass.getText().toString(),tel.getText().toString(),profe.getText().toString(),recom.getText().toString());
                            profs.child(util.getText().toString()).setValue(prof);
                            reset();
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                   dialogInterface.dismiss();
                                }
                            });
                            Toast.makeText(Inscription.this,"Utilisateur Creer",Toast.LENGTH_SHORT).show();

                        } else{Toast.makeText(Inscription.this,"Veuillez entrer un Email existant!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void back(){
        Intent inte= new Intent(this,MainActivity.class);
        inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(inte);    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email1",a1);
        outState.putString("user",a2);
        outState.putString("pass1",a3);
        outState.putInt("phone",a);
        outState.putString("profession",a4);
        outState.putString("recommande",a5);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }
}
