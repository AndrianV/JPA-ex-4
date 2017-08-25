package entity;

import java.util.List;
import javax.persistence.*;


public class Tester {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_StudyPointFPQLtb_jar_1.0-SNAPSHOTPU");
    private static final EntityManager em = emf.createEntityManager();
    
    public static void main(String[] args) {
        System.out.println(getAllStudents());
        System.out.println("*******************");
        System.out.println(getAllStudentsAnders());
        
        
    }
    
    public static List<Student> getAllStudents() {
        Query query = em.createQuery("SELECT s FROM Student s");
        return (List<Student>) query.getResultList();
    }
    
    public static List<Student> getAllStudentsAnders() {
        Query query = em.createQuery("SELECT s FROM Student s WHERE s.firstname = 'Anders'");
        return (List<Student>) query.getResultList();
    } 
    
    
}
