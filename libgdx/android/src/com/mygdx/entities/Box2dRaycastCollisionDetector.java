package com.mygdx.entities;

import android.util.Log;

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.utils.SteeringUtils;
import com.mygdx.world.tileGameMap;

import java.util.ArrayList;

//https://github.com/libgdx/gdx-ai/blob/master/tests/src/com/badlogic/gdx/ai/tests/steer/box2d/Box2dRaycastCollisionDetector.java
// couldnt find the file in the com.badlogic import

public class Box2dRaycastCollisionDetector implements RaycastCollisionDetector <Vector2>{
    World world;
    tileGameMap map;
    Box2dRaycastCallback callback;

    public Box2dRaycastCollisionDetector (tileGameMap map) {
        this(map.getWorld(), new Box2dRaycastCallback());
        this.map = map;
    }

    public Box2dRaycastCollisionDetector (World world) {
        this(world, new Box2dRaycastCallback());
    }

    public Box2dRaycastCollisionDetector (World world, Box2dRaycastCallback callback) {
        this.world = world;
        this.callback = callback;
    }

    public Box2dRaycastCallback getCallback() {
        return callback;
    }

    @Override
    public boolean collides (Ray<Vector2> ray) {
        return findCollision(null, ray);
    }

    @Override
    public boolean findCollision (Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        callback.collided = false;
        //SteeringUtils.DrawDebugLine(inputRay.start, inputRay.end, map.getPlayerOne().getPlayCam().combined);

        if (!inputRay.start.epsilonEquals(inputRay.end, MathUtils.FLOAT_ROUNDING_ERROR)) {
            callback.outputCollision = outputCollision;
            world.rayCast(callback, inputRay.start, inputRay.end);
        }

        return callback.collided;
    }

    public static class Box2dRaycastCallback implements RayCastCallback {
        public Collision<Vector2> outputCollision;
        public boolean collided;

        private static ArrayList<entity> foundEnts;

        public Box2dRaycastCallback () {
            this.foundEnts = new ArrayList<entity>();
        }

        @Override
        public float reportRayFixture (Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            //check the fixture if a zombie attack or evade depending on the class
            if (outputCollision != null) outputCollision.set(point, normal);
            //Log.d("Fixture Detected", "distance is " + fraction);
            collided = true;
            if(fixture.getUserData() != null) {
                switch(((entity)fixture.getUserData()).getClassID()) {
                    case Zombie:
                    case PZombie:
                    case ConvertedPer:
                        this.foundEnts.add((entity) fixture.getUserData());
                        Log.d("Zombie Fixture Detected", "distance is " + fraction);
                    break;
                }
               //Log.d("Fixt Detected", "distance " + fraction);
            }
            return fraction;
        }

        public static ArrayList<entity> getFoundEnts() {
            return foundEnts;
        }
    }
}
