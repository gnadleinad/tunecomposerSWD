/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.util.*;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.Line;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;


/**
 * This JavaFX app lets the user play scales.
 * @author Janet Davis 
 * @author Project 3 - Team 4
 * @since March 3, 2020
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
    private static Map<Pair, Note> notePosition;
    
    private ArrayList<Pair> selected;
    
    @FXML
    private Line one_line;
    
    @FXML
    private ToggleGroup instrument;
    
    @FXML Line red_line;
    
    @FXML
    public AnchorPane music_staff;
    
    public static String current_instrument;
    
    
    final Timeline timeline = new Timeline();

    /**
     * Constructs a new ScalePlayer application.
     */
    public TuneComposer() {
        this.player = new MidiPlayer(1,10000);
        this.notePosition = new HashMap<>();
        this.selected = new ArrayList<>();
    }
    
    
    
    /**
     * Play a new scale, after stopping and clearing any previous scale.
     * @param startingPitch an integer between 0 and 115
     */
    protected void playScale() {
        player.stop();
        player.clear();
        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
            player.addNote((int)Math.round((double)(entry.getValue()).midi_y), VOLUME, (int)Math.round((double)(entry.getValue()).x),  1, 0, 0);       
        } 
        player.play();
    }
    
    /**
     * Sorts the keys of the notes from notePosition in ascending order.
     * don't think that this method is necessary....
     * CURRENTLY UNUSED
     */
    protected void sortNoteKeys() {
        Map<Pair,Note> treeMap = new TreeMap<>();
        //Issue with pair not comparable exists on line below if function run.
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
    
    @FXML
    protected void handleDeleteAllButtonAction(ActionEvent event){
        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
            entry.getValue().display_delete();
        } 
        notePosition.clear(); //deletes note positions that are used to create player composition.
    }
    
    @FXML
    protected void handleSelectAllButtonAction(ActionEvent event){
        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
            entry.getValue().display_select();
            selected.add(entry.getKey());
        } 
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
        controller.change_instrument();

        
        controller.music_staff.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent mouseEvent) -> {
            controller.change_instrument();
            double x  = mouseEvent.getX();
            double y  = mouseEvent.getY();
            Pair cordinates = new Pair(x,y);
            Note n = new Note(x,y,current_instrument);
            controller.music_staff.getChildren().add(n.display_note);

            if(n.midi_y >= 0 && n.midi_y < 128){notePosition.put(cordinates,n);} //ignores menu bar click
            //sortNoteKeys(); Notes remain unsorted. If this becomes an issue must switch pair to a list.

        });
   
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
     double y = 0;
     while (y < 1280){
         Line line = new Line(0,y,2000, y);
         music_staff.getChildren().add(line);
         y = y + 10;
        }
    }
    
    public void change_instrument(){
        RadioButton selectedRadioButton = (RadioButton) instrument.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        current_instrument = toggleGroupValue;    
    }

    /**
     * Creates and moves a red line across the screen to show the duration of time.
     */
    public void move_red() {
        final Rectangle line = new Rectangle(0, 30, 1, 1280);
        line.getStyleClass().add("playbar");
        
        music_staff.getChildren().add(line);
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
