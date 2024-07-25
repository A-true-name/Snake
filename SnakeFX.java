import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class SnakeFX extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setOnAction(event -> System.out.println("Snake!"));

        Scene scene = new Scene(btn, 1000, 1000); //Size of the window

        primaryStage.setTitle("Snake!"); //Title of the window
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}