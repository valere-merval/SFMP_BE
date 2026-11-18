package com.smartfan.mobility;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ApplicationModulesTest {

    @Test
    void modulesAreWellStructured() {
        ApplicationModules.of(SfmpBeApplication.class).verify();
    }
}
