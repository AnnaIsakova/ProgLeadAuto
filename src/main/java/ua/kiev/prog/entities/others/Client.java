package ua.kiev.prog.entities.others;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.kiev.prog.DTO.ClientDTO;

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

    @Column(nullable = false)
    private boolean isDeleted;

    public ClientDTO toDTO(){
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(this.id);
        clientDTO.setRole(this.role);
        clientDTO.setPhone(this.phone);
        clientDTO.setPhone2(this.phone2);
        clientDTO.setPhone3(this.phone3);
        clientDTO.setName(this.name);
        clientDTO.setSurname(this.surname);
        clientDTO.setPassword(this.password);
        clientDTO.setEmail(this.email);
        clientDTO.setSkype(this.skype);
        clientDTO.setFacebook(this.facebook);
        return clientDTO;
    }

    public static Client fromDTO(ClientDTO clientDTO){
        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setRole(clientDTO.getRole());
        client.setPhone(clientDTO.getPhone());
        client.setPhone2(clientDTO.getPhone2());
        client.setPhone3(clientDTO.getPhone3());
        client.setName(clientDTO.getName());
        client.setSurname(clientDTO.getSurname());
        client.setPassword(clientDTO.getPassword());
        client.setEmail(clientDTO.getEmail());
        client.setSkype(clientDTO.getSkype());
        client.setFacebook(clientDTO.getFacebook());
        return client;
    }
}
