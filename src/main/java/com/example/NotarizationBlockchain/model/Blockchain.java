package com.example.NotarizationBlockchain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder// Agrega un builder para instancias si se necesita en el futuro
@AllArgsConstructor
public class Blockchain {
    private List<Block> chain; // Lista de bloques que forman la blockchain

    // Constructor para inicializar la blockchain
    public Blockchain() {
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock()); // Añadir el bloque génesis al iniciar
    }

    // Crear el bloque génesis
    private Block createGenesisBlock() {
        return Block.builder()
                .index(0)
                .timestamp(System.currentTimeMillis())
                .previousHash("0")
                .data("Genesis Block")
                .documentType("default")
                .owner("system")
                .hash(Block.applySHA256("0" + System.currentTimeMillis() + "Genesis Block" + "default" + "system"))
                .build();
    }

    // Obtener el último bloque de la blockchain
    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    // Añadir un nuevo bloque a la cadena
    public void addBlock(String data, String documentType, String owner) {
        Block previousBlock = getLatestBlock(); // Obtener el último bloque
        Block newBlock = Block.builder()
                .index(previousBlock.getIndex() + 1)
                .timestamp(System.currentTimeMillis())
                .previousHash(previousBlock.getHash())
                .data(data)
                .documentType(documentType)
                .owner(owner)
                .hash(Block.applySHA256(previousBlock.getHash() + System.currentTimeMillis() + data + documentType + owner))
                .build();

        chain.add(newBlock); // Añadir el bloque a la cadena
    }

    // Validar la integridad de la blockchain
    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            // Validar hash del bloque actual
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            // Validar hash del bloque anterior
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }
}




