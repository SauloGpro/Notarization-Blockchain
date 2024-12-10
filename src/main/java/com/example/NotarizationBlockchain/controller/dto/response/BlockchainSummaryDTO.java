package com.example.NotarizationBlockchain.controller.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockchainSummaryDTO {
    private int totalBlocks;
    private long genesisTimestamp;
    private long latestTimestamp;
    private boolean isChainValid;
}
