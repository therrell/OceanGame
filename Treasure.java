package com.therrell.oceangame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Aidan Ryan on 6/16/2016.
 */
public class Treasure {
    static Texture chest = new Texture("treasure.png");
    public Sprite sprite;
    public Rectangle pup;

    public Treasure(float x){ //beware of the void
        sprite = new Sprite(chest);
        sprite.setScale(.25f);
        sprite.setY(0);
        sprite.setX(x);
        pup = sprite.getBoundingRectangle();
    }
}
