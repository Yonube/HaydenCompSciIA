package src.JavaFXGUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainapp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        TextField textField = new TextField();
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");

        // Layout
        VBox layout = new VBox(10); // Spacing of 10 between elements
        layout.getChildren().addAll(textField, button1, button2);

        // Scene
        Scene scene = new Scene(layout, 300, 200);

        // Stage
        primaryStage.setTitle("JavaFX Panel Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
