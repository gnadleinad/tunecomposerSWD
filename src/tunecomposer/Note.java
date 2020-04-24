/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.scene.shape.Rectangle;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author schneicw
 */
public class Note extends Rectangle implements Moveable{

    private String instrument;
    public Double x;
    public Double y; //midi value of y
    public Double duration;

    
    /**
     * Constructs a note. 
     * @param temp_instrument 
     * @param start_position
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
        x = temp_x;
        y = temp_y;
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
                && this.equals(other); 
                
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
    public int[] get_MIDI(double start_tick) {
        int[] MIDI_array;
        MIDI_array = new int[5];
        MIDI_array[2] = 0;
        MIDI_array[3] = (int)start_tick;
        MIDI_array[4] = 0;
        if (instrument != null) switch (instrument) {
            case "Piano": //works
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE;
                MIDI_array[1] = 0;
                break;
            case "Harpsichord":
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE + 1;
                MIDI_array[1] = 6;
                break;
            case "Marimba":
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE + 2;
                MIDI_array[1] = 12;
                break;
            case "Church-Organ":
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE + 3;
                MIDI_array[1] = 19;
                break;
            case "Accordion":
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE + 4;
                MIDI_array[1] = 21;
                break;
            case "Guitar":
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE + 5;
                MIDI_array[1] = 24;
                break;
            case "Violin":
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE + 6;
                MIDI_array[1] = 40;
                break;
            case "French-Horn":
                MIDI_array[0] = ShortMessage.PROGRAM_CHANGE + 7;
                MIDI_array[1] = 60;
                break;
            default:
                break;
        }
        return MIDI_array;
    }
    
    
    
    private void Convert_Instrument(){
        if(instrument.contains(" ")){
            instrument = instrument.replace(' ', '-');
        }
    }
    
    
    
    
    

}

