package com.dragonfruitstudios.brokenbonez.Gameplay;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.format.Time;
import android.view.MotionEvent;

import com.dragonfruitstudios.brokenbonez.AssetLoading.AssetLoader;
import com.dragonfruitstudios.brokenbonez.Game.GameView;
import com.dragonfruitstudios.brokenbonez.Game.Graphics;
import com.dragonfruitstudios.brokenbonez.Menu.ImageButton;

import java.util.Date;

public class FinishOverlay {
    AssetLoader assetLoader;

    private boolean enabled;
    // Information which determines what is displayed to the user.
    private boolean crashed; // Whether the bike crashed or finished successfully.
    private float finishTime; // How long the bike rode for (in seconds).
    private float ghostTimeDiff; // The difference between the fastest bike ride and this ride.

    ImageButton continueBtn;
    ImageButton mainMenuBtn;
    ImageButton restartLevelBtn;

    public FinishOverlay(AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
        assetLoader.AddAssets(new String[]{"menu/deathoverlay/continue.png",
                "menu/deathoverlay/main_menu.png",
                "menu/deathoverlay/restart_level.png"});

        Bitmap btnImg = assetLoader.getBitmapByName("menu/deathoverlay/main_menu.png");
        float buttonsX = (Graphics.getScreenWidth() / 2) - (btnImg.getWidth() / 2);
        continueBtn = new ImageButton("menu/deathoverlay/continue.png", assetLoader, buttonsX,
                400, btnImg.getWidth(), btnImg.getHeight());
        mainMenuBtn = new ImageButton("menu/deathoverlay/main_menu.png", assetLoader, buttonsX,
                480, btnImg.getWidth(), btnImg.getHeight());
        restartLevelBtn = new ImageButton("menu/deathoverlay/restart_level.png", assetLoader, buttonsX,
                560, btnImg.getWidth(), btnImg.getHeight());
    }

    public void enable(boolean crashed, float finishTime, float ghostTimeDiff) {
        this.crashed = crashed;
        this.finishTime = finishTime / 1000;
        this.ghostTimeDiff = ghostTimeDiff / 1000;
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void draw(GameView view) {
        if (enabled) {
            // Draw a transparent overlay over the GameScene.
            view.drawRect(0, 0, view.getWidth(), view.getHeight(), Color.argb(150, 0, 0, 0));

            // Draw a message in the center of the screen.
            String text = crashed ? "Game Over!" : "Level Finished!";
            view.drawTextCenter(text, view.getWidth() / 2, 200, Color.parseColor("#ffffff"),
                    150);

            // Draw extra information.
            String smallText = "";
            if (crashed) {
                smallText = "You have crashed after " + finishTime + " seconds!";
            }
            else {
                smallText = "You have finished in " + finishTime + " seconds, ";
                if (ghostTimeDiff < 0) {
                    smallText += String.format("but lost against the Ghost by %.1f seconds.",
                        Math.abs(ghostTimeDiff));
                }
                else if (ghostTimeDiff > 0) {
                    smallText += String.format("and won against the Ghost by %.1f seconds!",
                        ghostTimeDiff);
                }
            }
            view.drawTextCenter(smallText, view.getWidth() / 2, 300, Color.WHITE, 40);

            // Draw the buttons.
            if (!crashed) {
                continueBtn.draw(view);
            }
            mainMenuBtn.draw(view);
            restartLevelBtn.draw(view);
        }
    }

    public enum OverlayResult {
        Continue, ShowMainMenu, RestartLevel, None
    }

    public OverlayResult onTouchEvent(MotionEvent event) {
        // Only test for touch events if the overlay is enabled.
        if (enabled && continueBtn != null) {
            if (!crashed) {
                continueBtn.onTouchEvent(event);
            }
            mainMenuBtn.onTouchEvent(event);
            restartLevelBtn.onTouchEvent(event);

            if (continueBtn.isTouched()) {
                return OverlayResult.Continue;
            }
            else if (mainMenuBtn.isTouched()) {
                return OverlayResult.ShowMainMenu;
            } else if (restartLevelBtn.isTouched()) {
                return OverlayResult.RestartLevel;
            }
        }
        // Return `None` when no touch events occurred.
        return OverlayResult.None;
    }
}
