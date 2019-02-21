package com.hfad.boatcontroller;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Bitmap;

import android.view.View;


public  class DrawController extends View {
    float xPos, yPos;
    float joyW, joyH;
    Bitmap joyImg;
    Paint paint;

    public DrawController(Context Context, float joyW, float joyH, Bitmap joyImg, Paint paint) {
        super(Context);
        this.joyH = joyH;
        this.joyW = joyW;
        this.joyW = joyW;
        this.joyH = joyH;
        this.joyImg = joyImg;
        this.paint = paint;
    }
    public void setJoyPos(float xPos, float yPos) {
        this.xPos = xPos - (joyW/ 2);
        this.yPos = yPos - (joyH / 2);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(joyImg, xPos, yPos, paint);
    }


}
