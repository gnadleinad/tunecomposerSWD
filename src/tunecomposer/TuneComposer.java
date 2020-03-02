/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.util.*;
import java.awt.Color;
import java.io.IOException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.Line;
import javafx.scene.layout.AnchorPane;
//import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


/**
 * This JavaFX app lets the user play scales.
 * @author Janet Davis 
 * @author SOLUTION - PROJECT 1
 * @since January 26, 2017
 */
public class TuneComposer extends Application {
    
    /**
     * Represents the number of pitch steps for 
     * do, re, mi, fa, so, la, ti, do.
     * Source: https://en.wikipedia.org/wiki/Solfège
     */
    private static final int[] SCALE = {0, 2, 4, 5, 7, 9, 11, 12};
    
    /**
     * Play notes at maximum volume.
     */
    
    private static final int VOLUME = 127;
    
    /**
     * One midi player is used throughout, so we can stop a scale that is
     * currently playing.
     */
    private final MidiPlayer player;
    
    /**
     * Represents the time position of notes along the width as keys
     * Represents the pitch position of notes along the height as values
     * player object will play given pitch when time has passed the position.
     */
    private static Map<Double, Double> notePosition;
    
    @FXML
    private Line one_line;
    
    @FXML Line red_line;
    
    @FXML
    private AnchorPane anchorPane;
    
    final Timeline timeline = new Timeline();

    /**
     * Constructs a new ScalePlayer application.
     */
    public TuneComposer() {
        this.player = new MidiPlayer(1,12000);
        //ticksPerSecond = 1 * (2000/60);
        this.notePosition = new HashMap<>();
    }
    
    
    
    /**
     * Play a new scale, after stopping and clearing any previous scale.
     * @param startingPitch an integer between 0 and 115
     */
    protected void playScale() {
        player.stop();
        player.clear();
        for(Map.Entry<Double, Double> entry : notePosition.entrySet()){  
            player.addNote((int)Math.round(entry.getValue()), VOLUME, (int)Math.round(entry.getKey()),  1, 0, 0);       
                } 
        player.play();
    }
    
    /**
     * Sorts the keys of the notes from notePosition in ascending order.
     */
    protected void sortNoteKeys() {
        Map<Double,Double> treeMap = new TreeMap<>();
        treeMap.putAll(notePosition);
        notePosition = treeMap;
    }
    
    /**
     * When the user clicks the "Play scale" button, show a dialog to get the 
     * starting note and then play the scale.
     * @param event the button click event
     */
    @FXML 
    protected void handlePlayScaleButtonAction(ActionEvent event) {
        move_red();
        playScale();
        
    }    
    
    /**
     * When the user clicks the "Stop playing" button, stop playing the scale.
     * @param event the button click event
     */
    @FXML 
    protected void handleStopPlayingButtonAction(ActionEvent event) {
        player.stop();
        timeline.jumpTo(Duration.INDEFINITE);
    }    
    
    /**
     * When the user clicks the "Exit" menu item, exit the program.
     * @param event the menu selection event
     */
    @FXML
    protected void handleExitMenuItemAction(ActionEvent event) {
        System.exit(0);
    }
    
    /**
     * Construct the scene and start the application.
     * @param primaryStage the stage for the main window
     * @throws java.io.IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("TuneComposer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TuneComposer controller = loader.getController();
        controller.one_Line();

        
        controller.anchorPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                double x  = mouseEvent.getX();
                double y  = mouseEvent.getY();
                double midi_val = Math.floor((y - 30) / 10);
                controller.make_note(x, y);
                notePosition.put(x,midi_val);
                sortNoteKeys();
                System.out.println("mouse click detected! " + x + " and " + midi_val );
                for(Map.Entry<Double, Double> entry : notePosition.entrySet()){  
                System.out.println("Key = " + entry.getKey() +  
                             ", Value = " + entry.getValue());         
                } 
            }
 
        });
        System.out.println(notePosition); 
        
        
            primaryStage.setTitle("Scale Player");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest((WindowEvent we) -> {
                System.exit(0);
            });   
            primaryStage.show();
        }
        
    /**
     * Constructs a line graphic and duplicates until window is filled.
     */
    @FXML
    public void one_Line()  {
     System.out.print(one_line.getStartY());
     double y = one_line.getStartY()+ 40;
     int count = 1;
     while (y < 1310){
         Line line = new Line(one_line.getStartX(),y, one_line.getEndX(), y);
         anchorPane.getChildren().add(line);
         //System.out.print(y);
         y = y + 10;
         count += 1;
        }
     System.out.print(count);
    }

    
    public void make_note(double x,double y){
     y = Math.floor(y / 10) * 10;
     if(y>25) {
        Rectangle rectangle = new Rectangle(x, y, 100, 10);
        rectangle.setFill(javafx.scene.paint.Color.DODGERBLUE);
        rectangle.setStroke(javafx.scene.paint.Color.BLACK);
        anchorPane.getChildren().add(rectangle);
        }
    }
    
    public void move_red() {
        final Rectangle line = new Rectangle(0, 30, 1, 1280);
        line.setFill(javafx.scene.paint.Color.RED);
        anchorPane.getChildren().add(line);
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        final KeyValue kv = new KeyValue(line.xProperty(), 1999,
            Interpolator.LINEAR);
        final KeyFrame kf = new KeyFrame(Duration.millis(12000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        }
    
    /**
     * Launch the application.
     * @param args the command line arguments are ignored
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
