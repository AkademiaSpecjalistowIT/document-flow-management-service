package pl.akademiaspecjalistowit.documentflowmanagementservice.document.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.entity.DocumentEntity;

import java.util.Optional;
import java.util.UUID;

public interface  DocumentRepository extends JpaRepository<DocumentEntity, Long> {

    Optional<DocumentEntity> findByDocumentId(UUID documentId);
}
