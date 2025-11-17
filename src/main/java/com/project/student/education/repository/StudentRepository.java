package com.project.student.education.repository;

import com.project.student.education.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByClassSection_ClassSectionId(String classSectionId);



    @Query("SELECT s.studentId FROM Student s WHERE s.classSection.classSectionId = :classSectionId")
    List<String> findStudentIdsByClassSectionId(String classSectionId);


}
