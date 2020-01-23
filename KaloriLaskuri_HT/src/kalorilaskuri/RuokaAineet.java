/**
 * 
 */
package kalorilaskuri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.fxgui.Dialogs;

/** Ruoka-aineet-luokka
 * @author juuso pajasmaa ja riku laitinen
 * @version 29 4 2019
 * 
 */
public class RuokaAineet implements Iterable<RuokaAine> {
    
    private boolean muutettu = false;
    private String tiedostoNimi = "RuokaAineet";
    private static final int MAX_RUO = 5;
    private int lkm = 0;
    private RuokaAine[] alkiot = new RuokaAine[MAX_RUO];
    
    
    /**
     * Oletusmuodostaja 
     */
    public RuokaAineet() {
        //
    }

    /**
     * Lisataan taulukkoon uusi alkio, joka sisaltaa ruuan. Jos taulukko tayttaa, kopioidaan se isompaan taulukkoon.
     * @param ruoka ruokaaine
     * @example
     * <pre name="test">
     * RuokaAineet ruuat = new RuokaAineet();
     * RuokaAine porkkana = new RuokaAine(), puuro = new RuokaAine();
     * ruuat.getLkm() === 0;
     * ruuat.lisaa(porkkana); ruuat.getLkm() === 1;
     * ruuat.lisaa(puuro); ruuat.getLkm() === 2;
     * ruuat.lisaa(porkkana); ruuat.getLkm() === 3;
     * ruuat.anna(0) === porkkana;
     * ruuat.anna(1) === puuro;
     * ruuat.anna(2) === porkkana;
     * ruuat.anna(1) == porkkana === false;
     * ruuat.anna(1) == puuro === true; 
     * ruuat.lisaa(puuro); ruuat.getLkm() === 4;
     * ruuat.lisaa(puuro); ruuat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(RuokaAine ruoka) {
    	if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, alkiot.length+5);
        alkiot[lkm] = ruoka;
        lkm++;
        muutettu = true;
    }
    
    /** Haetaan tietty ruoka-aine taulukon indeksin mukaan
     * @param i indeksin viite
     * @return ruokaaineen
     * @throws IndexOutOfBoundsException jos virhe
     */
    public RuokaAine anna(int i) throws IndexOutOfBoundsException {
         if (i < 0 || lkm <= i){
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        }
        return alkiot[i];
    }
       
    /**
     * Ruoka-ainetaulukon koon getti.
     * @return alkiot taulukon koko
     */
    public int getLkm() {
        return this.lkm;
    }
    
    /** haetaan ruoka-aineen nimi ruokaidn perusteella.
     * @param ruokaId ruoka-aineen id
     * @return palauttaa ruoka-aineen nimen
     */
    public String getRuokaNimi(int ruokaId) {
        return alkiot[etsi(ruokaId)].getRuokaNimi();
    }
 
    /** haetaan ruoka-aineen nimi ruoka-aineidn perusteella.
     * @param ruokaAineId ruoka-aineen id
     * @return palauttaa ruoka-aineen nimen
     */
    public String getRuokaAine(int ruokaAineId) {
        return alkiot[etsiR(ruokaAineId)].getRuokaNimi();
    }
    
    /** Hakee ruokaId:n tunnustuksella ruuan listasta. Palauttaa nollapaikassa olevan jos ei loydy.
     * @param Id ruoka-aineen id
     * @return alkion loydetysta paikasta tai null jos ei loydy
     * @example
     * <pre name="test">
     * 
     *  RuokaAineet ruuat = new RuokaAineet();
     *  RuokaAine kala = new RuokaAine(); kala.rekisteroi(); ruuat.lisaa(kala);
     *  RuokaAine broileri = new RuokaAine(); broileri.rekisteroi(); ruuat.lisaa(broileri);
     *  RuokaAine hernekeitto = new RuokaAine(); hernekeitto.rekisteroi(); ruuat.lisaa(hernekeitto);
     *  RuokaAine kinkku = new RuokaAine(); kinkku.rekisteroi(); ruuat.lisaa(kinkku);
     *  
     *  int id = kala.getRuokaId();
     *  ruuat.annaRuuat(id) == kala === true;
     *  ruuat.annaRuuat(id+1) == broileri === true;
     *  ruuat.annaRuuat(id+2) == hernekeitto === true;
     *  ruuat.annaRuuat(id+3) == kinkku === true;
     *  ruuat.annaRuuat(id+4) == kinkku === false; #THROWS NullPointerException
     *  ruuat.annaRuuat(id) == kinkku === false;
     * </pre> 
     */
    public RuokaAine annaRuuat(int Id) {
    	for (int i = 0; i < alkiot.length; i++) {
        	if (alkiot[i].getRuokaId() == Id) return alkiot[i];
        }
    	return null;
    }
    
    /**
     * Asetetaan uusi nimi ja kalorimaara muokattavalle ruokaAineelle
     * @param ruoka kyseinen ruoka-aine
     * @param nimi ruuan nimi
     * @param kalorit kalorit
     */
    public void muokkaa(RuokaAine ruoka, String nimi, Double kalorit) {
    	ruoka.setNimi(nimi);
        ruoka.setKalorit(kalorit);
        muutettu = true;
    }
    
    /** etsitaan oikea alkio jolla kyseinen ruokaid
     * @param id ruokaid jota etsitaan
     * @return oikean alkion viitteen, -1 jos ei loydy
     * @example
     * <pre name="test"> 
     *   
     * RuokaAineet ruuat = new RuokaAineet(); 
     * RuokaAine kala = new RuokaAine(); kala.rekisteroi(); ruuat.lisaa(kala);
     * RuokaAine broileri = new RuokaAine(); broileri.rekisteroi(); ruuat.lisaa(broileri);
     * RuokaAine hernekeitto = new RuokaAine(); hernekeitto.rekisteroi(); ruuat.lisaa(hernekeitto);
     *   
     * int id1 = kala.getRuokaId(); 
     * ruuat.etsi(id1) === 0; 
     * ruuat.etsi(id1+1) === 1;
     * ruuat.etsi(id1+2) === 2;
     * </pre> 
     */
    public int etsi(int id) {
        for (int i = 0; i < alkiot.length; i++) {
            if (alkiot[i].getRuokaId() == id) return i;
        }
        return -1;
    }
    
    /** etsitaan oikea alkio ruoka-aineidn avulla
     * @param ruokaid ruoka-aineid jota etsitaan
     * @return oikean alkion, -1 jos ei loydy
     * @example
     * <pre name="test"> 
     *   
     * RuokaAineet ruuat = new RuokaAineet(); 
     * RuokaAine kala = new RuokaAine(); kala.rekisteroi(); kala.setRuokaAineId(); ruuat.lisaa(kala);
     * RuokaAine broileri = new RuokaAine(); broileri.rekisteroi(); broileri.setRuokaAineId(); ruuat.lisaa(broileri);
     * RuokaAine hernekeitto = new RuokaAine(); hernekeitto.rekisteroi(); hernekeitto.setRuokaAineId(); ruuat.lisaa(hernekeitto);
     *   
     * int id1 = kala.getRuokaAineId(); 
     * ruuat.etsiR(id1) === 0; 
     * ruuat.etsiR(id1+1) === 1;
     * ruuat.etsiR(id1+2) === 2;
     * </pre> 
     */
    public int etsiR(int ruokaid) {
        for (int i = 0; i < alkiot.length; i++) {
            if (alkiot[i].getRuokaAineId() == ruokaid) return i;
        }
        return -1;
    }
    
    /**
     * luetaan annetun nimisestä tiedostosta
     * @throws SailoException sailoexception jos lukemisessa ongelmia
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /** luetaan tiedostosta tiedot
     * @param tied luettava tiedosto
     * @throws SailoException sailoexception jos lukeminen epaonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  RuokaAineet ruuat = new RuokaAineet();
     *  RuokaAine porkkana = new RuokaAine(); porkkana.rekisteroi(); porkkana.tayta("porkkana", 40);
     *  RuokaAine puuro = new RuokaAine(); puuro.rekisteroi(); puuro.tayta("puuro", 50); 
     *  RuokaAine porkkana3 = new RuokaAine(); porkkana3.rekisteroi(); porkkana3.tayta("porkkana", 40);
     *  String hakemisto = "testilaskuri";
     *  String tiedNimi = hakemisto+"/RuokaAineetTesti";
     *  File ftied = new File(tiedNimi+".json");
     *  ruuat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  ruuat.lisaa(porkkana);
     *  ruuat.lisaa(puuro);
     *  ruuat.lisaa(porkkana3);
     *  ruuat.tallenna();
     *  ruuat = new RuokaAineet();            // Poistetaan vanhat luomalla uusi
     *  ruuat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<RuokaAine> i = ruuat.iterator();
     *  i.next().toString() === porkkana.toString();
     *  i.next().toString() === puuro.toString();
     *  i.next().toString() === porkkana3.toString();
     *  i.hasNext() === false;
     *  ruuat.tallenna();
     *  ftied.delete() === true;
     *  //File fbak = new File(tiedNimi+".bak");
     *  //fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        
        try ( Scanner s = new Scanner(new FileInputStream(new File(getTiedostonNimi()))) ) {
            while (s.hasNext()) {
                String rivi = s.nextLine();
                RuokaAine ruoka = new RuokaAine();
                ruoka.parse(rivi); 
                lisaa(ruoka);
            }    
            muutettu = false;
        } catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");            
        }     
    }

    /**
     * Tarkoitettu tiedoston tallentamista varten. Jos ei muutoksia, niin ei tallenneta.
     * @throws SailoException sailo
     */
    public void tallenna() throws SailoException {
        
        if(muutettu == false) return;

        
        File ruobak = new File(getBackNimi());
        File ruoTied = new File(getJsonNimi());

        ruobak.delete();
        ruoTied.renameTo(ruobak);

        try (PrintStream ruo = new PrintStream(new FileOutputStream(ruoTied.getCanonicalPath())) ) {
            for(RuokaAine ruoka : this) {
                ruo.println(ruoka.toString());
        }
        
        } catch (IOException e) {
           Dialogs.showMessageDialog("Ruoka-aineen tallentaminen epaonnistui! ");;
        }

        muutettu = false;
         
        }
    
    /** Poistaa ruoka-aineen taulukosta
     * @param poistettava poistettava ruoka
     * @throws SailoException poikkeus jos poisto epaonnistuu
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException
     * RuokaAineet ruuat = new RuokaAineet();
     * RuokaAine kala = new RuokaAine(); kala.rekisteroi();
     * RuokaAine broileri = new RuokaAine(); broileri.rekisteroi();
     * RuokaAine hernekeitto = new RuokaAine(); hernekeitto.rekisteroi();  
     * ruuat.lisaa(kala); ruuat.lisaa(broileri); ruuat.lisaa(hernekeitto);
     * ruuat.getLkm() === 3;
     * ruuat.poista(kala); 
     * ruuat.getLkm() === 2;  
     * ruuat.poista(hernekeitto);
     * ruuat.getLkm() === 1; 
     * </pre> 
     */
    public void poista(RuokaAine poistettava) throws SailoException{
        int idn = etsi(poistettava.getRuokaId());
        if(idn < 0) return;
        lkm--;
        for(int i = idn; i < lkm; i++) {
            alkiot[i] = alkiot[i+1];
        }
        alkiot[lkm] = null;
        muutettu = true;
        return;
    }
    
    
    /** getti alkiot taulukolle
     * @return alkiot taulukko
     */
    public RuokaAine[] getRuoka() {
		return alkiot;
	}
    
    /** getti tiedoston nimen paattelle.
     * @return palauttaa tiedoston nimen
     */
    public String getJsonNimi() {
        return tiedostoNimi + ".json";
    }
   
    /** get metodi tiedostonnimelle
     * @return getti tiedostonnimelle
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".json";
    }
    
    /** set metodi tiedoston nimelle
     * @param tied tiedoston nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostoNimi = tied;
    }
   
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostoNimi;
    }

    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBackNimi() {
        return tiedostoNimi + ".bak";
    }
    
    
    
    /** RuokaAine iteraattori
     * @author juuso pajasmaa ja riku laitinen
     * @version 25 4 2019
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * RuokaAineet ruuat = new RuokaAineet();
     * RuokaAine porkkana = new RuokaAine(), puuro = new RuokaAine();
     * porkkana.rekisteroi(); puuro.rekisteroi();
     *
     * ruuat.lisaa(porkkana); 
     * ruuat.lisaa(puuro); 
     * ruuat.lisaa(porkkana); 
     * 
     * StringBuilder ids = new StringBuilder(30);
     * for (RuokaAine ruoka:ruuat)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+ruoka.getRuokaId());           
     * 
     * String tulos = " " + porkkana.getRuokaId() + " " + puuro.getRuokaId() + " " + porkkana.getRuokaId();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuilder(30);
     * for (Iterator<RuokaAine>  i=ruuat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   RuokaAine ruoka = i.next();
     *   ids.append(" "+ruoka.getRuokaId());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<RuokaAine>  i=ruuat.iterator();
     * i.next() == porkkana  === true;
     * i.next() == puuro  === true;
     * i.next() == porkkana  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class RuokaIterator implements Iterator<RuokaAine> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa ruokaa
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä ruokia
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava ruoka
         * @return seuraava ruoka
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public RuokaAine next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    /**
     * Iteraattori kaikkien ruokaAineiden lapikaymiseen
     * @return alkiot.iterator();
     *
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     *
     *  RuokaAineet ruokaAineet = new RuokaAineet();
     *  RuokaAine ruoka1 = new RuokaAine(); ruoka1.lisaaUusiRuoka(1, "Omena", 40.0); ruokaAineet.lisaa(ruoka1);
     *  RuokaAine ruoka2 = new RuokaAine(); ruoka2.lisaaUusiRuoka(2, "Mansikka", 15.0); ruokaAineet.lisaa(ruoka2);
     *  RuokaAine ruoka3 = new RuokaAine(); ruoka3.lisaaUusiRuoka(3, "Mustikka", 20.0); ruokaAineet.lisaa(ruoka3);
     *  RuokaAine ruoka4 = new RuokaAine(); ruoka4.lisaaUusiRuoka(4, "Mammi", 100); ruokaAineet.lisaa(ruoka4);
     *  
     *  Iterator<RuokaAine> i2=ruokaAineet.iterator();
     *  i2.next() === ruoka1;
     *  i2.next() === ruoka2;
     *  i2.next() === ruoka3;
     *  i2.next() === ruoka4;  
     * </pre>
     */
     @Override
     public Iterator<RuokaAine> iterator() {
         return new RuokaIterator();
     }
}