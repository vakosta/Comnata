package tv.comnata.mainservice.entities

import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(
    @Id
    @GeneratedValue
    var id: Int,

    var username: String,

    var password: String,

    var email: String?,

    var picture: String?,

    @ManyToOne(fetch = FetchType.LAZY)
    var room: Room?,
)