package ozi.app.printer.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ozi.app.printer.data.models.OrderStatus;
import ozi.app.printer.data.models.PrintOrder;
import ozi.app.printer.data.models.PrintUser;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<PrintOrder, String> {
    List<PrintOrder> findByOrderDate(LocalDateTime date);

    List<PrintOrder> findByOrderStatus(OrderStatus status);

    List<PrintOrder> findPrintOrderByUserId(String userId);

    void deleteByUserId(String userId);
}
