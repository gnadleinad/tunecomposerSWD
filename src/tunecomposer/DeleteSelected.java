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
    private ArrayList<Moveable> original_selected;
    
    
    public DeleteSelected(ArrayList<Moveable> s, MainController m){

        main = m;
        System.out.println("delete selected s: " + s);
        ArrayList<Moveable> selected = main.getSelected();
        original_selected = new ArrayList();
        original_selected.addAll(s);
        System.out.println("delete selected original: " + original_selected);
        
        redoAction();

        main.done.push(this);
        main.undone.clear();
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov: original_selected) {
            main.removePaneChild("notes_pane",mov);
        }
        
        selected.clear();
        main.updateSelected(selected);
    }

    @Override
    public void undoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov: original_selected) {
            main.addPaneChild("notes_pane",mov);
        }
        
        selected.clear();
        //main.updateSelected(selected);
    }

    @Override
    public void selectMoveables() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
        ArrayList<Moveable> selected = main.getSelected();
        selected.addAll(original_selected);
        //System.out.println("PEEK toBeSelected.size(): " +  toBeSelected);
        for (Moveable mov : selected){
            //System.out.println("selected.size() selectMov: "+ selected.size());
            mov.display_select();
        }
*/
    }
    
}
