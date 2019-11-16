package com.mygdx.entities;


// tutorial link
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.utils.SteeringUtils;

public class Box2dSteering implements Steerable<Vector2> {


   public Body body;

    boolean tagged;
    float boundRadius;
    float maxLinearSpeed, maxLinearAcc;
    float maxaAngleSpeed, maxAngleAcc;

    public boolean isIndependentFacing() {
        return independentFacing;
    }

    public void setIndependentFacing(boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    boolean independentFacing;



    SteeringBehavior<Vector2> behavior; //might have to put these in the entity class

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());


    public Box2dSteering(Body body, float boundRadius){
        this.body = body;
        this.boundRadius = boundRadius;

        this.tagged = false;

        //this.maxLinearSpeed = (float) (Math.random()*((55-47)+47))+1;
        this.maxLinearSpeed = 40;
        this.maxLinearAcc = 4500;
        this.maxaAngleSpeed = 10;
        this.maxAngleAcc = 10;

        this.independentFacing = true; // will change later
        //this.independentFacing = independentFacing;
     this.body.setUserData(this);
    }


    private void applySteering(float dt){
        boolean anyAcc = false;

        if(!steeringOutput.linear.isZero()) {
            Vector2 force = (Vector2)steeringOutput.linear.scl(dt);
            body.applyForceToCenter(force,true);
            anyAcc = true;
        }

        // Update orientation and angular velocity
        if (isIndependentFacing()) {
            if (steeringOutput.angular != 0) {
                // this method internally scales the torque by deltaTime
                body.applyTorque(steeringOutput.angular, true);
                anyAcc = true;
            }
        } else {
            // If we haven't got any velocity, then we can do nothing.
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * dt); // this is superfluous if independentFacing is always true
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        if(anyAcc){
           /*float currentSpeed = body.getLinearVelocity().len2();
            if(currentSpeed > maxLinearSpeed * maxLinearSpeed)
               body.setLinearVelocity(body.getLinearVelocity().scl(maxLinearSpeed/(float)Math.sqrt(currentSpeed)));
*/
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float)Math.sqrt(currentSpeedSquare)));
            }

            // Cap the angular speed
            float maxAngVelocity = getMaxAngularSpeed();
            if (body.getAngularVelocity() > maxAngVelocity) {
                body.setAngularVelocity(maxAngVelocity);
            }
        }
    }

    public void update(float dt){
        if(behavior != null) {
            behavior.calculateSteering(steeringOutput);
            applySteering(dt);
        }
    }
    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(getPosition(), orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vect2Angle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
   return SteeringUtils.angle2vec(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return this.maxLinearAcc;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxAngleAcc = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxaAngleSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxaAngleSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return this.maxAngleAcc;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngleAcc = maxaAngleSpeed;
    }

    public Body getBody(){
     return this.body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior){
    this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior(){
    return this.behavior;
    }

}
