package iRide.service.InstructorRating;

import iRide.repository.InstructorRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorRatingService {

    private InstructorRatingRepository instructorRatingRepository;

    @Autowired
    public InstructorRatingService(InstructorRatingRepository instructorRatingRepository){
        this.instructorRatingRepository = instructorRatingRepository;
    }

    public void deleteById(int id){
        this.instructorRatingRepository.deleteById(id);
    }
}
