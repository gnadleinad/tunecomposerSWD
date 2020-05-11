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
public class DeleteSelected implements Action{
    private MainController main;
    
    //private AddNote current;
    /*
    private MouseEvent event;
    private double note_x;
    private double note_y;
    */
    private ArrayList<Moveable> origional_selected;
    
    
    public DeleteSelected(ArrayList s, MainController m){

        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
        origional_selected = (ArrayList<Moveable>) s.clone();
        
        redoAction();

        main.done.push(this);
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov: origional_selected) {
            main.removePaneChild("notes_pane",mov);
        }
        
        selected.clear();
        main.updateSelected(selected);
    }

    @Override
    public void undoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov: origional_selected) {
            main.addPaneChild("notes_pane",mov);
        }
        
        selected.clear();
        main.updateSelected(selected);
    }
    
}
