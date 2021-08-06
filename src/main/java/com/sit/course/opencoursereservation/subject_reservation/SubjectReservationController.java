package com.sit.course.opencoursereservation.subject_reservation;

import java.util.List;
import java.util.Map;

import com.sit.course.opencoursereservation.subject_reservation.model.Subject;
import com.sit.course.opencoursereservation.subject_reservation.model.SubjectReservation;
import com.sit.course.opencoursereservation.subject_reservation.repository.SubjectRepository;
import com.sit.course.opencoursereservation.user.model.User;
import com.sit.course.opencoursereservation.subject_reservation.service.SubjectReservationService;
import com.sit.course.opencoursereservation.user.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Setter
@RestController
@CrossOrigin(origins = "*")
public class SubjectReservationController {
    @Autowired
    private SubjectReservationService subjectReservationService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/subject/{subjectId}/reserve")
    @ResponseStatus(HttpStatus.OK)
    public SubjectReservation reserveSubject(@PathVariable String subjectId) {
        /*
         * This is a bad practice,
         * because it's should get from session or decoding from a jwt token.
         * then uses user id to find user from database.
         *
         * but we need a quick demo project.
         */
        User mockUser = userRepository.findUserById(1L);

        return subjectReservationService.reserveSubject(subjectId, mockUser);
    }

    @GetMapping("/subjects")
    @ResponseStatus(HttpStatus.OK)
    public List<Subject> listAllSubjects() {
        return subjectRepository.listAllSubjects();
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String test() {
        return "Hello, Test.";
    }
}
