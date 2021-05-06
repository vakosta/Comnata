package tv.comnata.mainservice.entities

import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(
    var username: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var room: Room,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
)