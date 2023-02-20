package me.kalynbaev.onlinestoreofsocksapp.service;

import me.kalynbaev.onlinestoreofsocksapp.model.Color;
import me.kalynbaev.onlinestoreofsocksapp.model.Size;
import me.kalynbaev.onlinestoreofsocksapp.model.SocksBatch;
import org.springframework.web.bind.annotation.RequestParam;

public interface SocksStoreService {
    void accept(SocksBatch socksBatch);

    int issuance(SocksBatch socksBatch);

    int reject(SocksBatch socksBatch);

    int getCount(Color color, Size size, int cottonMin, int cottonMax);
}
