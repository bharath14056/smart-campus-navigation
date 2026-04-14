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
    hint TEXT,
    FOREIGN KEY (from_node) REFERENCES nodes(id),
    FOREIGN KEY (to_node) REFERENCES nodes(id)
);
```

## API Endpoints

### `GET /api/nodes`

Returns all nodes for the dropdowns.

### `POST /api/shortest-path`

Request body:

```json
{
  "start": "GATE",
  "end": "GF_LIB"
}
```

Example response:

```json
{
  "path": ["GATE", "ADMIN", "COLL_MAIN", "GF_MAIN", "GF_LIB"],
  "directions": [
    "Follow the main campus road ahead (Pass the security cabin)",
    "Turn left at the big pillar (Continue past the notice board)",
    "Enter the building and go straight (Use the central glass door)",
    "Turn right, Library entrance is ahead (Look for the blue signboard)"
  ],
  "totalWeight": 6
}
```

## How Dijkstra's Algorithm Is Used

- The backend loads all edge records from SQLite and builds an adjacency list graph.
- A priority queue is used to always expand the current lowest-cost node.
- The algorithm stores previous nodes and previous edges to reconstruct the full path and human-readable directions.

## QR Code Based Navigation

Each campus building can have its own QR code that opens the deployed site with a prefilled start location.

Example:

```text
https://smart-campus-navigation.vercel.app/?start=Library
```

When a user scans a QR code:

1. The website opens automatically.
2. The starting location is selected from the `start` URL parameter.
3. The user selects the destination.
4. The frontend calls the backend shortest path API and shows the route.

### Supported `start` values

The frontend accepts either backend node IDs or user-friendly building names.

Examples:

- `?start=GATE`
- `?start=Gate`
- `?start=ADMIN`
- `?start=Admin`
- `?start=GF_LIB`
- `?start=Library`
- `?start=CAFETERIA`
- `?start=Canteen`

### Example QR Links For Campus Buildings

| Building | URL |
| --- | --- |
| Gate | `https://smart-campus-navigation.vercel.app/?start=Gate` |
| Admin | `https://smart-campus-navigation.vercel.app/?start=Admin` |
| Main Building | `https://smart-campus-navigation.vercel.app/?start=Main%20Building` |
| Lobby | `https://smart-campus-navigation.vercel.app/?start=Lobby` |
| Library | `https://smart-campus-navigation.vercel.app/?start=Library` |
| Lab A | `https://smart-campus-navigation.vercel.app/?start=Lab%20A` |
| Canteen | `https://smart-campus-navigation.vercel.app/?start=Canteen` |
| Auditorium | `https://smart-campus-navigation.vercel.app/?start=Auditorium` |

### How To Generate QR Codes

1. Copy the building URL you want to encode.
2. Open any QR code generator tool such as [QR Code Monkey](https://www.qrcode-monkey.com/) or [QRStuff](https://www.qrstuff.com/).
3. Paste the URL into the generator.
4. Download the QR code image as PNG or SVG.
5. Print the image and place it at the building entrance.

Additional QR examples are listed in [qr-codes/README.md](C:\Users\Bharath.S\Downloads\Smartcampus\smart-campus-navigation\qr-codes\README.md).

## How To Run In VS Code

### 1. Install prerequisites

- Install Java 21 or later.
- Install Maven 3.9 or later.
- Optional: install the VS Code extensions `Extension Pack for Java` and `Live Server`.

### 2. Start the backend

Open a terminal in:

```powershell
cd C:\Users\Bharath.S\Downloads\Smartcampus\smart-campus-navigation\backend
```

Run:

```powershell
mvn spring-boot:run
```

What happens:

- Spring Boot starts on `http://localhost:8080`
- SQLite automatically creates `backend/database/campus.db`
- The schema and sample campus data are loaded on startup

### 3. Start the frontend

Option A: open `frontend/index.html` directly in the browser.

Option B: use VS Code Live Server for a smoother local workflow.

Open a terminal in:

```powershell
cd C:\Users\Bharath.S\Downloads\Smartcampus\smart-campus-navigation\frontend
```

If Python is installed, you can serve it locally with:

```powershell
python -m http.server 5500
```

Then open:

- `http://localhost:5500`

### 4. Use the app

- Choose a starting location
- Choose a destination
- Click `Find Path`
- View the shortest path, visual node list, and step-by-step directions

If the app is opened using a QR code link, the starting location is selected automatically and the user only needs to choose the destination.

## Notes

- The frontend is configured to call the backend at `http://localhost:8080/api`
- CORS is enabled in the Spring Boot backend for local frontend access
- You can expand the campus map by inserting more nodes and edges into SQLite
