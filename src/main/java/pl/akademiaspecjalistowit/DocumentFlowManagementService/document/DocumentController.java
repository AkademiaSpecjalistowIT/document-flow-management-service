package pl.akademiaspecjalistowit.DocumentFlowManagementService.document;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/document")
@AllArgsConstructor
public class DocumentController {
    private DocumentService documentService;

    @GetMapping("/all")
    public List<DocumentDto> getDocuments(){
        return documentService.getAllDocuments();
    }
}
