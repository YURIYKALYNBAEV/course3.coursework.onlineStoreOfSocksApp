package me.kalynbaev.onlinestoreofsocksapp.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import me.kalynbaev.onlinestoreofsocksapp.exception.ValidationException;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.Color;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.Size;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.Socks;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.SocksBatch;
import me.kalynbaev.onlinestoreofsocksapp.repository.SocksRepository;
import me.kalynbaev.onlinestoreofsocksapp.service.FileService;
import me.kalynbaev.onlinestoreofsocksapp.service.SocksStoreService;
import me.kalynbaev.onlinestoreofsocksapp.service.StoreOperationService;
import me.kalynbaev.onlinestoreofsocksapp.service.ValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocksStoreServiceImpl implements SocksStoreService {
    private final SocksRepository socksRepository;
    private final ValidationService validationService;
    private final FileService fileService;
    private final StoreOperationService operationService;
    private final Path path = Path.of("src/main/resources", "socks.json");

    @Override
    public void accept(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        operationService.accept(socksBatch);
        socksRepository.save(socksBatch);
    }

    @Override
    public int issuance(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        operationService.issuance(socksBatch);
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int reject(SocksBatch socksBatch) {
        checkSocksBatch(socksBatch);
        operationService.reject(socksBatch);
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int getCount(Color color, Size size, int cottonMin, int cottonMax) {
        if (!validationService.validate(color, size, cottonMin, cottonMax)) {
            throw new ValidationException();
        }
        Map<Socks, Integer> socksMap = socksRepository.getAll();
        for (Map.Entry<Socks, Integer> socksItem : socksMap.entrySet()) {
            Socks socks = socksItem.getKey();
            if (socks.getColor().equals(color) &&
                    socks.getSize().equals(size) &&
                    socks.getCottonPart() >= cottonMin &&
                    socks.getCottonPart() <= cottonMax) {
                return socksItem.getValue();
            }
        }
        return 0;
    }
    @Override
    public File exportFile() throws IOException {
        return fileService.saveToFile(socksRepository.getList(), path).toFile();
    }

    @Override
    public void importFromFile(MultipartFile file) throws IOException {
        List<SocksBatch> socksBatchList = fileService.uploadFromFile(file, path,
                new TypeReference<List<SocksBatch>>() {
                });
        socksRepository.replace(socksBatchList);

    }
    private void checkSocksBatch(SocksBatch socksBatch) {
        if (!validationService.validate(socksBatch)) {
            throw new ValidationException();
        }
    }
}
