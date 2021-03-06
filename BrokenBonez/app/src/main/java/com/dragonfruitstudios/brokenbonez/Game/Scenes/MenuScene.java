package com.dragonfruitstudios.brokenbonez.Game.Scenes;

import android.view.MotionEvent;
import com.dragonfruitstudios.brokenbonez.AssetLoading.AssetLoader;
import com.dragonfruitstudios.brokenbonez.Game.GameView;
import com.dragonfruitstudios.brokenbonez.GameSceneManager;
import com.dragonfruitstudios.brokenbonez.Menu.MenuState;

public class MenuScene extends Scene {
    MenuState state;

    public MenuScene(AssetLoader assetLoader, GameSceneManager gameSceneManager) {
        super(assetLoader, gameSceneManager);
        this.state = new MenuState(assetLoader, gameSceneManager);
    }

    public void draw(GameView view) {
        state.draw(view);
    }
    public void update(float lastUpdate) {
        state.update(lastUpdate);
    }
    public void updateSize(int w, int h) {
        state.updateSize(w, h);
    }
    public void onTouchEvent(MotionEvent event) {
       state.onTouchEvent(event);
    }

    @Override
    public void activate(){
        if (assetLoader.getSoundByName("brokenboneztheme.ogg").isPlaying()) {
            assetLoader.getSoundByName("brokenboneztheme.ogg").pause();
        }
    }
}