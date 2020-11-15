package iRide.controller;

import iRide.service.InstructorRating.InstructorRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instructor_rating")
public class InstructorRatingController {

    private InstructorRatingService instructorRatingService;

    @Autowired
    public InstructorRatingController(InstructorRatingService instructorRatingService) {
        this.instructorRatingService = instructorRatingService;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable int id) {
        this.instructorRatingService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
