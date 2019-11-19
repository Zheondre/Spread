package com.mygdx.entities.humans;

import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entityInfo;
import com.mygdx.world.gameMap;

public class EMT extends person {

    private int healamt;
    private int antiInfect = 5;


    public EMT(entityInfo entityType, gameMap map) {

        super(entityType, map);

        if (entityType.getId() == classIdEnum.Emt )
            this.setImage("emt.png");

    }

  @Override
    public void dispose(){
        super.dispose();
    }
    public void heal(){

    }
    /*
    public void transport();
    public void heal();
    public void treatInfection();
    public void provessMoves();
    public void callForHelp();
    */
}
