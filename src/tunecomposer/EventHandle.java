/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.TRANSPARENT;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 *
 * @author charlieschneider
 */
public class EventHandle {

    /**
     *
     * @param event
     * @param map
     * @param selected
     */
    private static ArrayList<Moveable> selected = new ArrayList<>();
    public static boolean drag = false;
    public static boolean extend = false;
    public static boolean inside_rect = false;
    public static Rectangle select_rect = null;
    public static boolean new_rectangle_is_being_drawn = false;
    public static Moveable dragged;
    public static double starting_point_x;
    public static double starting_point_y;
    
    //START OF BAD STUFF
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

    //private static Map<Pair, Note> notePosition;
    

    private static TreeMap<Pair,Note> noteTreeMap;
    
    //private ArrayList<Note> selected;
    
     /**
     * ArrayList of integer lists that stores the MIDI event parameters
     * for the addMidiEvent method
     */
    private static ArrayList<int[]> MIDI_events;
    
    @FXML
    private Line one_line;
    
    @FXML
    private ToggleGroup instrument;
    
    @FXML Line red_line;
    
    @FXML
    public AnchorPane lines_pane;
    
    @FXML
    public AnchorPane notes_pane;
    
    @FXML
    public AnchorPane redline_pane;
    
    public static String current_instrument;
  
    public TranslateTransition transition;

    private static double finalNote;
    
    public EventHandle() {
        this.player = new MidiPlayer(1,10000);
        this.MIDI_events = new ArrayList<>();
        //this.notePosition = new HashMap<>();
        //this.selected = new ArrayList<>();
        this.transition = new TranslateTransition();
        //this.finalNote = 0.0;
        
        

    }
    
    
      
     /**
     * Sorts an ArrayList of list of integers given an element to compare.
     * @param unsorted the ArrayList with which to sort
     * @param element the element within all integer lists that will be compared
     */
    
    public void sortArrayList(ArrayList<int[]> unsorted, int element) {
        Collections.sort(unsorted, new Comparator<int[]>() {
            public int compare(int[] current_int, int[] other_int) {
                return Integer.compare(current_int[element],other_int[element]);
            }
        });
    }
    

    /**
     * Adds all of the MIDI_events to the current composition.
     */
    protected void addAllEvents() {
        for  (int[] event : MIDI_events) {
            player.addMidiEvent(event[0], event[1], event[2], event[3], event[4]);
        }
    }
    
    /**
     * Play a new scale, after stopping and clearing any previous scale.
     */
    
    
    protected void playScale() {
        /*
        int channel_accum;
        player.stop();
        player.clear();

        sortArrayList(MIDI_events, 3);
        addAllEvents();
        channel_accum = 0;
        for(Map.Entry<Pair, Note> entry : EventHandle.notePosition.entrySet()){ 
           player.addNote((int)Math.round((double)(entry.getValue()).midi_y), VOLUME, (int)Math.round((double)(entry.getValue()).x), (int)Math.round((double)(entry.getValue()).duration), MIDI_events.get(channel_accum)[0] - ShortMessage.PROGRAM_CHANGE, 0);       

          channel_accum += 1;
        } 

        player.play();
        */
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
        //timeline.jumpTo(Duration.INDEFINITE); //Possibly not necessary line
        transition.stop();
    }  
    
    @FXML

    protected void handleDeleteButtonAction(ActionEvent event) throws InvocationTargetException{
        //System.out.println(notes_pane.getChildren().size());
        for (Moveable mov: selected) {
            //Movable note = it.next(); 
            notes_pane.getChildren().remove(mov);
        }
        selected.clear();
        
  }

    @FXML
    protected void handleSelectAllButtonAction(ActionEvent event){
        for(Node node : notes_pane.getChildren()){
            selectNote((Note)node);
        }
//        selected.clear();
//        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
//            entry.getValue().display_select();
//            selected.add(entry.getValue());
//
//        } 
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
     * Constructs a line graphic and duplicates until window is filled.
     */
    @FXML
    public void one_Line()  {
     double y = 0;
     while (y < 1280){
         Line line = new Line(0,y,2000, y);
         redline_pane.getChildren().add(line);
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
        double duration = finalNote * 6 + 150;
        transition.setDuration(Duration.millis(duration));
        transition.setNode(red_line);
        transition.setFromX(red_line.getStartX()+22);
        if(finalNote == 0){
            transition.setToX(finalNote);
        }
        else{transition.setToX(finalNote);}
        transition.setInterpolator(Interpolator.LINEAR);
        red_line.setOpacity(1);
        transition.play();
    }
 
    // GOOD STUFF STARTS HERE
    public void onClick(MouseEvent event){
        double x  = event.getX();
        double y  = event.getY();
        if (drag == true || extend == true || new_rectangle_is_being_drawn == true){
            resetBooleans(); 
        }
        else{
            y = Math.floor(y / 10) * 10;

            for(Node node : notes_pane.getChildren()){
                //System.out.println("for loop");
                if(((Note)node).contains(x, y)){
                    return;
                }  
            }

            makeNote(event,x,y);
        } 
    }
      
    public void onPressed(MouseEvent event) { 
        inside_rect = false;
        starting_point_x = event.getX() ;
        starting_point_y = event.getY() ;
        changeDragOrExtendBooleans(starting_point_x,starting_point_y);
        selectNotes(event);

        if (inside_rect == false){
            select_rect = new Rectangle() ;
            select_rect.getStyleClass().add("selectionRect");
            // A non-finished rectangle has always the same color.
            notes_pane.getChildren().add(select_rect);
            new_rectangle_is_being_drawn = true ;
        }

    }
    
    public void onDragged(MouseEvent event) {  
        if(extend == true || new_rectangle_is_being_drawn == true){
            drag = false;
        }
        else{
            drag = true;
        }
        
        double current_ending_point_x = event.getX() ;
        double current_ending_point_y = event.getY() ;
          
        if (drag == true){
            dragNotes(current_ending_point_x,current_ending_point_y);   
        }
          
        if (extend == true){
            extendNotes(current_ending_point_x);   
        }
         
        if ( new_rectangle_is_being_drawn == true ){
            adjust_rectangle_properties( starting_point_x,
                                         starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         select_rect) ;
        }
    }
    
    public void onReleased(MouseEvent event) {
        double ending_point_x = event.getX();
        double ending_point_y = event.getY();
        
        if ( new_rectangle_is_being_drawn == true ){
            endDrawingRectangle(event, ending_point_x,ending_point_y);
        }
        if (drag == true){
            endDrag(ending_point_x,ending_point_y);
        }
        if (extend == true) {
            endExtend(ending_point_x);
        }
        else{
            
            for(Node node : notes_pane.getChildren()){ 
                if(((Note)node).contains(starting_point_x, starting_point_y)){
                    if(event.isControlDown() == false){
                        deselectNotes(event);
                        selectNote((Note)node);
                    }
                }
            }
        }
    }
    
    
    
    public void makeNote(MouseEvent event, double x,double y){
        deselectNotes(event);
        //change_instrument();

        Pair cordinates = new Pair(x,y);
        Note n = new Note(x,y,current_instrument);
        //MIDI_events.add(n.get_MIDI(x));

        selected.add(n);
        notes_pane.getChildren().add(n);
        n.display_select();


    }
       
        
    public void changeDragOrExtendBooleans(double x, double y){
        for(Node node : notes_pane.getChildren()){
            Rectangle rnote = ((Rectangle)node); 
            
            if (rnote.getY() == Math.floor(y/10)*10 
                && (rnote.getX() <= x && (rnote.getX())+rnote.getWidth() - 10  >  x )){
                drag = false;
                break;
            }
            else if (rnote.getY() == Math.floor(y/10)*10 
                && (rnote.getX())+(rnote.getWidth()-10) <= x && (rnote.getX())+rnote.getWidth()  >  x ){ 
                extend = true;
                break;
            }   
        }
    }
    
    public void selectNotes(MouseEvent event){
        for(Node node : notes_pane.getChildren()){
            if(((Note)node).contains(starting_point_x, starting_point_y)){
                dragged = (Note)node;
                inside_rect = true;
                
                if(event.isControlDown() == true){
                    controlClick((Note)node);
                } else{
                    selectNote((Note)node);
                    
                }
            }
        }
    }
    
    public void selectNote(Note n){
        if(!selected.contains(n)){
            n.display_select();
            selected.add(n); 
        }

    }
    
    public void deselectNotes(MouseEvent event){
        if(event.isControlDown() == false){
            for (Moveable mov : selected){
                mov.display_deselect();
            }
            selected.clear();
        }
        
    }
    
    public void deselectNote(Note n){
        n.display_deselect();
        selected.remove(n);
        
    }
    
    public void dragNotes(double x, double y){
        double dify = (y - dragged.getMoveableY());
        double difx = (x - dragged.getMoveableX());
        for (Moveable mov : selected) {
            mov.drag(difx, dify);
             

        } 
    }
    
    public void extendNotes(double x){
        double extentionlen = (x - dragged.getMoveableX());
        for (Moveable mov : selected) {
            if(extentionlen < 5.0){
                extentionlen = 5.0;
            }
            mov.extend(extentionlen);
        } 
    }
    
    
    public void endDrawingRectangle(MouseEvent event, double x, double y){
        deselectNotes(event);
        for(Node node : notes_pane.getChildren()){
            
            
            if((((Rectangle)node).getY() > Math.min(starting_point_y,y)  && ((Rectangle)node).getY() < Math.max(y,starting_point_y))
                   && (((Rectangle)node).getX() > Math.min(starting_point_x, x) && ((Rectangle)node).getX() < Math.max(x, starting_point_x)))
            {  
                selected.add((Note)node);
            }
       }
        for (Moveable mov : selected){
            mov.display_select();
        }


       notes_pane.getChildren().remove( select_rect ) ;
       new_rectangle_is_being_drawn = false ;
        
    }
    
    public void endDrag(double x, double y) {
        double dify = (y - dragged.getMoveableY());
        double difx = (x - dragged.getMoveableX());

        for (Moveable mov : selected) {

            mov.releaseDrag(difx, dify);
        }
    }
    
    public void resetBooleans(){
        drag = false;
        extend = false;
        inside_rect = false;
        new_rectangle_is_being_drawn = false;     
    }
    
    public void endExtend(double x) {
        double ext_len = (x - dragged.getMoveableX());
        for (Moveable mov : selected) {
            if(ext_len < 5.0){
                ext_len = 5.0;
            }
            mov.releaseExtend(ext_len);
        }
    }
    
    private void controlClick(Note note){
        if(!selected.contains(note)){       
            selectNote(note);
        }
        else{
            deselectNote(note);
        }
    }
    
    public void adjust_rectangle_properties( double starting_point_x,
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
   //GOOD STUFF ENDS HERE
}   
