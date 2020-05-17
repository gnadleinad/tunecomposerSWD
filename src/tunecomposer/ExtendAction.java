/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import tunecomposer.controllers.MainController;
import static tunecomposer.controllers.MainController.dragged;

/**
 *
 * @author charlieschneider
 */
public class ExtendAction implements Action{
        
    private MainController main; 
    private Double startWidth;
    private Double startX;
    private Moveable draggedm; 
    private Double dX;
    
    
    
    public ExtendAction(double width,double x, Moveable mov, MainController m){
        System.out.println(x);
        main = m;
        startWidth = width;
        startX = x;
        draggedm = mov;
        main.done.push(this);
        main.undone.clear();
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mv : selected){
            mv.extend(dX);
        }
    }

    @Override
    public void undoAction() {
        System.out.println("sdfsaf");
//        System.out.println(draggedm.getMoveableX());
//        System.out.println(draggedm.getMoveableY());
        dX = draggedm.getMoveableWidth();
        ArrayList<Moveable> selected = main.getSelected();
//        double dify = (dragged.getMoveableY() - startY);
        //double difx = (dragged.getMoveableWidth() - startWidth);
        for (Moveable mv : selected) {
            mv.undoExtend(startWidth, startX);
        } 
    }
}
