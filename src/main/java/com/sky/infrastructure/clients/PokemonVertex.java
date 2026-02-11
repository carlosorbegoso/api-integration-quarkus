package com.sky.infrastructure.clients;

import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.vertx.mutiny.core.http.HttpClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PokemonVertex {

    @Inject
    Vertx vertx;

    public Uni<String> getPokemon() {
        HttpClient client = vertx.createHttpClient(
                new HttpClientOptions()
                        .setDefaultHost("pokeapi.co")
                        .setDefaultPort(443)
                        .setSsl(true)
        );

        return client
                .request(
                        HttpMethod.GET,
                        "/api/v2/pokemon/rattata"
                )
                .flatMap(req -> req.send())
                .flatMap(resp -> resp.body())
                .map(Buffer::toString);
    }
}
