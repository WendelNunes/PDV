package br.com.tiaorockeiro;

import br.com.tiaorockeiro.controller.TelaLoginController;
import br.com.tiaorockeiro.util.JpaUtil;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemConfirmacao;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    private static MainApp instance;
    public Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaLogin.fxml"));
        Parent root = loader.load();
        TelaLoginController telaLoginController = loader.getController();
        telaLoginController.setApplication(this);
        this.stage.setOnCloseRequest((WindowEvent event) -> {
            sair();
            event.consume();
        });
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void sair() {
        if (enviarMensagemConfirmacao("Deseja realmente sair?")) {
            JpaUtil.closeEntityManagerFactory();
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void irParaTelaPrincipal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaPrincipal_new.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.setResizable(true);
    }

    public Stage popup(Parent root, boolean modal) {
        Stage popup = new Stage();
        if (modal) {
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.centerOnScreen();
            popup.initOwner(this.stage);
        }
        Scene scene = new Scene(root);
        popup.setScene(scene);
        popup.show();
        return popup;
    }

    public static MainApp getInstance() {
        return instance;
    }
}
