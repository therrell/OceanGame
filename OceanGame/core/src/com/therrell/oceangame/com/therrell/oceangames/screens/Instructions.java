package com.therrell.oceangame.com.therrell.oceangames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.therrell.oceangame.OceanGame;


public class Instructions implements Screen {

    OceanGame myGame;
    int numP;
    SpriteBatch batch;
    OrthographicCamera camera;
    Texture instr;
    Sound wait;

    public Instructions(OceanGame g, int p) {
        myGame = g;
        numP = p;
    }

    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 800);
        instr = new Texture("instruct.png");

        wait = Gdx.audio.newSound(Gdx.files.internal("wait.wav"));

        wait.loop();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.draw(instr, 0, 0);
        batch.end();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            wait.stop();
            myGame.setScreen(new MainGame(myGame, numP));
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