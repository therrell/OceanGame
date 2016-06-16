package com.therrell.oceangame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Aidan Ryan on 6/15/2016.
 */
public class Obstacle{
    static Texture min = new Texture("min.png");
    public Sprite sprite;
    public Rectangle hit; //circle does not allow for overlap

    public Obstacle(float x, float y) {
        sprite = new Sprite(min);
        sprite.setScale(.25f);
        sprite.setX(x);
        sprite.setY(y);
        hit = sprite.getBoundingRectangle();
    }

    public void Draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}
