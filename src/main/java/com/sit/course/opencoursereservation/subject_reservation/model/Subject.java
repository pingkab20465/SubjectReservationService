package com.sit.course.opencoursereservation.subject_reservation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Subject {
    private String subjectId;
    private int quota;
    private int currentStudentNumber;
    private String subjectName;

    public Subject() {

    }

    public Subject(String subjectId) {
        this.subjectId = subjectId;
        this.quota = 30;
        this.currentStudentNumber = 0;
    }

    public Subject(String subjectId,String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.quota = 30;
        this.currentStudentNumber = 0;
    }

    public Subject(String subjectId,String subjectName,int limitQuota) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.quota = limitQuota;
        this.currentStudentNumber = 0;
    }
}
