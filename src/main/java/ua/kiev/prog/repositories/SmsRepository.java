package ua.kiev.prog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.prog.entities.Sms;

public interface SmsRepository extends JpaRepository<Sms, Long> {}