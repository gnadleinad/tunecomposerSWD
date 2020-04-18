/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import java.util.Map;
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
    private static Map<Pair, Note> notePosition;
    private static ArrayList<Note> selected;
    public static boolean drag = false;
    public static boolean extend = false;
    public static boolean inside_rect = false;
    public static Rectangle select_rect = null;
    
    
//    public EventHandle(TuneComposer c) {
//        this.controller = c
//    }

    static public void onClick(MouseEvent event, ArrayList<Note> selected, TuneComposer tc){
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
   
    static public void makeNote(double x,double y, TuneComposer tc){
//            for (Note note : selected){
//                note.display_deselect();
//            }
            //controller.change_instrument();
            System.out.println("sdfsf");
            Pair cordinates = new Pair(x,y);
            Note n = new Note(x,y,current_instrument);
            //MIDI_events.add(n.get_MIDI(x));
//            if(event.isControlDown() == false){
//                selected.clear();
//            }
            //selected.add(n);
            tc.notes_pane.getChildren().add(n.display_note);
//            for (Note note : selected){
//                note.display_select();
//            }
            if(n.midi_y >= 0 && n.midi_y < 128){
                notePosition.put(cordinates,n);
                //noteTreeMap.put(cordinates,n);
            }

    }
    
    static public void selectNote(Note n, ArrayList<Note> selected){
        n.display_select();
        selected.add(n);       
    }
        
    static public void onPressed(MouseEvent event, TuneComposer tc) {  
        inside_rect = false;
        double starting_point_x = event.getX() ;
        double starting_point_y = event.getY() ;
        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
            if (entry.getValue().y == Math.floor(starting_point_y/10)*10 
                && (entry.getValue().x <= starting_point_x && (entry.getValue().x)+(entry.getValue()).display_note.getWidth() - 10  >  starting_point_x ) 
                    && (!selected.contains(entry.getValue()))){
                drag = false;
                inside_rect = true;
                selectNote(entry.getValue(), selected);

                break;
            }
            if (entry.getValue().y == Math.floor(starting_point_y/10)*10 
                && (entry.getValue().x)+(entry.getValue().display_note.getWidth()-10) <= starting_point_x && (entry.getValue().x)+entry.getValue().display_note.getWidth()  >  starting_point_x 
                && (!selected.contains(entry.getValue()))){ 
                extend = true;
                inside_rect = true;
                selectNote(entry.getValue(), selected);
                break;
            }
        }

        if (inside_rect == false){
            select_rect = new Rectangle() ;
            // A non-finished rectangle has always the same color.
            tc.notes_pane.getChildren().add(select_rect);
            select_rect.getStyleClass().add("selectionRect");
//                new_rectangle_is_being_drawn = true ;
            }
        }
}   
