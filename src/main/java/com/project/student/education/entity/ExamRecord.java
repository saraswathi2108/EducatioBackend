package com.project.student.education.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "exam_record",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"exam_id", "class_section_id", "subject_id", "student_id", "exam_date"}
        )
)
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ExamRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;


    @Column(name = "exam_id", nullable = false)
    private String examId;

    @Column(name = "class_section_id", nullable = false)
    private String classSectionId;

    @Column(name = "subject_id", nullable = false)
    private String subjectId;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(name = "invigilator_id")
    private String invigilatorId;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_section_id", insertable = false, updatable = false)
    private ClassSection classSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invigilator_id", insertable = false, updatable = false)
    private Teacher invigilator;



    private LocalDate examDate;

    private LocalTime startTime;
    private LocalTime endTime;

    private String roomNumber;

    private Double maxMarks;
    private Double passMarks;

    private Double marksObtained;
    private String grade;

    private String attendanceStatus;

    private String status;
    private String remarks;

    private Boolean revaluationRequested;
    private String revaluationStatus;

    private String enteredBy;
    private String verifiedBy;
    private String publishedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
