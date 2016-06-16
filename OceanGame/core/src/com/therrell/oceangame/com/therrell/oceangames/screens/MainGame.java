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
import com.badlogic.gdx.math.Rectangle;
import com.therrell.oceangame.Obstacle;
import com.therrell.oceangame.OceanGame;

import java.util.ArrayList;


public class MainGame implements Screen {

    OceanGame myGame;

    //sprites, textures, and sounds
    SpriteBatch batch;
    Texture fishPic, crabPic, sHorsePic, ocean, ocean2, lastalive;
    Sprite fish, crab, seahorse;
    Sound start;

    //camera
    OrthographicCamera cam;

    //variables
    float speed1[] = {0,0}, speed2[] = {0,0}, speed3[] = {0,0};
    float x1 = 250, y1 = 25, x2 = 250, y2 = 150, x3 = 250, y3 = 275;
    float maxVel = 10;
    float timer = 2.3f;
    int dl = 0, currBG = 0;
    int numP;
    boolean first = true;
    int[] score = new int[3];


    ArrayList<Obstacle> mineList = new ArrayList<Obstacle>();
    int delete;
    float x,y;
    boolean need;
    float time;

    ArrayList<Sprite> alive;

    Rectangle[] hitbox = new Rectangle[3];

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
        x = (float)Gdx.graphics.getWidth();
        need = false;

        //manipulate sprites
        fish = new Sprite(fishPic);
        crab = new Sprite(crabPic);
        seahorse = new Sprite(sHorsePic);


        fish.setSize(75, 50);
        crab.setSize(75, 50);
        seahorse.setSize(75, 50);

        //change pos of characters
        fish.setPosition(x1, y1);
        crab.setPosition(x2, y2);
        seahorse.setPosition(x3, y3);

        //create hitboxes
        hitbox[0] = new Rectangle(fish.getX() + 4,fish.getY() + 9, 63, 31);
        hitbox[1] = new Rectangle(crab.getX() + 12, crab.getY() + 4,50, 39);
        hitbox[2] = new Rectangle(seahorse.getX() + 12, seahorse.getY() + 2, 42, 46);


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

        //TODO: fish collision

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int counter = 0;

        for(Obstacle i : mineList){
            if(i.sprite.getX() < cam.position.x-Gdx.graphics.getWidth()){
                need = true;
                delete = counter;
            }
            for(Obstacle j : mineList){
                if(j.hit.overlaps(i.hit) && i != j){
                    need = true;
                    delete = counter;
                }
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

        //draw mines
        for(Obstacle d : mineList) d.Draw(batch);

        //draw players
        drawPlayers();

        batch.end();

        //move background
        if(dl > currBG + ocean.getWidth()) {
            currBG += ocean.getWidth();
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
            updateCam();

            //check collision and move living players
            checkCollisions();
            checkAlive();

            // obstacles
            need = false;
            x = cam.position.x + Gdx.graphics.getWidth()/2;
            y = (Gdx.graphics.getHeight()*(float)Math.random()) - 150;
            time += Gdx.graphics.getDeltaTime();

            if(time>.5f){
                //mine = new Obstacle(x, y);
                mineList.add(new Obstacle( x, y));
                time = 0;
            }
        }

        if(alive.isEmpty())
            getWinner(numP);

    }

    public void hide(){}
    public void resize(int x, int y) {}
    public void pause() {}
    public void resume() {}
    public void dispose() {}

    //camera adjustments
    public void updateCam() {
        cam.position.x += 1;
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        dl++;
    }

    //player methods
    public void drawPlayers() {
        //check number of players
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
    }
    public void checkCollisions() {

        //check collision with front
        if (fish.getX() > dl + Gdx.graphics.getWidth() - fish.getWidth()) {
            fish.setX(dl + Gdx.graphics.getWidth() - fish.getWidth());
            speed1[0] = 0;
            x1 = fish.getX();
        }
        if (crab.getX() > dl + Gdx.graphics.getWidth() - crab.getWidth()) {
            crab.setX(dl + Gdx.graphics.getWidth() - crab.getWidth());
            speed2[0] = 0;
            x2 = crab.getX();
        }
        if (seahorse.getX() > dl + Gdx.graphics.getWidth() - seahorse.getWidth()) {
            seahorse.setX(dl + Gdx.graphics.getWidth() - seahorse.getWidth());
            speed3[0] = 0;
            x3 = seahorse.getX();
        }


        //top boundary
        if (fish.getY() > Gdx.graphics.getHeight() - fish.getHeight()) {
            fish.setY(Gdx.graphics.getHeight() - fish.getHeight());
            y1 = fish.getY();
            speed1[1] = 0;
        }
        if (crab.getY() > Gdx.graphics.getHeight() - crab.getHeight()) {
            crab.setY(Gdx.graphics.getHeight() - crab.getHeight());
            y2 = crab.getY();
            speed2[1] = 0;
        }
        if (seahorse.getY() > Gdx.graphics.getHeight() - seahorse.getHeight()) {
            seahorse.setY(Gdx.graphics.getHeight() - seahorse.getHeight());
            y3 = seahorse.getY();
            speed3[1] = 0;
        }


        //bottom boundary
        if (fish.getY() < 0) {
            fish.setY(0);
            y1 = fish.getY();
            speed1[1] = 0;
        }
        if (crab.getY() < 0) {
            crab.setY(0);
            y2 = crab.getY();
            speed2[1] = 0;
        }
        if (seahorse.getY() < 0) {
            seahorse.setY(0);
            y3 = seahorse.getY();
            speed3[1] = 0;
        }

        playerCollisions();
        lethalCollisions();
    }
    public void playerCollisions() {

        //fish and crab collision
        if(hitbox[0].overlaps(hitbox[1])) {
            if(fish.getX() > crab.getX()) {
                if(fish.getY() > crab.getY()) {
                    fish.setY(fish.getY() + 1);
                    crab.setY(crab.getY() - 1);
                    speed1[1] = 5;
                    speed2[1] = -5;
                }
                else if(fish.getY() < crab.getY()) {
                    fish.setY(fish.getY() - 1);
                    crab.setY(crab.getY() + 1);
                    speed1[1] = -5;
                    speed2[1] = 5;
                }
                fish.setX(fish.getX() + 1);
                crab.setX(crab.getX() - 1);
                speed1[0] = 5;
                speed2[0] = -5;

            }
            else if(fish.getX() < crab.getX()) {
                if(fish.getY() > crab.getY()) {
                    fish.setY(fish.getY() + 1);
                    crab.setY(crab.getY() - 1);
                    speed1[1] = 5;
                    speed2[1] = -5;
                }
                else if(fish.getY() < crab.getY()) {
                    fish.setY(fish.getY() - 1);
                    crab.setY(crab.getY() + 1);
                    speed1[1] = -5;
                    speed2[1] = 5;
                }
                fish.setX(fish.getX() - 1);
                crab.setX(crab.getX() + 1);
                speed1[0] = -5;
                speed2[0] = 5;
            }
            else {
                if(fish.getY() > crab.getY()) {
                    fish.setY(fish.getY() + 1);
                    crab.setY(crab.getY() - 1);
                    speed1[1] = 5;
                    speed2[1] = -5;

                }
                else if(fish.getY() < crab.getY()) {
                    fish.setY(fish.getY() - 1);
                    crab.setY(crab.getY() + 1);
                    speed1[1] = -5;
                    speed2[1] = +5;

                }
            }
        }

        //fish and seahorse collision
        if(hitbox[0].overlaps(hitbox[2])) {
            if(fish.getX() > seahorse.getX()) {
                if(fish.getY() > seahorse.getY()) {
                    fish.setY(fish.getY() + 1);
                    seahorse.setY(seahorse.getY() - 1);
                    speed1[1] = 5;
                    speed3[1] = -5;
                }
                else if(fish.getY() < seahorse.getY()) {
                    fish.setY(fish.getY() - 1);
                    seahorse.setY(seahorse.getY() + 1);
                    speed1[1] = -5;
                    speed3[1] = 5;
                }
                fish.setX(fish.getX() + 1);
                seahorse.setX(seahorse.getX() - 1);
                speed1[0] = 5;
                speed3[0] = -5;

            }
            else if(fish.getX() < seahorse.getX()) {
                if(fish.getY() > seahorse.getY()) {
                    fish.setY(fish.getY() + 1);
                    seahorse.setY(seahorse.getY() - 1);
                    speed1[1] = 5;
                    speed3[1] = -5;
                }
                else if(fish.getY() < seahorse.getY()) {
                    fish.setY(fish.getY() - 1);
                    seahorse.setY(seahorse.getY() + 1);
                    speed1[1] = -5;
                    speed3[1] = 5;
                }
                fish.setX(fish.getX() - 1);
                seahorse.setX(seahorse.getX() + 1);
                speed1[0] = -5;
                speed3[0] = 5;
            }
            else {
                if(fish.getY() > seahorse.getY()) {
                    fish.setY(fish.getY() + 1);
                    seahorse.setY(seahorse.getY() - 1);
                    speed1[1] = 5;
                    speed3[1] = -5;

                }
                else if(fish.getY() < seahorse.getY()) {
                    fish.setY(fish.getY() - 1);
                    seahorse.setY(seahorse.getY() + 1);
                    speed1[1] = -5;
                    speed3[1] = +5;

                }
            }
        }

        //seahorse and crab collision
        if(hitbox[1].overlaps(hitbox[2])) {
            if(crab.getX() > seahorse.getX()) {
                if(crab.getY() > seahorse.getY()) {
                    crab.setY(crab.getY() + 1);
                    seahorse.setY(seahorse.getY() - 1);
                    speed2[1] = 5;
                    speed3[1] = -5;
                }
                else if(crab.getY() < seahorse.getY()) {
                    crab.setY(crab.getY() - 1);
                    seahorse.setY(seahorse.getY() + 1);
                    speed2[1] = -5;
                    speed3[1] = 5;
                }
                crab.setX(crab.getX() + 1);
                seahorse.setX(seahorse.getX() - 1);
                speed2[0] = 5;
                speed3[0] = -5;

            }
            else if(crab.getX() < seahorse.getX()) {
                if(crab.getY() > seahorse.getY()) {
                    crab.setY(crab.getY() + 1);
                    seahorse.setY(seahorse.getY() - 1);
                    speed2[1] = 5;
                    speed3[1] = -5;
                }
                else if(crab.getY() < seahorse.getY()) {
                    crab.setY(crab.getY() - 1);
                    seahorse.setY(seahorse.getY() + 1);
                    speed2[1] = -5;
                    speed3[1] = 5;
                }
                crab.setX(crab.getX() - 1);
                seahorse.setX(seahorse.getX() + 1);
                speed2[0] = -5;
                speed3[0] = 5;
            }
            else {
                if(crab.getY() > seahorse.getY()) {
                    crab.setY(crab.getY() + 1);
                    seahorse.setY(seahorse.getY() - 1);
                    speed2[1] = 5;
                    speed3[1] = -5;

                }
                else if(crab.getY() < seahorse.getY()) {
                    crab.setY(crab.getY() - 1);
                    seahorse.setY(seahorse.getY() + 1);
                    speed2[1] = -5;
                    speed3[1] = +5;

                }
            }
        }
    }
    public void lethalCollisions() {
        //kill if touches back
        if (fish.getX() < dl) {
            alive.remove(fish);
        }
        if (crab.getX() < dl) {
            alive.remove(crab);
        }
        if (seahorse.getX() < dl) {
            alive.remove(seahorse);
        }


        //kill if touches mine
        for(Obstacle i : mineList) {
            if (hitbox[0].overlaps(i.hitbox)) {
                alive.remove(fish);
            }
            if (hitbox[1].overlaps(i.hitbox)) {
                alive.remove(crab);
            }
            if (hitbox[2].overlaps(i.hitbox)) {
                alive.remove(seahorse);
            }
        }
    }

    public void checkAlive() {
        //fish movement
        if (alive.contains(fish))
            moveP1();
        score[0] = (int) fish.getX();
        hitbox[0].setPosition(fish.getX() + 4, fish.getY() + 9);

        //crab movement
        if (alive.contains(crab))
            moveP2();
        score[1] = (int) crab.getX();
        hitbox[1].setPosition(crab.getX() + 12, crab.getY() + 4);

        //seahorse movement
        if (alive.contains(seahorse))
            moveP3();
        score[2] = (int) seahorse.getX();
        hitbox[2].setPosition(seahorse.getX() + 12, seahorse.getY() + 2);

    }

    //check for keys being held
    public boolean WS() {
        boolean give = false;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
            give = true;

        else if(Gdx.input.isKeyPressed(Input.Keys.S))
            give = true;


        return(give);
    }
    public boolean AD() {
        boolean give = false;

        if(Gdx.input.isKeyPressed(Input.Keys.A))
            give = true;

        else if(Gdx.input.isKeyPressed(Input.Keys.D))
            give = true;


        return(give);
    }
    public boolean Varr() {
        boolean give = false;

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            give = true;

        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            give = true;

        return(give);
    }
    public boolean Harr() {
        boolean give = false;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            give = true;

        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            give = true;

        return(give);
    }
    public boolean Vnum() {
        boolean give = false;

        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8))
            give = true;

        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5))
            give = true;

        return(give);
    }
    public boolean Hnum() {
        boolean give = false;

        if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4))
            give = true;

        else if(Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6))
            give = true;

        return(give);
    }

    //movement and acceleration
    public void moveP1() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {

            if(speed1[1] < 10)
                speed1[1] += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {

            if(speed1[0] > -10)
                speed1[0] -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

            if(speed1[1] > -10)
                speed1[1] -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {

            if(speed1[0] < 10)
                speed1[0] += 1;
        }

        fish.setPosition(fish.getX() + speed1[0], fish.getY() + speed1[1]);

        if(!AD() && speed1[0] > 0)
        {
            speed1[0] -= 1;
        }
        if(!AD() && speed1[0] < 0)
        {
            speed1[0] += 1;
        }

        if(!WS() && speed1[1] > 0)
        {
            speed1[1] -= 1;
        }
        if(!WS() && speed1[1] < 0)
        {
            speed1[1] += 1;
        }


    }
    public void moveP2() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {

            if(speed2[1] < 10)
                speed2[1] += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            if(speed2[0] > -10)
                speed2[0] -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

            if(speed2[1] > -10)
                speed2[1] -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

            if(speed2[0] < 10)
                speed2[0] += 1;
        }

        crab.setPosition(crab.getX() + speed2[0], crab.getY() + speed2[1]);

        if(!Harr() && speed2[0] > 0)
        {
            speed2[0] -= 1;
        }
        if(!Harr() && speed2[0] < 0)
        {
            speed2[0] += 1;
        }

        if(!Varr() && speed2[1] > 0)
        {
            speed2[1] -= 1;
        }
        if(!Varr() && speed2[1] < 0)
        {
            speed2[1] += 1;
        }

    }
    public void moveP3() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {

            if(speed3[1] < 10)
                speed3[1] += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {

            if(speed3[0] > -10)
                speed3[0] -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_5)) {

            if(speed3[1] > -10)
                speed3[1] -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {

            if(speed3[0] < 10)
                speed3[0] += 1;
        }

        seahorse.setPosition(seahorse.getX() + speed3[0], seahorse.getY() + speed3[1]);

        if(!Hnum() && speed3[0] > 0)
        {
            speed3[0] -= 1;
        }
        if(!Hnum() && speed3[0] < 0)
        {
            speed3[0] += 1;
        }

        if(!Vnum() && speed3[1] > 0)
        {
            speed3[1] -= 1;
        }
        if(!Vnum() && speed3[1] < 0)
        {
            speed3[1] += 1;
        }

    }

    //determines winner based on farthest movement
    public void getWinner(int players) {
        if(players == 1)
        {
            myGame.setScreen(new EndScreen(myGame, fishPic));
        }
        else if(players == 2) {
            if(crab.getX() > fish.getX())
            {
                myGame.setScreen(new EndScreen(myGame, crabPic));
            }
            else
            {
                myGame.setScreen(new EndScreen(myGame, fishPic));
            }
        }
        else if(players == 3) {
            if(crab.getX() > fish.getX() && crab.getX() > seahorse.getX())
            {
                myGame.setScreen(new EndScreen(myGame, crabPic));
            }
            else if(fish.getX() > crab.getX() && fish.getX() > seahorse.getX())
            {
                myGame.setScreen(new EndScreen(myGame, fishPic));
            }
            else if(seahorse.getX() > crab.getX() && seahorse.getX() > fish.getX())
            {
                myGame.setScreen(new EndScreen(myGame, sHorsePic));
            }
        }


    }
}
