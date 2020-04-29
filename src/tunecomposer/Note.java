/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author schneicw
 */
public class Note extends Rectangle implements Moveable{

    private String instrument;
    public Double x;
    public Double y; //midi value of y
    public Double midi_y; 
    public Rectangle display_note;
    public Double duration;
    public int channel_index;
    
    /**
     * Constructs a note. 
     * @param temp_instrument 
     */
    public Note(Double temp_x,Double temp_y, String temp_instrument){

        this.setX(temp_x);
        this.setY(temp_y);
        this.setWidth(100);
        this.setHeight(10);
        this.getStyleClass().add("note");
        this.getStyleClass().add(temp_instrument);
        
        instrument = temp_instrument;
        Convert_Instrument();
        initChannelIndex();
        x = temp_x;
        y = temp_y;
        //midi_y = Math.floor(127-((temp_y - 30) / 10));
        midi_y = Math.floor(124-((temp_y - 30) / 10));
        duration = 100.0;
        
    }
    
    public void drag(double difx, double dify){
        this.setX(x + difx);
        this.setY(y + dify);
    }
    
    public void releaseDrag(double difx, double dify){
        this.setX(x + difx);
        this.setY(Math.floor((y + dify)/ 10) * 10);

        this.y = Math.floor((y+ dify)/ 10) * 10;
        this.x = x+ difx;
    }
    
    public void extend(double extentionlen){
        this.setWidth(extentionlen);
    }
    
    public void releaseExtend(double extentionlen){
        this.setWidth(extentionlen);
    }
    
    @Override
    public boolean equals(Object o){
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Note)) { 
            return false; 
        } 
        Note other = (Note) o; 
          
        // Compare the data members and return accordingly  
        return Double.compare(x, other.x) == 0 
                && Double.compare(duration, other.duration) == 0
                && display_note.equals(other.display_note); 
                
    }
    
    public double getMoveableX(){
        return x;
    }
    
    public double getMoveableY(){
        return y;
    }
    
    public void display_select(){
        this.getStyleClass().add("selected");
    }
    

    public void display_deselect(){
        this.getStyleClass().remove("selected");
    }
    
    
    /**
     * function that returns information about the note that communicates with 
     * MidiPlayer.java and its addMidiEvent method.
     * element 0 refers to the channel of the corresponding instrument
     * element 1 refers to the data1 byte, which is the MIDI instrument
     * element 2 refers to the data2 byte, which currently will always be 0
     * element 3 refers to the start tick of the corresponding instrument
     * element 4 refers to the track index, which currently will always be 0
     * @param start_tick the start tick of the current note
     * @return the list of the parameters of the MIDI event corresponding to 
     *         the instrument of the note.
     */ 
    private void initChannelIndex() {
        if ("Piano".equals(instrument)) {
            channel_index = 0;
        }
        else if ("Harpsichord".equals(instrument)) {
            channel_index = 1;
        }
        else if ("Marimba".equals(instrument)) {
            channel_index = 2;
        }
        else if ("Church-Organ".equals(instrument)) {
            channel_index = 3;
        }
        else if ("Accordion".equals(instrument)) {
            channel_index = 4;
        }
        else if ("Guitar".equals(instrument)) {
            channel_index = 5;
        }
        else if ("Violin".equals(instrument)) {
            channel_index = 6;
        }
        else if ("French-Horn".equals(instrument)) {
            channel_index = 7;
        }    
    }
    
    private void Convert_Instrument(){
        if(instrument.contains(" ")){
            instrument = instrument.replace(' ', '-');
        }
    }
    
    
    
    
    

}

