package pl.akademiaspecjalistowit.documentflowmanagementservice.document.service;

import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.entity.DocumentEventEntity;

import java.util.List;

public interface DocumentEventService {
    DownloadDocumentDto appendEventsToPDF(DownloadDocumentDto documentDto, List<DocumentEventEntity> events);
}
