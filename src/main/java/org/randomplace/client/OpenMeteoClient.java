package org.randomplace.client;

import org.randomplace.dto.OpenMeteoResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "openmeteo-client")
@Path("/v1/forecast")
public interface OpenMeteoClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    OpenMeteoResponse getElevation(@QueryParam("latitude") String latitude,
                                   @QueryParam("longitude") String longitude);
}