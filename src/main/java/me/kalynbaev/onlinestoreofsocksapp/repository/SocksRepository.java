package me.kalynbaev.onlinestoreofsocksapp.repository;

import me.kalynbaev.onlinestoreofsocksapp.model.Socks;
import me.kalynbaev.onlinestoreofsocksapp.model.SocksBatch;

import java.util.Map;

public interface SocksRepository {
    void save(SocksBatch socksBatch);

    int remove(SocksBatch socksBatch);

    Map<Socks, Integer> getAll();
}
