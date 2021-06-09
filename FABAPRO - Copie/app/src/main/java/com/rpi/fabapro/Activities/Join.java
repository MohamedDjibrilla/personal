package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rpi.fabapro.Pojos.join;
import com.rpi.fabapro.R;

public class Join extends AppCompatActivity {
    private EditText e1,e2,e3,e4;
    private DatabaseReference r1;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        e1=findViewById(R.id.e1);
        e2=findViewById(R.id.e2);
        e3=findViewById(R.id.e3);
        e4=findViewById(R.id.e4);
        btn=findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                envoyer();

            }
        });

    }

    private void envoyer() {
        String a,b,c,d;
        a=e1.getText().toString();
        b=e2.getText().toString();
        c=e3.getText().toString();
        d=e4.getText().toString();
        if(a.isEmpty()){
            e1.setError("Nom Obligatoire");
            e1.requestFocus();
            return;
        }
        if(b.isEmpty()){
            e2.setError("Profession est Obligatoire");
            e2.requestFocus();
            return;
        }
        if(c.isEmpty() && c.length()>=6){
            e3.setError("Numero est Obligatoire et Veuillez verifier le nombre de chiffre");
            e3.requestFocus();
            return;
        }
        if(d.isEmpty()){
            e4.setError("Addresse Obligatoire");
            e4.requestFocus();
            return;
        }

        send();
    }

    private void send() {

        r1= FirebaseDatabase.getInstance().getReference("join");
        String id=r1.push().getKey();
        join j1 = new join(e1.getText().toString(),e2.getText().toString(),Integer.parseInt(e3.getText().toString()),e4.getText().toString());
        r1.child(id).setValue(j1);
        Toast.makeText(this,"Votre Requete a ete envoyer, nos agents vous contacterons sous peu",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // back();
    }

    private void back() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
