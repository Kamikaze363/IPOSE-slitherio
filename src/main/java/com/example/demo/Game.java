package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

public class Game extends GameApplication {
    private Player player1;
    private Player player2;
    private Food food;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1000);
        gameSettings.setHeight(800);

        gameSettings.setTitle("Slither.io 1v1");
        gameSettings.setVersion("0.3");
//        gameSettings.setProfilingEnabled(true);
    }

    @Override
    protected void initGame() {
        player1 = new Player(333, 400, Color.RED);
        player2 = new Player(666, 400, Color.BLUE);

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
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player1, Entity food) {
                food.removeFromWorld();
            }
        });
    }



    public static void main(String[] args) {
        launch(args);
    }
}
