DROP TABLE IF EXISTS edges;
DROP TABLE IF EXISTS nodes;

CREATE TABLE nodes (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    floor TEXT NOT NULL,
    type TEXT NOT NULL
);

CREATE TABLE edges (
    from_node TEXT NOT NULL,
    to_node TEXT NOT NULL,
    weight INTEGER NOT NULL,
    direction TEXT NOT NULL,
    hint TEXT,
    FOREIGN KEY (from_node) REFERENCES nodes(id),
    FOREIGN KEY (to_node) REFERENCES nodes(id)
);
