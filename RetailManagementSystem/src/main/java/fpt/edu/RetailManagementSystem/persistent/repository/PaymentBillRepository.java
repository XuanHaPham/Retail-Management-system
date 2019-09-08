package fpt.edu.RetailManagementSystem.persistent.repository;
import fpt.edu.RetailManagementSystem.persistent.entity.PaymentBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentBillRepository extends JpaRepository<PaymentBill, Integer> {
    @Query("SELECT b FROM PaymentBill b WHERE b.status = true ")
    List<PaymentBill> findAllByIsDelete();

    @Query("SELECT b FROM PaymentBill b WHERE b.id = :id")
    PaymentBill findBillByID(@Param("id") Integer id);

    @Query("UPDATE PaymentBill b SET b.status = :status WHERE b.id = :id")
    @Modifying
    void deleteByID(@Param("id") Integer id, @Param("status") Boolean status);
}