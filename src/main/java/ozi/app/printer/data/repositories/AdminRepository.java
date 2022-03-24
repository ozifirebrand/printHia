package ozi.app.printer.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printer.data.models.PrintAdmin;

public interface AdminRepository extends JpaRepository<PrintAdmin, String> {
}
