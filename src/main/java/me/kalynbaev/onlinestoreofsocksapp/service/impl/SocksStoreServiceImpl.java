package me.kalynbaev.onlinestoreofsocksapp.service.impl;

import lombok.AllArgsConstructor;
import me.kalynbaev.onlinestoreofsocksapp.exception.ValidationException;
import me.kalynbaev.onlinestoreofsocksapp.model.Color;
import me.kalynbaev.onlinestoreofsocksapp.model.Size;
import me.kalynbaev.onlinestoreofsocksapp.model.Socks;
import me.kalynbaev.onlinestoreofsocksapp.model.SocksBatch;
import me.kalynbaev.onlinestoreofsocksapp.repository.SocksRepository;
import me.kalynbaev.onlinestoreofsocksapp.service.SocksStoreService;
import me.kalynbaev.onlinestoreofsocksapp.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class SocksStoreServiceImpl implements SocksStoreService {
    private final SocksRepository socksRepository;
    private final ValidationService validationService;

    @Override
    public void accept(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        socksRepository.save(socksBatch);
    }

    @Override
    public int issuance(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int reject(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int getCount(Color color, Size size, int cottonMin, int cottonMax) {
        if (validationService.validate(color, size, cottonMin, cottonMax)) {
            throw new ValidationException();
        }
        Map<Socks, Integer> socksMap = socksRepository.getAll();
        for (Map.Entry<Socks, Integer> socksItem : socksMap.entrySet()) {
            Socks socks = socksItem.getKey();
            if (socks.getColor().equals(color) &&
                    socks.getSize().equals(size) &&
                    socks.getCottonPart() >= cottonMax &&
                    socks.getCottonPart() <= cottonMax) {
                return socksItem.getValue();
            }
            return 0;
        }
        return 0;
    }

    private void checkSocksBatch(SocksBatch socksBatch) {
        if (!validationService.validate(socksBatch)) {
            throw new ValidationException();
        }
    }
}
