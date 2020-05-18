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
        //System.out.println(note.getStyleClass());
        System.out.println("selected.size() selectMov: "+ selected.size());
        
    }

    @Override
    public void selectMoveables() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ArrayList<Moveable> selected = main.getSelected();
        //if(!selected.contains(note)){
            //note.display_select();
        //}
        System.out.println("selected.size() selectMov: "+ selected.size());
        selected.add(note);
        for (Moveable mov : selected){
            //System.out.println("selected.size() selectMov: "+ selected.size());
            mov.display_select();
        }
        System.out.println("selected.size() selectMov: "+ selected.size());
    }
    
    
}
