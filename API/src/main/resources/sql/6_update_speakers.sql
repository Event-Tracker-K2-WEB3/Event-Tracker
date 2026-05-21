CREATE TABLE IF NOT EXISTS session_speaker (
                                               session_id INTEGER NOT NULL,
                                               speaker_id INTEGER NOT NULL,
                                               PRIMARY KEY (session_id, speaker_id),
                                               CONSTRAINT fk_session_speaker_session
                                                   FOREIGN KEY (session_id)
                                                       REFERENCES session(id)
                                                       ON DELETE CASCADE,
                                               CONSTRAINT fk_session_speaker_speaker
                                                   FOREIGN KEY (speaker_id)
                                                       REFERENCES speaker(id)
                                                       ON DELETE CASCADE
);

INSERT INTO session_speaker (session_id, speaker_id)
VALUES (2, 1)
ON CONFLICT DO NOTHING;

SELECT *
FROM speaker
WHERE id = 1;

SELECT *
FROM session
WHERE id = 2;

SELECT
    ss.session_id,
    ss.speaker_id,
    sp.name AS speaker_name,
    s.title AS session_title,
    s.event_id,
    s.room_id,
    e.title AS event_title,
    r.name AS room_name
FROM session_speaker ss
         JOIN speaker sp ON sp.id = ss.speaker_id
         JOIN session s ON s.id = ss.session_id
         LEFT JOIN event e ON e.id = s.event_id
         LEFT JOIN room r ON r.id = s.room_id
WHERE ss.speaker_id = 1;

UPDATE speaker
SET photo = '/images/speakers/alexandre-moreau.jpg'
WHERE id = 1;