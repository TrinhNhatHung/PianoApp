package com.example.piano.model;

import android.graphics.RectF;

public class Key {
    public final int sound;
    public final RectF rectF;
    public boolean down;

    public Key(int sound, RectF rectF, boolean down) {
        this.sound = sound;
        this.rectF = rectF;
        this.down = down;
    }

}
