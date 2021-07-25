package dao;

import models.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class StudentRepository {

    private final EntityManagerFactory emFactory;

    public StudentRepository(EntityManagerFactory emFactory) { this.emFactory = emFactory; }

    public StudentRepository() {

        emFactory = null;
    }

    public List<Student> findAll() {
        EntityManager em = emFactory.createEntityManager();
        List<Student> studentList = em.createQuery("from Student",Student.class).getResultList();
        return studentList;
    }

    public Student findById(int id){
        EntityManager em = emFactory.createEntityManager();
        Student student = em.find(Student.class, id);
        return student;
    }

    public void insert (Student student) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }

    public void update (Student student) {
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        student.setName("name");
        em.getTransaction().commit();
        em.close();
    }


    public void delete (int id){
        EntityManager em = emFactory.createEntityManager();
        em.getTransaction().begin();
        Student student = em.find(Student.class, id);
        if (student !=null) {em.remove(student);}
        em.getTransaction().commit();
        em.close();
    }

}
