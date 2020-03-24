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
public class Note {

    boolean isSelected = false;
    private String instrument;
    private Rectangle display_note;
    
    /**
     * Constructs a note. 
     * @param temp_instrument 
     * @param start_position
     */
    public Note(String temp_instrument){
        instrument = temp_instrument;
        
    }
    /**
     * makes the rectangle on screen symbolizing a note
     * @param x the x coordinate of the note
     * @param y the y coordinate of the note
     * @return disp
     */
    public Rectangle draw_note(double x, double y) {
        y = Math.floor(y / 10) * 10;
        display_note = new Rectangle(x, y, 100, 10);
        display_note.getStyleClass().add("note");
        Convert_Instrument();
        display_note.getStyleClass().add(instrument);
        //display_select();
     return display_note;
    }
    
    public void display_select(){
        display_note.getStyleClass().add("selected");
        isSelected = true;
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
            case "Church Organ":
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
            case "French Horn":
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
