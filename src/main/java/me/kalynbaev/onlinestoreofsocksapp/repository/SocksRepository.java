package me.kalynbaev.onlinestoreofsocksapp.repository;

import me.kalynbaev.onlinestoreofsocksapp.model.socks.Socks;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.SocksBatch;

import java.util.List;
import java.util.Map;

public interface SocksRepository {
    void save(SocksBatch socksBatch);

    int remove(SocksBatch socksBatch);

    Map<Socks, Integer> getAll();

    List<SocksBatch> getList();

    void replace(List<SocksBatch> socksBatchList);
}
