package me.kalynbaev.onlinestoreofsocksapp.service;

import me.kalynbaev.onlinestoreofsocksapp.model.socks.Color;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.Size;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.SocksBatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface SocksStoreService {
    void accept(SocksBatch socksBatch);

    int issuance(SocksBatch socksBatch);

    int reject(SocksBatch socksBatch);

    int getCount(Color color, Size size, int cottonMin, int cottonMax);

    File exportFile() throws IOException;

    void importFromFile(MultipartFile file) throws IOException;
}
