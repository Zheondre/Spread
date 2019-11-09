package com.mygdx.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.world.gameMap;

public class gameBlocks extends entity {

   public gameBlocks(entityInfo entType, gameMap Map, Rectangle rec) {
        super(entType, Map, rec);
    }

    public void update(float dTime) {
        ;
    }
    public void render(SpriteBatch batch){
        //batch.draw(image,mPos.x, mPos.y, mWidth, mHeight);
    }
}

