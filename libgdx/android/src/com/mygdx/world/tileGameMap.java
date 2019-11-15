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
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;

import com.mygdx.entities.objects.gameBlocks;
import com.mygdx.entities.humans.person;
import com.mygdx.entities.humans.player;
import com.mygdx.entities.humans.zombie;

import java.util.ArrayList;

import static com.mygdx.utils.entUtils.getStopVec;

public class tileGameMap extends gameMap {

    public static final int STATSCREEN_WIDTH = 400;
    public static final int STATSCREEN_HEIGHT = 208;

    private static libgdxSreen statsScreen;

    private static SpriteBatch batch;

    private TiledMap m_TileMap;
    private OrthogonalTiledMapRenderer m_TileMapRender;
    private int mapWidth;
    private int mapHeight;

    //private Texture up_button;
    //private Texture right_button;
    //private Texture left_button;
    //private Texture down_button;
    private int tileWidth;
    private int tileHeight;

    private static World world;
    private Box2DDebugRenderer b2dr;

    private ArrayList<person> people;
    private ArrayList<zombie> zombies;
    private ArrayList<gameBlocks> gameBlocks;
    private ArrayList<person> CnvrtdEntRdy;

    private zombie convertedEnt;

    private player playerOne;

    Controller controller;

    public tileGameMap() {

        //up_button = new Texture("up_button.png");
        //right_button = new Texture("right_button.png");
        //left_button = new Texture("left_button.png");
        //down_button = new Texture("down_button.png");

        batch = new SpriteBatch();

        m_TileMap = new TmxMapLoader().load("house_road.tmx"); // we will have to make this dynamic based on user map selection
        m_TileMapRender = new OrthogonalTiledMapRenderer(m_TileMap);
        MapProperties MapProp = m_TileMap.getProperties();

        mapWidth =  MapProp.get("width", Integer.class)* MapProp.get("tilewidth", Integer.class);
        mapHeight =  MapProp.get("height", Integer.class)* MapProp.get("tileheight", Integer.class);

        world = new World(getStopVec(),true);// make sure to dispose of this when needed
        b2dr = new Box2DDebugRenderer();

        gameBlocks = new ArrayList<gameBlocks>();

        for(MapObject object : m_TileMap.getLayers().get(7).getObjects().getByType(RectangleMapObject.class))
        {
            gameBlocks.add(new gameBlocks(entityInfo.STATIC_OBJECT, this, ((RectangleMapObject)object).getRectangle()));
        }

        people = new ArrayList<person>();
        CnvrtdEntRdy = new ArrayList<person>();
        zombies = new ArrayList<zombie>();

        playerOne = new player(new zombie(entityInfo.ZPLAYER,this)); // temp
        //zombies.add(playerOne.getHost());

       // playerOne = new player(new person(CPlAYER,this));
        //people.add((person)playerOne.getHost());
        //people.add
        playerOne.setPeopleRef(people);

        for(int i = 0; i < 2; i++)
            zombies.add(new zombie(entityInfo.ZOMBIE,this));

        //debug
        for(int i = 0; i < 7; i++)
            people.add(new person(entityInfo.PERSON,this));

        statsScreen = new libgdxSreen(batch, people.size());

        controller = new Controller();

        // testing ai behaviors
        /*

        people.get(2).setPursueSB(people.get(0));//
       // people.get(2).setPursueSB(people.get(0).getSteerEnt());//
        people.get(2).getSteerEnt().setMaxLinearSpeed(50);
        people.get(2).getSteerEnt().setMaxLinearAcceleration(4000);
        people.get(2).getSteerEnt().setMaxAngularSpeed(20f);
        people.get(2).getSteerEnt().setMaxAngularAcceleration(10f);

*/

        //people.get(2).setArriveSB(people.get(0).getSteerEnt());// over shoots

    }

    @Override
    public void render(){

        m_TileMapRender.setView(this.playerOne.getPlayCam());
        m_TileMapRender.render();

        batch.setProjectionMatrix(statsScreen.stage.getCamera().combined);
        statsScreen.setNonZombies(people.size());
        //statsScreen.setCurrentLevel();
        //statsScreen.setPlayerScore(this.playerOne.getPoints());
        statsScreen.stage.draw(); // calling statsScreen.stage.draw() after batch.begin() will crash the program

        batch.setProjectionMatrix(this.playerOne.getPlayCam().combined);
        batch.begin();

        playerOne.getHost().render(batch);

        //batch.draw(up_button, Gdx.graphics.getWidth() - (up_button.getWidth() * 2), Gdx.graphics.getHeight()/5);
        //batch.draw(right_button, Gdx.graphics.getWidth() - right_button.getWidth(), Gdx.graphics.getHeight()/5 - right_button.getHeight());
        //batch.draw(down_button, Gdx.graphics.getWidth() - (down_button.getWidth() * 2), Gdx.graphics.getHeight()/5 - (down_button.getHeight() * 2));
        //batch.draw(left_button, Gdx.graphics.getWidth() - (left_button.getWidth() * 2), Gdx.graphics.getHeight()/5 - left_button.getHeight());

       for(zombie ent: zombies)
           ent.render(batch);

        for(person ent: people)
            ent.render(batch);

        people.get(0).render(batch);//z player
       /*
        people.get(1).render(batch);//person
        people.get(2).render(batch);//zombie
        */

        //box2d Debug
        b2dr.render(world,playerOne.getPlayCam().combined);
        controller.draw();
       batch.end();
    }

    @Override
    public void update(float deltaT){

       world.step(1/60f,6,2);// need to read docs on this

        for(zombie ent: zombies)
            ent.update(deltaT);

        for(person ent: people)
            ent.update(deltaT);

        playerOne.update(deltaT);

        int lastEntPos = CnvrtdEntRdy.size() - 1;
        // becarefull bellow here
        // safetly remove civilian turned zombies from civilian array
        while(lastEntPos > -1){
            getPeople().remove(CnvrtdEntRdy.get(lastEntPos));
            CnvrtdEntRdy.remove(lastEntPos);
            lastEntPos--;
        }

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

        //check to see who has died and clean them off the map ?

    }

    public void setBatch(SpriteBatch batch) {
        tileGameMap.batch = batch;
    }

    public void setCnvrtdEntRdy(person moveReady) { this.CnvrtdEntRdy.add(moveReady); }

    public zombie getConvertedEnt() { return convertedEnt; }

    public void setConvertedEnt(zombie convertedEnt) { this.convertedEnt = convertedEnt; }

    public ArrayList<person> getPeople() {return people; }

    public ArrayList<zombie> getZombies() { return zombies; }

    @Override

    public void disposeTileMap(){ m_TileMap.dispose(); }

    @Override
    public int getMapWidth(){ return mapWidth; }

    public World getWorld(){return world;}

    @Override
    public int getMapHeight(){ return mapHeight; }

    @Override

    public MapLayers getMapLayers(){ return m_TileMap.getLayers(); }

    public int getPixelWidth(){ return 0; }

    public int getPixelHeight(){ return 0; }

    public player getPlayerOne() { return playerOne; }

}
