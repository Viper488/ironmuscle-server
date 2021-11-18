package com.muscle.user.repository;

import com.muscle.user.entity.IronUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<IronUser, Long> {
    Optional<IronUser> findByUsername(String username);
    Optional<IronUser> findByEmail(String email);
    Integer countByUsername(String username);
    Integer countByEmail(String email);

    @Query(value = "SELECT DISTINCT * FROM iron_user iu INNER JOIN iron_user_roles iur ON iur.iron_user_id = iu.id INNER JOIN role r ON r.id = iur.roles_id WHERE r.name = ?1", nativeQuery = true)
    List<IronUser> findByRole(String roleName);

    Page<IronUser> findByUsernameContainsOrderByUsernameAsc(Pageable pageable, String username);

}
