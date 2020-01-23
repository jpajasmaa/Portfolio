/**
 * 
 */
package kalorilaskuri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/** Ruoka-aine luokka
 * @author juuso pajasmaa ja riku laitinen
 * @version 29 4 2019
 *
 */
public class RuokaAine {
    private String ruokaNimi;
    private int ruokaId;
    private int ruokaAineId;
    private double kalorit;
    
    private static int seuraavaId  = 1;
    
    /**
     * Oletusmuodostaja
     */
    public RuokaAine() {
        //
    }
    
    /**
     * Asetettan vain nimi jolloin tulee vakio id ja kalorimaara
     * @param nimi ruuan nimi
     */
    public RuokaAine(String nimi) {
        this.ruokaNimi = nimi;
    }
    
    /**
     * Asetetaan nimi ja oma id
     * @param nimi ruuan nimi
     * @param id ruuan id
     */
    public RuokaAine(String nimi, int id) {
        this.ruokaNimi = nimi;
        this.ruokaId = id;
    }
    
    /**
     * Taysin asetettu uusi ruokaAine aka uusi ruoka
     * @param nimi ruuan nimi
     * @param id ruuan id
     * @param kalorit ruuan kalorit
     */
    public RuokaAine(String nimi, int id, int kalorit) {
        this.ruokaNimi = nimi;
        this.ruokaId = id;
        this.kalorit = kalorit;
    }

    /**
     * lisataan uusi ruoka
     * @param id ruoan id
     * @param nimi nimen id
     * @param kalori kalorimaara
     */  
    public void lisaaUusiRuoka(int id, String nimi, double kalori) {
        ruokaId = id;
        ruokaNimi = nimi;
        kalorit = kalori;
    }
    

    /** rekisteroidaan ruualle id
     * @return ruoka idn
     * @example
     * <pre name="test">
     *   RuokaAine ruoka1 = new RuokaAine();
     *   ruoka1.rekisteroi();
     *   RuokaAine ruoka2 = new RuokaAine();
     *   ruoka2.rekisteroi();
     *   int n1 = ruoka1.getRuokaId();
     *   int n2 = ruoka2.getRuokaId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        ruokaId = seuraavaId; 
        seuraavaId++;
        return ruokaId;
    }
    
    /** tayttaa ruuan tiedot
     * @param ruokanimi ruuan nimi
     * @param kaloreita kalorimaara
     */
    public void tayta(String ruokanimi, double kaloreita) {
        ruokaNimi = ruokanimi;
        kalorit = kaloreita;
    }
    
    
    /** ruoka-aineIdn setmetodi
     */
    public void setRuokaAineId() {
        this.ruokaAineId = seuraavaId + 20;
    }
    
    /** getti ruoka-aineidlle
     * @return ruokaAineidn
     */
    public int getRuokaAineId() {
        return ruokaAineId;
    }
    
    
    /** getti ruokaidlle
     * @return ruokaidn
     */
    public int getRuokaId() {
        return ruokaId;
    }
    
    /**
     * getti ruokaNimelle
     * @return ruokaNimi
     */
    public String getRuokaNimi() {
    	return ruokaNimi;
    }   
    
    /**
     * getti ruuan kaloreille
     * @return kalorit
     */
    
    public double getRuokaKalorit() {
    	return kalorit;
    }
    
    @Override
    public String toString() {
        return " " + getRuokaId() + " | " + getRuokaAineId()  +" | " + getRuokaNimi() + " | " + getRuokaKalorit();
    }
    
    /** tulostusmetodi
     * @param out ulostulo
     */
    public void tulosta(PrintStream out) {
        out.println(ruokaId + " " + ruokaNimi + " " + kalorit);        
    }
    
     /** 
     * @param os ulostulo
     */
    public void tulosta(OutputStream os) {
         tulosta(new PrintStream(os));
    }     
    
    /**
     * parse-metodi
     * @param rivi rivi jota parsetetaan
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setRuokaId(Mjonot.erota(sb, '|', getRuokaId()));
        ruokaAineId = Mjonot.erota(sb, '|', ruokaAineId);
        ruokaNimi = Mjonot.erota(sb, '|', ruokaNimi);
        kalorit = Mjonot.erota(sb, '|', kalorit);    
    }

    /** RuokaIdn setti parsen kanssa.
     * @param id id
     */
	private void setRuokaId(int id) {
	    ruokaId = id;
        if (ruokaId >= seuraavaId) seuraavaId = ruokaId + 1;
	}

	/** ruuan nimen setmetodi
	 * @param nimi ruuan nimi
	 */
	public void setNimi(String nimi) {
		this.ruokaNimi = nimi;
	}

	/** kalorien set metodi
	 * @param kalorit2 kalorien maara
	 */
	public void setKalorit(Double kalorit2) {
		this.kalorit = kalorit2;
		
	}
}