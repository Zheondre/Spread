package com.mygdx.entities;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Pursue;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.world.gameMap;

public class gameBlocks extends entity {

   public gameBlocks(entityInfo entType, gameMap Map, float posX, float posY) {
        super( entType,Map);
        super.setPosX(posX);
        super.setPosY(posY);
    }

    public void update(float dTime) {
        ;
    }
    public void render(SpriteBatch batch){
        //batch.draw(image,mPos.x, mPos.y, mWidth, mHeight);
    }

    public Arrive<Vector2> getArriveSB(){ return null;}


    public void setArriveSB(Arrive<Vector2> arriveSB){
        ;
    }
    public void setArriveSB(Box2dSteering prey){
        ;
    }
    public void setArrivePrey(Box2dSteering prey){
        ;
    }

    public void setPursueSB(entity prey){
        ;
    }

    public Pursue<Vector2> getPursueSB(){
        return null;
    }

    public Box2dSteering getSteerEnt(){
        return null;
    }
}

