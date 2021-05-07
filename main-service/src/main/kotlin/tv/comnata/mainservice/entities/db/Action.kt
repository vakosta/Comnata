package tv.comnata.mainservice.entities.db

import javax.persistence.*

@Entity
@Table(name = "action")
class Action(
    var type: String,

    var seekTime: Double,

    var step: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var room: Room,

    @ManyToOne(fetch = FetchType.LAZY)
    var author: User,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_to_action",
        joinColumns = [JoinColumn(
            name = "action_id", referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "user_id", referencedColumnName = "id"
        )]
    )
    var users: MutableSet<User> = HashSet(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
) {
    fun addUser(user: User) {
        users.add(user)
        user.actions.add(this)
    }

    fun addUsers(newUsers: Set<User>) {
        users.addAll(newUsers)
        newUsers.forEach { it.actions.add(this) }
    }

    fun deleteUser(user: User) {
        users.remove(user)
        user.actions.remove(this)
    }

    fun deleteAllUsers() {
        for (user in users) {
            user.actions.clear()
        }
        users.clear()
    }
}