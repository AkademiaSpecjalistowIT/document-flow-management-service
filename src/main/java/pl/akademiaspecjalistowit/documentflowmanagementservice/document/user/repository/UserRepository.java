package pl.akademiaspecjalistowit.documentflowmanagementservice.document.user.repository;

import org.springframework.data.jpa.repository.*;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username=:username")
    Optional<UserEntity> findByUsername(String username);
}
