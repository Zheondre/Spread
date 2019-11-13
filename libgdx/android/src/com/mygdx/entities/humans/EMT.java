package com.mygdx.entities.humans;

import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entityInfo;
import com.mygdx.world.gameMap;

public class EMT extends person {

    private int healamt;
    private int antiInfect;


    public EMT(entityInfo entityType, gameMap map) {

        super(entityType, map);

        if (entityType.getId() == classIdEnum.Emt )
            this.setImage("emt.png");

    }
    /*
    public void transport();
    public void heal();
    public void treatInfection();
    public void provessMoves();
    public void callForHelp();
    */
}
