package tv.comnata.mainservice.entities

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
// @Table(name = "rooms")
class Room(
    @Id
    @GeneratedValue
    var id: Int,

    var creationDate: LocalDateTime,
)