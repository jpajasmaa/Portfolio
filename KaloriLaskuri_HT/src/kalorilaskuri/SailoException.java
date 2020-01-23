package kalorilaskuri;




/** Sailoexception class
 * @author juuso pajasmaa ja riku laitinen
 * @version 28 Feb 2019
 *
 */
public class SailoException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

   
    /**
     * Poikkeuksien muodostaja jolle tuodaan poikkeustilanteissa käytettävät
     * viestit
     * @param viesti Poikkeuksista tuleva viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}