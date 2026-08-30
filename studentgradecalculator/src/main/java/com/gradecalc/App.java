package com.gradecalc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        GradeCalculatorUI ui = new GradeCalculatorUI();

        Scene scene = new Scene(ui.getRoot(), 700, 750);

        scene.getStylesheets().addAll(
                getClass().getResource("/styles/main.css").toExternalForm(),
                getClass().getResource("/styles/components.css").toExternalForm(),
                getClass().getResource("/styles/controls.css").toExternalForm()
        );

        primaryStage.setTitle("Student Grade Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}