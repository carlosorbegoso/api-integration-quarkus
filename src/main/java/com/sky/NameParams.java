package com.sky;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

public record NameParams(
        @QueryParam("name") String name,
        @HeaderParam("X-Token") String token
        ){}
