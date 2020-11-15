package iRide.service.Event;

import iRide.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    public EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public int deleteById(int id) {
        eventRepository.deleteById(id);
        return id;
    }

}
