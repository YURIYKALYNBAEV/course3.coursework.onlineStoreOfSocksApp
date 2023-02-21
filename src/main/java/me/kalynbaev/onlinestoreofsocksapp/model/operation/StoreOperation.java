package me.kalynbaev.onlinestoreofsocksapp.model.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.SocksBatch;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreOperation {
    private OperationType operationType;
    private final LocalDateTime localDateTime = LocalDateTime.now();
    private SocksBatch socksBatch;
}
