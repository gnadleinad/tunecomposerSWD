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
public class MoveAction implements Action {
    
    private MainController main;
    
    //private AddNote current;
    /*
    private MouseEvent event;
    private double note_x;
    private double note_y;
    */
    
    //private Moveable moveable;
        //private MainController main;
    
    //private AddNote current;
    /*
    private MouseEvent event;
    private double note_x;
    private double note_y;
    */
    private Double startY; 
    private Double startX;
    private Moveable draggedm;
    
    private Double dY; 
    private Double dX;
    
    
    
    public MoveAction(double x, double y, Moveable mov, MainController m){
        System.out.println(x);
        System.out.println(y);
        main = m;
        startY = y;
        startX = x;
        draggedm = mov;
        if(!(startX - draggedm.getMoveableX() == 0)) {
            main.done.push(this);
        }
    }
    
    @Override
    public void redoAction() {
        System.out.println("yes");
        System.out.println(draggedm.getMoveableX());
        System.out.println(draggedm.getMoveableY());
        System.out.println(startX);
        System.out.println(startY);
        double dify = (dY - dragged.getMoveableY());
        double difx = (dX - dragged.getMoveableX());
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mv : selected){
            mv.drag(difx, dify);
        }
    }

    @Override
    public void undoAction() {
        System.out.println("d");
        System.out.println(draggedm.getMoveableX());
        System.out.println(draggedm.getMoveableY());
        dX = draggedm.getMoveableX();
        dY = draggedm.getMoveableY();
        ArrayList<Moveable> selected = main.getSelected();
        double dify = (dragged.getMoveableY() - startY);
        double difx = (dragged.getMoveableX() - startX);
        for (Moveable mv : selected) {
            mv.undoDrag(difx ,dify);
        } 
    }
}