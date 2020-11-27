package miner.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import miner.Main;



public class InputsController {

    public static String gridSize;
    public static String pitLoc;
    public static String goldLoc;
    public static String beaconLoc;
    public static String config;

    private static Main main;

    ObservableList<String> toggleChoicesList = FXCollections.observableArrayList("Random", "Smart") ;

    @FXML
    private TextField gridSizeField;
    @FXML
    private TextArea pitField;
    @FXML
    private TextField goldField;
    @FXML
    private TextArea beaconField;

    @FXML
    public ChoiceBox randomSmartBox;

    @FXML
    public void startMiner(ActionEvent actionEvent) {
        System.out.println("Puts in the values from GUI to model and starts the miner");
        gridSize = gridSizeField.getText();
        pitLoc = pitField.getText();
        goldLoc = goldField.getText();
        beaconLoc = beaconField.getText();
        config = (String) randomSmartBox.getValue();
        main.closeWindow();
    }

    @FXML
    public void initialize() {
        randomSmartBox.setValue("Random");
        randomSmartBox.setItems(toggleChoicesList);
    }
}