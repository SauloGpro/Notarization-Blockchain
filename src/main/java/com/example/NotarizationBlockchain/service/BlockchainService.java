package com.example.NotarizationBlockchain.service;

import com.example.NotarizationBlockchain.model.Block;
import com.example.NotarizationBlockchain.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlockchainService {

    @Autowired
    private BlockRepository blockRepository;

    // Añadir un nuevo bloque a la cadena
    public Block addDocument(String data, String documentType, String owner) {
        // Obtener todos los bloques de la base de datos
        List<Block> chain = blockRepository.findAll();

        // Validar si el documento ya está registrado
        for (Block block : chain) {
            if (block.getData().equals(data)) {
                throw new IllegalArgumentException("El documento ya está registrado en la blockchain");
            }
        }

        // Crear un nuevo bloque con los datos proporcionados
        String previousHash = chain.isEmpty() ? "0" : chain.get(chain.size() - 1).getHash();
        Block newBlock = new Block(previousHash, data, documentType, owner);

        // Guardar el nuevo bloque en la base de datos
        return blockRepository.save(newBlock);
    }




    // Validar si la blockchain es válida
    public boolean validateBlockchain() {
        List<Block> chain = blockRepository.findAll();
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Validar el hash actual y el hash anterior
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    // Obtener toda la cadena de bloques
    public List<Block> getBlockchain() {
        return blockRepository.findAll();
    }

    // Buscar un bloque por su hash
    public Block searchBlockByHash(String hash) {
        return blockRepository.findAll()
                .stream()
                .filter(block -> block.getHash().equals(hash))
                .findFirst()
                .orElse(null);
    }

    // Obtener bloques por propietario
    public List<Block> getBlocksByOwner(String owner) {
        return blockRepository.findAll()
                .stream()
                .filter(block -> block.getOwner().equals(owner))
                .collect(Collectors.toList());
    }
}





