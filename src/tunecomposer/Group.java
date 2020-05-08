/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author zoehill
 */
public class Group extends Rectangle implements Moveable{
    public Double original_x;
    public ArrayList<Moveable> group = new ArrayList<>();
    public double originalWidth;
    
    public Group(ArrayList selected){
        group = (ArrayList<Moveable>)selected.clone();
        setXandY();
        originalWidth = this.getWidth();
        this.display_select();
    }
    
    private void setXandY(){
        double max_x = group.get(0).getMoveableX()+group.get(0).getMoveableWidth();
        double max_y = group.get(0).getMoveableY()+group.get(0).getMoveableHeight();
        double min_x = group.get(0).getMoveableX();
        double min_y = group.get(0).getMoveableY();
        
        for(Moveable mov: group){
            min_x = Math.min(mov.getMoveableX(), min_x);
            min_y = Math.min(mov.getMoveableY(), min_y);
            max_x = Math.max(mov.getMoveableX()+mov.getMoveableWidth(), max_x);
            max_y = Math.max(mov.getMoveableY()+mov.getMoveableHeight(), max_y);    
        }
        this.setX(min_x); // correct

        this.setY(min_y); 

        this.setWidth(max_x - min_x);
        this.setHeight(max_y - min_y);
        
    }
    
    
    @Override
    public boolean equals(Object o){
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Group)) { 
            return false; 
        } 
        Group other = (Group) o; 
          
        // Compare the data members and return accordingly  
        return  group.equals(other.group) && this.equals(other); 
                
    }
    
    public double getMoveableX(){return this.getX();}
    
    public double getMoveableY(){return this.getY();}
    
    public double getOriginalWidth() {return this.originalWidth;}
    
    public double getMoveableWidth() {return this.getWidth();}
    
    public void setMoveableX(double x1) {; 
        double dist;
        dist = this.getMoveableX() - x1;
        this.setX(x1);// if only this, then groups are good, notes are not
        for (Moveable mov : this.group){
            mov.setMoveableX(mov.getMoveableX() - dist);
        }
    }
    
    public void setMoveableWidth(double width) {this.setWidth(width);}
    
    public void setOriginalWidth() {this.originalWidth = this.getWidth();}
    
    public void setOriginalX(){this.original_x = this.getX();}
    
    public void drag(double difx, double dify){
        this.setX(this.getX() + difx);
        this.setY(this.getY() + dify);
        for(Moveable mov : group) {
            mov.drag(difx,dify);
        }
    }
    
    public void releaseDrag(double difx, double dify){
        this.setX(this.getX() + difx);
        this.setY(Math.floor((this.getY() + dify)/ 10) * 10);
        for(Moveable mov : group) {
            mov.releaseDrag(difx,dify);
        }
    }
      public void extend(double extentionlen){
        double startX;
        double widthFraction;
        double scaleFactor = extentionlen / getMoveableWidth(); //
        for(Moveable mov : group) {
            startX = this.getMoveableX() + (((mov.getMoveableX() - this.getMoveableX())*scaleFactor));
            widthFraction = mov.getMoveableWidth() / this.getMoveableWidth();
            mov.setMoveableX(startX);
            mov.extend(extentionlen * widthFraction);
            }
        this.setWidth(extentionlen);
        }
    
    public void releaseExtend(double extentionlen){
        //nothing is needed here!

    }
    
    public void display_select(){
        for(Moveable mov : group){
            mov.display_deselect();
            mov.display_select();
        }
        this.getStyleClass().clear();
        this.getStyleClass().add("group");
    }

    public void display_deselect(){
        for(Moveable mov : group){
            mov.display_deselect();
        }
        this.getStyleClass().clear();
        this.getStyleClass().remove("group");
        this.getStyleClass().add("unselect-group");    
    }
    
    public void display_ungroup(){
        this.getStyleClass().remove("group");
    }
    
    @Override
    public String getClassName(){
        return "group";
    }
    
    public double getMoveableHeight(){return this.getHeight();}
    
}
