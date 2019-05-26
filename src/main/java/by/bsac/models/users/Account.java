package by.bsac.models.users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @Column(name = "account_id")
    private long account_id;

    @Column(name = "user_account")
    @OneToOne(mappedBy = "account_id", orphanRemoval = true)
    @MapsId(value = "account_id")
    private User user_owner;

    private String email;
    //Telephone
    private transient String password;
    private String password_hash;
    private String password_salt;



}
