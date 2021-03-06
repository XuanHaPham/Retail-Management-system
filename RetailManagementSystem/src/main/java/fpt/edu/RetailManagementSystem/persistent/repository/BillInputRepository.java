package fpt.edu.RetailManagementSystem.persistent.repository;

import fpt.edu.RetailManagementSystem.persistent.entity.BillInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillInputRepository extends JpaRepository<BillInput, Integer> {
    @Query("SELECT b FROM BillInput b WHERE b.status = true ")
    List<BillInput> findAllByIsDelete();

    @Query("SELECT b FROM BillInput b WHERE b.id = :id")
    BillInput findBillByID(@Param("id") Integer id);

    @Query("UPDATE BillInput b SET b.status = :status WHERE b.id = :id")
    @Modifying
    void deleteByID(@Param("id") Integer id, @Param("status") Boolean status);

    @Query("UPDATE BillInput b SET b.isPaid = :isPaid WHERE b.id = :id")
    @Modifying
    void updateIsPaid(@Param("id") Integer id, @Param("isPaid") Boolean isPaid);

    @Query("SELECT b FROM BillInput b WHERE b.code = :code")
    BillInput findByCode(@Param("code") String code);
}

