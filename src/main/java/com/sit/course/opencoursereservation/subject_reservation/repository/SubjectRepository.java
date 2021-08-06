package com.sit.course.opencoursereservation.subject_reservation.repository;

import java.util.List;
import java.util.Map;

import com.sit.course.opencoursereservation.subject_reservation.model.Subject;

public interface SubjectRepository {
    /**
     * Finds subject by subject id.
     *
     * @param subjectId
     * @return subject
     */
    Subject findBySubjectId(String subjectId);

    List<Subject> listAllSubjects();
}
