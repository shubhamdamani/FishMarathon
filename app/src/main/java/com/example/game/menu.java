package com.example.game;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import static java.lang.Integer.max;

public class menu extends AppCompatActivity {

    public int swcore=0;
    TextView t1;
    Button b1,b2,b3;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        t1=findViewById(R.id.tv1);
        String b=t1.getText().toString();
        int q=Integer.parseInt(b);

        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        b3=findViewById(R.id.button3);

        Intent i=getIntent();
        int a = Objects.requireNonNull(i.getExtras()).getInt("data");//getStringExtra("data");

       /* if(a==null)
        {
            a="0";
        }*/
        int y=a;
        y=max(q,y);

        t1.setText(String.valueOf(y));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(menu.this,MainActivity.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(menu.this,over.class);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(1);
            }
        });
    }
}
