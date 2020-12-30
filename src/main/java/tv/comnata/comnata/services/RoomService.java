package tv.comnata.comnata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.comnata.comnata.entities.Room;
import tv.comnata.comnata.repositories.RoomRepository;

import java.util.List;

@Service
public class RoomService {
    private RoomRepository repository;

    @Autowired
    public void setRepository(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> getAllRooms() {
        return repository.findAll();
    }
}
