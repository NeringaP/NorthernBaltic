package dao;

import domain.Customer;
import domain.Engineer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;

public class EngineerDao {

    public void addEngineer(Engineer engineer) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(engineer);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public Engineer getEngineer(String lastName) {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            Engineer engineer = session.find(Engineer.class, lastName);
            session.close();
            return engineer;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updateEngineer(Engineer engineer) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(engineer);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void deleteEngineer(Engineer engineer) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(engineer);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public List<Engineer> getAllEngineers() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<Engineer> engineers = session.createQuery("from Engineer", Engineer.class).list();
            session.close();
            return engineers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getAllEngineersLastName() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<String> engineersLastNames =
                    session.createQuery("select lastName from Engineer").list();
            session.close();
            return engineersLastNames;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
