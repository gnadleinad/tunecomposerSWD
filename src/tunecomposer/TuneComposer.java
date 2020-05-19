/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



/**
 * This JavaFX app lets the user play scales.
 * @author Janet Davis 
 * @author Project 3 - Team 4
 * @since March 3, 2020
 */
public class TuneComposer extends Application {

    /**
     * Constructs a new ScalePlayer application.
     */
    public TuneComposer() {
        
    }
  
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("fxml/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });   
        primaryStage.show();
    }

        
    /**
     * Launch the application.
     * @param args the command line arguments are ignored
     */
    public static void main(String[] args) {
        launch(args);
    }

}
