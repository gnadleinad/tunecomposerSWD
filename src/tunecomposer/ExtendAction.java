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
    private ArrayList<Moveable> temp_selected = new ArrayList();
    
    
    
    public ExtendAction(double width,double x, Moveable mov, MainController m){
        main = m;
        startWidth = width;
        startX = x;
        draggedm = mov;
        main.done.push(this);
        main.undone.clear();
        temp_selected.addAll(main.getSelected());
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
        dX = draggedm.getMoveableWidth();
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mv : selected) {
            mv.undoExtend(startWidth, startX);
        } 
    }

    @Override
    public void selectMoveables() {
        ArrayList<Moveable> selected = main.getSelected();
        selected.addAll(temp_selected);
        for(Moveable mov : temp_selected){
            mov.display_select();
        }
    }
}
