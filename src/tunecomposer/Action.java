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
public interface Action {
    
    public void redoAction();
    
    public void undoAction();
    
    public void selectMoveables();
    
}
