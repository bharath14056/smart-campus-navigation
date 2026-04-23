# Smart Campus Navigation System

A complete web application that helps users navigate inside a college campus and find the shortest path between two locations using Dijkstra's Algorithm. The frontend now also supports QR code based navigation, so scanning a building QR code opens the site with the starting location preselected.

## Project Structure

```text
smart-campus-navigation/
├── backend/
│   ├── database/
│   │   ├── .gitkeep
│   │   ├── schema.sql
│   │   └── seed-data.sql
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/smartcampus/navigation/
│       │   │   ├── SmartCampusNavigationApplication.java
│       │   │   ├── config/
│       │   │   ├── controller/
│       │   │   ├── model/
│       │   │   ├── repository/
│       │   │   └── service/
│       │   └── resources/
│       │       ├── application.properties
│       │       ├── data.sql
│       │       └── schema.sql
│       └── test/
└── frontend/
    ├── index.html
    ├── script.js
    └── style.css
```

## Database Schema

### `nodes`

```sql
CREATE TABLE nodes (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    floor TEXT NOT NULL,
    type TEXT NOT NULL
);
```

### `edges`

```sql
CREATE TABLE edges (
    from_node TEXT NOT NULL,
    to_node TEXT NOT NULL,
    weight INTEGER NOT NULL,
    direction TEXT NOT NULL,
