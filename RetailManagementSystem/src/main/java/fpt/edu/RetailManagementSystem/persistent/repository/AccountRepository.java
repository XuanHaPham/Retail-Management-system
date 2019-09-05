package fpt.edu.RetailManagementSystem.persistent.repository;
import fpt.edu.RetailManagementSystem.persistent.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account>  {
//    Optional<Account> findByName(String name);
//
//    static Specification<Account> filterByName(String name) {
//        return (root, cq, cb) -> cb.like(root.get("name"), "%" + name + "%");
//    }

    @Query("SELECT a FROM Account a WHERE a.username = :username AND a.password = :password AND a.status = false")
    Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("SELECT a FROM Account a WHERE a.status = false ")
    List<Account> findAllByIsDelete();

    @Query("UPDATE Account a SET a.status = false WHERE a.id = :id")
    @Modifying
    void deleteByID(Integer id);

}