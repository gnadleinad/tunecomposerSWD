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
    
    //private AddNote current;
    /*
    private MouseEvent event;
    private double note_x;
    private double note_y;
    */
    private Note note;
    
    
    public AddNote(Note n, MainController m){
        note = n;
        main = m;
        
        ArrayList<Moveable> selected = main.getSelected();
        selected.add(note);
        
        main.addPaneChild("notes_pane", note);        
        note.display_select();
        
        main.done.push(this);
    }
    
    @Override
    public void redoAction() {
        Action undoneAction = main.undone.pop();
        main.done.push(undoneAction);
        ArrayList<Moveable> selected = main.getSelected();
        selected.add(note);
        
        main.addPaneChild("notes_pane", note);        
        note.display_select();
        
    }

    @Override
    public void undoAction() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<Moveable> selected = main.getSelected();
        main.removePaneChild("notes_pane", note);
        selected.clear();
        main.updateSelected(selected);
        Action undoneAction = main.done.pop();
        main.undone.push(undoneAction);
    }
    
    
}
