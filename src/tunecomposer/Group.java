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
    public Double x;
    public Double y;
    private static ArrayList<Moveable> group = new ArrayList<>();
    
    public Group(ArrayList selected){
        group = (ArrayList<Moveable>)selected.clone();
        x = group.get(0).getMoveableX();
        y = group.get(0).getMoveableY();
        setXandY();
        
        this.display_select();
        
        //this.setX(x);
        //this.setY(y);
        //this.setWidth(100);
        //this.setHeight(100);
        
        //this.display_select();
           
    }
    
    private void setXandY(){
        double max_x = group.get(0).getMoveableX();
        double max_y = group.get(0).getMoveableY();
        double min_x = group.get(0).getMoveableX();
        double min_y = group.get(0).getMoveableY();
        
        for(Moveable mov: group){
            min_x = Math.min(mov.getMoveableX(), min_x);
            min_y = Math.min(mov.getMoveableY(), min_y);
            max_x = Math.max(mov.getMoveableX(), max_x);
            max_y = Math.max(mov.getMoveableY(), max_y);
        }
        max_x = max_x+100;
        max_y = max_y +10;
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
    
    public double getMoveableX(){return x;}
    
    public double getMoveableY(){return y;}
    
    public double getMoveableWidth() {return this.getWidth();}
    
    public void setMoveableX(double x) {this.setX(x);}
    
    public void setMoveableWidth(double width) {this.setWidth(width);}
    
    public void drag(double difx, double dify){
        this.setX(x + difx);
        this.setY(y + dify);
        
        for(Moveable mov : group) {
            mov.drag(difx,dify);
        }
    }
    
    public void releaseDrag(double difx, double dify){
        this.setX(x + difx);
        this.setY(Math.floor((y + dify)/ 10) * 10);

        this.y = Math.floor((y+ dify)/ 10) * 10;
        this.x = x+ difx;
        for(Moveable mov : group) {
            mov.releaseDrag(difx,dify);
        }
    }
    public void extend(double extentionlen){
        double leftFraction;
        double rightFraction;
        double widthFraction;
        this.setWidth(extentionlen);
        for(Moveable mov : group) {
            leftFraction = (mov.getMoveableX() - this.getMoveableX()) / extentionlen;
            rightFraction = (this.getMoveableX() - mov.getMoveableX())/ extentionlen;
            widthFraction = mov.getMoveableWidth()/ extentionlen;
            mov.setMoveableX((leftFraction * extentionlen));
            System.out.println(leftFraction * extentionlen);
            //mov.setMoveableWidth(extentionlen * widthFraction);
        }
    }
    
    public void releaseExtend(double extentionlen){
        extend(extentionlen);
    }
    
    public void display_select(){
        this.getStyleClass().remove("unselect-group");
        this.getStyleClass().add("group");
    }

    public void display_deselect(){
        for(Moveable mov : group){
            mov.display_deselect();
        }
        this.getStyleClass().remove("group");
        this.getStyleClass().add("unselect-group");    
    }
    
    public void display_ungroup(){
        this.getStyleClass().remove("group");
    }
    
}
