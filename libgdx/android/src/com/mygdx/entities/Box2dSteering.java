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

    Body body;
    boolean tagged;
    float boundRadius;
    float maxLinearSpeed, maxLinearAcc;
    float maxaAngleSpeed, maxAngleAcc;

    SteeringBehavior<Vector2> behavior; //might have to put these in the entitiy class
    SteeringAcceleration steeringOutput;

    public Box2dSteering(Body body, float boundRadius ){
     this.body = body;
     this.boundRadius = boundRadius;

        this.tagged = false;
        this.maxLinearSpeed = 500;
        this.maxLinearAcc = 5000;
        this.maxaAngleSpeed = 30;
        this.maxAngleAcc = 5;
    }


    private void applySteering(float dt){
        boolean anyAcc = false;

        if(!steeringOutput.linear.isZero()) {
            Vector2 force = (Vector2)steeringOutput.linear.scl(dt);
            body.applyForceToCenter(force,true);
            anyAcc = true;
        }

        if(anyAcc){
            float currentSpeed = body.getLinearVelocity().len2();
            if(currentSpeed > maxLinearSpeed * maxLinearSpeed)
               body.setLinearVelocity(body.getLinearVelocity().scl(maxLinearSpeed/(float)Math.sqrt(currentSpeed)));

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
        return null;
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return 0;
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
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return 0;
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
        return 0;
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
