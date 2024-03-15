package pl.akademiaspecjalistowit.DocumentFlowManagementService.document;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> uploadDocument(@RequestParam("file") MultipartFile file){
        UUID savedDocumentId = documentService.saveDocument(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumentId);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable UUID documentId){
        byte[] document = documentService.getDocument(documentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        //todo: po zaktualizowaniu pól DocumentEntity przypisać poprawną nazwę pobieranego pliku
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("document.pdf").build());
        return ResponseEntity.ok().headers(headers).body(document);
    }
}
