const API_BASE_URL = "http://localhost:8080/api";

const startSelect = document.getElementById("start-location");
const endSelect = document.getElementById("end-location");
const pathForm = document.getElementById("path-form");
const findPathButton = document.getElementById("find-path-btn");
const statusCard = document.getElementById("status-card");
const statusText = document.getElementById("status-text");
const emptyState = document.getElementById("empty-state");
const resultContent = document.getElementById("result-content");
const pathText = document.getElementById("path-text");
const pathList = document.getElementById("path-list");
const directionsList = document.getElementById("directions-list");
const totalWeight = document.getElementById("total-weight");
const urlParams = new URLSearchParams(window.location.search);
const requestedStartLocation = urlParams.get("start");

document.addEventListener("DOMContentLoaded", () => {
    loadNodes();
    pathForm.addEventListener("submit", handleFindPath);
});

async function loadNodes() {
    setStatus("Loading campus locations...", "loading");

    try {
        const response = await fetch(`${API_BASE_URL}/nodes`);
        if (!response.ok) {
            throw new Error("Unable to load campus locations.");
        }

        const nodes = await response.json();
        populateSelect(startSelect, nodes);
        populateSelect(endSelect, nodes);
        applyStartLocationFromUrl(nodes);
    } catch (error) {
        setStatus(error.message, "error");
    }
}

function populateSelect(selectElement, nodes) {
    const options = nodes.map((node) => {
        const option = document.createElement("option");
        option.value = node.id;
        option.textContent = `${node.name} (${node.id})`;
        return option;
    });

    options.forEach((option) => selectElement.appendChild(option));
}

function applyStartLocationFromUrl(nodes) {
    if (!requestedStartLocation) {
        setStatus("Campus locations loaded. Choose a route to begin.", "ready");
        return;
    }

    const normalizedStart = normalizeLocationValue(requestedStartLocation);
    const matchedNode = nodes.find((node) => {
        const aliases = [
            node.id,
            node.name,
            getFriendlyLocationName(node.id, node.name)
        ];

        return aliases.some((alias) => normalizeLocationValue(alias) === normalizedStart);
    });

    if (!matchedNode) {
        setStatus(`Campus locations loaded, but "${requestedStartLocation}" is not a valid start location.`, "error");
        return;
    }

    startSelect.value = matchedNode.id;
    setStatus(`Campus locations loaded. Start location preset to ${matchedNode.name}.`, "ready");
}

function normalizeLocationValue(value) {
    return value.trim().toLowerCase().replace(/[\s_-]+/g, "");
}

function getFriendlyLocationName(nodeId, nodeName) {
    const friendlyNameMap = {
        GATE: "Gate",
        ADMIN: "Admin",
        COLL_MAIN: "Main Building",
        GF_MAIN: "Lobby",
        GF_LIB: "Library",
        LAB_A: "Lab A",
        CAFETERIA: "Canteen",
        AUDITORIUM: "Auditorium"
    };

    return friendlyNameMap[nodeId] || nodeName;
}

async function handleFindPath(event) {
    event.preventDefault();

    const start = startSelect.value;
    const end = endSelect.value;

    if (!start || !end) {
        setStatus("Please select both the starting location and destination.", "error");
        return;
    }

    if (start === end) {
        setStatus("Starting location and destination must be different.", "error");
        return;
    }

    findPathButton.disabled = true;
    setStatus("Finding the shortest route...", "loading");

    try {
        const response = await fetch(`${API_BASE_URL}/shortest-path`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ start, end })
        });

        const data = await response.json();
        if (!response.ok) {
            throw new Error(data.error || "Unable to calculate the shortest path.");
        }

        renderResult(data);
        setStatus("Route generated successfully.", "ready");
    } catch (error) {
        hideResult();
        setStatus(error.message, "error");
    } finally {
        findPathButton.disabled = false;
    }
}

function renderResult(result) {
    emptyState.classList.add("hidden");
    resultContent.classList.remove("hidden");
    totalWeight.classList.remove("hidden");

    pathText.textContent = result.path.join(" \u2192 ");
    totalWeight.textContent = `Total distance weight: ${result.totalWeight}`;

    pathList.innerHTML = "";
    result.path.forEach((nodeId, index) => {
        const item = document.createElement("li");
        item.className = "path-item";

        const nodeLabel = document.createElement("span");
        nodeLabel.className = "path-node";
        nodeLabel.textContent = nodeId;
        item.appendChild(nodeLabel);

        if (index < result.path.length - 1) {
            const arrow = document.createElement("span");
            arrow.className = "path-arrow";
            arrow.textContent = "\u279C";
            item.appendChild(arrow);
        }

        pathList.appendChild(item);
    });

    directionsList.innerHTML = "";
    result.directions.forEach((direction, index) => {
        const item = document.createElement("li");
        item.className = "direction-item";

        const icon = document.createElement("span");
        icon.className = "direction-icon";
        icon.textContent = "\u27A4";

        const text = document.createElement("p");
        text.className = "direction-text";
        text.textContent = `${index + 1}. ${direction}`;

        item.appendChild(icon);
        item.appendChild(text);
        directionsList.appendChild(item);
    });
}

function hideResult() {
    resultContent.classList.add("hidden");
    emptyState.classList.remove("hidden");
    totalWeight.classList.add("hidden");
}

function setStatus(message, state) {
    statusText.textContent = message;
    statusCard.classList.remove("ready", "error");

    if (state === "ready") {
        statusCard.classList.add("ready");
    } else if (state === "error") {
        statusCard.classList.add("error");
    }
}
