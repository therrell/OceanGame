package com.therrell.oceangame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Aidan Ryan on 6/15/2016.
 */
public class Obstacle{
    static Texture min = new Texture("mine.jpg");
    public Sprite sprite;
    static Circle hit;

    public Obstacle(float x, float y) {
        sprite = new Sprite(min);
        sprite.setScale(.25f);
        sprite.setX(x);
        sprite.setY(y);
        hit = new Circle(x,y,sprite.getHeight()/2);
    }

    public void Draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}
