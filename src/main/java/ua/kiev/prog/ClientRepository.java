package ua.kiev.prog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.phone = :phone")
    Client findByPhone(String phone);

    @Override
    @Query("SELECT c FROM Client c WHERE c.isDeleted = false")
    List<Client> findAll();
}
