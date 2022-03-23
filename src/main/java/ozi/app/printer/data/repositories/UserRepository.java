package ozi.app.printer.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printer.data.models.PrintUser;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<PrintUser, String> {
    Optional<PrintUser> findPrintUserByEmail(String email);

    void deletePrintUserByEmail(String email);

    void deletePrintUserByUsername(String  username);

    Optional<PrintUser> findPrintUserByUsername(String username);
}