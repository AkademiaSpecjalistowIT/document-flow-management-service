package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
}
