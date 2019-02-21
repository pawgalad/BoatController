package com.hfad.boatcontroller;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class Controller {

    public static final int STOP = 0;
    public static final int UP = 1;
    public static final int RIGHTUP1 = 2;
    public static final int RIGHTUP2 = 3;
    public static final int RIGHT = 4;
    public static final int RIGHTDOWN1 = 5;
    public static final int RIGHTDOWN2 = 6;
    public static final int DOWN = 7;
    public static final int LEFTDOWN2 = 8;
    public static final int LEFTDOWN1 = 9;
    public static final int LEFT = 10;
    public static final int LEFTUP1 = 11;
    public static final int LEFTUP2 = 12;
    public static final int minDistance = 20;

    public float x = 0;
    public float y = 0;
    private float posX = 0;
    private float posY = 0;
    private DrawController drawController;
    private int joyW, joyH;
    private Bitmap joyImg;
    public float angle = 0;
    private Context appContext;
    private LayoutParams layoutParams;
    private ViewGroup joyLayout;
    private float constRadius = 0;
    public float currRadius = 0;
    public MotionEvent motionEvent;
    public  int DISTANCE = 90;
    private Paint paint;

    public Controller(ViewGroup joyLayout, int joyID, Context context) {

        appContext = context;
        this.joyLayout = joyLayout;
        layoutParams = joyLayout.getLayoutParams();
        joyImg = BitmapFactory.decodeResource(appContext.getResources(), joyID);

        if(appContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ){
            layoutParams.width = 200;
            layoutParams.height = 400;
            joyImg = Bitmap.createScaledBitmap(joyImg, 130, 130, false);
            DISTANCE = 80;

        }else{
            layoutParams.width = 500;
            layoutParams.height = 500;
            joyImg = Bitmap.createScaledBitmap(joyImg, 150, 150, false);
        }
        constRadius =  (layoutParams.width / 2) - DISTANCE;
        joyW = joyImg.getWidth();
        joyH = joyImg.getHeight();
        drawController = new DrawController(appContext,joyW, joyH, joyImg, paint);
        paint = new Paint();
    }
    //this method sets the position of clicked fragment and draw a joystick there
    public void setJoyParam() {
        posX = (int) ((layoutParams.width)/2 - motionEvent.getX());
        posY = (int) ((layoutParams.height / 2)- motionEvent.getY());
        currRadius = (float) Math.sqrt(Math.pow(posX, 2) + Math.pow(posY, 2));
        angle = (float) countDegrees(posX, posY, currRadius);
        if(currRadius >= 20) {
            x = (float) (Math.cos(Math.toRadians(countDegrees(posX, posY, currRadius))) * constRadius);
            y = (float) (Math.sin(Math.toRadians(countDegrees(posX, posY, currRadius))) * constRadius);
            x = x + (layoutParams.width / 2);
            y = y + (layoutParams.height / 2);
            drawController.setJoyPos(x, y);
            joyLayout.removeView(drawController);
            joyLayout.addView(drawController);;
        }else{
            x = (layoutParams.width)/2;
            y = (layoutParams.height / 2);
            drawController.setJoyPos(x, y);

        }

    }
    private double countDegrees(float x, float y, float radius) {
        if(x >= 0 && y >= 0)
            return Math.toDegrees(Math.asin(y / radius)) + 180;
        else if(x < 0 && y >= 0)
            return  (-1) * Math.toDegrees(Math.asin(y / radius)) +360    ;
        else if(x < 0 && y < 0)
            return (-1) * Math.toDegrees(Math.asin(y / radius))   ;
        else if(x >= 0 && y < 0)
            return Math.toDegrees(Math.asin(y / radius)) + 180 ;
        return 0;
    }

    public int checkAngle() {
        if( (currRadius > minDistance)) {
            if(angle >= 15 && angle <45  ) {
                return RIGHTDOWN1;
            } else if(angle >= 45  && angle < 75 ) {
                return RIGHTDOWN2;
            } else if(angle >= 75 && angle < 105 ) {
                return DOWN;
            } else if(angle >= 105 && angle < 135 ) {
                return LEFTDOWN2;
            } else if(angle >= 135 && angle < 165 ) {
                return LEFTDOWN1;
            } else if(angle >= 165 && angle < 195 ) {
                return LEFT;
            } else if(angle >= 195&& angle <225  ) {
                return LEFTUP2;
            }else if(angle >= 225 && angle <255  ) {
                return LEFTUP1;
            }else if(angle >= 255 && angle <285  ) {
                return UP;
            }else if(angle >= 285&& angle <315  ) {
                return RIGHTUP1;
            }else if(angle >= 315 && angle <345  ) {
                return RIGHTUP2;
            }else {
                return RIGHT;
            }
        } else if(currRadius <= minDistance) {
            return STOP;
        }
        return 0;
    }
    public void drawController(){

        setJoyParam();
        joyLayout.removeView(drawController);
        joyLayout.addView(drawController);
    }
    public void setMotionEvent(MotionEvent motionEvent){
        this.motionEvent = motionEvent;
    }

}