package iRide.service.Event;

import iRide.model.Course;
import iRide.model.Event;
import iRide.repository.EventRepository;
import iRide.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    public EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public int deleteEvent(int id) {
        Event event = getEvent(id);
        eventRepository.delete(event);
        return id;
    }

    public Event getEvent(int eventId) {
        Optional<Event> result = this.eventRepository.findById(eventId);
        if (!result.isPresent()) {
            throw new NotFoundException("Course with id = " + eventId + " has not been found");
        }
        return result.get();
    }



}
