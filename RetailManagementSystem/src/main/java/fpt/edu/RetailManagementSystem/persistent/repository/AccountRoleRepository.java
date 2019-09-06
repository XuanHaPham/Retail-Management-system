package fpt.edu.RetailManagementSystem.persistent.repository;

import fpt.edu.RetailManagementSystem.persistent.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer> {

    @Query("SELECT a.roleID FROM AccountRole a WHERE a.accountID = :accountID AND a.status = true")
    Integer getRoleIDByAccountID(@Param("accountID") Integer accountID);


}
