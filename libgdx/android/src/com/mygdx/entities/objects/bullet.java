package com.mygdx.entities.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.world.gameMap;

import static com.mygdx.utils.entUtils.getBullet;

public class bullet extends entity {

    public static final int Speed = 500;
    public boolean remove;


   public bullet(entityInfo eT, gameMap map, float x, float y){
        super(eT,map);
        setImage(getBullet()); //Make this Static
    }

    public void update(float dt){
        //if we get off the map remove
        //if we hit any ent remove

        if(getPosY() > mMap.getMapHeight() || getPosY() < 0)
            remove = true;

        if(getPosX() > mMap.getMapWidth() || getPosX() < 0)
            remove = true;

        /*
            apply force in the direction the bullet was fired in

         */

    }

    public void dispose(){
        makeImageNull();
    }

    public void render(SpriteBatch batch){
       //shoot requested draw image

        Texture image = getImage();
        if(image != null)
            batch.draw(image,mPos.x, mPos.y, getWidth(), getHeight());
    }

    public boolean attack(){
       return true;
    }

    public void shoot(entity shooter){

    }
}
