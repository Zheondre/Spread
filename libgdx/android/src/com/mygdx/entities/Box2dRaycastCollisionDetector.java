package com.mygdx.entities;

import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

//https://github.com/libgdx/gdx-ai/blob/master/tests/src/com/badlogic/gdx/ai/tests/steer/box2d/Box2dRaycastCollisionDetector.java
// couldnt find the file in the includes

public class Box2dRaycastCollisionDetector implements RaycastCollisionDetector <Vector2>{
    World world;
    Box2dRaycastCallback callback;

    public Box2dRaycastCollisionDetector (World world) {
        this(world, new Box2dRaycastCallback());
    }

    public Box2dRaycastCollisionDetector (World world, Box2dRaycastCallback callback) {
        this.world = world;
        this.callback = callback;
    }

    //@Override
    public boolean collides (Ray<Vector2> ray) {
        return findCollision(null, ray);
    }

    //@Override
    public boolean findCollision (Collision<Vector2> outputCollision, Ray<Vector2> inputRay) {
        callback.collided = false;
        if (!inputRay.start.epsilonEquals(inputRay.end, MathUtils.FLOAT_ROUNDING_ERROR)) {
            callback.outputCollision = outputCollision;
            world.rayCast(callback, inputRay.start, inputRay.end);
        }

        return callback.collided;
    }

    public static class Box2dRaycastCallback implements RayCastCallback {
        public Collision<Vector2> outputCollision;
        public boolean collided;

        public Box2dRaycastCallback () {
        }

        @Override
        public float reportRayFixture (Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            //check the fixture if a zombie attack or evade depending on the class
            if (outputCollision != null) outputCollision.set(point, normal);
            collided = true;
            return fraction;
        }
    }
}
