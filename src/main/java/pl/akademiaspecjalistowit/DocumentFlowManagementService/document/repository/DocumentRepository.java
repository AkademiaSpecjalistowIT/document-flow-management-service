package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;

import java.util.Optional;
import java.util.UUID;

public interface  DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Optional<DocumentEntity> findByDocumentId(UUID documentId);
}
