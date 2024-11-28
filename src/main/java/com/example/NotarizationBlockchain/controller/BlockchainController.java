package com.example.NotarizationBlockchain.controller;

import com.example.NotarizationBlockchain.model.Block;
import com.example.NotarizationBlockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blockchain")
public class BlockchainController {

    @Autowired
    private BlockchainService blockchainService;

    // Endpoint para añadir un documento al blockchain
    @PostMapping("/add")
    public ResponseEntity<?> addDocument(@RequestBody Map<String, String> request) {
        try {
            String data = request.get("data");
            String documentType = request.get("documentType");
            String owner = request.get("owner");

            Block block = blockchainService.addDocument(data, documentType, owner);
            return ResponseEntity.status(201).body(block); // Devuelve el bloque registrado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Endpoint para validar toda la cadena de bloques
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateBlockchain() {
        return ResponseEntity.ok(blockchainService.validateBlockchain());
    }

    // Endpoint para obtener toda la blockchain
    @GetMapping("/chain")
    public ResponseEntity<List<Block>> getBlockchain() {
        return ResponseEntity.ok(blockchainService.getBlockchain());
    }

    // Endpoint para buscar un documento específico por su hash
    @GetMapping("/search/{hash}")
    public ResponseEntity<?> searchDocument(@PathVariable String hash) {
        Block block = blockchainService.searchBlockByHash(hash);
        if (block != null) {
            return ResponseEntity.ok(block);
        }
        return ResponseEntity.status(404).body("Documento no encontrado");
    }

    // Endpoint para obtener un resumen del blockchain
    @GetMapping("/summary")
    public ResponseEntity<?> getBlockchainSummary() {
        int totalBlocks = blockchainService.getBlockchain().size();
        Block genesisBlock = blockchainService.getBlockchain().get(0);
        Block latestBlock = blockchainService.getBlockchain().get(totalBlocks - 1);

        return ResponseEntity.ok(Map.of(
                "totalBlocks", totalBlocks,
                "genesisTimestamp", genesisBlock.getTimestamp(),
                "latestTimestamp", latestBlock.getTimestamp(),
                "isChainValid", blockchainService.validateBlockchain()
        ));
    }

    // Nuevo Endpoint: Obtener bloques por propietario
    @GetMapping("/owner/{owner}")
    public ResponseEntity<List<Block>> getBlocksByOwner(@PathVariable String owner) {
        List<Block> blocks = blockchainService.getBlocksByOwner(owner);
        if (blocks.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(blocks);
    }
}




