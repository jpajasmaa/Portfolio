/**
 * 
 */
package kalorilaskuri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/** Ateria luokka, osaa mm. huolehtia oikeasta muodostaan
 * @author juuso pajasmaa ja riku laitinen
 * @version 29 4 2019
 *
 */
public class Ateria {
    
    private int ateriaID;
    private int ateriaType;
    private int ruokaAineId;
    private double maara;
    private String ruokaNimi;
    private String Pvm = "";
    
    private static int seuraavaId = 1;
    
    /**
     * muodostaja
     */
    public Ateria() {
        //
    }
    
    /**
     * parametrillinen muodostaja
     * @param ateriaid aterianid
     */
    public Ateria(int ateriaid) {
        this.ateriaID = ateriaid;
    }

    /** rekisteroidaan aterialle id
     * @return aterian idn
     * @example
     * <pre name="test">
     *   Ateria ateria1 = new Ateria();
     *   ateria1.rekisteroi();
     *   Ateria ateria2 = new Ateria();
     *   ateria2.rekisteroi();
     *   int n1 = ateria1.getAteriaId();
     *   int n2 = ateria2.getAteriaId();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        ateriaID = seuraavaId;
        seuraavaId++;
        return ateriaID;
    }

    /** get metodi aterian id:n saamiseksi
     * @return ateriaidn.
     */
    public int getAteriaId() {
        return ateriaID;
    }
       
    /** get metodi aterian id:n saamiseksi
     * @return ateriatyypin 
     */
    public int getAteriaType() {
        return ateriaType;
    }
    
    /** get metodi syödyn ruokaAineen idn saamiseksi
     * @return ruokaaine idn,
     */
    public int getRuokaAineId() {
        return ruokaAineId;
    }
    
    /** ruokamaaran getti
     * @return maara/100g
     */
    public double getMaara() {
        return this.maara;
    }
    
    /**
     * @return paivamaaran
     */
    public String getPvm() {
    	return this.Pvm;
    }
    
    /**
     * @return ruuan nimen
     */
    public String getRuokaNimi() {
    	return this.ruokaNimi;
    }
    
    /**
     * @param k k
     * @return palauttaa aterian Id
     */
    public String anna(int k) {
        if ( k == 0) return "" + ateriaID;
        return "";
    }
    
    /** Palauttaa ateriatyypin nimen merkkijonona
     * @param tyyppi aterian tyyppi
     * @return merkkijonon
     */
    public String annaNimi(int tyyppi) {
        if (tyyppi == 0 ) return "Aamupala";
        if (tyyppi == 1 ) return "Lounas";
        if (tyyppi == 2 ) return "Päivällinen";
        if (tyyppi == 3 ) return "Iltapala";
        return "";
    }
    
    /**
     * asetetaan toString
     */
    @Override
    public String toString() {
        return " " + getPvm() + " | " + getAteriaId() + " | " + getAteriaType() + " | " + getRuokaAineId()  + 
        		" | " + getRuokaNimi() +  " | " + getMaara();
    }
    
    /**
     * tulosta metodi nayttamaan toimintaa.
     * @param out  ulostulo
     */
    public void tulosta(PrintStream out) {
        out.println("Paivamaara: " + Pvm   + 
        		 "\nRuokaNimi: " + ruokaNimi + "\nsyotiiin: " + maara + "g");
    }
    
    /**
     * Tulostetaan aterian tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Tulostetaan aterian tiedot, jos halutaan luettavammassa muodossa
     * @param os tietovirta johon tulostetaan
     * @return true jos onnistuu
     */
    public boolean tulostaTiedostoon(OutputStream os) {
        tulosta(new PrintStream(os));
        return true;
    }
    
    /** taytetaan ateria
     * @param pvm paivamaara
     * @param ateriaid ateriaid
     * @param ateriatyyppi tyyppi
     * @param i i ruokaaine id
     * @param nimi nimi
     * @param amount ruuan maara
     */
    public void tayta(String pvm, int ateriaid, int ateriatyyppi, int i, String nimi, double amount) {
        Pvm = pvm;
    	ateriaID = ateriaid;
        ateriaType = ateriatyyppi;
        ruokaAineId = i;
        ruokaNimi = nimi;
        this.maara = amount;
    }
    
    /** parse metodia aterialle
     * @param rivi syotetty rivi
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        Pvm = Mjonot.erota(sb, '|', Pvm);
        setAteriaId(Mjonot.erota(sb, '|', getAteriaId()));
        ateriaType = Mjonot.erota(sb, '|', ateriaType);
        ruokaAineId = Mjonot.erota(sb, '|', ruokaAineId);
        ruokaNimi = Mjonot.erota(sb, '|', ruokaNimi);
        maara = Mjonot.erota(sb, '|', maara);
    }
    
    /**
     * setAteriaId parsea varten
     * @param id id
     */
    private void setAteriaId(int id) {
        ateriaID = id;
        if (ateriaID >= seuraavaId) seuraavaId = ateriaID + 1;
    }
    
    /** ruuan idn setmetodi
     * @param aineid ruuan nimi
     */
    public void setRuokaId(int aineid) {
        this.ruokaAineId = aineid;
    }

    /** maaran set metodi
     * @param maara kalorien maara
     */
    public void setMaara(String maara) {
        this.maara = Double.parseDouble(maara);    
    }
}