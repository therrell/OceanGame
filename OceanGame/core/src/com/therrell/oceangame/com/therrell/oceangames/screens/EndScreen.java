package com.therrell.oceangame.com.therrell.oceangames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.therrell.oceangame.OceanGame;


public class EndScreen implements Screen {

    OceanGame myGame;
    SpriteBatch batch;
    OrthographicCamera cam;
    Texture winpic, winGraph;
    Sprite winner;
    Sound win;
    boolean first = true;

    public EndScreen(OceanGame g, Texture w) {
        myGame = g;
        winpic = w;
    }

    public void show() {

        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1000, 800);

        winGraph = new Texture("win.png");
        winner = new Sprite(winpic);

        winner.setSize(300,200);
        winner.setPosition(350, 300);
        win = Gdx.audio.newSound(Gdx.files.internal("win_sound.wav"));
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        cam.update();

        if(first) {
            win.play();
            first = false;
        }

        batch.begin();
        batch.draw(winGraph, 0, 0);
        winner.draw(batch);
        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            myGame.setScreen(new Menu(myGame));
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