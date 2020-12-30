package tv.comnata.comnata.entities;

import javax.persistence.*;

@Entity
@Table
public class Room {
    @Id
    @GeneratedValue
    @Column
    long id;

    @Column
    String name;
}
