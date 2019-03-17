package com.okay.handler;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * Created by OKali on 2019/3/2.
 */
@RestController
@RequestMapping("/swagger-resources")
public class SwaggerHandler {

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;
    @Autowired(required = false)
    private UiConfiguration uiConfiguration;
    private final SwaggerResourcesProvider swaggerResources;
    @Autowired
    public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
        this.swaggerResources = swaggerResources;
    }

    @GetMapping("/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
        return Mono.just(new ResponseEntity<SecurityConfiguration>(
                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder
                .builder().build()), HttpStatus.OK
        ));
    }

    @GetMapping("/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
        return Mono.just(new ResponseEntity<UiConfiguration>(
                Optional.ofNullable(uiConfiguration)
                        .orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK
        ));
    }
    @GetMapping("")
    public Mono<ResponseEntity> swaggerResources() {
        return Mono.just(new ResponseEntity(swaggerResources.get(), HttpStatus.OK));
    }

}
