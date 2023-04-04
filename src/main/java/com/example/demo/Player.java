package com.example.demo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;

public class Player {
    private final Entity entity;

    public Player(double x, double y, Color color) {
        entity = FXGL.entityBuilder()
                .at(x, y).viewWithBBox(new Circle(15, color))
                .with(new CollidableComponent(true))
                .type(EntityTypes.Player)
                .buildAndAttach();
    }

    public void move(KeyCode leftKey, KeyCode rightKey, KeyCode upKey, KeyCode downKey) {
        FXGL.onKey(leftKey, () -> {
            entity.translateX(-3);
        });
        FXGL.onKey(rightKey, () -> {
            entity.translateX(3);
        });
        FXGL.onKey(upKey, () -> {
            entity.translateY(-3);
        });
        FXGL.onKey(downKey, () -> {
            entity.translateY(3);
        });
    }

    public Entity getEntity() {
        return entity;
    }
}
