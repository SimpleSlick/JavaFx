package com.numbergame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {

        GameUI gameUI = new GameUI();

        Scene scene = new Scene(
                gameUI.getRoot(),
                700,
                720
        );

        primaryStage.setTitle("Number Guess");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(650);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}