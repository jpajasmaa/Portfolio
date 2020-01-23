package fxKaloriLaskuri;



import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kalorilaskuri.Ateria;
import kalorilaskuri.Kalorilaskuri;
import kalorilaskuri.RuokaAine;
import kalorilaskuri.SailoException;


/**
 * @author juuso pajasmaa ja riku laitinen
 * @version 29.4.2019
 *
 */
public class UusiAteriaController implements  ModalControllerInterface<Ateria>, Initializable{

    @FXML private Button fxsave;
    @FXML private Button fxcancel;
    @FXML private TextField fxmaara;
    @FXML private Label fxAteriaType;
    @FXML private ChoiceBox<RuokaAine> fxruuat;
    @FXML private TextArea fxnaytto;
    @FXML private Button fxlisaa;  
    @FXML private Button fxmuokkaa;    
    
    
    @FXML
    private void keyPeruuta() {
    	ModalController.closeStage(fxmaara);
    }

    @FXML
    private void keyReleased() {
        Dialogs.showMessageDialog("Ei osata viela tulostaa");  	
    }

    @FXML
    private void keyTallenna() throws SailoException {
        kalorilaskuri.tallenna();
    }
   
    @FXML
    private void keyLisaa() {
        lisaaAteria();
    }


    @FXML
    private void keyRuuat() {
        asetaRuuatBox();
    }
    
    @FXML
    private void keyMuokkaa() {
        muokkaa();
    }

	@Override
	public void handleShown() {
	    //
	}

	
	// =============================================================================================================================================================
	 

	private Kalorilaskuri kalorilaskuri;
	
	private Ateria viimeisinAteria;
	private Ateria ateriaKohdalla;
	private int ateriaType;
	private double maara;
	
	ObservableList<RuokaAine> ruokaLista = FXCollections.observableArrayList();
	
	private void setKalorilaskuri(Kalorilaskuri kalorilaskuri, int ateriatyyppi) {
        this.kalorilaskuri = kalorilaskuri;
        this.ateriaType = ateriatyyppi;
        fxAteriaType.setText(setTyyppi(ateriatyyppi));
    }
	
	/** setataan aterian oikea nimi merkkijonona
	 * @param ateriatyyppi mika ateria kyseessa
	 * @return palauttaa merkkijonona mika ateria
	 */
	public String setTyyppi(int ateriatyyppi) {
	    if (ateriatyyppi == 0) return "Aamupala";
	    if (ateriatyyppi == 1) return "Lounas";
	    if (ateriatyyppi == 2) return "P√§iv√§llinen";
	    if (ateriatyyppi == 3) return "Iltapala";
	    return "";
	}
	
	/**
	 * asetaan ruuat listaan
	 */
	public void asetaRuuatBox() {
	    fxruuat.getItems().removeAll(kalorilaskuri.getRuoka());
        ruokaLista.clear();
        ruokaLista.addAll(kalorilaskuri.getRuoka());
        fxruuat.getItems().addAll(kalorilaskuri.getRuoka());
	}
	
	/**
	 * lisataan uusi ateria
	 */
	public void lisaaAteria() {
	    RuokaAine valittu = fxruuat.getValue();
	    if(valittu == null) {
	    	Dialogs.showMessageDialog("Valitse ruoka-aine ennen lis‰‰mist‰!");
	    	return;
	    } 
        if (!fxmaara.getText().isEmpty() && !tarkista(fxmaara.getText().toString())) maara = Double.valueOf(fxmaara.getText());
        else {
        	Dialogs.showMessageDialog("Syotyyn maaraan voi vain laittaa numeroita!");
        	return;
        }
        Ateria uusiAteria = new Ateria();
        uusiAteria.rekisteroi();
        try {
                uusiAteria.tayta(kalorilaskuri.getPvm(),uusiAteria.getAteriaId(), ateriaType, valittu.getRuokaAineId(), kalorilaskuri.getRuokaNimi(valittu.getRuokaAineId()) ,maara);
                kalorilaskuri.lisaa(uusiAteria);
                viimeisinAteria = uusiAteria;
        } catch (SailoException e) {
            e.printStackTrace();
        }
        if (viimeisinAteria != null) naytaAteria();
	}
	
	/**
	 * tarkista aliohjelma tarkistaa sisaltaako string kirjaimia tai erikoismerkkeja
	 * @param string tarkistettava merkkijono
	 * @return palauttaa true jos on tai false jos ei ole
	 */
	public boolean tarkista (String string) {
	    return Pattern.compile("[a-ˆA-÷[^0-9]]").matcher(string).find();
	}
	
	/**
	 * muokataan ateriaa
	 */
	public void muokkaa() {
	    Ateria ate = ateriaKohdalla;
	    RuokaAine ruoka = fxruuat.getValue();
        String ruokamaara = fxmaara.getText();
        if(ruoka == null) {
        	Dialogs.showMessageDialog("Valitse ruoka mita muokata ennen muokkaamista!");
        	return;
        }
        if(tarkista(ruokamaara)) Dialogs.showMessageDialog("Syotyyn maaraan voi vain laittaa numeroita!");
        if (ruokamaara != fxmaara.getText()) {            
            kalorilaskuri.muokkaa(ate, ruoka, ruokamaara);
        }
        else Dialogs.showMessageDialog("Kalorimaaran on muututtava");
        return;
	}
	
	/**
     * tulostetaan kasitelty ateria
	 * @param kohdalla mika ateria
     */
    public void naytaAteria(Ateria kohdalla) {
        if (kohdalla == null) return;
        fxmaara.setText(String.valueOf(kohdalla.getMaara()));
        fxnaytto.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(fxnaytto)) {
            kohdalla.tulosta(os); 
        }
    }
	
	/**
	 * tulostetaan viimeiseki kasitelty ateria
	 */
	public void naytaAteria() {
	    fxnaytto.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(fxnaytto)) {
            viimeisinAteria.tulosta(os); 
        }
	}
	
    /**
     * @param modalityStage on
     * @param oletus oletus ruokaaine
     * @param ateriatyyppi ateriatyyppi
     * @param kalorilaskuri laskuri
     * @return uuden aterian
     */
    public static Ateria uusiAteria(Stage modalityStage, Ateria oletus, int ateriatyyppi, Kalorilaskuri kalorilaskuri) {
         return ModalController.<Ateria, UusiAteriaController>showModal(
                     UusiAteriaController.class.getResource("UusiAteriaController.fxml"),
                     "Kalorilaskuri",
                     modalityStage, oletus,
                     ctrl -> { ctrl.setKalorilaskuri(kalorilaskuri, ateriatyyppi); }
                 );
        }
    
    
    @Override
    public Ateria getResult() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void setDefault(Ateria oletus) {
        ateriaKohdalla = oletus;
        naytaAteria(ateriaKohdalla);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }
}