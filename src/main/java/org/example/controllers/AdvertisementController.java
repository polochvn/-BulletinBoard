package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.dto.AdvertisementDto;
import org.example.services.AdvertisementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/advertisements")
@RequiredArgsConstructor
@Tag(name = "Advertisement Controller", description = "Отвечает за размещение и показ объявлений")
public class AdvertisementController {
    private final static Log log = LogFactory.getLog(AdvertisementController.class);
    private final AdvertisementService advertisementService;
    private String uploadPath;
    @Operation(
            summary = "Создание объявления",
            description = "Позволяет создать объявление"
    )
    @PostMapping
    AdvertisementDto create(@RequestBody AdvertisementDto advertisementDto,
                            @RequestParam("file") MultipartFile file) throws IOException {
        log.info("create advertisement");
        if (file != null) {
            File uploadFolder = new File(uploadPath);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }
            String uuidFileName = UUID.randomUUID().toString();
            String resultFileName = uuidFileName + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            advertisementDto.setImageFile(resultFileName);
        }
        return advertisementService.create(advertisementDto);
    }

    @Operation(
            summary = "Получение объявления",
            description = "Позволяет получить объявление по Id"
    )
    @GetMapping("/{advertisementId}")
    AdvertisementDto getAdvertisementById(@PathVariable long advertisementId) {
        log.info("Advertisement Id = " + advertisementId + ";");
        return advertisementService.getAdvertisementById(advertisementId);
    }

    @Operation(
            summary = "Получение списка объявлений для главной страницы",
            description = "Позволяет получить список всех объявлений "
    )
    @GetMapping()
    List<AdvertisementDto> getAll() {
        log.info("Get All Advertisements");
        return advertisementService.getAllAdvertisements();
    }

    @Operation(
            summary = "Получение списка объявлений для юзера",
            description = "Позволяет получить список всех объявлений, созданных определенным пользователем "
    )
    @GetMapping("/user/{userId}")
    List<AdvertisementDto> getAllByUser(@PathVariable long userId) {
        log.info("get all advertisements ");
        return advertisementService.getAllByUser(userId);
    }

    @Operation(
            summary = "Изменение объявления",
            description = "Позволяет изменить объявление"
    )
    @PatchMapping("/{advertisementId}")
    AdvertisementDto update(@PathVariable long advertisementId,
                            @RequestBody AdvertisementDto advertisementDto,
                            @PathVariable Integer newPrice) {
        log.info("Update Advertisement Id = " + advertisementId + ";");
        return advertisementService.update(advertisementId, advertisementDto, newPrice);
    }
}
