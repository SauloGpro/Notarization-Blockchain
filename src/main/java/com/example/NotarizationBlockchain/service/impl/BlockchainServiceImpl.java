package com.example.NotarizationBlockchain.service.impl;

import com.example.NotarizationBlockchain.mapper.AppMapper;
import com.example.NotarizationBlockchain.model.Block;
import com.example.NotarizationBlockchain.repository.BlockRepository;
import com.example.NotarizationBlockchain.repository.entities.BlockEntity;
import com.example.NotarizationBlockchain.service.BlockchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlockchainServiceImpl implements BlockchainService {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private AppMapper appMapper;

    @Override
    public Block addDocument(String data, String documentType, String owner) {
        // Obtener todos los bloques actuales
        List<BlockEntity> chain = blockRepository.findAll();

        // Validar si el documento ya está registrado
        if (chain.stream().anyMatch(block -> block.getData().equals(data))) {
            throw new IllegalArgumentException("El documento ya está registrado en la blockchain");
        }

        // Obtener el hash del bloque anterior
        String previousHash = chain.isEmpty() ? "0" : chain.get(chain.size() - 1).getHash();
        long timestamp = System.currentTimeMillis();

        // Crear un bloque temporal para calcular el hash
        Block tempBlock = Block.builder()
                .previousHash(previousHash)
                .timestamp(timestamp)
                .data(data)
                .documentType(documentType)
                .owner(owner)
                .build();

        // Calcular el hash usando el método de Block
        String computedHash = tempBlock.calculateHash();

        // Crear la entidad BlockEntity
        BlockEntity newBlockEntity = new BlockEntity();
        newBlockEntity.setPreviousHash(previousHash);
        newBlockEntity.setData(data);
        newBlockEntity.setDocumentType(documentType);
        newBlockEntity.setOwner(owner);
        newBlockEntity.setTimestamp(timestamp);
        newBlockEntity.setHash(computedHash); // Asignar el hash calculado

        // Guardar en la base de datos
        BlockEntity savedEntity = blockRepository.save(newBlockEntity);

        // Convertir la entidad guardada al modelo y devolverla
        return appMapper.blockEntityToBlock(savedEntity);
    }


    @Override
    public boolean validateBlockchain() {
        // Convierte la lista de entidades a modelos
        List<Block> chain = blockRepository.findAll()
                .stream()
                .map(appMapper::blockEntityToBlock)
                .collect(Collectors.toList());

        // Valida la cadena usando la lógica del modelo
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Validar el hash calculado
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Validar la referencia al hash del bloque anterior
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }



    @Override
    public List<Block> getBlockchain() {
        return blockRepository.findAll().stream()
                .map(appMapper::blockEntityToBlock)
                .collect(Collectors.toList());
    }

    @Override
    public Block searchBlockByHash(String hash) {
        return blockRepository.findAll()
                .stream()
                .filter(blockEntity -> blockEntity.getHash().equals(hash))
                .findFirst()
                .map(appMapper::blockEntityToBlock)
                .orElse(null);
    }

    @Override
    public List<Block> getBlocksByOwner(String owner) {
        return blockRepository.findAll()
                .stream()
                .filter(blockEntity -> blockEntity.getOwner().equals(owner))
                .map(appMapper::blockEntityToBlock)
                .collect(Collectors.toList());
    }
}







