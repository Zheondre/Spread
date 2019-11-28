package com.mygdx.entities.objects;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.humans.person;
import com.mygdx.entities.humans.player;
import com.mygdx.entities.humans.zombie;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

import static com.mygdx.utils.entUtils.getBullet;
import static com.mygdx.utils.entUtils.getMoveLeftVec;
import static com.mygdx.utils.entUtils.getMoveUpVec;
import static java.lang.StrictMath.abs;

public class bullet extends entity {

    public static final int vSpeed = 100;
zombie shooter;
    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isRemove() {
        return remove;
    }

    private boolean remove;
    private boolean fired;

   public bullet(entityInfo eT, zombie ent, gameMap map){
        super(eT, ent, map);
       setImage("blank.jpg"); // temp change this later...
      // setImage(mMap.getPlayerHealth());
       fired = false;
       shooter = ent;

   }
    @Override
    public void update(float dt){
        //if we get off the map remove
        //if we hit any ent remove

        mPos.x = this.getBody().getPosition().x ;
        mPos.y = this.getBody().getPosition().y ;

        if(getPosY() > mMap.getMapHeight() || getPosY() < 0)
            remove = true;

        if(getPosX() > mMap.getMapWidth() || getPosX() < 0)
            remove = true;

        if(remove)
            ((tileGameMap)mMap).getReadyForDeletion().add(this);

    }

    public void dispose(){
        makeImageNull();
        getBody().getFixtureList().clear();
        mPos = null;
    }
    @Override
    public void render(SpriteBatch batch){
       //shoot requested draw image
        Texture image = getImage();
        if((image != null) && (!remove))
            batch.draw(image,mPos.x, mPos.y, getWidth(), getHeight());
    }

    public boolean attack(){
       return true;
    }

    public void shoot(zombie target){

        float tangle;
        float vy;
        float vx;
        float yforce = 35.8f * 10000;
        float xforce = 25.8f * 10000;
        float tempx ;
        float tempy ;

        if(target != null) {
            //need to test
            //if we have a clear shot shoot zombie
            getEntDistance(target);
            tempx = target.getPosX() - this.mPos.x;
            tempy = target.getPosY() - this.mPos.y;
            tangle = (float)Math.atan(tempy/tempx);
            vy = (float)(vSpeed * Math.sin(tangle));
            vx = (float) (vSpeed * Math.cos(tangle));
            Vector2 tv = new Vector2(vx,vy); //this might slow things down, change later
            getBody().applyLinearImpulse(tv, getBody().getWorldCenter(), true);
        } else {
            if (!fired) {
                fired = true;
                // need to grab shooter current direction ebfore the bullet is allocated
                //getting set to 0 ??
                int tD = shooter.getWlkDirection();
                if ((tD == 5) || (tD == 0)) // if we are stopped get the direction we are looking in
                    tD = shooter.getPrevDrct();
                //temp
                tD = 2;
                switch (tD) {
                    case 1:
                        //this.setMoveUp(true);
                        this.getBody().applyLinearImpulse(new Vector2(0, yforce), getBody().getWorldCenter(), true);
                        break;
                    case 2:
                        //this.setMoveRight(true);
                        this.getBody().applyLinearImpulse(new Vector2(xforce, 0), getBody().getWorldCenter(), true);
                        break;
                    case 3:
                        //this.setMoveDown(true);
                        this.getBody().applyLinearImpulse(new Vector2(0, -yforce), getBody().getWorldCenter(), true);
                        break;
                    case 4:
                        //this.setMoveLeft(true);
                        this.getBody().applyLinearImpulse(new Vector2(-xforce, 0), getBody().getWorldCenter(), true);
                        break;
                    default:
                }
            }
        }

    }
    public boolean handleMessage(Telegram msg) {
        return true;
    }
  /*  public void shoot(Vector2 vPos){
// a gun should be shooting a bullet but leave this here for debug
    }
*/
    public void shootDebug(player play){

//play.getHost().



    }
}
