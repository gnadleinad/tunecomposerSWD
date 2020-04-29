/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer.controllers;

import java.util.ArrayList;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import javax.sound.midi.ShortMessage;
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
    /**
     * Play notes at maximum volume.
     */
    
    private static final int VOLUME = 127;
        
    /**
     * One midi player is used throughout, so we can stop a scale that is
     * currently playing.
     */
    final MidiPlayer player;
    
    public Transition redlineAnimation;
    
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
    
    public MainController() {
        this.player = new MidiPlayer(1,10000);
        this.redlineAnimation = new SequentialTransition();
    }
    
    private void prepareMIDIChannels(){
        int data2Byte = 0;
        int startTick = 0;
        
        
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE, 0, data2Byte, startTick, 0);
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 1, 6, data2Byte, startTick, 0);
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 2, 12, data2Byte, startTick, 0);
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 3, 19, data2Byte, startTick, 0);
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 4, 21, data2Byte, startTick, 0);
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 5, 24, data2Byte, startTick, 0);
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 6, 40, data2Byte, startTick, 0);
        player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + 7, 60, data2Byte, startTick, 0);
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
    
        /**
     * Play a new scale, after stopping and clearing any previous scale.
     * Can't add as notes are made because there is no remove note method in MidiPlayer
     */
    protected void playScale() {

        player.stop();
        player.clear();
        ObservableList<Node> notesChildren = getPaneChildren("notes_pane");
        prepareMIDIChannels();
        for(Node node : notesChildren){
            player.addNote((int)Math.round(((Note) node).midi_y), 
                           VOLUME,
                           (int)Math.round(((Note) node).x), 
                           (int)Math.round(((Note) node).duration), 
                           ((Note) node).channel_index,
                           0);
            }
        player.play();
        }      


    // GOOD STUFF STARTS HERE
    
    public void makeNote(MouseEvent event, double x,double y){
        deselectNotes(event);

        Pair coordinates = new Pair(x,y);
        
        instrumentSelectController.change_instrument();
        String current_instrument = getInstrument();
        Note n = new Note(x,y,current_instrument);

        selected.add(n);
        
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
   //GOOD STUFF ENDS HERE
}   

