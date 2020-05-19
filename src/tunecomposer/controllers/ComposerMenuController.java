/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer.controllers;

import java.io.IOException;
import tunecomposer.Ungrouping;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import tunecomposer.Action;
import tunecomposer.DeleteSelected;
import tunecomposer.Group;
import tunecomposer.Grouping;
import tunecomposer.Moveable;
import tunecomposer.Note;
import tunecomposer.SelectAction;

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
    protected void handleUndoButtonAction(ActionEvent event) throws IOException{
        System.out.print(main.done);
        Action undoneAction = main.done.pop();
        undoneAction.undoAction();
        main.undone.push(undoneAction);
        if(!main.done.empty()){
            Action topDoneAction = main.done.peek();
            topDoneAction.selectMoveables();
        }
        
        
    }  
    
    @FXML 
    protected void handleRedoButtonAction(ActionEvent event) {
        //ArrayList selected = main.getSelected();
        Action redoneAction = main.undone.pop();
        redoneAction.redoAction();
        main.done.push(redoneAction);
    }  
    
        
    @FXML
    protected void handleDeleteButtonAction(ActionEvent event) throws InvocationTargetException{
        ArrayList<Moveable> selected = main.getSelected();
        
        DeleteSelected deleteAction = new DeleteSelected(selected,main);
        
  }

    @FXML
    protected void handleSelectAllButtonAction(ActionEvent event){
        ObservableList<Node> notesChildren = main.getPaneChildren("notes_pane");
        ArrayList<Moveable> temp_selected = new ArrayList();
        for(Node node : notesChildren){
            temp_selected.add((Moveable)node);
        }
        
        SelectAction selectMoveable = new SelectAction(temp_selected, main.getSelected(), main);
    }
    
        @FXML
    protected void handleGroupButtonAction(ActionEvent event) {
        ArrayList<Moveable> selected = main.getSelected();
        Group group = new Group(selected);
        Grouping groupAction = new Grouping(group, main);
    }
    /**
     * 
     * @param event 
     */
    @FXML
    protected void handleUngroupButtonAction(ActionEvent event) {
        ArrayList<Moveable> selected = main.getSelected();
        Moveable mov = selected.get(0);
        if(selected.size() == 1 && mov.getClassName() == "group"){
            Ungrouping ungroupAction = new Ungrouping((Group)mov, main);
        }    
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
