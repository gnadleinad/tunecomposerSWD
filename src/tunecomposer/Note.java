/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author schneicw
 */
public class Note {

    boolean isSelected = false;
    private String instrument;
    public Double x;
    public Double y; //midi value of y
    public Double midi_y; 
    public Rectangle display_note;
    
    /**
     * Constructs a note. 
     * @param temp_instrument 
     */
    public Note(Double temp_x,Double temp_y, String temp_instrument){
        instrument = temp_instrument;
        
        x = temp_x;
        y = temp_y;
        midi_y = Math.floor(127-((temp_y - 30) / 10));
        draw_note(x,y);
        
    }
    
    /**
     * makes the rectangle on screen symbolizing a note
     * @param x the x coordinate of the note
     * @param y the y coordinate of the note
     * @return display_note
     */
    public void draw_note(double x, double y) {
        y = Math.floor(y / 10) * 10;
        display_note = new Rectangle(x, y, 100, 10);
        display_note.getStyleClass().add("note");
        Convert_Instrument();
        display_note.getStyleClass().add(instrument);
    }
    
    public void display_delete(){
        display_note.setVisible(false);
    }
    
    public void display_select(){
        display_note.getStyleClass().add("selected");
        isSelected = true;
    }
    
    private void Convert_Instrument(){
        if(instrument.contains(" ")){
            instrument = instrument.replace(' ', '-');
        }
    }
    
    
    
    
    
}
