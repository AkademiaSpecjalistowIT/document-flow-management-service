package pl.akademiaspecjalistowit.DocumentFlowManagementService.document;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service.DocumentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@AllArgsConstructor
class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/all")
    public List<DocumentDto> getDocuments(){
        return documentService.getAllDocuments();
    }

    @PostMapping("/upload")
    ResponseEntity<UUID> uploadDocument(@RequestParam("file") MultipartFile file){
        UUID savedDocumentId = documentService.saveDocument(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumentId);
    }
}
