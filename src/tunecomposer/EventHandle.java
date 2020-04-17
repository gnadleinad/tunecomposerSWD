/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import java.util.Map;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import static tunecomposer.TuneComposer.current_instrument;

/**
 *
 * @author charlieschneider
 */
public interface EventHandle {

    /**
     *
     * @param event
     * @param map
     * @param selected
     */
    static public void onClick(MouseEvent event, Map<Pair, Note> map, ArrayList<Note> selected, TuneComposer tc){
            double x  = event.getX();
            double y  = event.getY();
            y = Math.floor(y / 10) * 10;
            System.out.println("oc");
            for(Map.Entry<Pair, Note> entry : map.entrySet()){ 
                System.out.println("loop");
                if (entry.getValue().y == y && (entry.getValue().x <= x && entry.getValue().x + entry.getValue().display_note.getWidth()  >  x )){  
                    selectNote(entry.getValue(), selected);
                    System.out.println("if");
                    return;
                }  
            }
            makeNote(x,y,tc,map);        
    }
   
    static public void makeNote(double x,double y, TuneComposer tc,Map<Pair, Note> map){
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
                map.put(cordinates,n);
                //noteTreeMap.put(cordinates,n);
            }

    }
    
    static public void selectNote(Note n, ArrayList<Note> selected){
        n.display_select();
        selected.add(n);       
    }
        

}
