package com.gpcinternship;

import com.gpcinternship.controller.ApplicationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class GpcInternshipApplicationTests {

    @Autowired
    private ApplicationController applicationController;

    @Test
    void contextLoads() {
        assertThat(applicationController).isNotNull();
    }

}
