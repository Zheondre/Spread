package com.mygdx.entities.objects;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.humans.player;
import com.mygdx.world.gameMap;

import static com.mygdx.utils.entUtils.getBullet;
import static com.mygdx.utils.entUtils.getMoveUpVec;
import static java.lang.StrictMath.abs;

public class bullet extends entity {

    public static final int vSpeed = 100;
    public boolean remove;

   public bullet(entityInfo eT, gameMap map, float x, float y){
        super(eT,map);
        //setImage(getBullet()); //Make this Static
       setImage(map.getPlayerHealth()); // temp
    }

    public void update(float dt){
        //if we get off the map remove
        //if we hit any ent remove

        if(getPosY() > mMap.getMapHeight() || getPosY() < 0)
            remove = true;

        if(getPosX() > mMap.getMapWidth() || getPosX() < 0)
            remove = true;

        /*if we have a collision delete
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

    public void shoot(entity target){

        float tangle;
        float vy;
        float vx;

        if(target != null) {
            getEntDistance(target);
            float tempx = target.getPosX() - this.mPos.x;
            float tempy = target.getPosY() - this.mPos.y;
            tangle = (float)Math.atan(tempy/tempx);
           vy =  (float)(vSpeed * Math.sin(tangle));
           vx = (float) (vSpeed * Math.cos(tangle));
             Vector2 tv = new Vector2(vx,vy); //this might slow things down, change later
            getBody().applyLinearImpulse(tv, getBody().getWorldCenter(), true);
        }
    }
    public boolean handleMessage(Telegram msg) {
        return true;
    }
    public void shoot(Vector2 vPos){

    }

    public void shootDebug(player play){

//play.getHost().



    }
}
