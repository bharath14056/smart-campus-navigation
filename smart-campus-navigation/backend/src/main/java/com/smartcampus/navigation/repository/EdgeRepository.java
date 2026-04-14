package com.smartcampus.navigation.repository;

import com.smartcampus.navigation.model.Edge;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EdgeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EdgeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Edge> findAll() {
        String sql = "SELECT from_node, to_node, weight, direction, hint FROM edges";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Edge(
                rs.getString("from_node"),
                rs.getString("to_node"),
                rs.getInt("weight"),
                rs.getString("direction"),
                rs.getString("hint")
        ));
    }
}
