package dao;

import domain.HouseModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;

public class HouseModelDao {

    public void addHouseModel(HouseModel houseModel) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(houseModel);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public HouseModel getHouseModel(Integer id) {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            HouseModel houseModel = session.find(HouseModel.class, id);
            session.close();
            return houseModel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HouseModel getHouseModel(String name) {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            HouseModel houseModel = session.find(HouseModel.class, name);
            session.close();
            return houseModel;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updateHouseModel(HouseModel houseModel) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(houseModel);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void deleteHouseModel(HouseModel houseModel) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(houseModel);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public List<HouseModel> getAllModels() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<HouseModel> houseModels = session.createQuery("from HouseModel", HouseModel.class).list();
            session.close();
            return houseModels;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getAllModelsNames() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<String> houseModelsNames =
                    session.createQuery("select name from HouseModel").list();
            session.close();
            return houseModelsNames;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Integer> getAllModelsID() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<Integer> houseModelsID =
                    session.createQuery("select id from HouseModel").list();
            session.close();
            return houseModelsID;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
