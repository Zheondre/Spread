package com.mygdx.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.entities.classIdEnum;
import com.mygdx.entities.humans.player;

import static com.mygdx.utils.entUtils.getStopVec;

public class GameOver_Screen {

    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed, attackPressed;
    OrthographicCamera cam;

    player player1;

    Image upImg = new Image(new Texture("up_button.png"));
    Image downImg = new Image(new Texture("down_button.png"));
    Image leftImg = new Image(new Texture("left_button.png"));
    Image rightImg = new Image(new Texture("right_button.png"));
    Image attackImg = new Image(new Texture("bomb.png"));
    Image leftCycle = new Image(new Texture("left_button.png"));
    Image rightCycle = new Image(new Texture("right_button.png"));

    public GameOver_Screen(final player playerOne) {
        this.player1 = playerOne;
        cam = new OrthographicCamera();
        viewport = new FitViewport(800, 480, cam);
        stage = new Stage(viewport); //this line might cause errors with batch

        Table attack = new Table();
        attack.left().bottom();

        Table table = new Table();
        table.left().bottom();


        upImg.setSize(50, 50);
        downImg.setSize(50, 50);
        leftImg.setSize(50, 50);
        rightImg.setSize(50, 50);
        attackImg.setSize(100, 100);
        leftCycle.setSize(50, 50);
        rightCycle.setSize(50, 50);

        Gdx.input.setInputProcessor(stage);
        upImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                player1.getHost().setMoveUp(upPressed);
                upImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("up_button_purple.png"))));

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
                player1.getHost().setMoveUp(upPressed);
                player1.getHost().setPrevDrct(1); // debug for shooting
                upImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("up_button.png"))));
            }
        });

        downImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                player1.getHost().setMoveDown(downPressed);
                downImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("down_button_purple.png"))));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
                downImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("down_button.png"))));
                player1.getHost().setMoveDown(downPressed);
                player1.getHost().setPrevDrct(3);
            }
        });

        leftImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                player1.getHost().setMoveLeft(leftPressed);
                leftImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("left_button_purple.png"))));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
                player1.getHost().setMoveLeft(leftPressed);
                player1.getHost().setPrevDrct(4);
                leftImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("left_button.png"))));
            }
        });

        rightImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                player1.getHost().setMoveRight(rightPressed);
                rightImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("right_button_purple.png"))));
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
                player1.getHost().setMoveRight(rightPressed);
                player1.getHost().setPrevDrct(2);
                rightImg.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("right_button.png"))));
            }
        });

        attackImg.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = true;
                player1.setAttackPressed(attackPressed);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = false;

                //  player1.setAttackPressed(attackPressed);
            }
        });

        leftCycle.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player1.setLeftCyclePressed(true);
                leftCycle.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("left_button_purple.png"))));
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftCycle.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("left_button.png"))));
                // player1.setLeftCyclePressed(false);
            }
        });

        rightCycle.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                player1.setRightCyclePressed(true);
                rightCycle.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("right_button_purple.png"))));
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightCycle.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("right_button.png"))));
                //player1.setRightCyclePressed(false);
            }
        });


        table.add();
        table.add(upImg).size(upImg.getWidth(), upImg.getHeight());
        table.add();
        table.row().pad(5, 5, 5, 5);
        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add();
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
        table.row().padBottom(30);
        table.add();
        table.add(downImg).size(downImg.getWidth(), downImg.getHeight());
        table.add();
        stage.addActor(table);



        attack.row().pad(5, 550, 60, 5);
        attack.add(leftCycle).size(leftCycle.getWidth(), leftCycle.getHeight());
        attack.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight()).padLeft(5);
        attack.add(rightCycle).size(rightCycle.getWidth(), rightCycle.getHeight()).padLeft(5);

        stage.addActor(attack);

        /*attack.row().pad(5, 650, 75, 5);
        attack.add(leftCycle).size(leftCycle.getWidth(), leftCycle.getHeight());
        attack.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight());
        attack.add(rightCycle).size(rightCycle.getWidth(), rightCycle.getHeight());
        stage.addActor(attack);*/
    }

    public void draw() {
        stage.draw();
    }

    public boolean isUpPressed() {

        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isAttackPressed() {
        return attackPressed;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}