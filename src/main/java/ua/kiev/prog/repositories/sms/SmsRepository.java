package ua.kiev.prog.repositories.sms;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.prog.entities.sms.Sms;


public interface SmsRepository extends JpaRepository<Sms, Long> {}