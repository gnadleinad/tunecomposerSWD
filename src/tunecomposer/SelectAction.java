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
    
    //private AddNote current;
    /*
    private MouseEvent event;
    private double note_x;
    private double note_y;
    */
    
    //private Moveable moveable;
    private ArrayList<Moveable> previously_selected = new ArrayList();
    private ArrayList<Moveable> toBeSelected = new ArrayList();
    
    /*
    public SelectAction(Moveable mov, ArrayList<Moveable> prev, MainController m){
        System.out.println("item");
        toBeSelected.add(mov);
        Moveable moveable = toBeSelected.get(0);
        previously_selected = prev;
        System.out.println("previously selected size: "+ previously_selected.size());
        System.out.println("To be selected size: "+ toBeSelected.size());
        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
        //System.out.println(previously_selected.size());
        if(!selected.contains(moveable)){
            moveable.display_select();
            selected.add(moveable); 
        }
        main.done.push(this);
    }
    */
    public SelectAction(ArrayList<Moveable> a,ArrayList<Moveable> prev, MainController m){
        System.out.println("list");
        previously_selected = prev;
        System.out.println("previously selected size: "+ previously_selected.size());
        toBeSelected = a;
        System.out.println("To be selected size: "+ toBeSelected.size());
        previously_selected.removeAll(toBeSelected);
        System.out.println("UPDATED previously selected size: "+ previously_selected.size());
        main = m;
        redoAction();
        //main.done.push(this);
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
        System.out.println("undo");
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov : toBeSelected){
            mov.display_deselect();
        }
        selected.clear();
        System.out.println(previously_selected.size());
        selected.addAll(previously_selected);
        for(Moveable mov : previously_selected){
            mov.display_select();
        }

        
    }

    @Override
    public void selectMoveables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
