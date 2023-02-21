package me.kalynbaev.onlinestoreofsocksapp.service;

import me.kalynbaev.onlinestoreofsocksapp.model.socks.Color;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.Size;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.SocksBatch;

public interface ValidationService {
    boolean validate(SocksBatch socksBatch);

    boolean validate(Color color, Size size, int cottonMin, int cottonMax);
}
