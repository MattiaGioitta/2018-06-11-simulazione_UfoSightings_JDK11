package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Anno> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	this.txtResult.clear();
    	String scelto = this.boxStato.getValue();
    	if(scelto == null) {
    		this.txtResult.setText("scegli stato");
    		return;
    	}
    	List<String> precedenti = this.model.getPrecedenti(scelto);
    	List<String> successivi = this.model.getSuccessivi(scelto);
    	List<String> raggiungibili = this.model.getRaggiungibili(scelto);
    	this.txtResult.appendText("Stati precedenti\n");
    	for(String s : precedenti) {
    		this.txtResult.appendText(s+"\n");
    	}
    	this.txtResult.appendText("Stati successivi\n");
    	for(String s : successivi) {
    		this.txtResult.appendText(s+"\n");
    	}
    	this.txtResult.appendText("Stati raggiungibili: "+raggiungibili.size()+"\n");
    	this.txtResult.appendText("Stati raggiungibili\n");
    	for(String s : raggiungibili) {
    		this.txtResult.appendText(s+"\n");
    	}

    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	this.txtResult.clear();
    	Anno a = this.boxAnno.getValue();
    	if(a == null) {
    		this.txtResult.setText("scegli anno");
    		return;
    	}
    	this.model.createGraph(a);
    	this.txtResult.appendText("Grafo creato con: \n");
    	this.txtResult.appendText(String.format("#Vertici: %d\n#Archi: %d\n", this.model.nVertici(),this.model.nArchi()));
    	this.boxStato.getItems().addAll(this.model.getVertici());
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	this.txtResult.clear();
    	String scelto = this.boxStato.getValue();
    	if(scelto == null) {
    		this.txtResult.setText("scegli stato");
    		return;
    	}
    	this.model.cammino(scelto);
    	List<String> bestPath = this.model.getCammino();
    	if(bestPath.size()==0) {
    		this.txtResult.appendText("Nessun cammino per lo stato scelto");
    		return;
    	}
    	for(String s : bestPath) {
    		this.txtResult.appendText(s+"\n");
    	}

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getAnni());
	}
}
