package com.sky.infrastructure.clients;

import io.netty.channel.ConnectTimeoutException;
import io.smallrye.mutiny.TimeoutException;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v2/pokemon")
@RegisterRestClient(configKey = "pokemon-api")
@RegisterClientHeaders(PokemonClientHeaderFactory.class)
public interface ApiPokemon {

    @Retry(
        retryOn = {
                ConnectTimeoutException.class
        },
        maxRetries = 2,
        maxDuration = 300
    )

    @CircuitBreaker(
        requestVolumeThreshold = 10,
            failureRatio = 0.5,
            successThreshold=3,
        failOn = {
                WebApplicationException.class,
                TimeoutException.class,
                ProcessingException.class,
                ConnectTimeoutException.class
        },
        skipOn = {
                WebApplicationException.class,
                IllegalArgumentException.class
        }
    )
    @Fallback(fallbackMethod = "fallbackPokemon")
    @GET
    @Path("/rattata")
    Uni<String> getPokemon();
    default Uni<String> fallbackPokemon(){
        return Uni.createFrom().item("""
                {
                             "name": "unknown",
                             "height": 0,
                             "source": "fallback"
                           }
                """);
    }
}
