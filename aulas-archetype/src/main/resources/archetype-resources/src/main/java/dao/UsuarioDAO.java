package ${package}.dao;

import org.hibernate.Session;
import ${package}.entidades.Usuario;
import ${package}.util.HibernateUtil;

public class UsuarioDAO {

    public Usuario autenticar(String correo, String claveHash) {
        String hql = "FROM Usuario u WHERE u.correo = :correo AND u.clave = :clave";
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            return sesion.createQuery(hql, Usuario.class)
                    .setParameter("correo", correo)
                    .setParameter("clave", claveHash)
                    .uniqueResultOptional()
                    .orElse(null);
        }
    }
}
