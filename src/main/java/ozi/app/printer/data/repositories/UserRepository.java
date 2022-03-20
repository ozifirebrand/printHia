package ozi.app.printer.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printer.data.models.PrintUser;

public interface UserRepository extends JpaRepository<PrintUser, String> {
}
