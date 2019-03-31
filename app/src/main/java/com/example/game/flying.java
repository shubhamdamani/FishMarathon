package com.example.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class flying extends View {

    private Bitmap bckgnd;
    private Paint score=new Paint();
    private Bitmap life[] =new Bitmap[2];
    private Bitmap fish[] =new Bitmap[2];
    private boolean touch=false;
    private int cscore,chnce;
    SoundPool dice_sound;
    int sid;

    private int fy,speed,canwidth,canheight,fx=10;

    private int yelx,yely,yelspeed=16;
    private Paint yelpaint=new Paint();

    private int greenx,greeny,greenspeed=20;
    private Paint greenPaint=new Paint();
    private int redx,redy,redspeed=25;
    private Paint redPaint=new Paint();




    public flying(Context context) {
        super(context);



        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        bckgnd=BitmapFactory.decodeResource(getResources(),R.drawable.background);

        score.setColor(Color.YELLOW);
        score.setTextSize(65);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        yelpaint.setColor(Color.YELLOW);
        yelpaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

       redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fy=550; // this is for initial position
        cscore=0;
        chnce=3;

    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        InitSound();
        super.onDraw(canvas);
        canheight=canvas.getHeight();
        canwidth=canvas.getWidth();




        canvas.drawBitmap(bckgnd,0,0,null);
        //canvas.drawBitmap(fis,0,0,null);

        int miny=fish[0].getHeight();
        int maxy=canheight-fish[0].getHeight()*3;
        fy+=speed;

        if(miny>fy){
            fy=miny;
        }
        if(fy>maxy){
            fy=maxy;
        }

        speed+=2;

        if(touch){
            canvas.drawBitmap(fish[1],fx,fy,null);
            touch=false;

        }else{
            canvas.drawBitmap(fish[0],fx,fy,null);
        }

        final MediaPlayer ab= (MediaPlayer) MediaPlayer.create(getContext(),R.raw.s);
        yelx-=yelspeed;
        if(hitBall(yelx,yely)){
            cscore+=10;
            yelx= -100;
          //  ab.start();
            dice_sound.play(sid, 1.0f, 1.0f, 0, 0, 1.0f);
        }


        if(yelx<0){
            yelx=canwidth+21;
            yely=(int) Math.floor(Math.random() * (maxy-miny))+miny;


        }

        canvas.drawCircle(yelx,yely,25,yelpaint);

        greenx-=greenspeed;
        if(hitBall(greenx,greeny)){
            cscore+=20;
            greenx= -100;
            dice_sound.play(sid, 1.0f, 1.0f, 0, 0, 1.0f);
        }


        if(greenx<0){
            greenx=canwidth+21;
            greeny=(int) Math.floor(Math.random() * (maxy-miny))+miny;


        }
        canvas.drawCircle(greenx,greeny,25,greenPaint);

        redx-=redspeed;
        if(hitBall(redx,redy)){
            cscore-=25;
            redx= -100;
            chnce--;
            dice_sound.play(sid, 1.0f, 1.0f, 0, 0, 1.0f);
            if(chnce==0){
                Toast.makeText(getContext(),"Game ended- score: ",Toast.LENGTH_SHORT).show();


                Intent i=new Intent(getContext(),menu.class);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("data",cscore);
                getContext().startActivity(i);
            }
        }


        if(redx<0){
            redx=canwidth+21;
            redy=(int) Math.floor(Math.random() * (maxy-miny))+miny;


        }
        canvas.drawCircle(redx,redy,35,redPaint);
        canvas.drawText("Score-"+cscore,20,60,score);

        for(int i=0;i<3;i++){
            int x= (int) (580+life[0].getWidth()*1.5*i);
            int y=30;
            if(i<chnce){
                canvas.drawBitmap(life[0],x,y,null);
            }else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }
       /* canvas.drawBitmap(life[0],580,15,null);
        canvas.drawBitmap(life[0],680,15,null);
        canvas.drawBitmap(life[0],780,15,null);*/

    }

    public boolean hitBall(int x,int y){
        if(x>fx && x<(fx+fish[0].getWidth()) && fy<y && y<(fy+fish[0].getHeight())){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){


        if(event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            speed=-22;
        }

        return true;
        //return super.onTouchEvent();
    }
    void InitSound() {
        AudioAttributes aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        dice_sound = new SoundPool.Builder().setAudioAttributes(aa).build();

        sid = dice_sound.load(getContext(), R.raw.s, 1);
    }



}
