package org.randomplace.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.randomplace.client.GeoNamesClient;
import org.randomplace.client.OpenMeteoClient;
import org.randomplace.dto.FileUploadForm;
import org.randomplace.dto.GeoNamesResponse;
import org.randomplace.dto.OpenMeteoResponse;
import org.randomplace.entity.RandomPlace;
import org.randomplace.repository.RandomPlaceRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class RandomPlaceService {

    @Inject
    RandomPlaceRepository repository;

    @Inject
    @RestClient
    GeoNamesClient geoNamesClient;

    @Inject
    @RestClient
    OpenMeteoClient openMeteoClient;

    @Transactional
    public RandomPlace save(RandomPlace place) {
        repository.persist(place);
        return place;
    }

    public RandomPlace generateLocation(Long id) {
        RandomPlace place = repository.findById(id);
        if (place == null) {
            throw new NotFoundException("RandomPlace with id " + id + " not found");
        }

        GeoNamesResponse geoResponse = geoNamesClient.getRandom("yes", "1");
        place.latituda = geoResponse.nearest.latt;
        place.longituda = geoResponse.nearest.longt;

        OpenMeteoResponse meteoResponse = openMeteoClient.getElevation(
                place.latituda, place.longituda
        );
        place.nadmorskaVisina = String.valueOf(meteoResponse.elevation);

        return updatePlace(place);
    }

    @Transactional
    public RandomPlace updatePlace(RandomPlace place) {
        RandomPlace managed = repository.findById(place.id);
        managed.latituda = place.latituda;
        managed.longituda = place.longituda;
        managed.nadmorskaVisina = place.nadmorskaVisina;
        return managed;
    }

    @Transactional
    public RandomPlace uploadSlika(Long id, FileUploadForm form) {
        RandomPlace place = repository.findById(id);
        if (place == null) {
            throw new NotFoundException("RandomPlace with id " + id + " not found");
        }

        String uploadDir = "uploads";
        String filePath = uploadDir + "/" + form.filename;

        try {
            if (!Files.exists(Paths.get(uploadDir))) {
                Files.createDirectories(Paths.get(uploadDir));
            }
            try (InputStream is = form.file;
                 FileOutputStream fos = new FileOutputStream(filePath)) {
                is.transferTo(fos);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save file", e);
        }

        place.putanjaDoSlike = filePath;
        return place;
    }

    @Transactional
    public RandomPlace getWithSlika(Long id) {
        RandomPlace place = repository.findById(id);
        if (place == null) {
            throw new NotFoundException("RandomPlace with id " + id + " not found");
        }

        if (place.putanjaDoSlike != null) {
            try {
                place.slika = Files.readAllBytes(Paths.get(place.putanjaDoSlike));
            } catch (IOException e) {
                throw new RuntimeException("Could not read file", e);
            }
        }

        return place;
    }
}