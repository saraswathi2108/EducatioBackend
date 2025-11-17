package com.project.student.education.repository;

import com.project.student.education.entity.IdSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdSequenceRepository extends JpaRepository<IdSequence, String> { }
