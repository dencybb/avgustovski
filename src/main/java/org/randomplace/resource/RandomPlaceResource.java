package org.randomplace.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.randomplace.dto.FileUploadForm;
import org.randomplace.entity.RandomPlace;
import org.randomplace.service.RandomPlaceService;

@Path("/random-place")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RandomPlaceResource {

    @Inject
    RandomPlaceService service;

    @POST
    @Operation(summary = "Dodavanje novog RandomPlace objekta")
    public RandomPlace save(RandomPlace place) {
        return service.save(place);
    }

    @GET
    @Path("/{id}/generate-location")
    @Operation(summary = "Generisanje lokacije i nadmorske visine")
    public RandomPlace generateLocation(@PathParam("id") Long id) {
        return service.generateLocation(id);
    }

    @POST
    @Path("/{id}/upload-slika")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Operation(summary = "Upload slike za RandomPlace")
    public RandomPlace uploadSlika(@PathParam("id") Long id,
                                   @MultipartForm FileUploadForm form) {
        return service.uploadSlika(id, form);
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Dohvatanje RandomPlace objekta sa slikom")
    public RandomPlace getWithSlika(@PathParam("id") Long id) {
        return service.getWithSlika(id);
    }
}