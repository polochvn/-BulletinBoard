package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.converts.AdvConverter;
import org.example.dto.AdvertisementDto;
import org.example.model.Advertisement;
import org.example.model.User;
import org.example.repository.AdvertisementRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    public AdvertisementDto create(AdvertisementDto advertisementDto) {
        Advertisement advertisement = AdvConverter.toAdvertisement(advertisementDto);

        if (!(advertisement == null)) {
            Optional<User> user = userRepository.findById(advertisementDto.getOwner());
            user.ifPresent(advertisement::setOwner);
        return AdvConverter.toAdvertisementDto(advertisementRepository
                .save(advertisement));
        }
        return null;
    }

    public AdvertisementDto update(long advertisementId, AdvertisementDto advertisementDto, Integer newPrice) {
        Optional<User> userOptional = userRepository.findById(advertisementDto.getOwner());

        Optional<Advertisement> advertisementOptional = advertisementRepository.findById(advertisementId);
        Advertisement advertisement = null;
        if (advertisementOptional.isPresent()) {
            advertisement = advertisementOptional.get();
            if (advertisement.getPrice() < newPrice) {
                advertisement.setPrice(newPrice);
                userOptional.ifPresent(advertisement::setOwner);
            } else {
                throw new RuntimeException("Ставка не превышает последнюю!");
            }
        }

        assert advertisement != null;
        return AdvConverter.toAdvertisementDto(advertisementRepository.save(advertisement));
    }

    public AdvertisementDto getAdvertisementById(long advertisementId) {
        Optional<Advertisement> advertisement = advertisementRepository.findById(advertisementId);
        return advertisement.map(AdvConverter::toAdvertisementDto).orElse(null);
    }

    public List<AdvertisementDto> getAllAdvertisements() {
        return advertisementRepository
                .findAll()
                .stream()
                .map(AdvConverter::toAdvertisementDto)
                .collect(Collectors.toList());
    }

    public List<AdvertisementDto> getAllByUser(long userId) {
        Optional<User> user =  userRepository.findById(userId);
        return user.map(value -> advertisementRepository.findAllByOwner(value)
                .stream()
                .map(AdvConverter::toAdvertisementDto)
                .collect(Collectors.toList())).orElse(null);
    }
}
