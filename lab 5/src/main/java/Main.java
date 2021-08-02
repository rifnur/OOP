import Service.StudentService;
import models.Student;
import org.hibernate.cfg.Configuration;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();

    for (int i=0;i<1000;i++) {
        Student student = new Student("Студент "+i, "1 курс");
        em.persist(student);
        }
        em.getTransaction().commit();

//      Update
        Student student4 = em.find(Student.class,1);
        em.getTransaction().begin();
        student4.setName("Вахитов");
        em.getTransaction().commit();

//      DELETE
        Student student5 = em.find(Student.class, 2);
        em.getTransaction().begin();
        if (student5 != null) {em.remove(student5);}
        em.getTransaction().commit();

//      Select by Id
        System.out.println("");
        Student student = em.find(Student.class,1);
        System.out.println(student);

//      Select All
        List<Student> studentList = em.createQuery("from Student",Student.class)
                .getResultList();
        System.out.println(studentList);

        em.close();
    }
}
