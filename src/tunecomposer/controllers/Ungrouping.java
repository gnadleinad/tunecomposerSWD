/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer.controllers;

import java.util.ArrayList;
import tunecomposer.Action;
import tunecomposer.Group;
import tunecomposer.Moveable;

/**
 *
 * @author zoehill
 */
public class Ungrouping implements Action {
    private MainController main;
    
    private Group original_group;
    
    public Ungrouping(Group g, MainController m){

        main = m;
        original_group = g;
        
        redoAction();

        main.done.push(this);
    }
    
    @Override
    public void redoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        main.removePaneChild("notes_pane",original_group);
        for(Moveable item : original_group.group){
            selected.add((Moveable)item);
        } 
        selected.remove(original_group);
    }

    @Override
    public void undoAction() {
        ArrayList<Moveable> selected = main.getSelected();
        main.getPaneChildren("notes_pane").add(original_group);
        selected.clear();
        selected.add(original_group);
    }
    
}