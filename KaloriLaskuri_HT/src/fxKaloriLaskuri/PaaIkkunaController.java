package fxKaloriLaskuri;

import java.io.PrintStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;


import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import kalorilaskuri.Ateria;
import kalorilaskuri.Kalorilaskuri;
import kalorilaskuri.RuokaAine;
import kalorilaskuri.SailoException;


/**
 * @author juuso pajasmaa ja riku laitinen
 * @version 29 4 2019
 *
 */
public class PaaIkkunaController implements Initializable {
    
    private int ateriaT; // ateriaTyyppi
    
    @FXML private DatePicker fxdate;
    @FXML private Button fxapRuoka;
    @FXML private Button fxloRuoka;
    @FXML private Button fxpaRuoka;
    @FXML private Button fxilRuoka;
    @FXML private Button fxateria;
    @FXML private Button fxlaske;
    @FXML private Button fxnewRuoka;
    @FXML private TextField fxetsi;
    @FXML private TextArea displayText;
    @FXML private ScrollPane panelAteria;
    @FXML private Button poistaAteria;
    @FXML private Button muokkaaAteria;
    @FXML private ListChooser<Ateria> chooserAteriat;    
    @FXML private Button fxEtsiNappi;
    
    

    
    @FXML
    private void keyMuokkaaAteria() {
        muokkaaAteria();
    }
    
    @FXML
    private void keyPoistaAteria() {
        poistaAteria();
    }
    
    @FXML
    private void keyReleased() {
        Dialogs.showMessageDialog("Ei osata viela tulostaa");
    }
    
    
    @FXML
    private void keyLaske() {
        tallenna();
    }
    
    @FXML
    private void etsiNappi() {
    	hae(0);
    }

    @FXML
    private void keyAP(){  
        ateriaT = 0;
    }
    
    @FXML
    private void keyLO(){
        ateriaT = 1;
    }
    
    @FXML
    private void keyPA(){
        ateriaT = 2;
    }
    
    @FXML
    private void keyIL(){
        ateriaT = 3;
    }
    
    @FXML
    private void keyUusiRuoka() {
        lisaaRuoka();
    }

    @FXML
    private void keyAteria() {
        lisaaAteria(ateriaT);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alusta();
    }



	// ===============================================================================================================================================
    
    private Kalorilaskuri kalorilaskuri;
    private String laskurinnimi = "laskuri";
    private Ateria ateriaKohdalla;
    private String pattern = "dd:MM:yyyy";
    private DateTimeFormatter muoto = DateTimeFormatter.ofPattern(pattern);
    private String Pvm = LocalDate.now().format(muoto).toString();
    
    /**
     * kalenteri
     */
    public void keyCalender() {
    	
        fxdate.setConverter(new StringConverter<LocalDate>() {
            {
                fxdate.setPromptText(pattern.toLowerCase());
                
            }
            
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {          
                    return muoto.format(date);
                } 
                return "";    		    
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, muoto);
                }
                return null;
            }
        }
        );
       
    }
    
    /** 
     * @param kalorilaskuri jota käytetään tässä käyttöliittymässä
     */
    public void setKalorilaskuri(Kalorilaskuri kalorilaskuri) {
        this.kalorilaskuri = kalorilaskuri;
        naytaAteria();
        kalorilaskuri.setPvm(Pvm);
    }
    
    /** ei valttamatta toimi kunnolla
     * lisataan uusi ruokaAine kalorilaskuriin
     */
    public void lisaaRuoka() {
        RuokaAine uusiRuoka = new RuokaAine();
        uusiRuoka = UusiRuokaController.uusiRuoka(null, uusiRuoka, kalorilaskuri);
        hae(0);
    }   
    
    /**
     * poistetaan valittu ateria
     */
    public void poistaAteria() {
        Ateria ateria = ateriaKohdalla;
        if ( ateria == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko ateria: " + ateria.annaNimi(ateria.getAteriaType()), "Kyllä", "Ei")) return;
        kalorilaskuri.poista(ateria);
        int index = chooserAteriat.getSelectedIndex();
        hae(0);
        chooserAteriat.setSelectedIndex(index);
    }
    
    /**
     * muokataan valittua ateriaa
     */
    public void muokkaaAteria() {
        if (ateriaKohdalla == null) return;
        ateriaKohdalla = UusiAteriaController.uusiAteria(null, ateriaKohdalla, ateriaKohdalla.getAteriaType(), kalorilaskuri); 
        hae(0);
    }
    
    /**
     * @return viimeisimman ruuan
     */
    public RuokaAine getRuoka() {
        int lkm = kalorilaskuri.getRuokaAineet();
        return kalorilaskuri.annaRuuat(lkm);
    }
    
    /**
     * lisataan ateria, jossa dummyruoka kalorilaskuriin
     * @param ateriaTyyppi ateria
     */
    public void lisaaAteria(int ateriaTyyppi) {
    	if(fxdate.getValue() != null) asetaPvm();
        Ateria uusiAteria = new Ateria();
        uusiAteria = UusiAteriaController.uusiAteria(null, uusiAteria, ateriaTyyppi, kalorilaskuri);
        hae(0);
    }
    
    /**
     * tulostetaan ateria
     */
    private void naytaAteria() {
        ateriaKohdalla = chooserAteriat.getSelectedObject();
        if (ateriaKohdalla == null) return;
        displayText.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(displayText)) {
            ateriaKohdalla.tulosta(os);
        }
    }
    
    /**
     * luetaan tiedostot
     * @param nimi tiedoston nimi
     * @return null jos onnistuu 
     * @throws SailoException sailo
     */
    protected String lueTiedosto(String nimi) throws SailoException {
        laskurinnimi = nimi;
        kalorilaskuri.lueTiedostosta(nimi);
        hae(0); 
        return null;
    }
    
    /**
     * 
     * @param id id
     */
    private void hae(int id) {
        int aid = id; // aid aterian id, joka aktivoidaan haun jälkeen 
        if ( aid <= 0 ) { 
            Ateria kohdalla = ateriaKohdalla; 
            if ( kohdalla != null ) aid = kohdalla.getAteriaId(); 
        }
        chooserAteriat.clear();

        String ehto = fxetsi.getText();
        if(fxdate.getValue() != null) asetaPvm();
        int index = 0;
        
        Collection<Ateria> ateriat;
        try {
            ateriat = kalorilaskuri.etsi(Pvm ,ehto);
            int i = 0;
            for (Ateria ateria:ateriat) {
                if (ateria.getAteriaId() == aid) index = i;
                chooserAteriat.add(ateria.annaNimi(ateria.getAteriaType()), ateria);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Aterian hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserAteriat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }    
    
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            kalorilaskuri.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
   
   /**
    * Paivamaaran asettaminen kalenterin tietojen mukaan
    */
   private void asetaPvm() {
	   Pvm = fxdate.getValue().format(muoto).toString();
	   kalorilaskuri.setPvm(Pvm);
   }
    
    /**
     * 
     * @return true jos onnistui, false jos ei
     * @throws SailoException sailo
     */
    public boolean avaa() throws SailoException {
        String nimi = laskurinnimi;
        lueTiedosto(nimi);
        return true;
    }
    
    /**
     * alustaa alustan
     */
    private void alusta() {
        ateriaT = 0;       
        chooserAteriat.clear();
        chooserAteriat.addSelectionListener(e -> naytaAteria());
    }    
}