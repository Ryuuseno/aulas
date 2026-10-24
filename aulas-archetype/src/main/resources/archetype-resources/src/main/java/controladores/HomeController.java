package ${package}.controladores;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ${package}.App;
import ${package}.dao.AulaDAO;
import ${package}.dao.CatalogoDAO;
import ${package}.entidades.Aula;
import ${package}.entidades.EstadoAula;
import ${package}.entidades.TipoAula;
import ${package}.entidades.Usuario;
import ${package}.util.SesionUsuario;

public class HomeController {

    @FXML private Label lblBienvenida;
    @FXML private Label lblMensaje;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEdificio;
    @FXML private ComboBox<Integer> cmbPlanta;
    @FXML private TextField txtCapacidad;
    @FXML private ComboBox<TipoAula> cmbTipoAula;
    @FXML private TextField txtResponsable;
    @FXML private ComboBox<EstadoAula> cmbEstado;

    private final AulaDAO aulaDAO = new AulaDAO();
    private final CatalogoDAO catalogoDAO = new CatalogoDAO();

    @FXML
    public void initialize() {
        Usuario u = SesionUsuario.getUsuarioActual();
        if (u != null) {
            lblBienvenida.setText("Bienvenido, " + u.getNombre());
        }
        try {
            cmbPlanta.getItems().setAll(catalogoDAO.listarPlantas());
            cmbTipoAula.getItems().setAll(catalogoDAO.listarTiposAula());
            cmbEstado.getItems().setAll(catalogoDAO.listarEstados());
        } catch (Exception e) {
            mostrarError("No se pudieron cargar los catálogos desde la base de datos.");
            e.printStackTrace();
        }
    }

    @FXML
    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String edificio = txtEdificio.getText().trim();
        String capacidadTexto = txtCapacidad.getText().trim();
        String responsable = txtResponsable.getText().trim();
        Integer planta = cmbPlanta.getValue();
        TipoAula tipo = cmbTipoAula.getValue();
        EstadoAula estado = cmbEstado.getValue();

        if (nombre.isEmpty() || edificio.isEmpty() || capacidadTexto.isEmpty()
                || responsable.isEmpty() || planta == null || tipo == null || estado == null) {
            mostrarError("Ningún campo puede quedar vacío.");
            return;
        }

        int capacidad;
        try {
            capacidad = Integer.parseInt(capacidadTexto);
        } catch (NumberFormatException e) {
            mostrarError("La capacidad debe ser un número entero.");
            return;
        }

        if (planta <= 0 || capacidad <= 0) {
            mostrarError("Planta y capacidad deben ser enteros mayores que 0.");
            return;
        }

        try {
            if (aulaDAO.existe(nombre, edificio)) {
                mostrarError("Ya existe un aula con ese nombre en ese edificio.");
                return;
            }

            Aula aula = new Aula();
            aula.setNombre(nombre);
            aula.setEdificio(edificio);
            aula.setPlanta(planta);
            aula.setCapacidad(capacidad);
            aula.setTipoAula(tipo);
            aula.setResponsable(responsable);
            aula.setEstado(estado);
            aulaDAO.guardar(aula);

            limpiarCampos();
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText("Aula guardada correctamente.");
        } catch (Exception e) {
            mostrarError("No se pudo guardar el aula en la base de datos.");
            e.printStackTrace();
        }
    }

    @FXML
    private void limpiar() {
        limpiarCampos();
        lblMensaje.setText("");
    }

    @FXML
    private void cerrarSesion() throws IOException {
        SesionUsuario.cerrar();
        App.cambiarVista("Login");
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtEdificio.clear();
        txtCapacidad.clear();
        txtResponsable.clear();
        cmbPlanta.setValue(null);
        cmbTipoAula.setValue(null);
        cmbEstado.setValue(null);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: red;");
        lblMensaje.setText(mensaje);
    }
}
