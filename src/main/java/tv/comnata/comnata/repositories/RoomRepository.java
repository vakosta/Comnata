package tv.comnata.comnata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tv.comnata.comnata.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
