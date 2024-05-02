package pl.akademiaspecjalistowit.documentFlowManagementService.document.service;

import pl.akademiaspecjalistowit.documentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.documentFlowManagementService.document.entity.DocumentEventEntity;

import java.util.List;

public interface DocumentEventService {
    DownloadDocumentDto appendEventsToPDF(DownloadDocumentDto documentDto, List<DocumentEventEntity> events);
}
