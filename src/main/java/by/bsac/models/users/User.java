package by.bsac.models.users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long user_id;

    @OneToOne(mappedBy = "user_owner", cascade = CascadeType.ALL)
    @Column(name = "account_id")
    private Account user_account;
}
