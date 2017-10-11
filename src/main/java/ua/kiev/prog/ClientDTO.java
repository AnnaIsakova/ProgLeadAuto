package ua.kiev.prog;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ClientDTO {

    private Long id;
    private UserRole role;
    private String phone;
    private String phone2;
    private String phone3;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String skype;
    private String facebook;

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", role=" + role +
                ", phone='" + phone + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", phone3='" + phone3 + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", skype='" + skype + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }
}
