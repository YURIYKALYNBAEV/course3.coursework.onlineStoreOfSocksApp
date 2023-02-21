package me.kalynbaev.onlinestoreofsocksapp.controller;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kalynbaev.onlinestoreofsocksapp.model.operation.StoreOperation;
import me.kalynbaev.onlinestoreofsocksapp.service.SocksStoreService;
import me.kalynbaev.onlinestoreofsocksapp.service.StoreOperationService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/socks/files")
@Tag(name = "API для работы с файлами", description = "Импорт/экспорт файлов носков")
@RequiredArgsConstructor
public class FileController {
    private final SocksStoreService socksStoreService;
    private final StoreOperationService storeOperationService;

    @GetMapping("/export")
    @Operation(summary = "Выгрузка json-файла носков")
    public ResponseEntity<InputStreamResource> downloadSocksJson() {
        try {
            File socksFile = socksStoreService.exportFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(socksFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(socksFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                            socksFile.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value ="/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка json-файла носков")
    public ResponseEntity<Object> uploadSocksJson(@RequestParam MultipartFile file) {
        try {
            socksStoreService.importFromFile(file);
            return ResponseEntity.ok("Файл успешно импортирован");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла." +
                    " Проверьте корректность файла");
        }
    }

    @GetMapping("/operation/export")
    @Operation(summary = "Выгрузка json-файла операций с носками")
    public ResponseEntity<InputStreamResource> downloadSocksOperationJson() {
        try {
            File socksOperationsFile = storeOperationService.exportFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(socksOperationsFile));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(socksOperationsFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                            socksOperationsFile.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value ="/operation/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка json-файла операций с носками")
    public ResponseEntity<Object> uploadSocksOperationJson(@RequestParam MultipartFile file) {
        try {
            storeOperationService.importFromFile(file);
            return ResponseEntity.ok("Файл успешно импортирован");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла." +
                    " Проверьте корректность файла");
        }
    }
}
