package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Game extends GameApplication {
    private Entity player1;
    private Entity player2;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1000);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Slither.io 1v1");
        gameSettings.setVersion("0.1");
//        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initGame() {
        player1 = FXGL.entityBuilder()
                .at(333, 400).view(new Circle(20, Color.RED))
                .buildAndAttach();
        player2 = FXGL.entityBuilder()
                .at(666, 400).view(new Circle(20,Color.BLUE))
                .buildAndAttach();

    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () ->{
            player1.translateX(3);
        });
        FXGL.onKey(KeyCode.A, () ->{
            player1.translateX(-3);
        });
        FXGL.onKey(KeyCode.W, () ->{
            player1.translateY(-3);
        });
        FXGL.onKey(KeyCode.S, () ->{
            player1.translateY(3);
        });
        FXGL.onKey(KeyCode.RIGHT, () ->{
            player2.translateX(3);
        });
        FXGL.onKey(KeyCode.LEFT, () ->{
            player2.translateX(-3);
        });
        FXGL.onKey(KeyCode.UP, () ->{
            player2.translateY(-3);
        });
        FXGL.onKey(KeyCode.DOWN, () ->{
            player2.translateY(3);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
