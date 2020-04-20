/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.TRANSPARENT;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import static tunecomposer.TuneComposer.current_instrument;

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
    private static Map<Pair, Note> notePosition = new HashMap<>();
    private static ArrayList<Note> selected = new ArrayList<>();
    public static boolean drag = false;
    public static boolean extend = false;
    public static boolean inside_rect = false;
    public static Rectangle select_rect = null;
    public static boolean new_rectangle_is_being_drawn = false;
    public static Note dragged;
    public static double starting_point_x;
    public static double starting_point_y;

    
    
    
    
        //controller.one_Line();
        
    
    public EventHandle(TuneComposer c) {
    }

    static public void onClick(MouseEvent event, TuneComposer tc){
            double x  = event.getX();
            double y  = event.getY();
            y = Math.floor(y / 10) * 10;
            System.out.println("oc");
            for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
                System.out.println("loop");
                if(entry.getValue().y == y && (entry.getValue().x <= x && entry.getValue().x + entry.getValue().display_note.getWidth()  >  x )){ 
                    selectNote(entry.getValue(), selected);
                    System.out.println("if");
                    return;
                }  
            }
            makeNote(x,y,tc);        
    }
      
    static public void onPressed(MouseEvent event, TuneComposer tc) {  
        inside_rect = false;
        starting_point_x = event.getX() ;
        starting_point_y = event.getY() ;
        changeDragOrExtendBooleans(starting_point_x,starting_point_y);
        if (inside_rect == false){
            select_rect = new Rectangle() ;
            // A non-finished rectangle has always the same color.
            tc.notes_pane.getChildren().add(select_rect);
            select_rect.getStyleClass().add("selectionRect");
//                new_rectangle_is_being_drawn = true ;
            }
        }
    
    static public void onDragged(MouseEvent event, TuneComposer tc) {  
        
        System.out.println(starting_point_x);
        System.out.print(extend);
        double current_ending_point_x = event.getX() ;
        double current_ending_point_y = event.getY() ;
          
        if (drag == true){
            dragNotes(selected,current_ending_point_x,current_ending_point_y);   
        }
          
        if (extend == true){
            extendNotes(selected, current_ending_point_x);
            
        }
         
        if ( new_rectangle_is_being_drawn == true )
         {

            adjust_rectangle_properties( starting_point_x,
                                         starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         select_rect) ;
         }
    }
    
    
    
        static public void makeNote(double x,double y, TuneComposer tc){
            deselectNotes();
            //controller.change_instrument();
            System.out.println("sdfsf");
            Pair cordinates = new Pair(x,y);
            Note n = new Note(x,y,current_instrument);
            //MIDI_events.add(n.get_MIDI(x));
//            if(event.isControlDown() == false){
//                selected.clear();
//            }
            selected.add(n);
            tc.notes_pane.getChildren().add(n.display_note);
            n.display_select();
            if(n.midi_y >= 0 && n.midi_y < 128){
                notePosition.put(cordinates,n);
                //noteTreeMap.put(cordinates,n);
            }

    }
        
    static public void changeDragOrExtendBooleans(double x, double y){
        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
            if (entry.getValue().y == Math.floor(y/10)*10 
                && (entry.getValue().x <= x && (entry.getValue().x)+(entry.getValue()).display_note.getWidth() - 10  >  x ) 
                && (selected.contains(entry.getValue()))){
                System.out.println("dragging");
                dragged = entry.getValue();
                drag = true;
                inside_rect = true;
                selectNote(entry.getValue(), selected);
                break;
            }
            else if (entry.getValue().y == Math.floor(y/10)*10 
                && (entry.getValue().x)+(entry.getValue().display_note.getWidth()-10) <= x && (entry.getValue().x)+entry.getValue().display_note.getWidth()  >  x 
                && (selected.contains(entry.getValue()))){ 
                System.out.println("extneding");
                dragged = entry.getValue();
                extend = true;
                inside_rect = true;
                selectNote(entry.getValue(), selected);
                break;
            }
            drag = !(extend == true || new_rectangle_is_being_drawn == true);    
        }
    }
    
    
    static public void selectNote(Note n, ArrayList<Note> selected){
        n.display_select();
        selected.add(n);       
    }
    
    public static void deselectNotes(){
        for (Note note : selected){
            note.display_deselect();
        }
        selected.clear();
    }
    
    public static void deselectNote(Note n){
        n.display_deselect();
        selected.remove(n);
    }
    
    public static void dragNotes(ArrayList<Note> array, double x, double y){
        double dify = (y - dragged.y);
        double difx = (x - dragged.x);
        
        for (Note note : array) {
            note.display_note.setX(note.x + difx);
            note.display_note.setY(note.y + dify); 
        } 
    }
    
    public static void extendNotes(ArrayList<Note> array, double x){
        double extentionlen = (x - dragged.x);
        for (Note note : array) {
            if(extentionlen < 5.0){
                extentionlen = 5.0;
            }
            note.display_note.setWidth(extentionlen); 
        } 
    }
    
    
    public static void adjust_rectangle_properties( double starting_point_x,
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
}   
