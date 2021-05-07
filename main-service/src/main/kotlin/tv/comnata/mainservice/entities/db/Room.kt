package tv.comnata.mainservice.entities.db

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "room")
class Room(
    var name: String,

    var creationDate: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    var users: Set<User> = hashSetOf(),
)
