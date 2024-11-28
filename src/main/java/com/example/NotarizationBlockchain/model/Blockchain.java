package com.example.NotarizationBlockchain.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data // Genera getters, setters, toString, equals y hashCode
public class Blockchain {

    private List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        return new Block(0, "0", "Genesis Block");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(String data, String documentType, String owner) {
        // Verificar si el documento ya está registrado en la blockchain
        for (Block block : chain) {
            if (block.getData().equals(data)) {
                throw new IllegalArgumentException("El documento ya está registrado en la blockchain");
            }
        }

        // Obtener el último bloque de la cadena
        Block previousBlock = getLatestBlock();

        // Crear el nuevo bloque con todos los campos requeridos
        Block newBlock = new Block(
                previousBlock.getHash(), // Hash del bloque anterior
                data,                    // Datos del bloque
                documentType,            // Tipo de documento
                owner                    // Propietario del documento
        );

        // Agregar el bloque a la cadena
        chain.add(newBlock);
    }



    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }
}



