package ${package}.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ${package}.entidades.EstadoAula;
import ${package}.entidades.Planta;
import ${package}.entidades.TipoAula;
import ${package}.entidades.Usuario;

public class DatosIniciales {

    public static void cargar() {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = sesion.beginTransaction();
            try {
                if (contar(sesion, "Planta") == 0) {
                    for (int i = 1; i <= 5; i++) {
                        sesion.persist(new Planta(i));
                    }
                }
                if (contar(sesion, "TipoAula") == 0) {
                    String[] tipos = {"Normal", "Centro de cómputo", "Laboratorio", "Especial"};
                    for (String t : tipos) {
                        sesion.persist(new TipoAula(t));
                    }
                }
                if (contar(sesion, "EstadoAula") == 0) {
                    String[] estados = {"Asignada", "Asignada temporalmente", "En mantenimiento", "Disponible"};
                    for (String e : estados) {
                        sesion.persist(new EstadoAula(e));
                    }
                }
                if (contar(sesion, "Usuario") == 0) {
                    Usuario admin = new Usuario();
                    admin.setNombre("Administrador");
                    admin.setCorreo("admin@ufg.edu");
                    admin.setClave(HashUtil.md5("1234"));
                    sesion.persist(admin);
                }
                tx.commit();
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }

    private static long contar(Session sesion, String entidad) {
        return sesion.createQuery("select count(e) from " + entidad + " e", Long.class).getSingleResult();
    }
}
