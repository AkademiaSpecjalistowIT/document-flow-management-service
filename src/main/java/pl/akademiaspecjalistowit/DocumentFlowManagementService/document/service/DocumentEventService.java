package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.service;

import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEventEntity;

import java.util.List;

public interface DocumentEventService {
    DownloadDocumentDto appendEventsToPDF(DownloadDocumentDto documentDto, List<DocumentEventEntity> events);
}
