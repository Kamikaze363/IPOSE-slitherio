package com.example.demo;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class FireTowardsPlayerComponent extends Component {
    private final Entity player;





    public FireTowardsPlayerComponent(double x, double y, EntityTypes type, Color color, Entity player){
        this.player = player;
        entity = FXGL.entityBuilder()
                .at(x, y).viewWithBBox(new Circle(30, color))
                .with(new CollidableComponent(true))
                .type(type)
                .buildAndAttach();
    }
    @Override
    public void onUpdate(double tpf) {
        Point2D playerPosition = player.getPosition();
        Point2D  enenemyPosition = entity.getPosition();

        Point2D direction = playerPosition.subtract(enenemyPosition).normalize();
        entity.translate(direction.multiply(tpf * 100));
    }
}
