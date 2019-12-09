package com.mygdx.entities.humans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.entities.BehaviorEnum;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.entity;
import com.mygdx.entities.objects.bomb;
import com.mygdx.world.GameOver_Screen;

import java.util.ArrayList;

public class player implements InputProcessor {

    private int points;
    private int HighScore;
    private int infects;
    private int kills;
    private int converts;
    private int myIndex;
    private int tIndex;
    public float gameOverTime = 1;
    private float saveTime = 1;
    private float screenX;
    private float screenY;

    private float CamXPos;
    private float CamYPos;

    private OrthographicCamera playCam;

    private entity host;
    private entity tempHost = null;

    private bomb bombRef;

    private ArrayList<zombie> ZombieRef;

    private boolean touchUp;
    private boolean attackButton;
    private boolean bombExploded = false;
    private boolean isAttackPressed = false;

    public boolean isLeftCyclePressed() {
        return leftCyclePressed;
    }

    public void setLeftCyclePressed(boolean leftCyclePressed) {
        this.leftCyclePressed = leftCyclePressed;
    }

    public boolean isRightCyclePressed() {
        return rightCyclePressed;
    }

    public void setRightCyclePressed(boolean rightCyclePressed) {
        this.rightCyclePressed = rightCyclePressed;
    }

    private boolean leftCyclePressed = false;
    private boolean rightCyclePressed = false;

    private Texture gameOver = new Texture("bomb.png");

    private Vector3 pos;

    private Preferences prefs;

    public player(entity host) {

        float wdth = Gdx.graphics.getWidth();
        float hght = Gdx.graphics.getHeight();
        playCam = new OrthographicCamera();
        playCam.setToOrtho(false, wdth - 1400, hght - 900);
        playCam.update();
        this.points = 0;
        this.infects = 0;
        this.kills = 0;
        this.converts = 0;
        Gdx.input.setInputProcessor(this);

        if(host.getClassID() == classIdEnum.PBomb)
            this.bombRef = (bomb)host;

        this.host = host;
        this.attackButton = false;

        prefs = Gdx.app.getPreferences("Spread_Stats_FILE");

        if (!prefs.contains("converts")) {
            prefs.putInteger("converts", 0);
            converts = 0;
        }

        if (prefs.contains("highScore")) {
            HighScore = prefs.getInteger("highScore");
        } else {
            prefs.putInteger("highScore", 0);
            HighScore = 0;
        }
    }

    public player(int points, int infects, int kills, int converts, zombie host) {
        float wdth = Gdx.graphics.getWidth();
        float hght = Gdx.graphics.getHeight();
        playCam = new OrthographicCamera();
        playCam.setToOrtho(false, wdth - 800, hght - 825);
        playCam.update();

        this.points = points;
        this.infects = infects;
        this.kills = kills;
        this.converts = converts;
        this.host = host;
        Gdx.input.setInputProcessor(this);
    }

    public int getHighScore() {
        return HighScore;
    }

    public void setBombRef(bomb bombRef) {
        this.bombRef = bombRef;
    }

    public bomb getBombRef() {
        return bombRef;
    }

    public float getCamXPos() {
        return CamXPos;
    }

    public float getCamYPos() {
        return CamYPos;
    }

    public void setAttackPressed(boolean attackPressed) {
        isAttackPressed = attackPressed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points){
        this.points += points;
    }

    public int getInfects() {
        return infects;
    }

    public void setInfects(int infects) {
        this.infects = infects;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getConverts() {
        return converts;
    }

    public void setConverts(int converts) {
        this.converts = converts;
    }

    public entity getHost() {
        return host;
    }

    public void setHost(zombie host) {
        this.host = host;
    }

    public void setZombieRef(ArrayList<zombie> ZombieRef) {
        this.ZombieRef = ZombieRef;
    }

    private boolean switchZombie(zombie host) {
        return false; // something got fucked up
    }

    public int ptsMgr(entity ent){
       int ptsHolder = 0;
        switch(ent.getClassID()) {
            case PBomb:
            case Zombie:
            case Person:
            case PZombie:
            case PPerson:
            case ConvertedPer:
                ptsHolder = 3;
                break;
            case Security:
            case Emt:
                ptsHolder = 7;
                break;
            case Cop:
                ptsHolder = 10;
                break;
            case Swat:
            case Medic:
                ptsHolder = 13;
                break;
            case Army:
            case Hazmat:
                ptsHolder = 20;
                break;
        }
        return ptsHolder;
    }

    public void update(float dTime) {
       // host.update(dTime);

        if(host.getClassID() == classIdEnum.PBomb)
            host.update(dTime);

        if(isAttackPressed)
        {

            if(!bombExploded) {
                //TODO place anamation
                if (host.attack()) {
                    points += ptsMgr(host);
                }
            }
            if(host.getClassID() == classIdEnum.PBomb)
                bombExploded = true;

            isAttackPressed = false;
        }


        int entSize;

        zombie tHost = null;
        if(leftCyclePressed){
            myIndex = getZombieIndex();
            tIndex = myIndex;
            entSize = ZombieRef.size();

            tIndex--;

            if(tIndex < 0)
                tIndex = entSize-1;

            tHost = ZombieRef.get(tIndex);
        }

        if(rightCyclePressed){
            myIndex = getZombieIndex();
            tIndex = myIndex;
            entSize = ZombieRef.size();

            tIndex++;

            if(tIndex > entSize - 1)
                tIndex = 0;

            tHost = ZombieRef.get(tIndex);
        }

        if(leftCyclePressed || rightCyclePressed) {
            //need to change class id
            if (myIndex!=tIndex) {
                if(tHost != null) {
                    ((zombie) host).setClassID(classIdEnum.ConvertedPer);
                    ((zombie) host).setCpuStatus(true);
                    ((zombie) host).setmAlerted(BehaviorEnum.WALK_RANDOMLY);
                    ((zombie) host).setWlkTime(0);
                    host = tHost;
                    ((zombie) host).setCpuStatus(false);

                    myIndex = tIndex;
                }
            }

            if(leftCyclePressed)
                leftCyclePressed = false;
            if(rightCyclePressed)
                rightCyclePressed = false;
        }

        if(bombExploded) {

            //gameOver_screen = new GameOver_Screen(this);
            //gameOver_screen.render(dTime);
            //TODO if no one turns into a zombie after an exposion after 30 seconds you failed the game try again
            //TODO hide bomb or showing explosion animation
            if(host.getClassID() != classIdEnum.ConvertedPer)
                if(host.getMap().getZombies().size() > 0) {

                    host = host.getMap().getZombies().get(0);
                    ((zombie)host).setCpuStatus(false);
                    host.setClassID(classIdEnum.PZombie);
                    bombExploded = false;
                } else {
                    gameOverTime -= .0001;
                }

            if(gameOverTime < 0)
            {
                //gameOver_screen.render(dTime);
            }

        }

        if(host.getBody().getPosition().x < 210) playCam.position.x = 210;
        else playCam.position.x = host.getBody().getPosition().x;

        if(host.getBody().getPosition().y < 100) playCam.position.y = 100;
        else playCam.position.y = host.getBody().getPosition().y;

        CamXPos = playCam.position.x;
        CamYPos = playCam.position.y;

        playCam.update();

        saveTime-= .0001;
        if (saveTime < 0) {
            saveTime = 1;
            /*
            totalkills += current kills
            totalplaytime += playtime;
             */
            int tempSize = host.getMap().getZombies().size();

            if(converts < tempSize) {
                converts = tempSize;
                tempSize = prefs.getInteger("converts");
                tempSize+= converts;
                prefs.putInteger("converts", converts);
            }

            if(HighScore < points) {
                HighScore = points;
                prefs.putInteger("highScore", HighScore);
                prefs.flush();
            }
        }
    }

    public void resetBomb() {
        //setPosition ??
            host = bombRef;
    }

    public OrthographicCamera getPlayCam() {
        return playCam;
    }

    public void setPlayCam(OrthographicCamera playCam) {
        this.playCam = playCam;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        //Log.d("TouchDown", "Screen Touched In X " + this.screenX + " Y " + this.screenY);
        //Touch Screen code was taken out 11.23.19 use this date as a reference to find the commmit : ZC
        //if longpressed then we are trying to get the zombie
        return true;
    }

    private int getZombieIndex() {
        int i;
        for (i = 0; i < ZombieRef.size(); i++)
            if(ZombieRef.get(i).equals(host))
                return i;
            return -1;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void showGameOver()
    {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}



