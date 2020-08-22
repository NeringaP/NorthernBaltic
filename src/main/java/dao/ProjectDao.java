package dao;

import domain.Engineer;
import domain.Project;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;

public class ProjectDao {

    public void addProject(Project project) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public Project getProject(Integer id) {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            Project project = session.find(Project.class, id);
            session.close();
            return project;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void updateProject(Project project) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(project);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public void deleteProject(Project project) {
        Transaction transaction = null;
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(project);
            transaction.commit();
            session.close();
        } catch (Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
    }

    public List<Project> getAllProjects() {
        try{
            Session session = HibernateUtils.getSessionFactory().openSession();
            List<Project> projects = session.createQuery("from Project", Project.class).list();
            session.close();
            return projects;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
