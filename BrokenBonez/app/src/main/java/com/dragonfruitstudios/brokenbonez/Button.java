package com.dragonfruitstudios.brokenbonez;

import android.graphics.Color;
import android.view.MotionEvent;

import com.dragonfruitstudios.brokenbonez.Game.GameView;
import com.dragonfruitstudios.brokenbonez.Math.Collisions.Rect;
import com.dragonfruitstudios.brokenbonez.Math.VectorF;

public class Button {
    Rect startButton;

    public Button() {
        startButton = new Rect(new VectorF(30, 50), 190, 90);
    }

    public void draw(GameView gameView) {
        Rect r = startButton;
        r.draw(gameView);
        gameView.drawRect(30, 50, 220, 140, Color.parseColor("#999999"));
        gameView.drawText("Start Game", 90, 100, Color.BLACK);
    }

    public void onTouchEvent(MotionEvent event) {

    }
}