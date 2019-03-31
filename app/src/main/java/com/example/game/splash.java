package com.example.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread thread=new Thread(){
          @Override
            public void run(){
              try{
                  sleep(3000);

              }catch (Exception e){
                  e.printStackTrace();

              }
              finally {
                  Intent i=new Intent(splash.this,menu.class);
                  i.putExtra("data",0);
                  startActivity(i);


              }
          }
        };

        thread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}
