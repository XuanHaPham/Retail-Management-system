package fpt.edu.RetailManagementSystem.persistent.repository;
import fpt.edu.RetailManagementSystem.persistent.entity.BillInputDetail;
import fpt.edu.RetailManagementSystem.persistent.entity.BillOutputDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillOutputDetailRepository extends JpaRepository<BillOutputDetail, Integer> {
    @Query("SELECT b FROM BillOutputDetail b where b.billID = :billID")
    List<BillOutputDetail> findAllByIsDelete(@Param("billID") Integer billID);
}
