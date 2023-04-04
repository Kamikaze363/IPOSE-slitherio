package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class Game extends GameApplication {
    private Player player1;
    private Player player2;
    private Food food;

    private int player1kills = 0;
    private int player2kills = 0;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1000);
        gameSettings.setHeight(800);

        gameSettings.setTitle("Slither.io 1v1");
        gameSettings.setVersion("0.3");
        gameSettings.setProfilingEnabled(true);
    }

    @Override
    protected void initGame() {
        player1 = new Player(100, 400, Color.RED, EntityTypes.Player1);
        player2 = new Player(900, 400, Color.BLUE, EntityTypes.Player2);

        FXGL.getGameWorld().addEntity(player1.getEntity());
        FXGL.getGameWorld().addEntity(player2.getEntity());

        FXGL.getGameTimer().runAtInterval(() -> {
            int randomPosX = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() - 80);
            int randomPosY = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() - 80);
            food = new Food(randomPosX, randomPosY);
        }, Duration.seconds(1));

        player1.move(KeyCode.A, KeyCode.D, KeyCode.W, KeyCode.S);
        player2.move(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN);
    }

    @Override
    protected void initPhysics() {

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player1, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                food.removeFromWorld();
                player1kills += 1;
                System.out.println("Player 1: " + player1kills);

            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player2, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                food.removeFromWorld();
                player2kills += 1;
                System.out.println("Player 2: " + player2kills);

            }
        });

    }

    @Override
    protected void initUI() {
        Label player1points = new Label("Player 1:" + player1kills);
        Label player2points = new Label("Player 2:" + player2kills);
        player1points.setTranslateX(80);
        player1points.setTranslateY(100);
        player2points.setTranslateX(80);
        player2points.setTranslateY(120);

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameScene().addUINode(player1points);
        FXGL.getGameScene().addUINode(player2points);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
