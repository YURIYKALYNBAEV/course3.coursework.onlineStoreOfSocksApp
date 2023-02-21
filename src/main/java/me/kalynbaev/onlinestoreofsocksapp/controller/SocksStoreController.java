package me.kalynbaev.onlinestoreofsocksapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.kalynbaev.onlinestoreofsocksapp.controller.dto.ResponseDto;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.Color;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.Size;
import me.kalynbaev.onlinestoreofsocksapp.model.socks.SocksBatch;
import me.kalynbaev.onlinestoreofsocksapp.service.SocksStoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "API для учета носков", description = "Регистрация прихода, отпуск со склада, списание брака," +
        " подсчет общего количества")
@RequiredArgsConstructor
public class SocksStoreController {
    private final SocksStoreService socksStoreService;

    @PostMapping
    @Operation(summary = "Регистрирует приход товара на склад")
    @ApiResponse(responseCode = "200", description = "удалось добавить приход")
    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<ResponseDto> accept(@RequestBody SocksBatch socksBatch) {
        socksStoreService.accept(socksBatch);
        return ResponseEntity.ok(new ResponseDto("Носки успешно добавлены на склад"));
    }

    @PutMapping
    @Operation(summary = "Регистрирует отпуск носков со склада")
    @ApiResponse(responseCode = "200", description = "удалось произвести отпуск носков со склада")
    @ApiResponse(responseCode = "400", description = "товара нет на складе в нужном количестве или" +
            " параметры запроса имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<ResponseDto> issuance(@RequestBody SocksBatch socksBatch) {
        int socksCount = socksStoreService.issuance(socksBatch);
        return ResponseEntity.ok(new ResponseDto(socksCount + " носков было отпущено со склада"));
    }

    @GetMapping
    @Operation(summary = "Возвращает общее количество носков на складе")
    @ApiResponse(responseCode = "200", description = "запрос выполнен, результат в теле ответа в виде " +
            "строкового представления целого числа")
    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<ResponseDto> getCount(@RequestParam Color color,
                                                @RequestParam Size size,
                                                @RequestParam int cottonMin,
                                                @RequestParam int cottonMax) {
        int socksCount = socksStoreService.getCount(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(new ResponseDto("Количество носков: " + socksCount));
    }

    @DeleteMapping
    @Operation(summary = "Регистрирует списание испорченных (бракованных) носков")
    @ApiResponse(responseCode = "200", description = "запрос выполнен, товар списан со склада")
    @ApiResponse(responseCode = "400", description = "тпараметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<ResponseDto> reject(@RequestBody SocksBatch socksBatch) {
        int socksCount = socksStoreService.reject(socksBatch);
        return ResponseEntity.ok(new ResponseDto(socksCount + " носков списано со склада"));
    }
}
