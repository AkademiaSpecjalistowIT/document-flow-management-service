package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.mapper.DocumentMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService{
    private final DocumentDataService documentDataService;
    @Override
    public List<DocumentDto> getAllDocuments() {
        return documentDataService.getAllDocuments()
                .stream()
                .map(DocumentMapper::dtoFromEntity)
                .collect(Collectors.toList());
    }
}
