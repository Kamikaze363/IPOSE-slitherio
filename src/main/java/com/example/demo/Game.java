package com.example.demo;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class Game extends GameApplication {

    private int roundCounter = 1;

    private  String username1 = "player1";
    private  String username2 = "player2";
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1200);
        settings.setHeight(700);

        settings.setTitle("Slither.io (dont touch)");
        settings.setVersion("17.2");

//        settings.setProfilingEnabled(true);

        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setManualResizeEnabled(true);
        settings.setPreserveResizeRatio(true);
//        settings.setIntroEnabled(true);

        settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));

        settings.getCredits().addAll(Arrays.asList(
                "BIG Shoutout naar de makers:",
                "Ali",
                "Rolan",
                "Ramy"
        ));

        settings.setApplicationMode(ApplicationMode.RELEASE);
    }


    protected void startHetSpel() {
//        Initialising Players 1 and 2
        Player player1 = new Player(200, 400, Color.RED, EntityTypes.Player1, 50);
        Player player2 = new Player(1000, 400, Color.BLUE, EntityTypes.Player2, 50);

        Label Level = new Label("Level" + roundCounter);
        Level.setTranslateX(1100);
        Level.setTranslateY(30);

        Duration timerDuration = Duration.seconds(30);

        FXGL.runOnce(() -> {
            FireTowardsPlayerComponent fireTowardsPlayer1 = new FireTowardsPlayerComponent(300, 500, EntityTypes.Fireball, Color.ORANGE, player1.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer2 = new FireTowardsPlayerComponent(700, 100, EntityTypes.Fireball, Color.ORANGE, player2.getEntity());
        }, timerDuration);

        Duration timerDuration2 = Duration.seconds(60);

        FXGL.runOnce(() -> {
            FireTowardsPlayerComponent fireTowardsPlayer12 = new FireTowardsPlayerComponent(1000, 400, EntityTypes.Fireball, Color.ORANGE, player1.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer22 = new FireTowardsPlayerComponent(500, 300, EntityTypes.Fireball, Color.ORANGE, player2.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer13 = new FireTowardsPlayerComponent(1100, 500, EntityTypes.Fireball, Color.ORANGE, player1.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer24 = new FireTowardsPlayerComponent(700, 200, EntityTypes.Fireball, Color.ORANGE, player2.getEntity());
            }, timerDuration2);



//        Initialing Food
        FXGL.getGameTimer().runAtInterval(() -> {
            int randomPosX = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() - 80);
            int randomPosY = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() - 80);
            Food food = new Food(randomPosX, randomPosY);
        }, Duration.seconds(0.5));

//        Initialising Player Key Binds
        player1.move(KeyCode.A, KeyCode.D, KeyCode.W, KeyCode.S);
        player2.move(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP, KeyCode.DOWN);

//        Level UI
//        Creating Points Tallies

        Label p1pointsCaller = new Label("Player 1 points:");
        Label p2pointsCaller = new Label("Player 2 points:");

        p1pointsCaller.textProperty().bind(FXGL.getWorldProperties().stringProperty("player 1"));
        p2pointsCaller.textProperty().bind(FXGL.getWorldProperties().stringProperty("player 2"));

        Label player1points = new Label("killsP1");
        Label player2points = new Label("killsP2");

        p1pointsCaller.setStyle("-fx-text-fill: white");
        p2pointsCaller.setStyle("-fx-text-fill: white");

        player1points.setStyle("-fx-text-fill: white");
        player2points.setStyle("-fx-text-fill: white");

        p1pointsCaller.setTranslateX(30);
        p1pointsCaller.setTranslateY(30);

        p2pointsCaller.setTranslateX(30);
        p2pointsCaller.setTranslateY(50);

        player1points.setTranslateX(120);
        player1points.setTranslateY(30);

        player2points.setTranslateX(120);
        player2points.setTranslateY(50);

        player1points.setVisible(true);
        player2points.setVisible(true);
        p1pointsCaller.setVisible(true);
        p2pointsCaller.setVisible(true);


        player1points.textProperty().bind(FXGL.getWorldProperties().intProperty("kills player 1").asString());
        player2points.textProperty().bind(FXGL.getWorldProperties().intProperty("kills player 2").asString());

        FXGL.getGameScene().setBackgroundRepeat("LevelBackground.png");

        FXGL.getGameScene().addUINode(p1pointsCaller);
        FXGL.getGameScene().addUINode(p2pointsCaller);

        FXGL.getGameScene().addUINode(player1points);
        FXGL.getGameScene().addUINode(player2points);

//        Creating Winner Label
        Label winnerLabel = new Label();
        winnerLabel.setStyle("-fx-text-fill: white");
        winnerLabel.setTranslateX(500);
        winnerLabel.setTranslateY(100);
        winnerLabel.setFont(Font.font("Arial", 30));
        FXGL.getGameScene().addUINode(winnerLabel);


        //Round Timer
        Label timer = new Label("90");
        timer.setStyle("-fx-text-fill: white");
        timer.setFont(Font.font("Arial", 30));
        timer.setTranslateX(600);
        timer.setTranslateY(30);
        FXGL.getGameTimer().runAtInterval(() -> {
            int currentTime = Integer.parseInt(timer.getText());
            if (currentTime > 0) {
                timer.setText(String.valueOf(currentTime - 1));
            } else {
                int killsP1 = FXGL.getWorldProperties().getInt("kills player 1");
                int killsP2 = FXGL.getWorldProperties().getInt("kills player 2");
                if (killsP1 > killsP2) {
                    winnerLabel.setText("Player 1 Wins!");
                    winnerLabel.setTranslateX(500);
                } else if (killsP2 > killsP1) {
                    winnerLabel.setText("Player 2 Wins!");
                    winnerLabel.setTranslateX(500);

                } else {
                    winnerLabel.setText("It's a Tie!");
                    winnerLabel.setTranslateX(520);
                }
                timer.setText("TIME'S UP");



                timer.setTranslateX(500);


                getDialogService().showMessageBox("Score:\n\n" +
                        "Player 1: " + FXGL.getGameWorld().getProperties().intProperty("kills player 1").getValue() + "\n" +
                        "Player 2: " + FXGL.getGameWorld().getProperties().intProperty("kills player 2").getValue());


                Platform.runLater(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                roundCounter += 1;

                if (roundCounter <= 3) {

                } else {

                }


            }
        }, Duration.seconds(1));

        FXGL.getGameScene().addUINode(timer);


    }

    @Override
    protected void initGame() {
//


    }


    @Override
    protected void initPhysics() {

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player1, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                FXGL.inc("kills player 1", +500);
                food.removeFromWorld();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player2, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                FXGL.inc("kills player 2", +500);
                food.removeFromWorld();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player2, EntityTypes.Fireball) {
            @Override
            protected void onCollision(Entity player, Entity fireball) {
                FXGL.inc("kills player 2", -1);
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player1, EntityTypes.Fireball) {
            @Override
            protected void onCollision(Entity player, Entity fireball) {
                FXGL.inc("kills player 1", -1);
            }
        });

    }


    @Override
    protected void initUI() {

        var dpadView = getInput().createVirtualDpadView();
        var buttonsView = getInput().createXboxVirtualControllerView();

        addUINode(dpadView, 0, getAppHeight() - 290);
        addUINode(buttonsView, getAppWidth() - 280, getAppHeight() - 290);

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);

        FXGL.getGameScene().clearUINodes();

        Button startGame = new Button("Start Game");
        startGame.setFont(Font.font("Arial", 24));
        startGame.setPrefSize(400, 50);
        startGame.setVisible(false);
        startGame.setTranslateX(400);
        startGame.setTranslateY(500);


        Pane containerPane = new Pane();

        Label userLabel = new Label("Player 1:");
        userLabel.setFont(Font.font("Arial", 24));
        userLabel.setTranslateX(290);
        userLabel.setTranslateY(300);

        TextField user1Field = new TextField();
        user1Field.setFont(Font.font("Arial", 24));
        user1Field.setMaxWidth(400);
        user1Field.setMaxHeight(30);
        user1Field.setTranslateX(450);
        user1Field.setTranslateY(300);
        user1Field.setAlignment(Pos.CENTER);

        Label user2Label = new Label("Player 2:");
        user2Label.setFont(Font.font("Arial", 24));
        user2Label.setTranslateX(290);
        user2Label.setTranslateY(400);

        TextField user2Field = new TextField();
        user2Field.setFont(Font.font("Arial", 24));
        user2Field.setMaxWidth(400);
        user2Field.setMaxHeight(30);
        user2Field.setTranslateX(450);
        user2Field.setTranslateY(400);
        user2Field.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("Arial", 24));
        loginButton.setTranslateX(450);
        loginButton.setTranslateY(500);
        loginButton.setOnAction(e -> {
            username1 = user1Field.getText();
            username2 = user2Field.getText();



            System.out.println("Login successful!");
            user2Field.setVisible(false);
            user1Field.setVisible(false);
            loginButton.setVisible(false);
            user2Label.setVisible(false);
            userLabel.setVisible(false);
            startGame.setVisible(true);

            startGame.setOnAction(d -> {
                startHetSpel();
                startGame.setVisible(false);

            });


// Voeg hier de code toe om de gebruiker in te loggen.

        });
        containerPane.getChildren().addAll(userLabel, user1Field, user2Label, user2Field, startGame, loginButton);
        FXGL.getGameScene().addUINode(containerPane);

    }
    


    @Override
    public void initGameVars(Map<String, Object> vars) {
        vars.put("player 1", username1);
        vars.put("player 2", username2);
        vars.put("kills player 1", 0);
        vars.put("kills player 2", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
