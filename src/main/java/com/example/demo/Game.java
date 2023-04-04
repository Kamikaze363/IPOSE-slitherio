package com.example.demo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Game extends GameApplication {
    private Entity player;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Slither.io 1v1");
        gameSettings.setVersion("0.1");

    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(400, 400).view(new Rectangle(30, 30, Color.RED))
                .buildAndAttach();
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () ->{
            player.translateX(3);
        });
        FXGL.onKey(KeyCode.A, () ->{
            player.translateX(-3);
        });
        FXGL.onKey(KeyCode.W, () ->{
            player.translateY(-3);
        });
        FXGL.onKey(KeyCode.S, () ->{
            player.translateY(3);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
