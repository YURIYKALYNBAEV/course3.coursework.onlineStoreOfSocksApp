package me.kalynbaev.onlinestoreofsocksapp.service;

import me.kalynbaev.onlinestoreofsocksapp.model.socks.SocksBatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface StoreOperationService {
    void accept(SocksBatch socksBatch);

    void issuance(SocksBatch socksBatch);

    void reject(SocksBatch socksBatch);
    File exportFile() throws IOException;

    void importFromFile(MultipartFile file) throws IOException;
}
