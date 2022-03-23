package ozi.app.printer.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printer.data.models.PrintOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<PrintOrder, String> {
    List<PrintOrder> findPrintOrderByOrderDate(LocalDateTime orderDate);
}
