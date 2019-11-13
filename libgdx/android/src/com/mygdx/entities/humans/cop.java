package com.mygdx.entities.humans;

import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entityInfo;
import com.mygdx.world.gameMap;

public class cop extends security {

    private byte donuts;
    //call for back up for cops or security
    // call for emt if some one needs help
    public cop(entityInfo entityType, gameMap map) {
        super(entityType, map);

        if(entityType.getId() == classIdEnum.Cop)
            this.setImage("cop.png");
    }

    @Override
    public boolean attack() {
        //return super.attack();
        return true;
    }
}
