package com.rpi.fabapro.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rpi.fabapro.R;

public class Manuel extends AppCompatActivity {
    private ImageView i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manuel);
        i1=findViewById(R.id.back);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  back();
    }

    private void back() {
        Intent intent = new Intent(Manuel.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
