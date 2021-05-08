package tv.comnata.mainservice.entities

class Action(
    var id: Long,

    var type: String,

    var seekTime: Double,

    var author: String,

    var users: MutableSet<String> = HashSet(),
) {
    fun addUser(user: String) {
        users.add(user)
    }

    fun addUsers(newUsers: Set<String>) {
        users.addAll(newUsers)
    }

    fun deleteUser(user: String) {
        users.remove(user)
    }

    fun deleteAllUsers() {
        users.clear()
    }
}
