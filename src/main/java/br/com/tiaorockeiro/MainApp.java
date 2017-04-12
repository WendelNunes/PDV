package br.com.tiaorockeiro;

import br.com.tiaorockeiro.controller.TelaLoginController;
import br.com.tiaorockeiro.controller.TelaPrincipalController;
import br.com.tiaorockeiro.util.JpaUtil;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    public Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaLogin.fxml"));
        Parent root = loader.load();
        TelaLoginController telaLoginController = loader.getController();
        telaLoginController.setApplication(this);
        this.stage.setOnCloseRequest((WindowEvent event) -> {
            JpaUtil.closeEntityManagerFactory();
        });
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaPrincipal.fxml"));
        loader.setController(new TelaPrincipalController());
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        this.stage.setScene(scene);
        this.stage.setMaximized(true);
        this.stage.setResizable(true);
    }
}