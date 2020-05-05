/*
 * CS 300-A, 2017S
 */
package tunecomposer;

//import tunecomposer.controllers.MainController;
//import java.util.*;
import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import javafx.animation.Interpolator;
//import javafx.animation.Timeline;
//import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.ToggleGroup;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//import javafx.scene.shape.Line;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.StackPane;
//import static javafx.scene.paint.Color.*;
//import javafx.scene.shape.Rectangle;
//import javafx.util.Duration;
//import javafx.util.Pair;
//import javax.sound.midi.ShortMessage;



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

        
        //MainController controller = loader.getController();
        //controller.change_instrument();
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
