package com.sky.infrastructure.clients;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;
@ApplicationScoped
public class Confi {
    @ConfigProperty(name = "application.lynx-component.credicard-card-header")
    public Map<String, String> cardHeader;
}
