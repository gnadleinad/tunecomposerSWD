/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.util.*;
import java.io.IOException;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
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
import static javafx.scene.paint.Color.*;
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
  
    public TranslateTransition transition;

    Rectangle select_rect = null;
    boolean new_rectangle_is_being_drawn = false;
    boolean drag = false;
    double starting_point_x;
    double starting_point_y;
    
    private static double finalNote;

    /**
     * Constructs a new ScalePlayer application.
     */
    public TuneComposer() {
        this.player = new MidiPlayer(1,10000);
        this.notePosition = new HashMap<>();
        this.selected = new ArrayList<>();
        this.transition = new TranslateTransition();
        this.finalNote = 0.0;
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
     * When the user clicks the "Play scale" button, show a dialog to get the 
     * starting note and then play the scale.
     * @param event the button click event
     */
    @FXML 
    protected void handlePlayScaleButtonAction(ActionEvent event) {
        transition.stop();
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
        transition.stop();
    }  
    
    @FXML
    protected void handleDeleteAllButtonAction(ActionEvent event){
        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
            entry.getValue().display_delete();
        } 
        finalNote = 0.0;
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
    
       void adjust_rectangle_properties( double starting_point_x,
                                     double starting_point_y,
                                     double ending_point_x,
                                     double ending_point_y,
                                     Rectangle given_rectangle )
   {
      given_rectangle.setX( starting_point_x ) ;
      given_rectangle.setY( starting_point_y ) ;
      given_rectangle.setWidth( ending_point_x - starting_point_x ) ;
      given_rectangle.setHeight( ending_point_y - starting_point_y ) ;

      if ( given_rectangle.getWidth() < 0 )
      {
         given_rectangle.setWidth( - given_rectangle.getWidth() ) ;
         given_rectangle.setX( given_rectangle.getX() - given_rectangle.getWidth() ) ;
      }

      if ( given_rectangle.getHeight() < 0 )
      {
         given_rectangle.setHeight( - given_rectangle.getHeight() ) ;
         given_rectangle.setY( given_rectangle.getY() - given_rectangle.getHeight() ) ;
      }
   }
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("TuneComposer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        TuneComposer controller = loader.getController();
        controller.one_Line();
        controller.change_instrument();


            controller.music_staff.setOnMouseClicked((MouseEvent event) -> {
            double x  = event.getX();
            double y  = event.getY();
            controller.change_instrument();

            Pair cordinates = new Pair(x,y);
            Note n = new Note(x,y,current_instrument);
            controller.music_staff.getChildren().add(n.display_note);

            if(n.midi_y >= 0 && n.midi_y < 128){
                notePosition.put(cordinates,n);
                if(x > finalNote){
                    finalNote=x;
                }
            } 

        });   
      
      controller.music_staff.setOnMousePressed( ( MouseEvent event ) ->
      {            
         if ( new_rectangle_is_being_drawn == false )
         {
            starting_point_x = event.getX() ;
            starting_point_y = event.getY() ;

            select_rect = new Rectangle() ;

            // A non-finished rectangle has always the same color.
            select_rect.setFill( TRANSPARENT ) ; // almost white color
            select_rect.setStroke( BLACK ) ;

            controller.music_staff.getChildren().add( select_rect ) ;
   
            new_rectangle_is_being_drawn = true ;
         }
      } ) ;

      controller.music_staff.setOnMouseDragged( ( MouseEvent event ) ->
      {
         if ( new_rectangle_is_being_drawn == true )
         {
            double current_ending_point_x = event.getX() ;
            double current_ending_point_y = event.getY() ;

            adjust_rectangle_properties( starting_point_x,
                                         starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         select_rect) ;
         }
      } ) ;

      controller.music_staff.setOnMouseReleased( ( MouseEvent event ) ->
      {
            if ( new_rectangle_is_being_drawn == true )
         {
            //select_rect = null ;
            controller.music_staff.getChildren().remove( select_rect ) ;
            new_rectangle_is_being_drawn = false ;
         }
      } ) ;
       
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
     * @param finalNote the x time position of the last note
     */
    public void move_red() {
        System.out.println("FN_moveRed: "+finalNote);
        double duration = finalNote * 6 + 600;
        transition.setDuration(Duration.millis(duration));
        transition.setNode(red_line);
        transition.setFromX(red_line.getStartX() + 22);
        if(finalNote == 0){
            transition.setToX(finalNote);
        }
        else{transition.setToX(finalNote+100);}
        transition.setInterpolator(Interpolator.LINEAR);
        red_line.setOpacity(1);
        transition.play();
        }
    
    /**
     * Launch the application.
     * @param args the command line arguments are ignored
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
