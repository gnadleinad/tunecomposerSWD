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

    /**
     * makes the rectangle on screen symbolizing a note
     * @param x the x coordinate of the note
     * @param y the y coordinate of the note
     * @param instrument
     * @return 
     */
    public Rectangle draw_note(double x, double y, String temp_instrument) {
        instrument = temp_instrument;
         y = Math.floor(y / 10) * 10;
         Rectangle rectangle;
        rectangle = new Rectangle(x, y, 100, 10);
        System.out.println(x);
        System.out.println(y);
        rectangle.getStyleClass().add("note");
        System.out.println(temp_instrument);
        Convert_Instrument();
        rectangle.getStyleClass().add(instrument);
        select(rectangle);
     return rectangle;
    }
    
    public void select(Rectangle r){
        r.setStroke(javafx.scene.paint.Color.RED);
        isSelected = true;
    }
    
    private void Convert_Instrument(){
        System.out.println("Old Instrument: "+ instrument);
        if(instrument.contains(" ")){
            instrument = instrument.replace(' ', '-');
        }
        System.out.println("New Instrument: "+ instrument);
    }
    
    
    
    
}
