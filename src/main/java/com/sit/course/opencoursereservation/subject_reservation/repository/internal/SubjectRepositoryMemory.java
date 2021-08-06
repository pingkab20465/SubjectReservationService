package com.sit.course.opencoursereservation.subject_reservation.repository.internal;

import com.sit.course.opencoursereservation.exception.EntityNotFoundException;
import com.sit.course.opencoursereservation.subject_reservation.model.Subject;
import com.sit.course.opencoursereservation.subject_reservation.repository.SubjectRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SubjectRepositoryMemory implements SubjectRepository {
    // private Map<String, Subject> subjects = new HashMap<String, Subject>();

    private ArrayList<Subject> subjects = new ArrayList();

    public SubjectRepositoryMemory() {
        // SubjectID
        // subjects.put("int101", new Subject("int101","Introduction to Java 1"));
        // subjects.put("int102", new Subject("int102","Object Oreinted to Expert",7));
        // subjects.put("int305", new Subject("int305","Project Management
        // Professional",5));
        // subjects.put("int405", new Subject("int405","Cloud Native Paradigm",15));
        // subjects.put("dotnet", new Subject("dotnet","Cloud Native Paradigm",30));

        subjects.add(new Subject("int101", "Introduction to Java 1"));
        subjects.add(new Subject("int102", "Object Oreinted to Expert", 7));
        subjects.add(new Subject("int305", "Project Management Professional", 5));
        subjects.add(new Subject("int405", "Cloud Native Paradigm", 15));
        subjects.add(new Subject("dotnet", "Cloud Native Paradigm", 30));
    }

    @Override
    public Subject findBySubjectId(String subjectId) {
        Subject foundedSubject = null;
        for (Subject subject : subjects) {
            if (subject.getSubjectId().equalsIgnoreCase(subjectId)) {
                foundedSubject = subject;
            }
        }

        if (foundedSubject == null) {
            throw new EntityNotFoundException();
        }

        return foundedSubject;
    }

    @Override
    public ArrayList<Subject> listAllSubjects() {
        return this.subjects;
    }

}
