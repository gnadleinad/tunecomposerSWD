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
    public ArrayList<Moveable> group = new ArrayList<>();
    
    public Group(ArrayList selected){
        group = (ArrayList<Moveable>)selected.clone();
        x = group.get(0).getMoveableX();
        y = group.get(0).getMoveableY();
        
        
        setXandY();
        this.display_select();
        
        System.out.println("Group size: " + group.size());
        for(Moveable mov: group){
            System.out.println("x: "+ mov.getMoveableX());
            System.out.println("y: "+ mov.getMoveableY());
        }
        

           
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
        x = min_x;
        this.setY(min_y); 
        y = min_y;
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
    public void extend(double extentionlen){}
    
    public void releaseExtend(double extentionlen){}
    
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
    
    public double getMoveableWidth(){return this.getWidth();};
    
    public void setMoveableWidth(double width){this.setWidth(width);};
    
}
