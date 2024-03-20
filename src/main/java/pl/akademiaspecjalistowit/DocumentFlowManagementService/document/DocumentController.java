package pl.akademiaspecjalistowit.DocumentFlowManagementService.document;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentCreationInput;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentResponse;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service.DocumentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
@AllArgsConstructor
class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/all")
    public List<DocumentResponse> getDocuments(){
        return documentService.getAllDocuments();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> uploadDocument(@ModelAttribute DocumentCreationInput input){
        UUID savedDocumentId = documentService.saveDocument(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumentId);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable UUID documentId){
        byte[] document = documentService.downloadDocument(documentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        //todo: po zaktualizowaniu pól DocumentEntity przypisać poprawną nazwę pobieranego pliku
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("document.pdf").build());
        return ResponseEntity.ok().headers(headers).body(document);
    }
}
