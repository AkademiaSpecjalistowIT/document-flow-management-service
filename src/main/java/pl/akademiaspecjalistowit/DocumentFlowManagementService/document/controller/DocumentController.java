package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service.DocumentService;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;

@RestController
@RequestMapping
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentApi;

    @PostMapping
    void saveDocument(@RequestBody DocumentDto documentDto){
        documentApi.saveDocument(documentDto);
    }

}
