package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service.DocumentServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor
class DocumentController {

    private final DocumentServiceImpl documentServiceImpl;

    @PostMapping("/upload")
    ResponseEntity<UUID> uploadDocument(@RequestParam("file") MultipartFile file){
        UUID savedDocumentId = documentServiceImpl.saveDocument(file);
       return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumentId);
    }

}
