package org.example.converts;

import org.example.dto.AdvertisementDto;
import org.example.model.Advertisement;

public class AdvConverter {
    public static Advertisement toAdvertisement(AdvertisementDto advertisementDto) {
        return Advertisement.builder()
                .id(advertisementDto.getId())
                .name(advertisementDto.getName())
                .description(advertisementDto.getDescription())
                .price(advertisementDto.getPrice())
                .status(advertisementDto.getStatus())
                .imageFile(advertisementDto.getImageFile())
                .build();
    }

    public static AdvertisementDto toAdvertisementDto(Advertisement advertisement) {
        return AdvertisementDto.builder()
                .id(advertisement.getId())
                .name(advertisement.getName())
                .description(advertisement.getDescription())
                .price(advertisement.getPrice())
                .status(advertisement.getStatus())
                .owner(advertisement.getOwner().getId())
                .imageFile(advertisement.getImageFile())
                .build();
    }
}
