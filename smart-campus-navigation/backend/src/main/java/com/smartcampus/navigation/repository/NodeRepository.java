package com.smartcampus.navigation.repository;

import com.smartcampus.navigation.model.Node;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NodeRepository {
    private final JdbcTemplate jdbcTemplate;

    public NodeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Node> findAll() {
        String sql = "SELECT id, name, floor, type FROM nodes ORDER BY name";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Node(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("floor"),
                rs.getString("type")
        ));
    }

    public boolean existsById(String nodeId) {
        String sql = "SELECT COUNT(1) FROM nodes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nodeId);
        return count != null && count > 0;
    }
}
