package pl.akademiaspecjalistowit.documentflowmanagementservice.document.mapper;


import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.DownloadDocumentDto;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.dto.DocumentResponse;
import pl.akademiaspecjalistowit.documentflowmanagementservice.document.entity.DocumentEntity;

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
