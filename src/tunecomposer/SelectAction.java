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
    
    //private Moveable moveable;
    private ArrayList<Moveable> toBeSelected = new ArrayList();
    
    
    public SelectAction(Moveable mov, MainController m){
        System.out.println("item");
        toBeSelected.add(mov);
        Moveable moveable = toBeSelected.get(0);
        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
        if(!selected.contains(moveable)){
            moveable.display_select();
            selected.add(moveable); 
        }
        main.done.push(this);
    }
    
    public SelectAction(ArrayList<Moveable> a, MainController m){
        System.out.println("list");
        toBeSelected = a;
        main = m;
        redoAction();
        main.done.push(this);
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov : toBeSelected){
        //moveable = mov;
            if(!selected.contains(mov)){
                mov.display_select();
                selected.add(mov); 
            }
        }
    }

    @Override
    public void undoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov : selected){
            mov.display_deselect();
        }
        selected.clear();
        
    }
    
    
}
