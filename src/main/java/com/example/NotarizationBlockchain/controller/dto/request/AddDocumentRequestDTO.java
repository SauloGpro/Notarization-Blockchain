package com.example.NotarizationBlockchain.controller.dto.request;

import lombok.Data;

@Data
public class AddDocumentRequestDTO {
    private String data;
    private String documentType;
    private String owner;
}
