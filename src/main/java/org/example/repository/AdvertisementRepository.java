package org.example.repository;

import org.example.model.Advertisement;
import org.example.model.Status;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findAllByStatus(Status status);

    List<Advertisement> findAllByOwner(User user);
}
