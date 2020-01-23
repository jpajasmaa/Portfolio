package fxKaloriLaskuri;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kalorilaskuri.Kalorilaskuri;
import kalorilaskuri.RuokaAine;
import kalorilaskuri.SailoException;

/**
 * @author juuso pajasmaa ja riku laitinen
 * @version 29 4 2019
 *
 */
public class UusiRuokaController implements  ModalControllerInterface<RuokaAine>, Initializable{
   
    
    @FXML private Button fxcancel;
    @FXML private Button fxsave;
    @FXML private TextField fxkcalTotal;
    @FXML private Button fxlisaaRuoka;
    @FXML private TextField fxRuoka;
    @FXML private TextField fxkalorit;
    @FXML private ChoiceBox<RuokaAine> fxvalitse;
    @FXML private Button fxpoista;
    @FXML private Button fxpaivita;
    @FXML private Button fxmuokkaa;

    @FXML
    private void keyLisaaRuoka() {
        lisaaRuoka();
    }

    @FXML
    private void keyChoiceBox() {
        asetaRuuatBox();
    }

    @FXML
    private void handlePoista() {
        poistaRuoka();
    }
    
    @FXML
    private void keyMuokkaa() {
    	muokkaaRuoka();	
    }
    
    @FXML
    private void keyPaivita() {
        asetaRuoka();
    }
    
    
    @FXML
    private void keyPeruuta() {
    	ModalController.closeStage(fxcancel); 
    }



    @FXML
    private void keyTallenna() throws SailoException {
    	kalorilaskuri.tallenna();
    	asetaRuuatBox();
    }


	@Override
	public void handleShown() {
		//	
	}
	
//============================================================================================================================================	
	
	private Kalorilaskuri kalorilaskuri;
	
	ObservableList<RuokaAine> ruokaLista = FXCollections.observableArrayList();
	
	private String ruokaNimi = "";
	private double kalorit = 0;
	

	private void setKalorilaskuri(Kalorilaskuri kalorilaskuri) {
	    this.kalorilaskuri = kalorilaskuri;
	}
	
	/**
	 * @param modalityStage on
	 * @param oletus oletus ruokaaine
	 * @param kalorilaskuri laskuri
	 * @return uuden ruokaaineen
	 */
	public static RuokaAine uusiRuoka(Stage modalityStage, RuokaAine oletus, Kalorilaskuri kalorilaskuri) {
         return ModalController.<RuokaAine, UusiRuokaController>showModal(
                     UusiRuokaController.class.getResource("UusiRuoka.fxml"),
                     "Kalorilaskuri",
                     modalityStage, oletus,
                     ctrl -> { ctrl.setKalorilaskuri(kalorilaskuri); }
                 );
	    }
	
	
	/**
	 * poistetaan valittu ruoka-aine
	 */
	public void poistaRuoka() {
	    RuokaAine poistettava = fxvalitse.getValue();
        if ( poistettava == null ) {
            Dialogs.showMessageDialog("Et voi poistaa tyhjaa!");
            return;
        }
        kalorilaskuri.poista(poistettava);
        asetaRuuatBox();
	}
	
	/**
	 * muokataan ruokaa
	 */
	public void muokkaaRuoka() {	
	    if (fxvalitse.getValue() == null || fxRuoka.getText().isBlank() || fxkalorit.getText().isBlank()) {
	        Dialogs.showMessageDialog("Valitse ensiksi muokattava ruoka");
	        return;
	    }
	    if(tarkista(fxkalorit.getText().toString())) {
	    	Dialogs.showMessageDialog("Kalorimaarassa saa olla vain numeroita");
	    	return;
	    } 
	    RuokaAine ruoka = fxvalitse.getValue();
	    String nimi = fxRuoka.getText();
        Double kalori = Double.valueOf(fxkalorit.getText());
	    
        if (nimi != fxRuoka.getText() || nimi.contains(".*\\d.*") && kalori != (Double.valueOf(fxkalorit.getText()))) {            
        	kalorilaskuri.muokkaa(ruoka, nimi, kalori);
        }    
        else Dialogs.showMessageDialog("Nimen sekÃ¤ kalorimaaran on muututtava");
        return;
	}
	
	/**
	 * muokataan ruoka-aine
	 */
	public void asetaRuoka() {
	    RuokaAine muokattava = fxvalitse.getValue();
        if ( muokattava == null ) {
            Dialogs.showMessageDialog("Et voi muokata tyhjaa!");
            return;
        }
        String nimi = muokattava.getRuokaNimi();
        Double kalori = muokattava.getRuokaKalorit();
        fxRuoka.setText(nimi);
        fxkalorit.setText(kalori.toString());
	}
	
	/**
     * lisataan ruokaAine jonka kayttaja kirjoittaa textboxeihin
     */
    public void lisaaRuoka() {
        ruokaNimi = fxRuoka.getText();
        if(ruokaNimi.isBlank() || fxkalorit.getText().isBlank()) {
        	Dialogs.showMessageDialog("Anna uudelle ruualle nimi ja kcal/100g arvo!");
        	return;
        }
        if(tarkista(fxkalorit.getText().toString())) {
	    	Dialogs.showMessageDialog("Kalorimaarassa saa olla vain numeroita");
	    	return;
	    } 
        kalorit = Double.valueOf(fxkalorit.getText());
        if (ruokaNimi.equals("")) return;
        RuokaAine uusiRuoka = new RuokaAine();
        uusiRuoka.rekisteroi();
        uusiRuoka.setRuokaAineId();
        asetaRuuatBox();
        try {
            uusiRuoka.tayta(ruokaNimi, kalorit);
            kalorilaskuri.lisaa(uusiRuoka);
            ruokaNimi = "";
        } catch (SailoException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
	 * tarkistaa loytyyko tietyt kirjaimet ja merkit tekstista
	 * @param string teksti mita tarkistetaan
	 * @return palauttaa true jos loytyy ja false jos ei loydy
	 */
	public boolean tarkista (String string) {
	    return Pattern.compile("[a-öA-Ö[^0-9]]").matcher(string).find();
	}
    
    /**
     * asetaan ruuat paikalleen
     */
    public void asetaRuuatBox() {
    	fxvalitse.getItems().removeAll(kalorilaskuri.getRuoka());
    	ruokaLista.clear();
    	ruokaLista.addAll(kalorilaskuri.getRuoka());
        fxvalitse.getItems().addAll(kalorilaskuri.getRuoka());
    }


    @Override
    public RuokaAine getResult() {
        return null;
    }


    @Override
    public void setDefault(RuokaAine arg0) {
        //
    }	
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //
    }
}