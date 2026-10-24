package ${package};

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ${package}.util.DatosIniciales;
import ${package}.util.HibernateUtil;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            DatosIniciales.cargar();
        } catch (Exception e) {
            System.err.println("No se pudieron cargar los datos iniciales: " + e.getMessage());
        }
        scene = new Scene(cargarVista("Login"));
        stage.setTitle("Gestión de Aulas");
        stage.setScene(scene);
        stage.show();
    }

    public static void cambiarVista(String nombre) throws IOException {
        scene.setRoot(cargarVista(nombre));
        scene.getWindow().sizeToScene();
    }

    private static Parent cargarVista(String nombre) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/vistas/" + nombre + ".fxml"));
        return loader.load();
    }

    @Override
    public void stop() {
        HibernateUtil.cerrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
