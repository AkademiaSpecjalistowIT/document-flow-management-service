package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.mapper;

import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentResponse;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;

public class DocumentMapper {

    public static DocumentResponse dtoFromEntity(DocumentEntity document){
        return new DocumentResponse(
                document.getDocumentId(),
                document.getCreationDate(),
                document.getFileName(),
                document.getDescription(),
                document.getDocumentType(),
                document.getDeadline(),
                document.getState()
        );
    }

    public static DownloadDocumentDto downloadDtoFromEntity(DocumentEntity document){
        return new DownloadDocumentDto(
                document.getFile(),
                document.getFileName()
        );
    }

}
