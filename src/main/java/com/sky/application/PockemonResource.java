package com.sky.application;

import com.sky.infrastructure.clients.ApiPokemon;
import com.sky.infrastructure.clients.PokemonVertex;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Map;

@Path("/pokems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PockemonResource {

    @RestClient
    ApiPokemon pokemonApi;
    
    @Inject
    PokemonVertex pokemonClient;

    @GET
    @Path("/rattata")
   public Uni<Response> getPokemon() {

        // Gets, transforms, and returns filtered Pokemon data
        return pokemonApi.getPokemon()
                .onItem().transform(
                        data -> {
                            JsonObject json = new JsonObject(data);
                            Map<String, Object> filtered = Map.of(
                                    "name", json.getString("name"),
                                    "height", json.getInteger("height")
                            );
                            return Response.ok(filtered).build();
                        }
                );
    }

    @GET
    @Path("web")
    public Uni<Response> getPokemonWebClient() {

        return pokemonClient.getPokemon()
                .onItem().transform(
                        data -> {
                            JsonObject json = new JsonObject(data);

                            Map<String, Object> filtered = Map.of(
                                    "name", json.getString("name"),
                                    "height", json.getInteger("height")
                            );
                            return Response.ok(filtered).build();
                        }
                );
    }


}
