package com.example.piano.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.example.piano.model.Key;

public class PianoView1 extends PianoView {
    public PianoView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        keyWidth = w / NUMBER_KEYS;
        keyHeight = h;
        int count =15;
        for (int i =0 ; i < NUMBER_KEYS ; i++){
            int right = (NUMBER_KEYS - i) * keyWidth;
            int left = (NUMBER_KEYS - i -1) * keyWidth;

            if (i == NUMBER_KEYS -1){
                left = 0;
            }
            RectF rect = new RectF(left,0,right,h);
            whites.add(new Key(i+1,rect,false));

            // Khoi tao phim mau den
            if (i != 0 &&  i!=3 && i != 7 && i != 10){
                rect = new RectF((float) (NUMBER_KEYS -i-1) * keyWidth + 0.75f * keyWidth
                        , 0.33f * keyHeight
                        , (float)(NUMBER_KEYS - i) *keyWidth + 0.25f * keyWidth
                        , keyHeight);
                blacks.add(new Key(count,rect,false));
                count ++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, keyHeight -2, NUMBER_KEYS * keyWidth,keyHeight -2 ,black);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
