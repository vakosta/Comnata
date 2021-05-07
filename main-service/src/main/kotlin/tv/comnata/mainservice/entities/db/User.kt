package tv.comnata.mainservice.entities.db

import javax.persistence.*

@Entity
@Table(name = "app_user")
class User(
    var username: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var room: Room,

    @ManyToMany(mappedBy = "users")
    var actions: MutableSet<Action> = HashSet(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun deleteAllActions() {
        for (action in actions) {
            action.users.clear()
        }
        actions.clear()
    }
}