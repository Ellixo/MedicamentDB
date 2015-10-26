package com.ellixo.healthcare.api;

import com.wordnik.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
@Api(value = "health", description = "Health Operations", produces = "application/json", consumes = "application/json")
public class HealthController {

    @RequestMapping(method = RequestMethod.GET)
    public String health() {
        return "{\"status\":\"OK\"}";
    }

}