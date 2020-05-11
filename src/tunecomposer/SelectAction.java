/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import tunecomposer.controllers.MainController;

/**
 *
 * @author zoehill
 */
public class SelectAction implements Action{
    private MainController main;
    
    //private AddNote current;
    /*
    private MouseEvent event;
    private double note_x;
    private double note_y;
    */
    private Moveable moveable;
    
    
    public SelectAction(Moveable mov, MainController m){
        moveable = mov;
        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
        if(!selected.contains(moveable)){
            moveable.display_select();
            selected.add(moveable); 
        }
        main.done.push(this);
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        if(!selected.contains(moveable)){
            moveable.display_select();
            selected.add(moveable); 
        }
    }

    @Override
    public void undoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        selected.add(moveable);       
        for (Moveable mov : selected){
            mov.display_deselect();
        }
        selected.clear();
        
    }
    
    
}
