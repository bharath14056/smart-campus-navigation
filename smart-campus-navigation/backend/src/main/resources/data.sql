INSERT INTO nodes (id, name, floor, type) VALUES
('GATE', 'Main Gate', 'Ground', 'Entrance'),
('ADMIN', 'Administrative Block', 'Ground', 'Office'),
('COLL_MAIN', 'College Main Building', 'Ground', 'Academic'),
('GF_MAIN', 'Ground Floor Lobby', 'Ground', 'Hall'),
('GF_LIB', 'Central Library', 'Ground', 'Library'),
('LAB_A', 'Computer Lab A', 'First', 'Laboratory'),
('CAFETERIA', 'Campus Cafeteria', 'Ground', 'Dining'),
('AUDITORIUM', 'Auditorium', 'Ground', 'Event');

INSERT INTO edges (from_node, to_node, weight, direction, hint) VALUES
('GATE', 'ADMIN', 2, 'Follow the main campus road ahead', 'Pass the security cabin'),
('ADMIN', 'GATE', 2, 'Walk back toward the entrance gate', 'Security cabin will be on your right'),
('ADMIN', 'COLL_MAIN', 2, 'Turn left at the big pillar', 'Continue past the notice board'),
('COLL_MAIN', 'ADMIN', 2, 'Walk toward the administration block', 'Big pillar remains on your right'),
('COLL_MAIN', 'GF_MAIN', 1, 'Enter the building and go straight', 'Use the central glass door'),
('GF_MAIN', 'COLL_MAIN', 1, 'Exit through the main lobby doors', 'Head toward the open courtyard'),
('GF_MAIN', 'GF_LIB', 1, 'Turn right, Library entrance is ahead', 'Look for the blue signboard'),
('GF_LIB', 'GF_MAIN', 1, 'Leave the library and return to the lobby', 'The reception desk will be behind you'),
('GF_MAIN', 'LAB_A', 3, 'Take the stairs to the first floor', 'Computer Lab A is opposite the stair landing'),
('LAB_A', 'GF_MAIN', 3, 'Walk down the stairs to the ground floor lobby', 'Follow the floor arrows'),
('COLL_MAIN', 'CAFETERIA', 4, 'Take the side walkway to the cafeteria', 'The food court is beside the garden'),
('CAFETERIA', 'COLL_MAIN', 4, 'Walk back along the garden pathway', 'The main building entrance is straight ahead'),
('ADMIN', 'AUDITORIUM', 5, 'Continue past the admin block toward the auditorium', 'The large dome-shaped roof is visible ahead'),
('AUDITORIUM', 'ADMIN', 5, 'Walk back toward the admin block', 'Stay on the paved central path'),
('CAFETERIA', 'AUDITORIUM', 2, 'Cross the courtyard toward the auditorium', 'You will pass the fountain'),
('AUDITORIUM', 'CAFETERIA', 2, 'Head across the courtyard to the cafeteria', 'The fountain stays to your left');
