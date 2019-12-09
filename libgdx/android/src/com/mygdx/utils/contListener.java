package com.mygdx.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.humans.zombie;
import com.mygdx.entities.objects.bullet;

public class contListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        boolean didIGetShot = false;

        bullet b = null;

        if (((entity) fixA.getUserData()).getClassID() == classIdEnum.Bullet) {
            //((bullet) fixA.getUserData()).setRemove(true);
            b = ((bullet) fixA.getUserData());
            didIGetShot = true;
        }
        if (((entity) fixB.getUserData()).getClassID() == classIdEnum.Bullet) {
            //((bullet) fixB.getUserData()).setRemove(true);
            b = ((bullet) fixB.getUserData());
            didIGetShot = true;
        }

        if(didIGetShot){
        if (((entity) fixA.getUserData()).isLivingObject()
                ||
                ((entity) fixB.getUserData()).isLivingObject()) {

            entity ent = ((entity) fixA.getUserData()).isLivingObject() == true ? ((entity) fixA.getUserData()) : ((entity) fixB.getUserData());

            //temp

            //
            // temp need to change this later if the zombie cop wants to shoot
            if(ent.getClassID() == classIdEnum.Cop)
                return;

            float t1 = ((zombie) ent).getArmorPts();
            float temp = ((zombie) ent).getArmorPts() - .2f;

            if (t1 > 0) {
                if (temp < 0) {
                    temp += t1;
                    ((zombie) ent).dcrseArmor(temp);
                    ((zombie) ent).decreaseHlth(t1 - temp);
                } else {
                    ((zombie) ent).dcrseArmor(.2f);
                }
            } else {
                ((zombie) ent).decreaseHlth(.2f);
            }

            if(ent.getClassID() != classIdEnum.Cop)
                b.setRemove(true);
        }
    }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold pastManld) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
