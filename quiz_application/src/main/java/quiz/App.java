package quiz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        QuizApplication quiz = new QuizApplication();

        Scene scene = new Scene(
                quiz.getRoot(),
                750,
                700
        );

        scene.getStylesheets().addAll(
                getClass().getResource("/styles/main.css").toExternalForm(),

                getClass().getResource("/styles/components.css").toExternalForm(),

                getClass().getResource("/styles/controls.css").toExternalForm()
        );

        primaryStage.setTitle("Quiz Application");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(650);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}