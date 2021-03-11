package tv.comnata.mainservice.entities

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue
    var id: Int,

    var username: String,

    var password: String,

    var email: String,

    @ManyToOne
    var room: Room,
)