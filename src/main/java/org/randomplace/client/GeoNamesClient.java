package org.randomplace.client;

import org.randomplace.dto.GeoNamesResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "geonames-client")
@Path("/")
public interface GeoNamesClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    GeoNamesResponse getRandom(@QueryParam("randomland") String randomland,
                               @QueryParam("json") String json);
}