/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer.controllers;

import java.util.ArrayList;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import tunecomposer.Group;
//import tunecomposer.Group;
import tunecomposer.Moveable;
//import tunecomposer.Note;


/**
 *
 * @author andrewyeon
 */
public class ComposerTrackController{
    
    @FXML
    private AnchorPane redline_pane;
    
    @FXML
    private Line red_line;
    
    @FXML
    private AnchorPane lines_pane;
    
    @FXML
    private AnchorPane notes_pane;
    
        
    private MainController main;
    
    //prepare endAnimation for use in "Stop Playing" menu button + full play animation
    public TranslateTransition endAnimation;
    
        
    @FXML
    private void onReleased(MouseEvent event) {
        ArrayList<Moveable> selected = main.getSelected();
        double ending_point_x = event.getX();
        double ending_point_y = event.getY();
        
        if ( main.new_rectangle_is_being_drawn == true ){
            main.endDrawingRectangle(event, ending_point_x,ending_point_y);
        }
        if (main.drag == true){
            main.endDrag(ending_point_x,ending_point_y);
        }
        if (main.extend == true) {
            main.endExtend(ending_point_x);
        }
        else{
            ObservableList<Node> notesChildren = main.getPaneChildren("notes_pane");
            for(Node node : notesChildren){ 
                if(((Moveable)node).contains(main.starting_point_x, main.starting_point_y)){
                    if(event.isControlDown() == false){
                        main.deselectNotes(event);
//                        main.selectNote((Moveable)node);
                    }
                }
                if(((Moveable)node).getClassName() == "group"){
                    selected.removeAll((((Group)node).group));
                }
            }
        }
    }

    @FXML
    private void onDragged(MouseEvent event) {
        if(main.extend == true || main.new_rectangle_is_being_drawn == true){
            main.drag = false;
        }
        else{
            main.drag = true;
        }
        
        double current_ending_point_x = event.getX() ;
        double current_ending_point_y = event.getY() ;
          
        if (main.drag == true){
            main.dragNotes(current_ending_point_x,current_ending_point_y);   
        }
          
        if (main.extend == true){
            main.extendNotes(current_ending_point_x);   
        }
         
        if ( main.new_rectangle_is_being_drawn == true ){
            main.adjust_rectangle_properties( main.starting_point_x,
                                         main.starting_point_y,
                                         current_ending_point_x,
                                         current_ending_point_y,
                                         main.select_rect) ;
        }
    }

    @FXML
    private void onClick(MouseEvent event) {
        double x  = event.getX();
        double y  = event.getY();
        if (main.drag == true || main.extend == true || main.new_rectangle_is_being_drawn == true){
            main.resetBooleans(); 
        }
        else{
            y = Math.floor(y / 10) * 10;

            for(Node node : notes_pane.getChildren()){
                //System.out.println("for loop");
                if(((Moveable)node).contains(x, y)){
                    return;
                }  
            }
            main.makeNote(event,x,y);
        } 
    }

    @FXML
    private void onPressed(MouseEvent event) {
        main.player.stop();
        main.moveRedBack();
        main.inside_rect = false;
        main.starting_point_x = event.getX() ;
        main.starting_point_y = event.getY() ;
        main.changeDragOrExtendBooleans(main.starting_point_x,main.starting_point_y);
        main.selectNotes(event);

        if (main.inside_rect == false){
            main.select_rect = new Rectangle() ;
            main.select_rect.getStyleClass().add("selectionRect");
            // A non-finished rectangle has always the same color.
            notes_pane.getChildren().add(main.select_rect);
            main.new_rectangle_is_being_drawn = true ;
        }
    } 
    
    private double getLastX() {
        double lastNoteX = 0.0;
        if (main.redlineAnimation.getStatus() == Status.RUNNING) {
           System.out.println(red_line.getLayoutX());
           lastNoteX = red_line.getLayoutX();
        }
        else {
            for(Node node : notes_pane.getChildren()){
                double currentDuration = ((Moveable)node).getMoveableWidth();
                double currentStartX = ((Moveable)node).getMoveableX();
                double currentEndX = currentStartX + currentDuration;
                if (currentEndX > lastNoteX) {
                    lastNoteX = currentEndX;
                }
            }
        }
        return lastNoteX;
    }
    
    public double prepareEndAnimation() {
        //finalNote will be updated in both cases, individual and in prepareFullAnimation()
        double lastNoteX = getLastX();
        endAnimation = new TranslateTransition();
        endAnimation.setNode(red_line);
        endAnimation.setDuration(Duration.ONE);
        endAnimation.setFromX(lastNoteX);
        endAnimation.setToX(red_line.getStartX()+22);
        return lastNoteX;
    }
    
     /**
     * Creates and moves a red line across the screen to show the duration of time.
     */
 
    public SequentialTransition prepareFullAnimation() {
    //finalNote prepared here
    double lastNoteX = prepareEndAnimation();
    double duration = lastNoteX * 6; //150
    //red_line.setOpacity(1);
    
    // startAnimation refers to the red line going to the location of the final note
    TranslateTransition startAnimation = new TranslateTransition();
    startAnimation.setNode(red_line);
    startAnimation.setDuration(Duration.millis(duration));
    startAnimation.setFromX(red_line.getStartX()+ 22);
    //ask about if statement later
    startAnimation.setToX(lastNoteX);
    startAnimation.setInterpolator(Interpolator.LINEAR);

    //maybe issues with new?
    SequentialTransition finalTransition = new SequentialTransition(startAnimation, endAnimation);
    return finalTransition;
    }
    
    public void init(MainController mainController) {
        this.main = mainController;
    }
       
    
    /**
     * Constructs a line graphic and duplicates until window is filled.
     */
    public final void one_Line()  {
     double y = 0;
     while (y < 1280){
         Line line = new Line(0,y,2000, y);
         lines_pane.getChildren().add(line);
         y = y + 10;
        }
    }
    
    public ObservableList<Node> getPaneChildren(String paneName) {
        ObservableList children = null;
        if ("notes_pane".equals(paneName)) {
            children = notes_pane.getChildren();
        }
        return children;
    }
    
    public void addPaneChild(String paneName, Object node) {
        if ("notes_pane".equals(paneName)) {
            notes_pane.getChildren().add((Node)node);
        }
    }
    
    public void removePaneChild(String paneName, Object node) {
        if ("notes_pane".equals(paneName)) {
            notes_pane.getChildren().remove((Node)node) ;
        }
    }
}

