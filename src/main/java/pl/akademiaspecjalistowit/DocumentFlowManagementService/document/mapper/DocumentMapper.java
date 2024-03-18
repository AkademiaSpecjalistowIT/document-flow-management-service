package pl.akademiaspecjalistowit.DocumentFlowManagementService.document.mapper;

import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.dto.DocumentDto;
import pl.akademiaspecjalistowit.DocumentFlowManagementService.document.entity.DocumentEntity;

public class DocumentMapper {

    public static DocumentDto dtoFromEntity(DocumentEntity document){
        return new DocumentDto(
                document.getDocumentId(),
                document.getCreationDate(),
                document.getFileName(),
                document.getDescription(),
                document.getState()
        );
    }

}
