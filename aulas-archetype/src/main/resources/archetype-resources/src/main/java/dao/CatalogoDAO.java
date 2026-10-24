package ${package}.dao;

import java.util.List;
import org.hibernate.Session;
import ${package}.entidades.EstadoAula;
import ${package}.entidades.TipoAula;
import ${package}.util.HibernateUtil;

public class CatalogoDAO {

    public List<Integer> listarPlantas() {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            return sesion.createQuery("select p.numero from Planta p order by p.numero", Integer.class).list();
        }
    }

    public List<TipoAula> listarTiposAula() {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            return sesion.createQuery("from TipoAula t order by t.id", TipoAula.class).list();
        }
    }

    public List<EstadoAula> listarEstados() {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            return sesion.createQuery("from EstadoAula e order by e.id", EstadoAula.class).list();
        }
    }
}
