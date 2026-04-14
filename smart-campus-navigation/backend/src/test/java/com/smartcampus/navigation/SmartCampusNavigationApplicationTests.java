package com.smartcampus.navigation;

import static org.assertj.core.api.Assertions.assertThat;

import com.smartcampus.navigation.model.ShortestPathResponse;
import com.smartcampus.navigation.service.DijkstraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartCampusNavigationApplicationTests {

    @Autowired
    private DijkstraService dijkstraService;

    @Test
    void shortestPathShouldReachLibraryFromGate() {
        ShortestPathResponse response = dijkstraService.calculateShortestPath("GATE", "GF_LIB");

        assertThat(response.getPath()).containsExactly("GATE", "ADMIN", "COLL_MAIN", "GF_MAIN", "GF_LIB");
        assertThat(response.getDirections()).hasSize(4);
        assertThat(response.getTotalWeight()).isEqualTo(6);
    }
}
