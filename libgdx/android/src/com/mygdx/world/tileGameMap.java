/*
Tutorial:
https://www.youtube.com/watch?v=MAFawG6lEkw

they also explain how to modify the tiles in a map during game play, we wont add that for now.

 */


package com.mygdx.world;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.entityInfo;

import com.mygdx.entities.objects.bomb;
import com.mygdx.entities.objects.gameBlocks;
import com.mygdx.entities.humans.person;
import com.mygdx.entities.humans.player;
import com.mygdx.entities.humans.zombie;
import com.mygdx.game.WaveInfo;

import java.util.ArrayList;

import static com.mygdx.entities.entityInfo.CPlAYER;
import static com.mygdx.entities.entityInfo.PBOMB;
import static com.mygdx.entities.entityInfo.ZOMBIE;
import static com.mygdx.utils.entUtils.getStopVec;


public class tileGameMap extends gameMap {

    public static Texture playerHealth;

    private classIdEnum DEBUGMODE = classIdEnum.PBomb;
    //private classIdEnum DEBUGMODE = classIdEnum.PZombie;
    //private classIdEnum DEBUGMODE = classIdEnum.PPerson;

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

    private int currentLevel;
    private int levelAmount;

    private static World world;
    private Box2DDebugRenderer b2dr;

    private ArrayList<person> people;
    private ArrayList<zombie> zombies;
    private ArrayList<gameBlocks> gameBlocks;
    private ArrayList<person> CnvrtdEntRdy;
    private ArrayList<WaveInfo> levels;
    private ArrayList<entity> ReadyForDeletion;
    private zombie convertedEnt;

    private player playerOne;
    private static Controller controller;

    private boolean paused = false;

    MessageManager mgMang;

    public tileGameMap() {

        //up_button = new Texture("up_button.png");
        //right_button = new Texture("right_button.png");
        //left_button = new Texture("left_button.png");
        //down_button = new Texture("down_button.png");

        playerHealth = new Texture("blank.jpg"); // will move later

        batch = new SpriteBatch();
        levelAmount = 3;
        currentLevel = 0;
        m_TileMap = new TmxMapLoader().load("house_road.tmx"); // we will have to make this dynamic based on user map selection
        m_TileMapRender = new OrthogonalTiledMapRenderer(m_TileMap);
        MapProperties MapProp = m_TileMap.getProperties();

        mapWidth =  MapProp.get("width", Integer.class)* MapProp.get("tilewidth", Integer.class);
        mapHeight =  MapProp.get("height", Integer.class)* MapProp.get("tileheight", Integer.class);

        world = new World(getStopVec(),true);// make sure to dispose of this when needed
        b2dr = new Box2DDebugRenderer();

        gameBlocks = new ArrayList<gameBlocks>();

        for(MapObject object : m_TileMap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))
        {
            gameBlocks.add(new gameBlocks(entityInfo.STATIC_OBJECT, this, ((RectangleMapObject)object).getRectangle()));
        }

        people = new ArrayList<person>();
        CnvrtdEntRdy = new ArrayList<person>();
        zombies = new ArrayList<zombie>();
        levels = new ArrayList<WaveInfo>();
        ReadyForDeletion = new ArrayList<>();

        switch (DEBUGMODE) {
            case PBomb:
                playerOne = new player(new bomb(PBOMB,this));
                break;
            case PPerson:
                playerOne = new player(new person(CPlAYER,this));
                people.add((person)playerOne.getHost());
                break;
            case PZombie:
                playerOne = new player(new zombie(entityInfo.ZPLAYER,this));
                zombies.add((zombie) playerOne.getHost());
                break;
        }

        /*
        for(int i = 0; i < levelAmount; i++) {
            levels.add(new WaveInfo(i, 10, , 0, 0, 0 ));
        }
        */

        for(int i = 0; i < 2; i++)
            zombies.add(new zombie(entityInfo.ZOMBIE,this));

        //debug
        for(int i = 0; i < 5; i++)
            people.add(new person(entityInfo.PERSON,this));

        statsScreen = new libgdxSreen(batch, people.size());
        controller = new Controller(playerOne);

        playerOne.setPeopleRef(people);

        initMessage();

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

    private void initMessage(){
        this.mgMang = MessageManager.getInstance();
    }
    public MessageManager getMgMang() {
        return mgMang;
    }
    @Override
    public void render(){

        m_TileMapRender.setView(this.playerOne.getPlayCam());
        m_TileMapRender.render();

        batch.setProjectionMatrix(statsScreen.stage.getCamera().combined);

        statsScreen.updateScreen(this); // calling statsScreen.stage.draw() after batch.begin() will crash the program

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

       /*
        people.get(1).render(batch);//person
        people.get(2).render(batch);//zombie
        */

        //box2d Debug
   //   b2dr.render(world,playerOne.getPlayCam().combined);
        controller.draw();
        batch.end();
    }

    @Override
    public void update(float deltaT){

       world.step(1/60f,6,2);// need to read docs on this

        for(zombie ent: zombies) //if ready for clean up skip
            ent.update(deltaT);

        for(person ent: people) //if ready for clean up skip
            ent.update(deltaT);

        playerOne.update(deltaT);

        int lastEntPos = CnvrtdEntRdy.size() - 1;
        // becarefull bellow here
        // safetly remove civilian turned zombies from civilian array
        while(lastEntPos > 0){
            getPeople().remove(CnvrtdEntRdy.get(lastEntPos));
            CnvrtdEntRdy.remove(lastEntPos);
            lastEntPos--;
        }

        lastEntPos = ReadyForDeletion.size()-1;
        entity entToBeDeleted;
        while(lastEntPos > 0) {
            entToBeDeleted = ReadyForDeletion.get(lastEntPos);
            switch(entToBeDeleted.getClassID()) {
                case Emt:
                case Security:
                case Hazmat:
                case Person:
                case Army:
                case Medic:
                    getPeople().remove(entToBeDeleted);// to be on the safe side im removing the exact instance of the object instad of the index
                    break;
                case ConvertedPer:
                case Zombie:
                    getZombies().remove(entToBeDeleted);
                    default:
            }
            ReadyForDeletion.remove(entToBeDeleted);
            entToBeDeleted.dispose();// index one size one error ? 8.14pm 11.19.19
            entToBeDeleted = null;
        }

        //check to see who has died and clean them off the map ?
    }

    public void waveLogic() {
        // if all civilans are converted add current level * 10 points
        //

        if(people.size() == 0) {
            ;//go to next wave and delete previous one
            //if all civilans are converted add current level * 10 point
        }

        if(zombies.size() == 0) {
            ; //all zombied were killed or no one was converted say game over
        }
    }

    public ArrayList<entity> getReadyForDeletion() {
        return ReadyForDeletion;
    }
    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setBatch(SpriteBatch batch) {
        tileGameMap.batch = batch;
    }

    public Texture getPlayerHealth() {
        return playerHealth;
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
