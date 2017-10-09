package ua.kiev.prog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(unique = true)
    private String phone2;

    @Column(unique = true)
    private String phone3;

    private String name;

    private String surname;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String skype;

    @Column(unique = true)
    private String facebook;
}
