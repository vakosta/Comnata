package tv.comnata.mainservice.entities

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Video(
    @Id
    var uuid: String,

    var name: String,

    var priority: Int,

    var status: Int,

    var progress: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var room: Room,
)
