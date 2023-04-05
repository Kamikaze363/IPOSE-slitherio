package com.example.demo;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final Entity head;
    private final List<Entity> bodyParts;

    public Player(double x, double y, Color color, EntityTypes type, int snakeLength) {
        head = FXGL.entityBuilder()
                .at(x, y).viewWithBBox(new Circle(15, color))
                .with(new CollidableComponent(true))
                .type(type)
                .buildAndAttach();

        bodyParts = new ArrayList<>();
        for (int i = 0; i < snakeLength; i++) {
            Entity bodyPart = FXGL.entityBuilder()
                    .at(x - (i + 1) * 2, y).viewWithBBox(new Circle(15, color))
                    .with(new CollidableComponent(true))
                    .type(type)
                    .buildAndAttach();
            bodyParts.add(bodyPart);
        }
    }


    public void move(KeyCode leftKey, KeyCode rightKey, KeyCode upKey, KeyCode downKey) {
        FXGL.onKey(leftKey, () -> {
            moveHead(-2, 0);
        });
        FXGL.onKey(rightKey, () -> {
            moveHead(2, 0);
        });
        FXGL.onKey(upKey, () -> {
            moveHead(0, -2);
        });
        FXGL.onKey(downKey, () -> {
            moveHead(0, 2);
        });
    }

    private void moveHead(double dx, double dy) {
        double oldX = head.getX();
        double oldY = head.getY();

        head.translateX(dx);
        head.translateY(dy);

        for (Entity bodyPart : bodyParts) {
            double tmpX = bodyPart.getX();
            double tmpY = bodyPart.getY();

            bodyPart.setX(oldX);
            bodyPart.setY(oldY);

            oldX = tmpX;
            oldY = tmpY;
        }
    }

    public Entity getEntity() {
        return head;
    }
}
