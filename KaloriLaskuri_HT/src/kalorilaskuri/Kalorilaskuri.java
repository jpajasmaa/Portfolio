/**
 * 
 */
package kalorilaskuri;


import java.io.File;
import java.util.Collection;

import fi.jyu.mit.fxgui.Dialogs;

/** 
 * @author juuso pajasmaa ja riku laitinen
 * @version 29 4 2019
 *
 Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import kalorilaskuri.SailoException;
 *  private Kalorilaskuri laskuri;
 *  private Ateria aamupala;
 *  private Ateria lounas;
 *  private Ateria paivallinen; 
 *  private Ateria iltapala;
 *  private Ateria iltapala2;
 *  private int aid1;
 *  private int aid2;
 *  private int aid3;
 *  private int aid4;
 *  private int aid5;
 *  private RuokaAine porkkana;
 *  private RuokaAine jauheliha;
 *  private RuokaAine kala;
 *  private RuokaAine salaatti;
 *  private RuokaAine peruna;
 *  
 *  public void alustaKalorilaskuri() {
 *    laskuri = new Kalorilaskuri();
 *    
 *    porkkana = new RuokaAine(); porkkana.rekisteroi(); porkkana.setRuokaAineId(); porkkana.tayta("porkkana", 40);
 *    jauheliha = new RuokaAine(); jauheliha.rekisteroi(); jauheliha.setRuokaAineId(); jauheliha.tayta("jauheliha", 300);
 *    kala = new RuokaAine(); kala.rekisteroi(); kala.setRuokaAineId(); kala.tayta("kala", 350);
 *    salaatti = new RuokaAine(); salaatti.rekisteroi(); salaatti.setRuokaAineId(); salaatti.tayta("salaatti", 30);
 *    peruna = new RuokaAine(); peruna.rekisteroi(); peruna.setRuokaAineId(); peruna.tayta("peruna", 80);
 *    
 *    aamupala = new Ateria(); aamupala.rekisteroi(); aamupala.tayta("29.04.2019", aamupala.getAteriaId(), 0, porkkana.getRuokaAineId(), "porkkana", 104.0);
 *    lounas = new Ateria(); lounas.rekisteroi(); lounas.tayta("29.04.2019", lounas.getAteriaId(), 1, jauheliha.getRuokaAineId(), "jauheliha", 300.0);
 *    paivallinen = new Ateria(); paivallinen.rekisteroi(); paivallinen.tayta("30.04.2019", paivallinen.getAteriaId(), 2, kala.getRuokaAineId(), "kala", 200.0);
 *    iltapala = new Ateria(); iltapala.rekisteroi(); iltapala.tayta("29.04.2019", iltapala.getAteriaId(), 3, salaatti.getRuokaAineId(), "salaatti", 500.0);
 *    iltapala2 = new Ateria(); iltapala2.rekisteroi(); iltapala2.tayta("29.04.2019", iltapala2.getAteriaId(), 3, salaatti.getRuokaAineId(), "salaatti", 500.0);
 *    aid1 = aamupala.getAteriaId();
 *    aid2 = lounas.getAteriaId();
 *    aid3 = paivallinen.getAteriaId();
 *    aid4 = iltapala.getAteriaId();
 *    aid5 = iltapala2.getAteriaId();
 *    try {
 *    laskuri.lisaa(porkkana);
 *    laskuri.lisaa(jauheliha);
 *    laskuri.lisaa(kala);
 *    laskuri.lisaa(salaatti);
 *    laskuri.lisaa(peruna);
 *    laskuri.lisaa(aamupala);
 *    laskuri.lisaa(lounas);
 *    laskuri.lisaa(paivallinen);
 *    laskuri.lisaa(iltapala);
 *    laskuri.lisaa(iltapala2);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Kalorilaskuri {

    private Ateriat ateriat = new Ateriat();
    private RuokaAineet ruokaAineet = new RuokaAineet();
    private String Pvm = "";
    
    
    /**hakee ruoka-aineiden maaran
     * @return ruoka-aineiden maaaran
     */
    public int getRuokaAineet() {
        return ruokaAineet.getLkm(); 
    }    
    
    /** hakee aterialistan koon
     * @return koon
     */
    public int getAteriat() {
        return ateriat.getLkm();
    }
    
    /** asetetaan paivamaara
     * @param pvm paivamaara
     */
    public void setPvm(String pvm) {
		this.Pvm = pvm;
		
	}
    /** haetaan paivamaara
     * @return paivamaara merkkijonona
     */
    public String getPvm() {
		return this.Pvm;
	}
    
    /**
     * @param ruokaid ruoka-aineen id
     * @return ruoka-aineen nimen
     */
    public String getRuuanNimi(int ruokaid) {
        return ruokaAineet.getRuokaNimi(ruokaid);
    }
    
    /**
     * @param ruokaAineId ruoka-aineen ruokaid
     * @return ruoka-aineen nimen
     */
    public String getRuokaNimi(int ruokaAineId) {
        return ruokaAineet.getRuokaAine(ruokaAineId);
    }
    
    /** hakee ruoka-aineen
     * @param i indeksi
     * @return i indeksin viitteen
     */
    public RuokaAine annaRuuat(int i) {
        return ruokaAineet.annaRuuat(i);
    }
    
    /** hakee aterian ruoka-aineen
     * @param ateria kyseinen ateria
     * @return aterian ruokaaineen.
     */
    public RuokaAine annaRuokaAineet(Ateria ateria) {
        return ruokaAineet.annaRuuat(ateria.getAteriaId());
    }
    
    
    /** lisaa ruuan ruoka aineisiin
     * @param ruoka lisattava ruoka
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     */
    public void lisaa(RuokaAine ruoka) throws SailoException { 
        ruokaAineet.lisaa(ruoka);
    }
    
    /** lisaa aterian aterioihin
     * @param ateria lisattava ateria
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaKalorilaskuri();
     *  laskuri.etsi("29.04.2019", "porkkana").size() === 1;
     *  laskuri.lisaa(aamupala);
     *  laskuri.etsi("29.04.2019", "porkkana").size() === 2;
     */
    public void lisaa(Ateria ateria) throws SailoException { 
        ateriat.lisaa(ateria);
    }
    
    /** 
     * Palauttaa listassa hakuehtoon vastaavien aterioiden viitteet 
     * @param pvm aterian paivamaara
     * @param hakuehto hakuehto   
     * @return tietorakenteen loytyneista aterioista 
     * @throws SailoException Jos jotakin menee väärin
     * @example 
     * <pre name="test">
     * </pre>
     */ 
    public Collection<Ateria> etsi(String pvm, String hakuehto) throws SailoException { 
        return ateriat.etsi(pvm ,hakuehto); 
    } 
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi tuotu nimi
     */
    public void setTiedosto(String nimi) {
        File f = new File(nimi);
        f.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        ateriat.setTiedostonPerusNimi(hakemistonNimi + "Ateriat");
        ruokaAineet.setTiedostonPerusNimi(hakemistonNimi + "RuokaAineet"); 
    }    
    
    /** luetaan tiedostosta
     * @param nimi tiedoston nimi
     * @throws SailoException  sailoexception jos lukeminen epaonnistuu
     *  TODO: korjaa testit
     * @example
     * <pre name="test"> 
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
    	ruokaAineet = new RuokaAineet();
    	ateriat = new Ateriat();
        setTiedosto(nimi);
        ruokaAineet.lueTiedostosta();
        ateriat.lueTiedostosta();
    }
    
    /**  tallennetaan tiedostoihin.
     * @throws SailoException poikkeus jos ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        
        try {
            ruokaAineet.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
            Dialogs.showMessageDialog("Ruoka-aineen tallentaminen epaonnistui! " + virhe);;
        }
        
        try {
            ateriat.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
            Dialogs.showMessageDialog("Aterian tallentaminen epaonnistui! " + virhe);;
        }
        if (!"".equals(virhe)) throw new SailoException(virhe);
    }
    
    /** poistetaan valittu ruoka-aine
     * @param poistettava poistettava
     * <pre name="test">
     * #THROWS Exception
     *   alustaKalorilaskuri();
     *   laskuri.getRuokaAineet() === 5;
     *   laskuri.getAteriat() === 5;
     *   laskuri.poista(porkkana);
     *   laskuri.getRuokaAineet() === 4;
     *   laskuri.getAteriat() === 4;
     * </pre>
     */
    public void poista(RuokaAine poistettava) {
    	String virhe = "";
    	
    	try {
    		ruokaAineet.poista(poistettava);
    		ateriat.poista(poistettava.getRuokaAineId());
    	} catch(SailoException ex) {
    		virhe = ex.getMessage();
            Dialogs.showMessageDialog("Ruoka-aineen tallentaminen epaonnistui! " + virhe);;
    	}		
	}
    
    /**
     * @param ateria poistettava
     * @return true jos onnistuu, false jos ei
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKalorilaskuri();
     *   laskuri.etsi("29.04.2019", "salaatti").size() === 2;
     *   laskuri.poista(iltapala2) === true;
     *   laskuri.poista(iltapala2) === false;
     *   laskuri.etsi("29.04.2019", "salaatti").size() === 1;
     * </pre>
     */
    public boolean poista(Ateria ateria) {
        if (ateria == null) return false;
        boolean onnistui = ateriat.poistaAteria(ateria.getAteriaId());
        return onnistui;
    }

    /** 
     * muokataan valittua ruoka-ainetta
     * @param ruoka kyseinen ruoka
     * @param nimi ruuan nimi
     * @param kalori ruuan kalorimaara
     */
    public void muokkaa(RuokaAine ruoka, String nimi, double kalori) {
        ruokaAineet.muokkaa(ruoka ,nimi, kalori);
    }
    
    /** 
     * muokataan valittua ateriaa
     * @param ate valittu ateria
     * @param ruoka kyseinen ruoka
     * @param maara ruokamaara
     */
    public void muokkaa(Ateria ate, RuokaAine ruoka, String maara) {
        ateriat.muokkaa(ate, ruoka, maara);
    }
    
    /** getti ruoka-aineelle
     * @return ruoka-aineen
     */
    public RuokaAine[] getRuoka() {
		return ruokaAineet.getRuoka();
	}
    
    /** 
     * Nopea kokeilu, ei toimi oikein
     * @param ate ateria
     * @return aterian kalorit
    public double Laske(Ateria ate) {
        double kalorimaara = 0;
        RuokaAine ruoka = annaRuokaAineet(ate);
        kalorimaara = ruoka.getRuokaKalorit()*ate.getMaara();
        return kalorimaara;
    }
     */
}