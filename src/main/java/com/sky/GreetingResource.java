package com.sky;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Path("/examples")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {
    List<String> names = List.of("John", "Paul", "George", "Ringo", "Johnson");

    //Use query param to filter the list names in quarkus
    @GET
    @Path("/names")
    public Uni<Response> listNamesQueryParams(@QueryParam("name") String name) {
        return Uni.createFrom().item(names)
                .onItem().transform(list-> {
                    if(names != null && !names.isEmpty()){
                        list = list.stream()
                                .filter(n -> n.equalsIgnoreCase(name))
                                .toList();
                    }
                    return Response.ok(list).build();
                });

    }

    @GET
    @Path("/names/{name}")
    public Uni<Response>listNamesPathParams(@PathParam("name") String name) {

        return Uni.createFrom().item(names)
                .onItem().transform(list-> {
                    if(names != null && !names.isEmpty()){
                        list = list.stream()
                                .filter(n -> n.equalsIgnoreCase(name))
                                .toList();
                    }
                    return Response.ok(Map.of("names",list)).build();
                });

    }

    @GET
    @Path("/headers")
    public Uni<Response> listNamesHeaders(@HeaderParam("name") String name) {

        return Uni.createFrom().item(names)
                .onItem().transform(list-> {
                    if(names != null && !names.isEmpty()){
                        list = list.stream()
                                .filter(n -> n.equalsIgnoreCase(name))
                                .toList();
                    }
                    return Response.ok(Map.of("names",list)).build();
                });

    }

    @GET
    @Path("/cookie")
    public Uni<Response> listNamesCookies(@CookieParam("name") String name) {
        return Uni.createFrom().item(names)
                .onItem().transform(list-> {
                    if(names != null && !names.isEmpty()){
                        list = list.stream()
                                .filter(n -> n.equalsIgnoreCase(name))
                                .toList();
                    }
                    return Response.ok(Map.of("names",list)).build();
                });
    }

    @GET
    @Path("/beans")
    public Uni<Response> listNamesBeansGroup(@BeanParam NameParams params){
        System.out.println("Name: " + params.name());
        System.out.println("Token: " + params.token());
        return Uni.createFrom().item(names)
                .onItem().transform(list-> {
                    if(names != null && !names.isEmpty()){
                        list = list.stream()
                                .filter(n -> n.equalsIgnoreCase(params.name()))
                                .toList();
                    }
                    return Response.ok(Map.of("names",list)).build();
                });
    }


}
;
