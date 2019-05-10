package com.brzn.mtgboard.user;

import com.brzn.mtgboard.security.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    //todo informacje o walidacji nakladaja sie. dodac na froncie czyszczenie + info z ktorego pola pochodzi

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    private String username;


    @Getter
    @Setter
    @Email
    private String email;

    @Size(min = 8)
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private int enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter
    @Setter
    private Set<Role> roles;

    public User() {
    }

}
