package dao;

import domain.Customer;
import domain.HouseModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;

public class CustomerDao {

    public void addCustomer(Customer customer) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public Customer getCustomer(String lastName) {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            Customer customer = session.find(Customer.class, lastName);
            session.close();
            return customer;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public Customer getCustomerByID(Integer id) {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            Customer customer = session.find(Customer.class, id);
            session.close();
            return customer;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updateCustomer(Customer customer) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void deleteCustomer(Customer customer) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<Customer> customers = session.createQuery("from Customer", Customer.class).list();
            session.close();
            return customers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<String> getAllCustomersLastName() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<String> customersLastNames =
                    session.createQuery("select lastName from Customer").list();
            session.close();
            return customersLastNames;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
