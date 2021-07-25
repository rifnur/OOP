package Service;

import dao.StudentRepository;
import models.Student;

import java.util.List;

public class StudentService {

    private StudentRepository studentRepository = new StudentRepository();

    public StudentService() {
    }

    public Student findStudent(int id) {
        return studentRepository.findById(id);
    }

    public void insertStudent(Student student) {
        studentRepository.insert(student);
    }

    public void deleteStudent(int id) {
        studentRepository.delete(id);
    }

    public void update(Student student) {
        studentRepository.update(student);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }


}