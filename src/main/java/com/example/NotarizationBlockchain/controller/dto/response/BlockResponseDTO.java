package com.example.NotarizationBlockchain.controller.dto.response;

import lombok.Data;

@Data
public class BlockResponseDTO {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private String data;
    private String documentType;
    private String owner;
}

