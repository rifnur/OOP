package com.rifnur.lab.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    List<Student> findStudentByNameLike(String name);

    Optional<Student> findStudentByName(String name);

    @Query("select s from Student s " +
            "where (s.name like :name or :name is null) and " +
            "      (s.age >= :minAge or :minAge is null) and " +
            "      (s.age <= :maxAge or :maxAge is null)")
    List<Student> findWithFilter(@Param("name") String nameFilter,
                                 @Param("minAge") Integer minAge,
                                 @Param("maxAge") Integer maxAge);

}
