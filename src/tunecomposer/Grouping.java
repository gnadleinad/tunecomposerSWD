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
public class Grouping implements Action{
    private MainController main;
    
    //private AddNote current;
    /*
    private MouseEvent event;
    private double note_x;
    private double note_y;
    */
    private ArrayList<Moveable> original_selected;
    
    private Group original_group;
    
    public Grouping(Group g, MainController m){

        main = m;
        original_group = g;
        
        redoAction();

        main.done.push(this);
        main.undone.clear();
    }
    
    @Override
    public void redoAction() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<Moveable> selected = main.getSelected();
        main.getPaneChildren("notes_pane").add(original_group);
        selected.clear();
        selected.add(original_group);
    }

    @Override
    public void undoAction() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<Moveable> selected = main.getSelected();
        main.removePaneChild("notes_pane",original_group); //removes rectangle
        for(Moveable item : original_group.group){
            selected.add((Moveable)item);
        } 
        selected.remove(original_group);
    }

    @Override
    public void selectMoveables() {
        ArrayList<Moveable> selected = main.getSelected();
        selected.add(original_group);
        original_group.display_select();
        for (Moveable mov : original_group.group){
            mov.display_select();
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
