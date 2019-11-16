package com.mygdx.entities.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.humans.person;
import com.mygdx.world.gameMap;

import static java.lang.StrictMath.abs;

public class bomb extends entity {

    float blastRadius;

    int infecRatio;
    int healthRatio;
    public bomb(entityInfo entType, gameMap Map){
        super(entType, Map);

        setImage("bomb.png");
        infecRatio = 350;
        healthRatio = 300;
        //what should the size be ?
    }

    public boolean attack(){

        //set infection based on the distance,
        // anything around 1 is a zombie
        int entDist;

        if(mMap.getPeople().size() == 0)
            return false;

        for(person victum : mMap.getPeople()) {
            entDist = (int)getEntDistance(victum);
            if(entDist < 1)
                victum.turnIntoAZombie();
            else {
                //the closer you are to the bomb the higher your infection is
                // we can put in deaths later
                victum.decreaseInfectTime((1 / entDist * infecRatio));
                victum.decreaseHlth((1 / entDist * healthRatio));
            }
        }
        return true;

    }
    public void update(float dTime) {
        super.update(dTime);
        //Update Picture position to box2d position
        mPos.x = this.getBody().getPosition().x - 7;
        mPos.y = this.getBody().getPosition().y - 7.5f;

    }
    public void render(SpriteBatch batch){
        Texture image = getImage();
        if(image != null)
            batch.draw(image,mPos.x, mPos.y, getWidth(), getHeight());
    }

    //bellow is duplicate code, ill place this in the ent class later
    public float getEntDistance(entity target) {
        mPos.x = super.getBody().getPosition().x;
        mPos.y = super.getBody().getPosition().y;
        float tempx = abs(target.getPosX() - this.mPos.x);
        float tempy = abs(target.getPosY() - this.mPos.y);
        return (float)Math.sqrt(tempx * tempx + tempy * tempy);
    }

    public void dispose(){
        ;

    }

}
