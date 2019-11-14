/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */


package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;
import com.mygdx.entities.person;
import com.mygdx.entities.player;
import com.mygdx.entities.zombie;

import java.util.ArrayList;

import static com.mygdx.entities.entityInfo.CPlAYER;

public class tileGameMap extends gameMap {

    private TiledMap m_TileMap;
    private OrthogonalTiledMapRenderer m_TileMapRender;
    private int mapWidth;
    private int mapHeight;

    //private Texture up_button;
    //private Texture right_button;
    //private Texture left_button;
    //private Texture down_button;

    Controller controller;

    private int tileWidth;
    private int tileHeight;

    private World world;
    private Box2DDebugRenderer b2dr;

    private ArrayList<entity> people;
    private ArrayList<entity> zombies;

    public ArrayList<entity> getPeople() {
        return people;
    }

    public ArrayList<entity> getZombies() {
        return zombies;
    }
    private player playerOne;

    public tileGameMap() {

        //up_button = new Texture("up_button.png");
        //right_button = new Texture("right_button.png");
        //left_button = new Texture("left_button.png");
        //down_button = new Texture("down_button.png");

        m_TileMap = new TmxMapLoader().load("house_road.tmx"); // we will have to make this dynamic based on user map selection
        m_TileMapRender = new OrthogonalTiledMapRenderer(m_TileMap);
        MapProperties MapProp = m_TileMap.getProperties();

        mapWidth =  MapProp.get("width", Integer.class)* MapProp.get("tilewidth", Integer.class);
        mapHeight =  MapProp.get("height", Integer.class)* MapProp.get("tileheight", Integer.class);

        world = new World(new Vector2(0,0),true);// make sure to dispose of this when needed
        b2dr = new Box2DDebugRenderer();

        people = new ArrayList<entity>();
        zombies = new ArrayList<entity>();

        playerOne = new player(new zombie(entityInfo.ZPLAYER,this));
        //playerOne = new player(new person(CPlAYER,this));

        playerOne.setPeopleRef(zombies);

        zombies.add(playerOne.getHost());
        zombies.add(new zombie(entityInfo.ZOMBIE,this));
        zombies.add(new zombie(entityInfo.ZOMBIE,this));

        people.add(new person(entityInfo.PERSON,this));
        people.add(new person(entityInfo.PERSON,this));
        people.add(new person(entityInfo.PERSON,this));


        // testing ai behaviors
        people.get(2).setPursueSB(people.get(0));//
       // people.get(2).setPursueSB(people.get(0).getSteerEnt());//
        people.get(2).getSteerEnt().setMaxLinearSpeed(50);
        people.get(2).getSteerEnt().setMaxLinearAcceleration(4000);
        people.get(2).getSteerEnt().setMaxAngularSpeed(20f);
        people.get(2).getSteerEnt().setMaxAngularAcceleration(10f);

        //people.get(2).setArriveSB(people.get(0).getSteerEnt());// over shoots

        controller = new Controller();

    }

    @Override
    public void render(SpriteBatch batch){
       m_TileMapRender.setView(this.playerOne.getPlayCam());
       m_TileMapRender.render();

       batch.setProjectionMatrix(this.playerOne.getPlayCam().combined);
       batch.begin();

       //for (int i = 0 : i < people.get )
       people.get(0).render(batch);//z player
        people.get(1).render(batch);//person
        people.get(2).render(batch);//zombie
        //batch.draw(up_button, Gdx.graphics.getWidth() - (up_button.getWidth() * 2), Gdx.graphics.getHeight()/5);
        //batch.draw(right_button, Gdx.graphics.getWidth() - right_button.getWidth(), Gdx.graphics.getHeight()/5 - right_button.getHeight());
        //batch.draw(down_button, Gdx.graphics.getWidth() - (down_button.getWidth() * 2), Gdx.graphics.getHeight()/5 - (down_button.getHeight() * 2));
        //batch.draw(left_button, Gdx.graphics.getWidth() - (left_button.getWidth() * 2), Gdx.graphics.getHeight()/5 - left_button.getHeight());
        //box2d Debug
        b2dr.render(world,playerOne.getPlayCam().combined);

        controller.draw();


        //render(batch);
       batch.end();
    }

    @Override
    public void update(float deltaT){ //update what the method name should say what we are updating

       world.step(1/60f,6,2);// need to read docs on this
        playerOne.update(deltaT); // zombie player

        people.get(1).update(deltaT);//person
        people.get(2).update(deltaT);//zombie
// check for new zombies and put them in the array
        // check to see who has died and clean them off the map ?

        if(controller.isDownPressed())
        {
            playerOne.getHost().getBody().setLinearVelocity(new Vector2(0, playerOne.getHost().getBody().getLinearVelocity().y));
            playerOne.getHost().setMoveDown(true);
            playerOne.getHost().setMoveUp(false);
            playerOne.getHost().setMoveLeft(false);
            playerOne.getHost().setMoveRight(false);
        }
        if(controller.isUpPressed())
        {
            playerOne.getHost().getBody().setLinearVelocity(new Vector2(0, playerOne.getHost().getBody().getLinearVelocity().y));
            playerOne.getHost().setMoveDown(false);
            playerOne.getHost().setMoveUp(true);
            playerOne.getHost().setMoveLeft(false);
            playerOne.getHost().setMoveRight(false);
        }
        if(controller.isRightPressed())
        {
            playerOne.getHost().getBody().setLinearVelocity(new Vector2(playerOne.getHost().getBody().getLinearVelocity().x, 0));
            playerOne.getHost().setMoveDown(false);
            playerOne.getHost().setMoveUp(false);
            playerOne.getHost().setMoveLeft(false);
            playerOne.getHost().setMoveRight(true);
        }
        if(controller.isLeftPressed())
        {
            playerOne.getHost().getBody().setLinearVelocity(new Vector2(playerOne.getHost().getBody().getLinearVelocity().x, 0));
            playerOne.getHost().setMoveDown(false);
            playerOne.getHost().setMoveUp(false);
            playerOne.getHost().setMoveLeft(true);
            playerOne.getHost().setMoveRight(false);
        }
        if(controller.isAttackPressed())
        {
            //Add attack code here
        }
        if(!controller.isUpPressed() && !controller.isDownPressed() && !controller.isLeftPressed() && !controller.isRightPressed())
        {
            playerOne.getHost().getBody().setLinearVelocity(0, 0);
        }

    }

    @Override
    public void disposeTileMap(){
        m_TileMap.dispose();
    }

    @Override
    public int getMapWidth(){ return mapWidth; }

    public World getWorld(){return world;}

    @Override
    public int getMapHeight(){ return mapHeight; }

    @Override
    public MapLayers getMapLayers(){ return m_TileMap.getLayers(); }

    public int getPixelWidth(){ return 0; }

    public int getPixelHeight(){ return 0; }

    public player getPlayerOne() {
        return playerOne;
    }

}
