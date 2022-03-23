package ozi.app.printer.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printer.data.models.PrintOrder;

public interface OrderRepository extends JpaRepository<PrintOrder, String> {
}
