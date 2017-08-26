package entity;

import java.util.List;
import javax.persistence.*;


public class Tester {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_StudyPointFPQLtb_jar_1.0-SNAPSHOTPU");
    private static final EntityManager em = emf.createEntityManager();
    
    public static void main(String[] args) {
              
        
        createStudents();
        System.out.println("*******************");
        System.out.println(getAllStudents());
        System.out.println("*******************");
        System.out.println(getAllStudentsByFirstName("Anders"));
        System.out.println("*******************");
        assignSemester(getAllStudentsByFirstName("1").get(0));
        System.out.println(findAllStudentsByLastName("and"));
        System.out.println("*******************");
        System.out.println("Total sum for CLcos-v14e is: " + totalSumForSemester("CLcos-v14e"));
        System.out.println("*******************");
        System.out.println("Total sum for Students with Semester is: " + sumOfStudentsWithSemester());
        System.out.println("*******************");
        System.out.println("Teacher with most classes is :" + findBusyTeacher().getFirstname());
        System.out.println("*******************");
        
        
        
    }
    
    public static List<Student> getAllStudents() {
        return em.createNamedQuery("Student.findAll", Student.class).getResultList();
    }
    
    public static List<Student> getAllStudentsByFirstName(String name) {
        return em.createNamedQuery("Student.findByFirstname", Student.class).setParameter("firstname", name).getResultList();
    } 
    
    public static void createStudents() {
        em.getTransaction().begin();
        for(int i = 0; i<10; i++) {
            Student s = new Student();
            s.setFirstname(Integer.toString(i));
            em.persist(s);
        }
        em.getTransaction().commit();
    }
    
    public static void assignSemester(Student s) {
        s.setSemester(em.find(Semester.class, 1l));
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
    }
    
    public static List<Student> findAllStudentsByLastName(String name) {
        return em.createNamedQuery("Student.findByLastname", Student.class).setParameter("lastname", name).getResultList();
    }
    
    public static int totalSumForSemester(String sem) {
        return em.createQuery("SELECT s FROM Student s WHERE s.semester.name = :name", Student.class).setParameter("name", sem).getResultList().size();
    }
    
    public static int sumOfStudentsWithSemester() {
        return em.createQuery("SELECT s FROM Student s WHERE s.semester IS NOT NULL", Student.class).getResultList().size();
    }
    
    public static Teacher findBusyTeacher() {
        Long tID = (Long) em.createNativeQuery("SELECT teachers_id FROM teacher_semester GROUP BY teachers_id ORDER BY COUNT(*) DESC LIMIT 1").getSingleResult();
        return em.createQuery("SELECT t FROM Teacher t WHERE t.id = ?1", Teacher.class).setParameter(1, tID).getSingleResult();
    }
}
