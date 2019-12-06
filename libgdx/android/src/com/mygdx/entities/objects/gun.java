package com.mygdx.entities.objects;

import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.humans.zombie;
import com.mygdx.world.gameMap;
import com.mygdx.world.tileGameMap;

public class gun extends entity {

  private int mags;
  private int bullets;
  private int bulletsRemaining;

  private float reloadRate;
  private float reloadTime;
  private float fireRate;

  private boolean equiped;

    public void setShooter(zombie shooter) {
        this.shooter = shooter;
    }

    private zombie shooter;

   public gun(classIdEnum guntype, gameMap Map){
       super();
       //this.mMap = Map;
        this.weapon = guntype;
       switch(weapon) {
           case PISTOL:
               reloadRate = 2;
               bullets = 8;
           case UZI:
               reloadRate = 3;
               bullets = 30;
           case SHOTTI:
               reloadRate= .5f;
               bullets = 12;
           case SEMIAUTORIFLE:
               bullets = 45;
               reloadRate = 5;
       }
       bulletsRemaining = bullets;
       equiped = false;
    }

    public gun(classIdEnum guntype, zombie shooter){
       super();
        this.shooter = shooter;
        this.weapon = guntype;
        switch(weapon) {
            case PISTOL:
                bullets = 8;
            case UZI:
                bullets = 30;
            case SHOTTI:
                bullets = 12;
            case SEMIAUTORIFLE:
                bullets = 45;
        }

        bulletsRemaining = bullets;
        equiped = true;
    }

    public boolean attack(){
        bullet tbullet = null;

        if (bulletsRemaining  > -1) {
            tbullet = new bullet(entityInfo.BULLET, shooter,(zombie)shooter.getPrey(), mMap);
            //only add if we were able to shoot
            ((tileGameMap) mMap).getBullets().add(tbullet);
            tbullet.shoot();
            bulletsRemaining--;
        } else {
            if(reloadRate > reloadTime)
                reloadTime+= .5;
            else if(weapon == classIdEnum.SHOTTI)
                bulletsRemaining++;
            else
                bulletsRemaining = bullets;
        }
        return true;
    }

    public void render(SpriteBatch batch){
        Texture image = getImage();
        if(!equiped) { // only render if we are on the floor
            if (image != null)
                batch.draw(image, mPos.x, mPos.y, getWidth(), getHeight());
        }
    }

    public boolean handleMessage(Telegram msg){
       return false;
    }

}
