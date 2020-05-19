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
    private ArrayList<Moveable> temp_selected = new ArrayList();
    
    
    public MoveAction(double x, double y, Moveable mov, MainController m){
        
        main = m;
        startY = y;
        startX = x;
        draggedm = mov;
        main.done.push(this);
        main.undone.clear();
        //temp_selected.addAll(main.getSelected());
        System.out.print(main.getSelected().size());
        System.out.println("moveAction x: "+ draggedm.getMoveableX());
        System.out.println("moveAction y: "+ draggedm.getMoveableY());
    }
    
    @Override
    public void redoAction() {
        System.out.println(dragged.getMoveableX());
        System.out.println(dX);
        System.out.println("2");
        /*
        System.out.println(draggedm.getMoveableX());
        System.out.println(draggedm.getMoveableY());
        System.out.println(startX);
        System.out.println(startY);
*/
        double dify = (dY - dragged.getMoveableY());
        double difx = (dX - dragged.getMoveableX());
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mv : selected){
            mv.drag(difx, dify);
        }
    }

    @Override
    public void undoAction() {
        System.out.println("Move undoAction");
        //System.out.println("DraggedM X(undoMove): " + draggedm.getMoveableX());
        //System.out.println(draggedm.getMoveableY());
        dX = draggedm.getMoveableX();
        dY = draggedm.getMoveableY();
        System.out.println(dX);
        System.out.println("initial dx");
        
        ArrayList<Moveable> selected = main.getSelected();
        System.out.println(selected.size());
        double dify = (dragged.getMoveableY() - startY);
        double difx = (dragged.getMoveableX() - startX);
        for (Moveable mv : selected) {
            mv.undoDrag(difx ,dify);
        } 
    }

    @Override
    public void selectMoveables() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<Moveable> selected = main.getSelected();
        selected.addAll(temp_selected);
        for(Moveable mov : temp_selected){
            mov.display_select();
        }
    }
}