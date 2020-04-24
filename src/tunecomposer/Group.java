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
        group = selected;
        setXandY();
        
        
        
    }
    
    private void setXandY(){
        for(Moveable mov: group){
            x = Math.min(mov.getMoveableX(), x);
            y = Math.min(mov.getMoveableY(), y);
        }
        
    }
    
    public double getMoveableX(){return x;}
    
    public double getMoveableY(){return y;}
    
    public void drag(double difx, double dify){}
    
    public void releaseDrag(double difx, double dify){}
    
    public void extend(double extentionlen){}
    
    public void releaseExtend(double extentionlen){}
    
    public void display_select(){
        this.getStyleClass().add("group");
    }

    public void display_deselect(){
        this.getStyleClass().remove("group");
    }
    
}
