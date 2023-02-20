package me.kalynbaev.onlinestoreofsocksapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Партия носков
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocksBatch {
    private Socks socks;
    private int quantity;
}
