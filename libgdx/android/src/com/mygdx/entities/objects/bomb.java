package com.mygdx.entities.objects;



import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.humans.person;
import com.mygdx.entities.humans.zombie;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;


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
        //bombExploded = false;
        //what should the size be ?
    }

    public boolean attack(){
        //set infection based on the distance,
        // anything around 30 is a zombie
        int entDist;
        entity tempEnt = null;



        //((tileGameMap)mMap).getPlayerOne()
        if(mMap.getPeople().size() == 0)
            return false;

        for (person victum : mMap.getPeople()) {
            entDist = (int) getEntDistance(victum);
            if (entDist < 30) {
                ((tileGameMap) mMap).getPlayerOne().addPoints(5);
                victum.turnIntoAZombie();
                if(tempEnt == null) {
                    tempEnt = victum;
                    ((zombie)tempEnt).setCpuStatus(false);
                    tempEnt.setClassID(classIdEnum.PZombie);
                }
            } else if (entDist < 150) {
                //the closer you are to the bomb the higher your infection is
                // we can put in deaths later
                //if a person is near a bomb and it goes off they should ran away from it
                //set an alterness for those near by but didnt get infected =
                // if we have an other ent to save this object's steeringent
                // we need to becareful of getting rid of this instance
                // when its time to switch to a zombie after an explosion
                ((tileGameMap) mMap).getPlayerOne().addPoints(
                        ((tileGameMap) mMap).getPlayerOne().ptsMgr(victum)
                );
                victum.setInfected(true, this);
                victum.decreaseInfectTime((1 /(entDist *100)* infecRatio));
                victum.decreaseHlth((1 /(entDist * 100) * healthRatio));
            } else if (entDist < 250) {
                //change alertness that a bomb went off
                //to investage for authorities
                //e
            }
        }

        if(tempEnt != null)
            ((tileGameMap)mMap).getPlayerOne().setTempHost(tempEnt);
        return true;
    }




    public void update(float dTime) {
        super.update(dTime);
        //Update Picture position to box2d position
        mPos.x = this.getBody().getPosition().x - 7;
        mPos.y = this.getBody().getPosition().y - 7.5f;

    }
    public void render(final SpriteBatch batch){
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
