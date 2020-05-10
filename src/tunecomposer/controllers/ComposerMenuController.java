/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import tunecomposer.Group;
import tunecomposer.Moveable;
import tunecomposer.Note;

/**
 *
 * @author andrewyeon
 */
public class ComposerMenuController{
    
    private MainController main;
    
    
    /**
     * When the user clicks the "Play scale" button, show a dialog to get the 
     * starting note and then play the scale.
     * @param event the button click event
     */
    @FXML 
    protected void handlePlayScaleButtonAction(ActionEvent event) {
        main.moveRedFull();
        main.playScale();
        
    } 
    
    /**
     * When the user clicks the "Stop playing" button, stop playing the scale.
     * @param event the button click event
     */
    @FXML 
    protected void handleStopPlayingButtonAction(ActionEvent event) {
        main.player.stop();
        main.moveRedBack();
    }  
    
    @FXML 
    protected void handleUndoButtonAction(ActionEvent event) {
        main.player.stop();
        main.moveRedBack();
    }  
    
    @FXML 
    protected void handleRedoButtonAction(ActionEvent event) {
        main.player.stop();
        main.moveRedBack();
    }  
    
        
    @FXML
    protected void handleDeleteButtonAction(ActionEvent event) throws InvocationTargetException{
        //System.out.println(notes_pane.getChildren().size());
        ArrayList<Moveable> selected = main.getSelected();
        for (Moveable mov: selected) {
            //Movable note = it.next();
            
            //notes_pane.getChildren().remove(mov);
            main.removePaneChild("notes_pane",mov);
        }
        selected.clear();
        main.updateSelected(selected);
        
  }

    @FXML
    protected void handleSelectAllButtonAction(ActionEvent event){
        ObservableList<Node> notesChildren = main.getPaneChildren("notes_pane");
        for(Node node : notesChildren){
            main.selectNote((Note)node);
        }
//        selected.clear();
//        for(Map.Entry<Pair, Note> entry : notePosition.entrySet()){ 
//            entry.getValue().display_select();
//            selected.add(entry.getValue());
//
//        } 
    }
    
        @FXML
    protected void handleGroupButtonAction(ActionEvent event) {
        ArrayList<Moveable> selected = main.getSelected();
        Group group = new Group(selected);
        main.getPaneChildren("notes_pane").add(group);
        selected.clear();
        selected.add(group);
    }
    /**
     * 
     * @param event 
     */
    @FXML
    protected void handleUngroupButtonAction(ActionEvent event) {
        
    }

    /**
     * When the user clicks the "Exit" menu item, exit the program.
     * @param event the menu selection event
     */
    @FXML
    protected void handleExitMenuItemAction(ActionEvent event) {
        System.exit(0);
    }

    
    public void init(MainController mainController) {
        main = mainController;
    }

}
