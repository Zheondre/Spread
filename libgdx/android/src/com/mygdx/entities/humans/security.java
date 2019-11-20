package com.mygdx.entities.humans;

import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.humans.person;
import com.mygdx.world.gameMap;

public class security extends person {
    // can knock down or whip zombies
    // tell people where they can get help

    //protected int armorPts;

    public security(entityInfo entityType, gameMap map) {
        super(entityType,map);

        if(entityType.getId() == classIdEnum.Security)
            this.setImage("security.png");

        armorPts =  entityType.getArmor();
    }

    @Override
    public boolean attack() {
        //return super.attack();
        return true;
    }
}
