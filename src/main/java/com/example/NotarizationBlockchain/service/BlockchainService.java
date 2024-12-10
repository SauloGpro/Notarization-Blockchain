package com.example.NotarizationBlockchain.service;

import com.example.NotarizationBlockchain.model.Block;

import java.util.List;

public interface BlockchainService {
    public Block addDocument(String data, String documentType, String owner);

    public boolean validateBlockchain();

    public List<Block> getBlockchain();

    public Block searchBlockByHash(String hash);

    public List<Block> getBlocksByOwner(String owner);
}

