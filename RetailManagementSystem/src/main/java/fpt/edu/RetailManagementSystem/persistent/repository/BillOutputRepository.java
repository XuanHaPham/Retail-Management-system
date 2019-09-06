package fpt.edu.RetailManagementSystem.persistent.repository;

import fpt.edu.RetailManagementSystem.persistent.entity.BillInput;
import fpt.edu.RetailManagementSystem.persistent.entity.BillOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillOutputRepository extends JpaRepository<BillOutput, Integer> {
    @Query("SELECT b FROM BillOutput b")
    List<BillOutput> findAllByIsDelete();

    @Query("SELECT b FROM BillOutput b WHERE b.id = :id")
    BillOutput findBillByID(@Param("id") Integer id);

    @Query("UPDATE BillOutput b SET b.status = :status WHERE b.id = :id")
    @Modifying
    void deleteByID(@Param("id") Integer id, @Param("status") Boolean status);
}
