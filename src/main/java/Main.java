import domain.Customer;
import domain.HouseModel;
import domain.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sample.test2;
import utils.HibernateUtils;

public class Main {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        HouseModel houseModel1 = new HouseModel("Adele",400000,185,false);

        Transaction transaction = null;
       try {
           transaction = session.beginTransaction();
           session.save(houseModel1);
           transaction.commit();
           session.close();
       } catch (Exception ex) {
           if (transaction != null) {
               transaction.rollback();
           }
           ex.printStackTrace();
       }

    }
}
