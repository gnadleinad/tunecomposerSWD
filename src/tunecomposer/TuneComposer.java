/*
 * CS 300-A, 2017S
 */
package tunecomposer;

import java.io.IOException;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;


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
    
    @FXML
    private Line one_line;
    
    @FXML
    private AnchorPane anchorPane;

    /**
     * Constructs a new ScalePlayer application.
     */
    public TuneComposer() {
        this.player = new MidiPlayer(1,60);
    }
    
    /**
     * Play a new scale, after stopping and clearing any previous scale.
     * @param startingPitch an integer between 0 and 115
     */
    protected void playScale(int startingPitch) {
        player.stop();
        player.clear();
        for (int i=0; i < 8; i++) {
            player.addNote(startingPitch+SCALE[i], VOLUME, i,    1, 0, 0);
            player.addNote(startingPitch+SCALE[i], VOLUME, 16-i, 1, 0, 0);
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
        TextInputDialog pitchDialog = new TextInputDialog("60");
        pitchDialog.setHeaderText("Give me a starting note (0-115):");
            pitchDialog.showAndWait().ifPresent(response -> {
                playScale(Integer.parseInt(response));
            });
    }    
    
    /**
     * When the user clicks the "Stop playing" button, stop playing the scale.
     * @param event the button click event
     */
    @FXML 
    protected void handleStopPlayingButtonAction(ActionEvent event) {
        player.stop();
        one_Line();
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
        

        
    scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
   
        public void handle(MouseEvent mouseEvent) {
        double x  = mouseEvent.getScreenX();
        double y  = mouseEvent.getScreenY();
        
        System.out.println("mouse click detected! " + x + " and " + y );
    }
});
        primaryStage.setTitle("Scale Player");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });   
        primaryStage.show();

        
    }
    
    @FXML
    public void one_Line()  {
     System.out.print(one_line.getStartY());
     double y = one_line.getStartY()+ 40;
     int count = 1;
     while (y < 1310){
         Line line = new Line(one_line.getStartX(),y, one_line.getEndX(), y);
         anchorPane.getChildren().add(line);
         y = y + 10;
         count += 1;
     }
     System.out.print(count);
    }
    
    
    
    
    /**
     * Launch the application.
     * @param args the command line arguments are ignored
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
