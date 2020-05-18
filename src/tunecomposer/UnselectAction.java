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
public class UnselectAction implements Action{
    
    private MainController main;
    private ArrayList<Moveable> previously_selected = new ArrayList();
    private ArrayList<Moveable> toBeSelected = new ArrayList();
    
    public UnselectAction(Moveable mov, ArrayList<Moveable> prev, MainController m){
        toBeSelected.add(mov);
        Moveable moveable = toBeSelected.get(0);
        previously_selected = prev;
        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
       
        if(!selected.contains(moveable)){
            moveable.display_deselect();
            toBeSelected.add(moveable); 
        }
        selected.clear();
        
        main.done.push(this);
        main.undone.clear();
    }
    
    public UnselectAction(ArrayList<Moveable> a,ArrayList<Moveable> prev, MainController m){
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov : toBeSelected){
            if(!selected.contains(mov)){
                mov.display_deselect();
                toBeSelected.add(mov); 
            }
            selected.clear();
        }
    }

    @Override
    public void undoAction() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public void selectMoveables() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
