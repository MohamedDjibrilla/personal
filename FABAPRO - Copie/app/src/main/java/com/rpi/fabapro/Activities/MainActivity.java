package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.rpi.fabapro.Pojos.logger;
import com.rpi.fabapro.Pojos.mess;
import com.rpi.fabapro.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce=false;
    private LinearLayout con,ouv,dec,doc,service,restauration;
    private String usables = null,usables1=null,usables2=null;
    private Button inscription, connecter,deconnecter;
    private EditText em,pas;
    private FirebaseAuth mAuth;
    private CheckBox cpass,ccompte;
    private TextView a1,a2;
    private ProgressBar p;
    private DatabaseReference log;
    private String usern=null;
    private String q1,q2,q3;
    private Toolbar toolbar;
    private String z1,z2,z3,z4,z5="two";
    private ArrayList<mess> list = new ArrayList<>();
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p=findViewById(R.id.w);toolbar=findViewById(R.id.toolbar);setSupportActionBar(toolbar);cpass=findViewById(R.id.cpass);
        ccompte=findViewById(R.id.ccompte);service=findViewById(R.id.pro);restauration=findViewById(R.id.res);
        doc=findViewById(R.id.doc);con=findViewById(R.id.connection);ouv=findViewById(R.id.ouvrir);dec=findViewById(R.id.deconnecter);
        deconnecter=findViewById(R.id.deconnecterbtn);connecter=findViewById(R.id.connecterbtn);em=findViewById(R.id.email);
        pas=findViewById(R.id.passwor);mAuth=FirebaseAuth.getInstance();a1=findViewById(R.id.username);a2=findViewById(R.id.balance);
        inscription=findViewById(R.id.inscription);
        mPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        mEditor=mPreferences.edit();

        checking();
        getusername();

        connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();
            }
        });
        final Spinner Saccueil= findViewById(R.id.spinneraccueil);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(MainActivity.this,R.array.Accueil,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Saccueil.setAdapter(adapter);
        Saccueil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int b= ((int) adapterView.getItemIdAtPosition(i));
                switch (b){
                    case(0):
                        con.setVisibility(View.VISIBLE);
                        ouv.setVisibility(View.INVISIBLE);
                        dec.setVisibility(View.INVISIBLE);
                        break;
                    case(1):

                        con.setVisibility(View.INVISIBLE);
                        p.setVisibility(View.VISIBLE);
                        ouv.setVisibility(View.VISIBLE);
                        dec.setVisibility(View.INVISIBLE);
                        break;
                    case(2):
                        con.setVisibility(View.INVISIBLE);
                        ouv.setVisibility(View.INVISIBLE);
                        dec.setVisibility(View.VISIBLE);
                        break;



                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

         cpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if(b){
          pas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            pas.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
});
        deconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signout();
            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Inscription.class);
                 i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);}
        });

        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Docteurs.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);}});

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Services.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);}
        });

        restauration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Restaurations.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        if(savedInstanceState!=null){
            z1=savedInstanceState.getString("email");
            z2=savedInstanceState.getString("pass");
             em.setText(z1);
            pas.setText(z2);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(doubleBackToExitPressedOnce){
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce=true;
        Toast.makeText(this,"Veillez cliquer une fois de plus pour quitter",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },1500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_assistance) {
            Intent i= new Intent(this, Assistance.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == R.id.action_settings) {
            Intent i= new Intent(this,Manuel.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        else if(id==R.id.action_CompteMail){
            Intent i= new Intent(this,CompteCredit.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }

        else if(id==R.id.Policy){
            Intent i= new Intent(this,Terms.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }



    private void getusername() {
        Query query= FirebaseDatabase.getInstance().getReference("CreditUtilisateurs")
                .orderByChild("uid").equalTo(usern);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot sn:dataSnapshot.getChildren()){
                    credit utils=sn.getValue(credit.class);
                    if(utils != null) {
                        z3=utils.getUsername();
                        z4=utils.getCredit().toString();
                    }settext();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Une erreur est survenu! Veuillez reessayer!",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void signin() {
        q1=em.getText().toString().trim();
        q2=pas.getText().toString().trim();
        if(q1.isEmpty()){
            em.setError("Veuillez entrer une addresse email correcte");
            em.requestFocus();
            return;
        }
        if(q2.isEmpty() && q2.length()<=6){
            pas.setError("Mot de passe doit etre au minimun de 6 characters");
            pas.requestFocus();
            return;
        }
        builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        final View view = getLayoutInflater().inflate(R.layout.chargement, null);
        builder.setView(view);
        builder.show();
        mAuth.signInWithEmailAndPassword( q1,q2)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            usern=FirebaseAuth.getInstance().getCurrentUser().getUid();
                            usables1= Build.FINGERPRINT;
                            usables=usables1.replace("[-\\s+.?!_,:;\"[]()<>/","");
                            Toast.makeText(MainActivity.this, "Bienvenue, chargement des profils", Toast.LENGTH_LONG).show();
                           log= FirebaseDatabase.getInstance().getReference("Logs");
                            logger loge=new logger(q1,usern,z3,usables,q2);
                            log.child(usern).setValue(loge);
                            getusername();
                            p.setVisibility(View.GONE);
                            con.setVisibility(View.INVISIBLE);
                            dec.setVisibility(View.VISIBLE);

                            check1();
                            z5="four";
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                errr();
            }
        });
saving();

    }

    private void saving() {

        if(ccompte.isChecked()){
            mEditor.putString(getString(R.string.switcher),"true");
            mEditor.commit();

            String user = em.getText().toString();
            mEditor.putString(getString(R.string.nom),user);
            mEditor.commit();

            String pass = pas.getText().toString();
            mEditor.putString(getString(R.string.pass),pass);
            mEditor.commit();
        }else{
            mEditor.putString(getString(R.string.switcher),"false");
            mEditor.commit();


            mEditor.putString(getString(R.string.nom),"false");
            mEditor.commit();


            mEditor.putString(getString(R.string.pass),"false");
            mEditor.commit();
        }
    }

    private void check1() {
        String user = a1.getText().toString();
        mEditor.putString(getString(R.string.username),user);
        mEditor.commit();

        String credit = a2.getText().toString();
        mEditor.putString(getString(R.string.credit),credit);
        mEditor.commit();
}

    private void checking() {
        String user = mPreferences.getString(getString(R.string.username),"Déconnecté");
        String credit= mPreferences.getString(getString(R.string.credit),"0");
        a1.setText(user);
        a2.setText(credit);
        if(user.equals("Déconnecté")){
            con.setVisibility(View.VISIBLE);
            dec.setVisibility(View.INVISIBLE);
              }else{
            con.setVisibility(View.INVISIBLE);
            dec.setVisibility(View.VISIBLE);
        }

        anothercheck();

    }

    private void anothercheck() {
        String checkbox = mPreferences.getString(getString(R.string.switcher),"false");
        String user = mPreferences.getString(getString(R.string.nom),"false");
        String pass = mPreferences.getString(getString(R.string.pass),"false");
        if(!user.equals("false") && !pass.equals("false") && !checkbox.equals("false")) {
            em.setText(user);
            pas.setText(pass);
            ccompte.setChecked(true);

        }else {
            em.setText("");
            pas.setText("");
            ccompte.setChecked(false);
        }
    /*    if(checkbox.equals("true")){
            ccompte.setChecked(true);
        }else{
            ccompte.setChecked(false);
        }
    */
    }


    private void errr() {
        Toast.makeText(MainActivity.this, "Email ou Mot de Passe incorect, Veillez re-essayer", Toast.LENGTH_LONG).show();
        em.setText("");
        pas.setText("");
        con.setVisibility(View.VISIBLE);
    }


    private void signout() {
        if(z5.equals("four")) {
            mAuth.signOut();
            removelog();
            em.setText("");
            pas.setText("");
            a1.setText("Déconnecté");
            a2.setText("0");
            String user = a1.getText().toString();

            mEditor.putString(getString(R.string.username),user);
            mEditor.commit();

            String credit = a2.getText().toString();
            mEditor.putString(getString(R.string.credit),credit);
            mEditor.commit();

            con.setVisibility(View.VISIBLE);
            dec.setVisibility(View.INVISIBLE);
        }else{
            Toast.makeText(MainActivity.this,"identifant ou mot de passe incorrect",Toast.LENGTH_LONG).show();
        }
    }

    private void removelog() {
        log.child(usern).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this,"A Bientot Sur FABA PRO",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("email",z1);
        outState.putString("pass",z2);
       }

    private void settext() {
        a1.setText(z3);
        a2.setText(z4);
    }

    @Override
    public void onResume() {
        super.onResume();
        getusername();

    }




}
