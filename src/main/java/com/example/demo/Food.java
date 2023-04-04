package com.example.demo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;

public class Food {
    private final Entity entity;

    public Food(double x, double y) {
        entity = FXGL.entityBuilder()
                .at( x, y).viewWithBBox(new Circle(5, Color.WHITE))
                .with(new CollidableComponent(true))
                .type(EntityTypes.Food)
                .buildAndAttach();
    }

    public Entity getEntity() {
        return entity;
    }
}
