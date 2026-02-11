package com.sky.infrastructure.clients;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class PokemonClientHeaderFactory implements ClientHeadersFactory {
    @Inject
    Confi confi;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
                                                 MultivaluedMap<String, String> clientOutgoingHeaders) {
        //customizado gene
        clientOutgoingHeaders.add("customDoc", "dsaf");
        //config del properties
        Map<String, String> cardHeader = new HashMap<>(confi.cardHeader);
        //config customizados para el cardHeader
        cardHeader.put("X-Token", "123456789");
        cardHeader.put("nobody", "asfas");

        String cardHeaderBase64 = Base64.getEncoder().encodeToString(cardHeader.toString().getBytes());

        clientOutgoingHeaders.add("Analyze",  cardHeaderBase64);

        return clientOutgoingHeaders;
    }
}
