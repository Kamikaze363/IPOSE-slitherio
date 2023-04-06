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

    private  String username1 = "player1";
    private  String username2 = "player2";
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1200);
        settings.setHeight(700);

        settings.setTitle("Slither.io 1v1");
        settings.setVersion("1.0");

//        settings.setProfilingEnabled(true);

        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setManualResizeEnabled(true);
        settings.setPreserveResizeRatio(true);
        settings.setIntroEnabled(true);

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

        Label level1 = new Label("Level 1");
        level1.setTranslateX(1000);
        level1.setTranslateY(30);
        level1.setStyle("-fx-text-fill: white");
        level1.setFont(Font.font("Arial", 30));

        Label level2 = new Label("Level 2");
        level2.setTranslateX(1000);
        level2.setTranslateY(30);
        level2.setStyle("-fx-text-fill: white");
        level2.setFont(Font.font("Arial", 30));

        Label level3 = new Label("Level 3");
        level3.setTranslateX(1000);
        level3.setTranslateY(30);
        level3.setStyle("-fx-text-fill: white");
        level3.setFont(Font.font("Arial", 30));
        level1.setVisible(true);
        level2.setVisible(false);
        level3.setVisible(false);

        Duration timerDuration = Duration.seconds(30);

        FXGL.runOnce(() -> {
            level1.setVisible(false);
            level2.setVisible(true);
            level3.setVisible(false);

            FireTowardsPlayerComponent fireTowardsPlayer1 = new FireTowardsPlayerComponent(300, 500, EntityTypes.Fireball, Color.ORANGE, player1.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer2 = new FireTowardsPlayerComponent(700, 100, EntityTypes.Fireball, Color.ORANGE, player2.getEntity());
        }, timerDuration);

        Duration timerDuration2 = Duration.seconds(60);

        FXGL.runOnce(() -> {
            level1.setVisible(false);
            level2.setVisible(false);
            level3.setVisible(true);
            FireTowardsPlayerComponent fireTowardsPlayer12 = new FireTowardsPlayerComponent(1000, 400, EntityTypes.Fireball, Color.ORANGE, player1.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer22 = new FireTowardsPlayerComponent(500, 300, EntityTypes.Fireball, Color.ORANGE, player2.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer13 = new FireTowardsPlayerComponent(1100, 200, EntityTypes.Fireball, Color.ORANGE, player1.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer24 = new FireTowardsPlayerComponent(100, 200, EntityTypes.Fireball, Color.ORANGE, player2.getEntity());
            FireTowardsPlayerComponent fireTowardsPlayer25 = new FireTowardsPlayerComponent(670, 600, EntityTypes.Fireball, Color.ORANGE, player2.getEntity());

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

        player1points.setFont(Font.font("Arial", 30));
        player2points.setFont(Font.font("Arial", 30));

        p1pointsCaller.setFont(Font.font("Arial", 30));
        p2pointsCaller.setFont(Font.font("Arial", 30));

        p1pointsCaller.setTranslateX(30);
        p1pointsCaller.setTranslateY(30);

        p2pointsCaller.setTranslateX(30);
        p2pointsCaller.setTranslateY(70);

        player1points.setTranslateX(200);
        player1points.setTranslateY(30);

        player2points.setTranslateX(200);
        player2points.setTranslateY(70);

        player1points.setVisible(true);
        player2points.setVisible(true);
        p1pointsCaller.setVisible(true);
        p2pointsCaller.setVisible(true);


        player1points.textProperty().bind(FXGL.getWorldProperties().intProperty("kills player 1").asString());
        player2points.textProperty().bind(FXGL.getWorldProperties().intProperty("kills player 2").asString());

        FXGL.getGameScene().setBackgroundRepeat("LevelBackground.png");

        FXGL.getGameScene().addUINode(p1pointsCaller);
        FXGL.getGameScene().addUINode(p2pointsCaller);
        FXGL.getGameScene().addUINode(level1);
        FXGL.getGameScene().addUINode(level2);
        FXGL.getGameScene().addUINode(level3);

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
                        FXGL.getGameWorld().getProperties().stringProperty("player 1").getValue() + " : " + FXGL.getGameWorld().getProperties().intProperty("kills player 1").getValue() + "\n" +
                        FXGL.getGameWorld().getProperties().stringProperty("player 2").getValue() + " : " + FXGL.getGameWorld().getProperties().intProperty("kills player 2").getValue()
                );
                FXGL.getGameWorld().removeEntities(player1.getEntity(), player2.getEntity());
                getInput().clearAll();
                getGameController().gotoMainMenu();



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
                FXGL.inc("kills player 1", +250);
                food.removeFromWorld();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.Player2, EntityTypes.Food) {
            @Override
            protected void onCollision(Entity player, Entity food) {
                FXGL.inc("kills player 2", +250);
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

        FXGL.getGameScene().setBackgroundRepeat("LevelBackground.png");

        FXGL.getGameScene().clearUINodes();

        Button startGame = new Button("Start Game");
        startGame.setFont(Font.font("Arial", 24));
        startGame.setPrefSize(400, 50);
        startGame.setVisible(false);
        startGame.setTranslateX(400);
        startGame.setTranslateY(500);

        Label info = new Label( "'Slither.io 1v1' is a  2-player game where \n" +
                                    "each player aims to collect as many \n" +
                                    "points as possible within a 90-second \n" +
                                    "time limit. Players must avoid touching the \n" +
                                    "dangerous fireballs that appear randomly \n" +
                                    "throughout the game, as touching a fireball \n" +
                                    "will deduct points from their score. The \n" +
                                    "player with the most points at the end of \n" +
                                    "the game wins!");
        info.setFont(Font.font("Arial", 24));
        info.setStyle("-fx-text-fill: white");
        info.setTranslateX(400);
        info.setTranslateY(150);
        info.setVisible(false);

        Pane containerPane = new Pane();

        Label userLabel = new Label("Player 1:");
        userLabel.setFont(Font.font("Arial", 24));
        userLabel.setStyle("-fx-text-fill: white");
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
        user2Label.setStyle("-fx-text-fill: white");
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

            FXGL.getWorldProperties().setValue("player 1", username1);
            FXGL.getWorldProperties().setValue("player 2", username2);



            System.out.println("Login successful!");
            user2Field.setVisible(false);
            user1Field.setVisible(false);
            loginButton.setVisible(false);
            user2Label.setVisible(false);
            userLabel.setVisible(false);
            startGame.setVisible(true);
            info.setVisible(true);

            startGame.setOnAction(d -> {
                startHetSpel();
                startGame.setVisible(false);
                info.setVisible(false);

            });


// Voeg hier de code toe om de gebruiker in te loggen.

        });
        containerPane.getChildren().addAll(userLabel, user1Field, user2Label, user2Field, startGame, loginButton, info);
        FXGL.getGameScene().addUINode(containerPane);

    }
    


    @Override
    public void initGameVars(Map<String, Object> vars) {
        vars.put("kills player 1", 0);
        vars.put("kills player 2", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
