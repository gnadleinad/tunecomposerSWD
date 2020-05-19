/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import java.util.Collection;
import tunecomposer.controllers.MainController;

/**
 *
 * @author zoehill
 */
public class SelectAction implements Action{
    private MainController main;
    private ArrayList<Moveable> previously_selected = new ArrayList();
    private ArrayList<Moveable> toBeSelected = new ArrayList();
    
    
    public SelectAction(Moveable mov, ArrayList<Moveable> prev, MainController m){
        toBeSelected.add(mov);
        Moveable moveable = toBeSelected.get(0);
        previously_selected = prev;
        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
        if(!selected.contains(moveable)){
            moveable.display_select();
            selected.add(moveable); 
        }
        main.done.push(this);
        main.undone.clear();
    }
    
    public SelectAction(ArrayList<Moveable> a,ArrayList<Moveable> prev, MainController m){
        previously_selected = prev;
        toBeSelected = a;
        previously_selected.removeAll(toBeSelected);
        main = m;
        redoAction();
        main.done.push(this);
        main.undone.clear();
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov : toBeSelected){
            if(!selected.contains(mov)){
                mov.display_select();
                selected.add(mov); 
            }
        }
    }

    @Override
    public void undoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov : toBeSelected){
            mov.display_deselect();
        }
        selected.clear();

        
    }

    @Override
    public void selectMoveables() {
        ArrayList<Moveable> selected = main.getSelected();
        selected.addAll(toBeSelected);
        for (Moveable mov : selected){
            mov.display_select();
        }
    }
    
    
}
