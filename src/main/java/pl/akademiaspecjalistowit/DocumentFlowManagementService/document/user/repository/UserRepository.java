package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
