package com.therrell.oceangame.com.therrell.oceangames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.therrell.oceangame.OceanGame;


public class Menu  implements Screen {

    OceanGame myGame;
    SpriteBatch batch;
    OrthographicCamera cam;
    Texture init;
    Sound intro;
    boolean first = true;


    public Menu(OceanGame g) {
        myGame = g;
    }

    public void show() {
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1000, 800);
        init = new Texture("splash.png");
        intro = Gdx.audio.newSound(Gdx.files.internal("splash_sound.wav"));


    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(first)
        {
            intro.play();
            first = false;
        }

        batch.begin();
        batch.draw(init, 0, 0);
        batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            myGame.setScreen(new Instructions(myGame, 1));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            myGame.setScreen(new Instructions(myGame, 2));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            myGame.setScreen(new Instructions(myGame, 3));
        }


    }

    public void hide(){

    }
    public void resize(int x, int y) {

    }
    public void pause() {

    }
    public void resume() {

    }
    public void dispose() {

    }
}
