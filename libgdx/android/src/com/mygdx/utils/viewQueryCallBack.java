package com.mygdx.utils;


import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;

import java.util.ArrayList;

public class viewQueryCallBack implements QueryCallback {

    private ArrayList<entity> foundEnts;

  public viewQueryCallBack(){

        foundEnts = new ArrayList<entity>();
    }
    @Override
    public boolean reportFixture(Fixture fixture) {
      if(fixture.getUserData() != null) {
          if (((entity) fixture.getUserData()).getClassID() != classIdEnum.STATIC_OBJECT)
              this.foundEnts.add((entity) fixture.getUserData());
      }
    return true;
    }

    public ArrayList<entity> getFoundEnts() {
        return foundEnts;
    }
}
