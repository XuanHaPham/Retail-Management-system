package fpt.edu.RetailManagementSystem.persistent.repository;

import fpt.edu.RetailManagementSystem.persistent.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.status = true")
    List<Role> getAllRole();

    @Query("UPDATE Role r SET r.status = false WHERE r.id = :id")
    @Modifying
    void deleteByID(@Param("id") Integer id);

}
