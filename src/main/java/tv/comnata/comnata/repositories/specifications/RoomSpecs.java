package tv.comnata.comnata.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import tv.comnata.comnata.entities.Room;

public class RoomSpecs {
    public static Specification<Room> titleContains(String word) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("title"), "" + word + "");
    }
}
