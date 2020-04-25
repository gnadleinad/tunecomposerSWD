/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

/**
 *
 * @author zoehill
 */
public interface Moveable {
    
    public double getMoveableX();
    
    public double getMoveableY();
    
    public boolean equals(Object o);
    
    public void drag(double difx, double dify);
    
    public void releaseDrag(double difx, double dify);
    
    public void extend(double extentionlen);
    
    public void releaseExtend(double extentionlen);
    
    public void display_select();

    public void display_deselect();

    public boolean contains(double starting_point_x, double starting_point_y);
    
}
