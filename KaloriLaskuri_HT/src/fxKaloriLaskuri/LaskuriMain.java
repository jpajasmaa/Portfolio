package fxKaloriLaskuri;
	


import fi.jyu.mit.fxgui.Dialogs;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import kalorilaskuri.Kalorilaskuri;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;




/**
 * @author riku laitinen & juuso pajasmaa
 * @version 22 4 2019
 *
 */
public class LaskuriMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KaloriLaskuriPaaIkkuna.fxml"));
		    final Pane root = (Pane)ldr.load();
		    final PaaIkkunaController kalorilaskuriCtrl = (PaaIkkunaController)ldr.getController();
		    
		    final Scene scene = new Scene(root,850,600);		    
			scene.getStylesheets().add(getClass().getResource("laskuri.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Kalorilaskuri");
				
			Kalorilaskuri kalorilaskuri = new Kalorilaskuri();
			kalorilaskuriCtrl.setKalorilaskuri(kalorilaskuri);
			
			Application.Parameters params = getParameters(); 
            if ( params.getRaw().size() > 0 ) 
                kalorilaskuriCtrl.lueTiedosto(params.getRaw().get(0));
            else
                if ( !kalorilaskuriCtrl.avaa() ) Platform.exit();
			
			primaryStage.show();
			
		} catch(Exception e) {
		    e.printStackTrace();
			Dialogs.showMessageDialog("Paaikkunan avaaminen epaonnistu!");
		}
	}	
	
	/** maini
	 * @param args ei kaytossa
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
