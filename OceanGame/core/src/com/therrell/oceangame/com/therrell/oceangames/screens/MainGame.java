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
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.therrell.oceangame.Obstacle;
import com.therrell.oceangame.OceanGame;

import java.util.ArrayList;


public class MainGame implements Screen {

    OceanGame myGame;

    //sprites, textures, and sounds
    SpriteBatch batch;
    Texture fishPic, crabPic, sHorsePic, ocean, ocean2, lastalive, min;
    Sprite fish, crab, seahorse;
    Sound start;

    //camera
    OrthographicCamera cam;
    //variables
    float x1 = 250, y1 = 25, x2 = 250, y2 = 150, x3 = 250, y3 = 275;
    float timer = 2.3f;
    int dl = 0, currBG = 0;
    int numP;
    boolean first = true;


    ArrayList<Obstacle> mineList = new ArrayList<Obstacle>();
    int delete;
    float x,y;
    boolean need;
    float time;

    ArrayList<Sprite> alive;
    //ArrayList<Obstacle> mineList;

    public MainGame(OceanGame g, int p) {
        myGame = g;
        numP = p;
    }

    public void show() {

        //assign sprites
        batch = new SpriteBatch();
        fishPic = new Texture("fish.png");
        crabPic = new Texture("crab.png");
        sHorsePic = new Texture("seahorse.png");

        //background
        ocean = new Texture("ocean.jpg");

        ocean2 = new Texture("oceanflip.jpg");

        //mine
        //min = new Texture("min.png");

        x = (float)Gdx.graphics.getWidth();
        need = false;
        //manipulate sprites
        fish = new Sprite(fishPic);
        crab = new Sprite(crabPic);
        seahorse = new Sprite(sHorsePic);

        fish.setSize(75, 50);
        crab.setSize(75, 50);
        seahorse.setSize(75, 50);

        //camera and alive array
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 1000, 800);

        alive = new ArrayList<Sprite>();
        mineList = new ArrayList<Obstacle>();

        start = Gdx.audio.newSound(Gdx.files.internal("start.wav"));

        //checks number of players to add to alive array
        if (numP == 3) {
            alive.add(fish);
            alive.add(crab);
            alive.add(seahorse);
        } else if (numP == 2) {
            alive.add(fish);
            alive.add(crab);
        } else if (numP == 1) {
            alive.add(fish);
        }

    }

    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int counter = 0;

        for(Obstacle i : mineList){
            if(i.sprite.getX() < cam.position.x-Gdx.graphics.getWidth()){
                need = true;
                delete = counter;
            }
            counter++;
        }

        if(need){
            mineList.remove(delete);
        }

        batch.begin();

        //ocean background
        batch.draw(ocean, currBG, 0);
        batch.draw(ocean2, currBG + ocean.getWidth(), 0);

        for(Obstacle d : mineList){
            d.Draw(batch);
        }

        if (numP == 3) {
            fish.draw(batch);
            crab.draw(batch);
            seahorse.draw(batch);
        } else if (numP == 2) {
            fish.draw(batch);
            crab.draw(batch);

        } else if (numP == 1) {
            fish.draw(batch);

        }

        batch.end();

        //move background
        if(dl > currBG + ocean.getWidth()) {
            currBG += ocean.getWidth() * 2;
        }

        //change pos of characters
        fish.setPosition(x1, y1);
        crab.setPosition(x2, y2);
        seahorse.setPosition(x3, y3);

        //handle collision
        for(Obstacle i : mineList) {
            if (fish.getBoundingRectangle().overlaps(i.hit)) {
                alive.remove(fish);
            }
            if (crab.getBoundingRectangle().overlaps(i.hit)) {
                alive.remove(crab);
            }
            if (seahorse.getBoundingRectangle().overlaps(i.hit)) {
                alive.remove(seahorse);
            }
        }

        //start timer
        timer -= delta;

        if(timer > 0)
        {
            if(first) {
                start.play();
                first = false;
            }

        }
        else {

            //TODO: add background music

            //moves camera and death line
            cam.position.x += 1;
            cam.update();
            batch.setProjectionMatrix(cam.combined);
            dl++;



            //check collision with back
            if (fish.getX() < dl) {
                alive.remove(fish);
            }
            if (crab.getX() < dl) {
                alive.remove(crab);
            }
            if (seahorse.getX() < dl) {
                alive.remove(seahorse);
            }

            //check collision with front

            if (fish.getX() > dl + Gdx.graphics.getWidth() - fish.getWidth()) {
                fish.setX(dl + Gdx.graphics.getWidth() - fish.getWidth());
                x1 = fish.getX();
            }
            if (crab.getX() > dl + Gdx.graphics.getWidth() - crab.getWidth()) {
                crab.setX(dl + Gdx.graphics.getWidth() - crab.getWidth());
                x2 = crab.getX();
            }
            if (seahorse.getX() > dl + Gdx.graphics.getWidth() - seahorse.getWidth()) {
                seahorse.setX(dl + Gdx.graphics.getWidth() - seahorse.getWidth());
                x3 = seahorse.getX();
            }

            //top boundary
            if (fish.getY() > Gdx.graphics.getHeight() - fish.getHeight()) {
                fish.setY(Gdx.graphics.getHeight() - fish.getHeight());
                y1 = fish.getY();
            }
            if (crab.getY() > Gdx.graphics.getHeight() - crab.getHeight()) {
                crab.setY(Gdx.graphics.getHeight() - crab.getHeight());
                y2 = crab.getY();
            }
            if (seahorse.getY() > Gdx.graphics.getHeight() - seahorse.getHeight()) {
                seahorse.setY(Gdx.graphics.getHeight() - seahorse.getHeight());
                y3 = seahorse.getY();
            }
            //bottom boundary
            if (fish.getY() < 0) {
                fish.setY(0);
                y1 = fish.getY();
            }
            if (crab.getY() < 0) {
                crab.setY(0);
                y2 = crab.getY();
            }
            if (seahorse.getY() < 0) {
                seahorse.setY(0);
                y3 = seahorse.getY();
            }

            //fish movement
            if (alive.contains(fish)) {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    y1 += 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    x1 -= 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    y1 -= 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    x1 += 10;
                }
            }

            //crab movement
            if (alive.contains(crab)) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    y2 += 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    x2 -= 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    y2 -= 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    x2 += 10;
                }
            }

            //seahorse movement
            if (alive.contains(seahorse)) {
                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
                    y3 += 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
                    x3 -= 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {
                    y3 -= 10;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
                    x3 += 10;
                }
            }

            //TODO: add obstacles
            need = false;
            Vector3 clickspace = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(clickspace);
            x = cam.position.x + Gdx.graphics.getWidth()/2;
            y = (Gdx.graphics.getHeight()*(float)Math.random());
            time += Gdx.graphics.getDeltaTime();

            if(time>.5f){
                //mine = new Obstacle(x, y);
                mineList.add(new Obstacle( x, y));
                time = 0;
            }
        }


        //determines winner
       if(alive.size() == 1)
        {
            lastalive = alive.get(0).getTexture();
        }

        if(alive.isEmpty())
        {
            myGame.setScreen(new EndScreen(myGame, lastalive));
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
