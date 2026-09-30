package com.example.plantmarket;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTest {
    @Test
    void verifyModuleBoundaries() {
        ApplicationModules.of(PlantMarketApplication.class).verify();
    }

    @Test
    void printModules() {
        System.out.println(ApplicationModules.of(PlantMarketApplication.class));
    }
}
