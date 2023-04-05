package com.example.demo;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class Game extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1200);
        settings.setHeight(700);

        settings.setTitle("Slither.io 1v1");
        settings.setVersion("17.2");

        settings.setProfilingEnabled(true);

        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setManualResizeEnabled(true);
        settings.setPreserveResizeRatio(true);
        settings.setIntroEnabled(true);
        settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));
        settings.getCredits().addAll(Arrays.asList(
                "BIG Shoutout naar de makers:",
                "Ali",
                "Rolan",
                "Ramy"
        ));
        settings.setApplicationMode(ApplicationMode.RELEASE);
    }


    @Override
    protected void initGame() {

        Player player1 = new Player(200, 400, Color.RED, EntityTypes.Player1,50);
        Player player2 = new Player(1000, 400, Color.BLUE, EntityTypes.Player2, 50);

        FXGL.getGameWorld().addEntity(player1.getEntity());
        FXGL.getGameWorld().addEntity(player2.getEntity());

        FXGL.getGameTimer().runAtInterval(() -> {
            int randomPosX = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() - 80);
            int randomPosY = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() - 80);
             Food food = new Food(randomPosX, randomPosY);
        }, Duration.seconds(0.5));

        player1.move(KeyCode.A, KeyCode.D, KeyCode.W, KeyCode.S);
        player2.move(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN);
    }



    @Override
    protected void initPhysics() {

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player1, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                FXGL.inc("kills player 1", +1);
                food.removeFromWorld();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player2, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                FXGL.inc("kills player 2", + 1);
                food.removeFromWorld();
            }
        });

    }

    @Override
    protected void initUI() {
        Label p1pointsCaller = new Label("Player 1 points:");
        Label p2pointsCaller = new Label("Player 2 points:");

        Label player1points = new Label("killsP1");
        Label player2points = new Label("killsP2");

        p1pointsCaller.setStyle("-fx-text-fill: white");
        p2pointsCaller.setStyle("-fx-text-fill: white");

        player1points.setStyle("-fx-text-fill: white");
        player2points.setStyle("-fx-text-fill: white");

        p1pointsCaller.setTranslateX(30);
        p1pointsCaller.setTranslateY(30);

        p2pointsCaller.setTranslateX(30);
        p2pointsCaller.setTranslateY(50);

        player1points.setTranslateX(120);
        player1points.setTranslateY(30);

        player2points.setTranslateX(120);
        player2points.setTranslateY(50);

        player1points.textProperty().bind(FXGL.getWorldProperties().intProperty("kills player 1").asString());
        player2points.textProperty().bind(FXGL.getWorldProperties().intProperty("kills player 2").asString());

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        FXGL.getGameScene().addUINode(p1pointsCaller);
        FXGL.getGameScene().addUINode(p2pointsCaller);

        FXGL.getGameScene().addUINode(player1points);
        FXGL.getGameScene().addUINode(player2points);
    }



    @Override
    public void initGameVars(Map<String, Object> vars) {
        vars.put("kills player 1",0);
        vars.put("kills player 2", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
