package com.mygdx.world;

import android.media.audiofx.DynamicsProcessing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.entities.humans.player;

// Turtorial Libdx part 4 creating a Hud - Super Mario Bros

public class libgdxSreen {

    public Stage stage;
    private Viewport viewport;

    private float levelTime;
    private int playerScore;
    private int nonZombies;
    private byte currentLevel;

    Label cntDwnLabel;
    Label scoreLabel;
    Label peopleLabel;
    Label levelLabel;

    Label cntDwnLabelValue;
    Label scoreLabelValue;
    Label peopleLabelValue;
    Label levelLabelValue;

    public libgdxSreen(SpriteBatch batch, int nonZombies){

        this.playerScore = 0;
        this.nonZombies = nonZombies; //get the size of the people array
        this.levelTime = 120;
        this.currentLevel = 1;

        viewport = new FillViewport(tileGameMap.STATSCREEN_WIDTH, tileGameMap.STATSCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //cntDwnLabel = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        scoreLabel = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        peopleLabel = new Label("Prey", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        levelLabel = new Label("Level", new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));


        //cntDwnLabelValue = new Label(String.format("%03d",levelTime), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        scoreLabelValue = new Label(String.format("%06d",playerScore), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        peopleLabelValue = new Label(String.format("%02d",this.nonZombies), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));
        levelLabelValue = new Label(String.format("%01d",currentLevel), new Label.LabelStyle(new BitmapFont(), Color.DARK_GRAY));

        //table.add(cntDwnLabel).expandX().pad(10); // should we even do a countdown ?
        table.add(scoreLabel).expandX().pad(2);
        table.add(peopleLabel).expandX().pad(2);
        table.add(levelLabel).expandX().pad(2);
        //not sure where to put the score so ill add later;
        table.row();
        //table.add(cntDwnLabelValue).expandX();
        table.add(scoreLabelValue).expandX();
        table.add(peopleLabelValue).expandX();
        table.add(levelLabelValue).expandX();

        stage.addActor(table);

    }

    public void updateScreen(tileGameMap gm){
        //cntDwnLabelValue.setText();
        scoreLabelValue.setText(gm.getPlayerOne().getPoints());
        peopleLabelValue.setText(gm.getPeople().size());
        levelLabelValue.setText(gm.getCurrentLevel());
        stage.draw();
    }

    //display health
    //display armor
}
