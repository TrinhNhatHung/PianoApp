package com.example.piano.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.piano.model.Key;
import com.example.piano.util.SoundManager;

import java.util.ArrayList;

public class PianoView extends View {
    public static  final int NUMBER_KEYS =14;
    private Paint black,white,yellow;
    private ArrayList<Key> whites, blacks;
    private int keyWidth, keyHeight;
    private Handler handler;
    private SoundManager soundManager;
    public PianoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        soundManager = SoundManager.getInstance();
        soundManager.init(context);
        whites = new ArrayList<Key>();
        blacks = new ArrayList<Key>();

        black = new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);

        yellow = new Paint();
        yellow .setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NUMBER_KEYS;
        keyHeight = h;

        int count =15;
        for (int i =0 ; i < NUMBER_KEYS ; i++){
            int left = i * keyWidth;
            int right = (i+1) * keyWidth;

            if (i == NUMBER_KEYS -1){
                right = w;
            }
            RectF rect = new RectF(left,0,right,h);
            whites.add(new Key(i+1,rect,false));

            // Khoi tao phim mau den
            if (i != 0 &&  i!=3 && i != 7 && i != 10){
                rect = new RectF((float)(i-1) *keyWidth + 0.75f * keyWidth
                                  , 0
                                  , (float) i * keyWidth + 0.25f * keyWidth
                                    , 0.67f * keyHeight);
                blacks.add(new Key(count,rect,false));
                count ++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Key key : whites){
            canvas.drawRect(key.rectF, key.down? yellow: white);
        }

        for (int i = 1; i< NUMBER_KEYS;i++){
            canvas.drawLine(i * keyWidth, 0 ,i * keyWidth,keyHeight,black);
        }

        for (Key key : blacks){
            canvas.drawRect(key.rectF,key.down? yellow:black);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        ArrayList<Key> tmp = new ArrayList<Key>(whites);
        tmp.addAll(blacks);
        if (action == MotionEvent.ACTION_UP){
            for (int touchIndex  = 0 ; touchIndex  < event.getPointerCount();touchIndex ++){
               float x = event.getX(touchIndex);
               float y = event.getY(touchIndex);

               Key key = keyIsOnTouchEvent(x,y);
               if (key!=null){
                key.down = false;
                invalidate();
               }
            }
        }

        if (action == MotionEvent.ACTION_MOVE){

            for (int touchIndex  = 0 ; touchIndex  < event.getPointerCount();touchIndex ++){
                float x = event.getX(touchIndex);
                float y = event.getY(touchIndex);
                Key key = keyIsOnTouchEvent(x,y);
                if (key!=null){
                    for (Key key1 : tmp ){
                        if (key.equals(key1)){
                            key1.down = true;
                        } else {
                            key1.down = false;
                        }
                    }
                }
                invalidate();
            }
        }
        return true;
    }

    private Key keyIsOnTouchEvent (float x, float y){
        for (Key key : blacks){
            if (key.rectF.contains(x,y)){
                return key;
            }
        }
        for (Key key : whites){
            if (key.rectF.contains(x,y)){
                return key;
            }
        }
        return null;
    }
}
