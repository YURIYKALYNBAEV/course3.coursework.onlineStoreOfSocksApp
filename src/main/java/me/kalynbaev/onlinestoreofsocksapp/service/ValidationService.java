package me.kalynbaev.onlinestoreofsocksapp.service;

import me.kalynbaev.onlinestoreofsocksapp.model.Color;
import me.kalynbaev.onlinestoreofsocksapp.model.Size;
import me.kalynbaev.onlinestoreofsocksapp.model.SocksBatch;

public interface ValidationService {
    boolean validate(SocksBatch socksBatch);

    boolean validate(Color color, Size size, int cottonMin, int cottonMax);
}
