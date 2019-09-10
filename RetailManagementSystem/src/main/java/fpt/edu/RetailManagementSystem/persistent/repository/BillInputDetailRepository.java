package fpt.edu.RetailManagementSystem.persistent.repository;
import fpt.edu.RetailManagementSystem.persistent.entity.BillInputDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillInputDetailRepository extends JpaRepository<BillInputDetail, Integer> {
    @Query("SELECT b FROM BillInputDetail b where b.billID = :billID and b.status = true ")
    List<BillInputDetail> findAllByBillID(@Param("billID") Integer billID);
}
