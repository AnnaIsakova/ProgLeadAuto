package ua.kiev.prog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    //for creating user
    @Query("SELECT c FROM Client c WHERE c.phone = :phone OR c.phone2 = :phone OR c.phone3 = :phone")
    Client findByPhone(String phone);

    //for editing user
    @Query("SELECT c.phone FROM Client c WHERE " +
            "c.id <> :id AND " +
            "(c.phone = :phone OR c.phone2 = :phone OR c.phone3 = :phone)")
    String findPhone(String phone, long id);

    @Override
    @Query("SELECT c FROM Client c WHERE c.isDeleted = false AND c.role <> 'ADMIN'")
    List<Client> findAll();

    @Query("SELECT c FROM Client c WHERE c.isDeleted = false AND c.role = 'USER'")
    List<Client> findAllClients();
}
