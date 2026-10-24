package ${package}.controladores;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ${package}.App;
import ${package}.dao.UsuarioDAO;
import ${package}.entidades.Usuario;
import ${package}.util.HashUtil;
import ${package}.util.SesionUsuario;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtClave;
    @FXML private Label lblMensaje;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void ingresar() {
        String correo = txtCorreo.getText().trim();
        String clave = txtClave.getText();

        if (correo.isEmpty() || clave.isEmpty()) {
            lblMensaje.setText("Ingrese correo y contraseña.");
            return;
        }

        try {
            Usuario usuario = usuarioDAO.autenticar(correo, HashUtil.md5(clave));
            if (usuario == null) {
                lblMensaje.setText("Correo o contraseña incorrectos.");
                return;
            }
            SesionUsuario.iniciar(usuario);
            App.cambiarVista("Home");
        } catch (IllegalStateException e) {
            lblMensaje.setText("No se pudo conectar con la base de datos.");
            e.printStackTrace();
        } catch (IOException e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}
