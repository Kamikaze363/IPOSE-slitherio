package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.LinkedList;
import java.util.Queue;

public class Slangen {
    // Constanten voor slanggrootte, initiële lengte en startpositie
    private static final double SLANG_RADIUS = 10;
    private static final int INITIELE_LENGTE = 10;
    private static final int INITIELE_POSITIE_X = 100;
    private static final int INITIELE_POSITIE_Y = 100;

    // Queue om de lichaamsdelen van de slang op te slaan
    private Queue<Circle> lichaam;
    // Richting waarin de slang beweegt
    private double richtingX;
    private double richtingY;
    // Groep voor het weergeven van de slang in JavaFX
    private Group slangGroup;

    public Slangen() {
        lichaam = new LinkedList<>();
        richtingX = 0;
        richtingY = 0;
        slangGroup = new Group();

        // Creëer de initiële lichaamsdelen van de slang
        for (int i = 0; i < INITIELE_LENGTE; i++) {
            Circle deel = new Circle(SLANG_RADIUS, Color.GREEN);
            deel.setTranslateX(INITIELE_POSITIE_X + i * SLANG_RADIUS * 2);
            deel.setTranslateY(INITIELE_POSITIE_Y);
            lichaam.add(deel);
            slangGroup.getChildren().add(deel);
        }
    }

    // Getter voor de JavaFX-groep van de slang
    public Group getSlangGroup() {
        return slangGroup;
    }

    // Update de positie van de slang op basis van de huidige richting
    public void update() {
        Circle laatsteDeel = lichaam.poll();
        laatsteDeel.setTranslateX(lichaam.peek().getTranslateX() + richtingX);
        laatsteDeel.setTranslateY(lichaam.peek().getTranslateY() + richtingY);
        lichaam.add(laatsteDeel);
    }

    // Stel de richting van de slang in
    public void setRichting(double x, double y) {
        this.richtingX = x;
        this.richtingY = y;
    }

    // Getters voor de richting van de slang
    public double getRichtingX() {
        return richtingX;
    }

    public double getRichtingY() {
        return richtingY;
    }
}