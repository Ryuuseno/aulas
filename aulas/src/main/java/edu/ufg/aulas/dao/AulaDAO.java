package edu.ufg.aulas.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import edu.ufg.aulas.entidades.Aula;
import edu.ufg.aulas.util.HibernateUtil;

public class AulaDAO {

    public boolean existe(String nombre, String edificio) {
        String hql = "select count(a) from Aula a "
                + "where lower(a.nombre) = lower(:nombre) and lower(a.edificio) = lower(:edificio)";
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            Long total = sesion.createQuery(hql, Long.class)
                    .setParameter("nombre", nombre)
                    .setParameter("edificio", edificio)
                    .getSingleResult();
            return total > 0;
        }
    }

    public void guardar(Aula aula) {
        try (Session sesion = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = sesion.beginTransaction();
            try {
                sesion.persist(aula);
                tx.commit();
            } catch (RuntimeException e) {
                tx.rollback();
                throw e;
            }
        }
    }
}
