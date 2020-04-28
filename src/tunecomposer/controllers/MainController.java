/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer.controllers;

//import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.TRANSPARENT;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import tunecomposer.MidiPlayer;
import tunecomposer.Moveable;
import tunecomposer.Note;

/**
 *
 * @author andrewyeon
 */
public class MainController {

    /**
     *
     * @param event
     * @param map
     * @param selected
     */
    
    
    @FXML
    private InstrumentSelectController instrumentSelectController;
    
    @FXML
    private ComposerMenuController composerMenuController;
    
    @FXML
    private ComposerTrackController composerTrackController;
    
    @FXML 
    private void initialize() {
        instrumentSelectController.init(this);
        composerMenuController.init(this);
        composerTrackController.init(this);
        composerTrackController.one_Line();
        instrumentSelectController.change_instrument();
    }

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
     * Play notes at maximum volume.
     */
    
    private static final int VOLUME = 127;
    
    /**
     * One midi player is used throughout, so we can stop a scale that is
     * currently playing.
     */
    final MidiPlayer player;
    
    /**
     * Represents the time position of notes along the width as keys
     * Represents the pitch position of notes along the height as values
     * player object will play given pitch when time has passed the position.
     */

    //private static Map<Pair, Note> notePosition;
    

    //private static TreeMap<Pair,Note> noteTreeMap;
    
    //private ArrayList<Note> selected;
    
     /**
     * ArrayList of integer lists that stores the MIDI event parameters
     * for the addMidiEvent method
     */
    //private static ArrayList<int[]> MIDI_events;
  
    public Transition redlineAnimation;

    
    
    public MainController() {
        this.player = new MidiPlayer(1,10000);
        //this.MIDI_events = new ArrayList<>();
        this.redlineAnimation = new SequentialTransition();
        //transition = new TranslateTransition();
        //this.notePosition = new HashMap<>();
        //this.selected = new ArrayList<>();
        //this.finalNote = 0.0;
        
        //initial function calls
 

    }
    
    
    /**
     * Creates and moves a red line across the screen to show the duration of time.
     * @param finalNote the x time position of the last note
     */
    public void moveRedFull() {
        redlineAnimation.stop();
        redlineAnimation = composerTrackController.prepareFullAnimation();
        redlineAnimation.play();
    }
    
    public void moveRedBack() {
        redlineAnimation.stop();
        composerTrackController.prepareEndAnimation();
        redlineAnimation = composerTrackController.endAnimation;
        redlineAnimation.play();
    }
    
    public ObservableList<Node> getPaneChildren(String pane) {
        return composerTrackController.getPaneChildren(pane);
    }
    
    public void addPaneChild(String paneName, Object node) {
        composerTrackController.addPaneChild(paneName, node);
    }
    
    public void removePaneChild(String paneName, Object node) {
        composerTrackController.removePaneChild(paneName, node);
    }
    
    public ArrayList getSelected() {
        return selected;
    }
    
    public String getInstrument() {
        return instrumentSelectController.getInstrument();
    }
    
    public void updateSelected(ArrayList newSelected) {
        selected = newSelected;
    }


    // GOOD STUFF STARTS HERE
    
    public void makeNote(MouseEvent event, double x,double y){
        deselectNotes(event);
        //change_instrument();

        Pair coordinates = new Pair(x,y);
        
        String current_instrument = getInstrument();
        Note n = new Note(x,y,current_instrument);
        //MIDI_events.add(n.get_MIDI(x));

        selected.add(n);
        
        //notes_pane.getChildren().add(n);
        addPaneChild("notes_pane", n);
        n.display_select();
    }
       
    
        
    public void dragNotes(double x, double y){
        double dify = (y - dragged.getMoveableY());
        double difx = (x - dragged.getMoveableX());
        for (Moveable mov : selected) {
            mov.drag(difx, dify);
        } 
    }
    
    public void endDrag(double x, double y) {
        double dify = (y - dragged.getMoveableY());
        double difx = (x - dragged.getMoveableX());

        for (Moveable mov : selected) {

            mov.releaseDrag(difx, dify);
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
    
    public void endExtend(double x) {
        double ext_len = (x - dragged.getMoveableX());
        for (Moveable mov : selected) {
            if(ext_len < 5.0){
                ext_len = 5.0;
            }
            mov.releaseExtend(ext_len);
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
    
    public void endDrawingRectangle(MouseEvent event, double x, double y){
        deselectNotes(event);
        ObservableList<Node> notesChildren = getPaneChildren("notes_pane");
        for(Node node : notesChildren){
            
            
            if((((Rectangle)node).getY() > Math.min(starting_point_y,y)  && ((Rectangle)node).getY() < Math.max(y,starting_point_y))
                   && (((Rectangle)node).getX() > Math.min(starting_point_x, x) && ((Rectangle)node).getX() < Math.max(x, starting_point_x)))
            {  
                selected.add((Note)node);
            }
       }
        for (Moveable mov : selected){
            mov.display_select();
        }

       removePaneChild("notes_pane", select_rect);
       new_rectangle_is_being_drawn = false ;
        
    }
        
    public void changeDragOrExtendBooleans(double x, double y){
        ObservableList<Node> notesChildren = getPaneChildren("notes_pane");
        for(Node node : notesChildren){
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
        ObservableList<Node> notesChildren = getPaneChildren("notes_pane");
        for(Node node : notesChildren){
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
    
    public void deselectNote(Note n){
        n.display_deselect();
        selected.remove(n);
        
    }
    
    
    
    public void resetBooleans(){
        drag = false;
        extend = false;
        inside_rect = false;
        new_rectangle_is_being_drawn = false;     
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
        //for  (int[] event : MIDI_events) {
        //    player.addMidiEvent(event[0], event[1], event[2], event[3], event[4]);
        //}
    }
    
    /**
     * Play a new scale, after stopping and clearing any previous scale.
     */
    
    
    protected void playScale() {
        //int channel_accum;
        player.stop();
        player.clear();
        //sortArrayList(MIDI_events, 3);
        addAllEvents();
        //channel_accum = 0;
        //for(Map.Entry<Pair, Note> entry : MainController.notePosition.entrySet()){ 
        //   player.addNote((int)Math.round((double)(entry.getValue()).midi_y), VOLUME, (int)Math.round((double)(entry.getValue()).x), (int)Math.round((double)(entry.getValue()).duration), MIDI_events.get(channel_accum)[0] - ShortMessage.PROGRAM_CHANGE, 0);       
        //  channel_accum += 1;
        //} 
        //player.play();
    }
   //GOOD STUFF ENDS HERE
}   

