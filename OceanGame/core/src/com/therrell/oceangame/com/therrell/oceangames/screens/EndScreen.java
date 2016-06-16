package com.therrell.oceangame.com.therrell.oceangames.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.therrell.oceangame.OceanGame;

import java.util.ArrayList;


public class EndScreen implements Screen {

    OceanGame myGame;
    SpriteBatch batch;
    OrthographicCamera cam;
    Texture winpic, winGraph;
    Sprite winner;
    Sound win;
    boolean first = true;
    int score;
    Texture[] nums = new Texture[10];
    int xNum = 600;
    boolean rend1 = true;

    static ArrayList<Texture> drawScore;

    public EndScreen(OceanGame g, Texture w, int sc) {
        myGame = g;
        winpic = w;
        score = sc;
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

        drawScore = new ArrayList<Texture>();

        for(int i = 0; i < 10; i++)
        {
            nums[i] = new Texture("num_" + i + ".png");
        }
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

        drawFinScore();

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

    public void getScore(int finScore) {

        int mod;

        while(finScore > 0)
        {
            mod = finScore % 10;
            drawScore.add(0,nums[mod]);

            finScore -= mod;
            finScore/=10;
        }
    }

    public void drawFinScore() {
            if(rend1) {
                getScore(score);
                rend1 = false;
            }

            for (int i = 0; i < drawScore.size(); i++) {
                batch.draw(drawScore.get(i), xNum + (60 * i), 570);
            }

    }
}