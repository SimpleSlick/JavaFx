package atm_interface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage primaryStage){
        ATMInterface atmInterface = new ATMInterface();

        Scene scene = new Scene(atmInterface.getRoot(), 700, 800);

        scene.getStylesheets().addAll(
            getClass().getResource("/styles/main.css").toExternalForm(),
            getClass().getResource("/styles/components.css").toExternalForm(),
            getClass().getResource("/styles/controls.css").toExternalForm()
        );

        primaryStage.setTitle("ATM interface");
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}