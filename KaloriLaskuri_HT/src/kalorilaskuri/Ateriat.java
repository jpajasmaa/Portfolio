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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import fi.jyu.mit.fxgui.Dialogs;

/** Ateriat luokka, osaa mm. lisätä aterian
 * @author juuso pajasmaa ja riku laitinen
 * @version 29 4 2019
 *
 */
public class Ateriat implements Iterable<Ateria> {
    
    private String tiedostonNimi =  "Ateriat";    
    private boolean muutettu = false;
    private final List<Ateria> alkiot  = new ArrayList<Ateria>();
    
    /**
     * parametriton muodostaja
     */
    public Ateriat() {
        // 
    }
    
    /** Lisaa uuden aterian tietorakenteeseen.
     * @param ateria lisattava ateria
     */
    public void lisaa(Ateria ateria) {
        alkiot.add(ateria);
        muutettu = true;
    }
    
    /** poistaa poistettavan aterian
     * @param id poistettavan aterian id
     * @return true jos onnistuu, muuten false
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Ateriat ateriat = new Ateriat(); 
     * Ateria aamupala = new Ateria(), lounas = new Ateria(), iltapala = new Ateria(); 
     * aamupala.rekisteroi(); lounas.rekisteroi(); iltapala.rekisteroi(); 
     * int id1 = aamupala.getAteriaId(); 
     * ateriat.lisaa(aamupala); ateriat.lisaa(lounas); ateriat.lisaa(iltapala); 
     * ateriat.poistaAteria(id1) === true;
     * ateriat.poistaAteria(id1) === false; 
     * ateriat.poistaAteria(id1+1) === true;
     * ateriat.poistaAteria(id1+3) === false; 
     * </pre> 
     */
    public boolean poistaAteria(int id) {
        int n = 0;
        for (Iterator<Ateria> it = alkiot.iterator(); it.hasNext();) {
            Ateria ate = it.next();
            if (id == ate.getAteriaId()) {
                it.remove();
                n++;
            }            
        }
        if (n > 0 ) {
            muutettu = true;
            return true;
        }
        return false;
    }
    
    /** poistaa aterian jolla poistettavan ruuan id
     * @param ruokaAineId poistettavan ruuan id
     * @return true jos onnistui, false jos ei
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Ateriat ateriat = new Ateriat(); 
     * Ateria aamupala = new Ateria(), lounas = new Ateria(), iltapala = new Ateria(); 
     * aamupala.rekisteroi(); lounas.rekisteroi(); iltapala.rekisteroi(); 
     * aamupala.tayta("01.02.2019", aamupala.getAteriaId(), 0, 4, "puuro", 300); 
     * lounas.tayta("01.02.2019", lounas.getAteriaId(), 0, 5, "makaroonilaatikko", 300); 
     * iltapala.tayta("01.02.2019", iltapala.getAteriaId(), 0, 4, "lihapulla", 300);
     * ateriat.lisaa(aamupala); ateriat.lisaa(lounas); ateriat.lisaa(iltapala); 
     * ateriat.poista(aamupala.getRuokaAineId()) === true;
     * ateriat.poista(aamupala.getRuokaAineId()) === false; 
     * ateriat.poista(lounas.getRuokaAineId()) === true; 
     * ateriat.poista(iltapala.getRuokaAineId()) === false;
     * </pre> 
     */
    public boolean poista(int ruokaAineId) {
        int n = 0;
        for (Iterator<Ateria> it = alkiot.iterator(); it.hasNext();) {
            Ateria ate = it.next();
            if (ate.getRuokaAineId() == ruokaAineId) {
                it.remove();
                n++;
            }
        }
        if (n > 0) {
            muutettu = true;
            return true;
        }
        return false;
    }
    
    /** etsitaan ateria 
     * @param Pvm paivamaara
     * @param hakuehto millainen hakuehto
     * @return loytyneet
     * @example
     * <pre name="test">
     * Ateriat ateriat = new Ateriat(); 
     * Ateria aamupala = new Ateria(), lounas = new Ateria(), iltapala1 = new Ateria(), iltapala = new Ateria(); 
     * aamupala.rekisteroi(); lounas.rekisteroi(); iltapala1.rekisteroi(); iltapala.rekisteroi(); 
     * aamupala.tayta("01.02.2019", aamupala.getAteriaId(), 0, 4, "puuro", 300); 
     * lounas.tayta("02.03.2019", lounas.getAteriaId(), 0, 5, "makaroonilaatikko", 300);
     * iltapala1.tayta("11.02.2019", iltapala1.getAteriaId(), 0, 7, "kalja", 45); 
     * iltapala.tayta("11.02.2019", iltapala.getAteriaId(), 0, 7, "kalja", 300);
     * ateriat.lisaa(aamupala); ateriat.lisaa(lounas); ateriat.lisaa(iltapala1); ateriat.lisaa(iltapala); 
     * 
     * List<Ateria> loytyneet;
     * loytyneet = (List<Ateria>)ateriat.etsi("11.02.2019", "kalja");  
     * loytyneet.size() === 2;  
     * loytyneet.get(0) == iltapala1 === true;
     * loytyneet.get(1) == iltapala === true;
     * 
     * loytyneet = (List<Ateria>)ateriat.etsi("01.02.2019", "puuro"); 
     * loytyneet.size() === 1;  
     * loytyneet.get(0) == aamupala === true; 
     *
     * loytyneet = (List<Ateria>)ateriat.etsi("", ""); 
     * loytyneet.size() === 0;
     * </pre>
     */
    public Collection<Ateria> etsi(String Pvm, String hakuehto) { 
        String ehto = ""; 
        //hakuehto != null && hakuehto.length() > 0
        if (!hakuehto.isBlank()) ehto = hakuehto;  
        List<Ateria> loytyneet = new ArrayList<Ateria>(); 
        for (Ateria ateria : this) { 
            //if (WildChars.onkoSamat(ateria.getRuokaNimi(), ehto)) 
        	if(ateria.getRuokaNimi().toLowerCase().contains(ehto.toLowerCase()) && ateria.getPvm().equals(Pvm)) loytyneet.add(ateria);   
        }  
        return loytyneet; 
    }
    
    /**
     * Asetetaan uusi nimi ja kalorimaara muokattavalle ruokaAineelle
     * @param ate valittu ateria
     * @param ruoka kyseinen ruoka-aine
     * @param maara ruokamaara
     */
    public void muokkaa(Ateria ate, RuokaAine ruoka, String maara) {
        ate.setRuokaId(ruoka.getRuokaAineId());
        ate.setMaara(maara);
        muutettu = true;
    }
    
    /**
     * Palauttaa aterioiden lukumaaran
     * @return aterioiden lukumaara
     */
    public int getLkm() {
        return alkiot.size();
    }    

    
    /** getti tiedostonnimelle ja paatteelle
     * @return palauttaa tiedoston nimen
     */
    public String getJsonNimi() {
        return tiedostonNimi + ".json";
    }
   
    /** get tiedostonperusnimen kautta.
     * @return getti tiedostonnimelle
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".json";
    }
    
    /** set metodi tiedoston nimelle
     * @param tied tiedoston nimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonNimi = tied;
    }
   
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }

    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBackNimi() {
        return tiedostonNimi + ".bak";
    }
    
    /**
     * luetaan annetun nimisestä tiedostosta
     * @throws SailoException sailoexception jos lukeminen epaonnistuus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    /** Luetaan tietoja tiedostosta
     * @param tied luettava tiedosto
     * @throws SailoException sailoexceptioni jos lukeminnen epaonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Ateriat ateriat = new Ateriat();
     *  Ateria aamupala = new Ateria(); aamupala.rekisteroi(); aamupala.tayta("01.02.2019", aamupala.getAteriaId(), 0, 4, "puuro", 300);
     *  Ateria aamupala2 = new Ateria(); aamupala2.rekisteroi(); aamupala2.tayta("02.02.2019", aamupala2.getAteriaId(), 0, 8, "makaroonilaatikko", 200); 
     *  Ateria lounas = new Ateria(); lounas.rekisteroi(); lounas.tayta("01.02.2019", lounas.getAteriaId(), 1, 2, "lihapulla", 500);
     *  String hakemisto = "testilaskuri";
     *  String tiedNimi = hakemisto+"/AteriatTesti";
     *  File ftied = new File(tiedNimi+".json");
     *  ateriat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  ateriat.lisaa(aamupala);
     *  ateriat.lisaa(aamupala2);
     *  ateriat.lisaa(lounas);
     *  ateriat.tallenna();
     *  ateriat = new Ateriat();            // Poistetaan vanhat luomalla uusi
     *  ateriat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Ateria> i = ateriat.iterator();
     *  i.next().toString() === aamupala.toString();
     *  i.next().toString() === aamupala2.toString();
     *  i.next().toString() === lounas.toString();
     *  i.hasNext() === false;
     *  ateriat.tallenna();
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
                Ateria ate = new Ateria();
                ate.parse(rivi);
                lisaa(ate);
            }    
            muutettu = false;
        } catch (FileNotFoundException e) {
          //Dialogs.showMessageDialog("Tiedosto ei aukea" + e.getMessage());;
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        }     
    }
    
    /** metodi joka tallentaa tiedostoon, ei tallenneta jos ei muutoksia.
     * @throws SailoException poikkeus joka lentaa jos tallennus epaonnistuu
     */
    public void tallenna() throws SailoException {
        
        if ( muutettu == false ) return;

        
        File atebak = new File(getBackNimi());
        File ateTied = new File(getJsonNimi());

        atebak.delete();
        ateTied.renameTo(atebak);

        try (PrintStream ate = new PrintStream(new FileOutputStream(ateTied.getCanonicalPath())) ) {
            for(Ateria ateria : this) {
                ate.println(ateria.toString());
        }
        
        } catch (IOException e) {
            Dialogs.showMessageDialog("Tiedostoon tallentaminen epaonnistui!");;
        }

        muutettu = false;
         
    }
    
    /**
     * Iteraattori kaikkien aterioiden lapikaymiseen
     * @return ateriaiteraattorin
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Ateriat ateriat = new Ateriat();
     *  Ateria ateria1 = new Ateria(); ateria1.tayta("21.02.2019", 1, 1, 5, "pulla", 104.0); ateriat.lisaa(ateria1);
     *  Ateria ateria2 = new Ateria(); ateria2.tayta("11.02.2019", 2, 0, 3, "porsaankyljys", 300.0); ateriat.lisaa(ateria2);
     *  Ateria ateria31 = new Ateria(); ateria31.tayta("01.03.2019", 3, 3, 6, "puuro", 440.0); ateriat.lisaa(ateria31);
     *  Ateria ateria14 = new Ateria(); ateria14.tayta("10.02.2019", 4, 2, 7, "omena", 150.0); ateriat.lisaa(ateria14);
     *  
     *  Iterator<Ateria> i2=ateriat.iterator();
     *  i2.next() === ateria1;
     *  i2.next() === ateria2;
     *  i2.next() === ateria31;
     *  i2.next() === ateria14;  
     *  
     * </pre>
     */
    @Override
    public Iterator<Ateria> iterator() {
        return alkiot.iterator(); 
    }
    
    

    /** Antaa etsityn aterian idn perusteella.
     * @param ateriaid ateriaidn
     * @return tietorakenne jossa viitteet loydettyihin aterioihin
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Ateriat ateriat2 = new Ateriat();
     *  List<Ateria> loytyneet;
     *  loytyneet = ateriat2.annaAteriat(1);
     *  loytyneet.size() === 0;
     *  Ateria ateria1 = new Ateria(); ateria1.rekisteroi(); ateriat2.lisaa(ateria1);
     *  Ateria ateria2 = new Ateria(); ateria2.rekisteroi(); ateriat2.lisaa(ateria2);
     *  Ateria ateria4 = new Ateria(); ateria4.rekisteroi(); ateriat2.lisaa(ateria4);
     *  loytyneet = ateriat2.annaAteriat(ateria1.getAteriaId());
     *  loytyneet.size() === 1;
     *  loytyneet.get(0) == ateria1 === true;
     *  loytyneet = ateriat2.annaAteriat(ateria2.getAteriaId());
     *  loytyneet.size() === 1;
     *  loytyneet.get(0) == ateria2 === true; 
     *  loytyneet.get(0) == ateria4 === false; 
     *  loytyneet = ateriat2.annaAteriat(ateria4.getAteriaId());
     *  loytyneet.get(0) == ateria1 === false;
     *  loytyneet.get(0) == ateria4 === true;
     * </pre>
     */
    public List<Ateria> annaAteriat(int ateriaid) {
        if (ateriaid == 0) return null;
        List<Ateria> loydetyt = new ArrayList<Ateria>();
        for (Ateria ateria : alkiot)
            if (ateria.getAteriaId() == ateriaid) loydetyt.add(ateria);
        return loydetyt;
    }
    
    /** haetaan ateriat joilla haettava ateriatyyppi, esim 0 aamupala
     * @param ateriatyyppi aterian tyyppi
     * @return palautetaan ateriat joilla kysytty tyyppi
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Ateriat ateriat2 = new Ateriat();
     *  List<Ateria> loytyneet;
     *  loytyneet = ateriat2.annaAteriat(1);
     *  loytyneet.size() === 0;
     *  Ateria ateria1 = new Ateria(); ateria1.rekisteroi(); ateria1.tayta("21.02.2019", 1, 1, 5, "pulla", 104.0); ateriat2.lisaa(ateria1);
     *  Ateria ateria2 = new Ateria(); ateria2.rekisteroi(); ateria2.tayta("11.02.2019", 2, 0, 3, "porsaankyljys", 300.0); ateriat2.lisaa(ateria2);
     *  Ateria ateria4 = new Ateria(); ateria4.rekisteroi(); ateria4.tayta("01.03.2019", 3, 3, 6, "puuro", 440.0); ateriat2.lisaa(ateria4);
     *  loytyneet = ateriat2.annaAteriaTyypit(ateria1.getAteriaType());
     *  loytyneet.size() === 1;
     *  loytyneet.get(0) == ateria1 === true;
     *  loytyneet = ateriat2.annaAteriaTyypit(ateria2.getAteriaType());
     *  loytyneet.size() === 1;
     *  loytyneet.get(0) == ateria2 === true; 
     *  loytyneet.get(0) == ateria4 === false; 
     *  loytyneet = ateriat2.annaAteriaTyypit(ateria4.getAteriaType());
     *  loytyneet.get(0) == ateria1 === false;
     *  loytyneet.get(0) == ateria4 === true;
     * </pre>
     */
    public List<Ateria> annaAteriaTyypit(int ateriatyyppi) {
        List<Ateria> loydetyt = new ArrayList<Ateria>();
        for (Ateria ateria : alkiot)
            if (ateria.getAteriaType() == ateriatyyppi) loydetyt.add(ateria);
        return loydetyt;
    }
}