package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;

import java.util.List;

public interface DocumentService {

    List<DocumentDto> getAllDocuments();
}
