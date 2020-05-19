/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import tunecomposer.controllers.MainController;

/**
 *
 * @author zoehill
 */
public class AddNote implements Action{
    private MainController main;

    private Note note;
    
    
    public AddNote(Note n, MainController m){
        note = n;
        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
        selected.add(note);
        
        main.addPaneChild("notes_pane", note);        
        note.display_select();
        
        main.done.push(this);
        main.undone.clear();
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov : selected){
            mov.display_deselect();
        }
        selected.clear();
        selected.add(note);
        main.addPaneChild("notes_pane", note); 
        if(!note.getStyleClass().contains("selected")){
            note.display_select();
        }
        
    }

    @Override
    public void undoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        main.removePaneChild("notes_pane", note);
        selected.clear();
        main.updateSelected(selected);
        
    }

    @Override
    public void selectMoveables() {
        ArrayList<Moveable> selected = main.getSelected();
        selected.clear();
        selected.add(note);
        for (Moveable mov : selected){
            mov.display_select();
        }
    }
    
    
}
