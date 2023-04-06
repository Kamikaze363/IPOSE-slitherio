package com.example.demo;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;


public class FireTowardsPlayerComponent extends Component {
    private final Entity player;

    FireTowardsPlayerComponent(Entity player) {
        this.player = player;
    }
    @Override
    public void onUpdate(double tpf) {
        Point2D playerPosition = player.getPosition();
        Point2D  enenemyPosition = entity.getPosition();

        Point2D direction = playerPosition.subtract(enenemyPosition).normalize();
        entity.translate(direction.multiply(tpf * 100));
    }
}
