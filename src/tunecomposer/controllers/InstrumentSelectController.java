/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author andrewyeon
 */
public class InstrumentSelectController{
    /**
    *@FXML
    *private RadioButton pianoButton;
    *@FXML
    *private RadioButton harpsichordButton;
    *@FXML
    *private RadioButton marimbaButton;
    *@FXML
    *private RadioButton organButton;
    *@FXML
    *private RadioButton accordionButton;
    *@FXML
    *private RadioButton guitarButton;
    *@FXML
    *private RadioButton violinButton;
    *@FXML
    *private RadioButton frenchhornButton;
    */
        
    @FXML
    private ToggleGroup instrument;
    
    //@FXML
    //private AnchorPane instrumentSelect;
    
    public static String current_instrument;
    
    private MainController main;
    
    
    
    
    public final void change_instrument(){
        RadioButton selectedRadioButton = (RadioButton) instrument.getSelectedToggle();
        String toggleGroupValue = selectedRadioButton.getText();
        current_instrument = toggleGroupValue;
        System.out.println(current_instrument);
    }
    
    public final String getInstrument() {
        return current_instrument;
    }
    
    public void init(MainController mainController) {
        this.main = mainController;
    }
    
}
