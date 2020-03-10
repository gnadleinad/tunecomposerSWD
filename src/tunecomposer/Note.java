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

    boolean isSelected;

    /**
     * makes the rectangle on screen symbolizing a note
     * @param x the x coordinate of the note
     * @param y the y coordinate of the note
     * @return 
     */
    public Rectangle draw_note(double x, double y) {
         y = Math.floor(y / 10) * 10;
         Rectangle rectangle;
     if(y>25) {
        rectangle = new Rectangle(x, y, 100, 10);
        rectangle.setFill(javafx.scene.paint.Color.DODGERBLUE);
        rectangle.setStroke(javafx.scene.paint.Color.BLACK);
        }
     else{
         rectangle = null;
     }
     return rectangle;
    }
    
    
    
    
}
