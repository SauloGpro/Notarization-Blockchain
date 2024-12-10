package com.example.NotarizationBlockchain.controller;

import com.example.NotarizationBlockchain.mapper.AppMapper;
import com.example.NotarizationBlockchain.controller.dto.request.AddDocumentRequestDTO;
import com.example.NotarizationBlockchain.controller.dto.response.BlockResponseDTO;
import com.example.NotarizationBlockchain.controller.dto.response.BlockchainSummaryDTO;
import com.example.NotarizationBlockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private AppMapper appMapper;

    // Endpoint para añadir un documento al blockchain
    @PostMapping("/add")
    public ResponseEntity<?> addDocument(@RequestBody AddDocumentRequestDTO requestDTO) {
        try {
            var block = blockchainService.addDocument(
                    requestDTO.getData(),
                    requestDTO.getDocumentType(),
                    requestDTO.getOwner()
            );
            return ResponseEntity.status(201).body(appMapper.blockToBlockResponseDTO(block));
        } catch (IllegalArgumentException ex) {
            // Manejo de errores específicos
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            // Manejo de errores generales
            return ResponseEntity.status(500).body("Error al añadir el documento al blockchain");
        }
    }


    // Endpoint para validar toda la cadena de bloques
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateBlockchain() {
        return ResponseEntity.ok(blockchainService.validateBlockchain());
    }

    // Endpoint para obtener toda la blockchain
    @GetMapping("/chain")
    public ResponseEntity<List<BlockResponseDTO>> getBlockchain() {
        var blocks = blockchainService.getBlockchain();
        return ResponseEntity.ok(appMapper.blocksToBlockResponseDTOs(blocks)); // Mapeo a DTOs
    }

    // Endpoint para buscar un documento específico por su hash
    @GetMapping("/search/{hash}")
    public ResponseEntity<BlockResponseDTO> searchDocument(@PathVariable String hash) {
        var block = blockchainService.searchBlockByHash(hash);
        if (block == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(appMapper.blockToBlockResponseDTO(block)); // Mapeo a DTO
    }

    // Endpoint para obtener un resumen del blockchain
    @GetMapping("/summary")
    public ResponseEntity<BlockchainSummaryDTO> getBlockchainSummary() {
        var blocks = blockchainService.getBlockchain();
        var totalBlocks = blocks.size();
        var genesisTimestamp = blocks.get(0).getTimestamp();
        var latestTimestamp = blocks.get(totalBlocks - 1).getTimestamp();
        var isChainValid = blockchainService.validateBlockchain();

        var summary = BlockchainSummaryDTO.builder()
                .totalBlocks(totalBlocks)
                .genesisTimestamp(genesisTimestamp)
                .latestTimestamp(latestTimestamp)
                .isChainValid(isChainValid)
                .build();

        return ResponseEntity.ok(summary);
    }

    // Endpoint para obtener bloques por propietario
    @GetMapping("/owner/{owner}")
    public ResponseEntity<List<BlockResponseDTO>> getBlocksByOwner(@PathVariable String owner) {
        var blocks = blockchainService.getBlocksByOwner(owner);
        if (blocks.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(appMapper.blocksToBlockResponseDTOs(blocks)); // Mapeo a DTOs
    }
}





