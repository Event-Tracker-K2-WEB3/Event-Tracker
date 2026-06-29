-- ATTENTION : ce script supprime les données existantes.
-- À utiliser seulement pour le seed / test local.

TRUNCATE TABLE
    question_vote,
    session_question,
    session_speaker,
    "session",
    speaker,
    room,
    "event"
    RESTART IDENTITY CASCADE;


-- =========================
-- 1. ROOMS
-- =========================

INSERT INTO room (name)
VALUES
    ('Main Hall'),
    ('Amphitheater A'),
    ('Room B'),
    ('Workshop Lab');


-- =========================
-- 2. EVENTS
-- =========================

INSERT INTO "event" (id, title, description, start_date, end_date, location)
VALUES
    (
        'ai-data-workshop',
        'AI & Data Workshop',
        'A practical workshop about artificial intelligence, machine learning and data-driven innovation.',
        NOW(),
        '2030-12-31T23:59:59Z',
        'Antananarivo'
    ),
    (
        'tech-summit-paris-2026',
        'Tech Summit Paris 2026',
        'A technology conference focused on AI, cloud, web development and digital careers.',
        '2030-01-10T08:00:00Z',
        '2030-01-10T18:00:00Z',
        'Paris'
    );


-- =========================
-- 3. SPEAKERS
-- =========================

INSERT INTO speaker (
    name,
    role,
    specialty,
    company,
    bio,
    photo,
    initials,
    linkedin,
    twitter,
    website,
    day,
    session_type
)
VALUES
    (
        'Alexandre Moreau',
        'Artificial Intelligence Expert',
        'AI',
        'TechVision',
        'Alexandre helps companies integrate AI to optimize their business processes.',
        '/images/speakers/alexandre-moreau.jpg',
        'AM',
        'https://linkedin.com',
        'https://twitter.com',
        'https://example.com',
        'Day 1',
        'Conference'
    ),
    (
        'Sophie Laurent',
        'Cloud Architect',
        'Cloud',
        'CloudPeak',
        'Sophie specializes in hybrid cloud infrastructure and helps organizations migrate, scale and secure their platforms.',
        NULL,
        'SL',
        'https://linkedin.com',
        'https://twitter.com',
        'https://example.com',
        'Day 1',
        'Workshop'
    ),
    (
        'Karim Benali',
        'Cybersecurity Expert',
        'Cybersecurity',
        'SecureNet',
        'Karim protects critical systems and trains teams on modern security best practices.',
        NULL,
        'KB',
        'https://linkedin.com',
        'https://twitter.com',
        'https://example.com',
        'Day 2',
        'Conference'
    ),
    (
        'Nadia Rossi',
        'Data Consultant',
        'Data',
        'DataNova',
        'Nadia helps organizations use their data to make better strategic decisions.',
        NULL,
        'NR',
        'https://linkedin.com',
        'https://twitter.com',
        'https://example.com',
        'Day 1',
        'Panel'
    );


-- =========================
-- 4. SESSIONS
-- =========================

INSERT INTO "session" (
    title,
    description,
    start_time,
    end_time,
    type,
    capacity,
    event_id,
    room_id,
    image
)
VALUES
    (
        'Introduction to Artificial Intelligence',
        'An introduction to the core concepts of AI, machine learning and neural networks.',
        NOW(),
        '2030-12-31T23:59:59Z',
        'Conference',
        100,
        'ai-data-workshop',
        (SELECT id FROM room WHERE name = 'Amphitheater A'),
        '/images/sessions/ai-introduction.jpg'
    ),
    (
        'Hybrid Cloud and Scalability',
        'Best practices for designing scalable, resilient and secure cloud infrastructure.',
        '2030-01-10T10:00:00Z',
        '2030-01-10T11:00:00Z',
        'Workshop',
        80,
        'ai-data-workshop',
        (SELECT id FROM room WHERE name = 'Workshop Lab'),
        '/images/sessions/cloud-hybrid.jpg'
    ),
    (
        'Cybersecurity for Modern Applications',
        'A practical approach to securing web applications, APIs and critical systems.',
        '2030-01-10T11:15:00Z',
        '2030-01-10T12:15:00Z',
        'Conference',
        90,
        'tech-summit-paris-2026',
        (SELECT id FROM room WHERE name = 'Room B'),
        '/images/sessions/cybersecurity.jpg'
    );


-- =========================
-- 5. SESSION - SPEAKER
-- =========================

INSERT INTO session_speaker (session_id, speaker_id)
VALUES
    (
        (SELECT id FROM "session" WHERE title = 'Introduction to Artificial Intelligence'),
        (SELECT id FROM speaker WHERE name = 'Alexandre Moreau')
    ),
    (
        (SELECT id FROM "session" WHERE title = 'Introduction to Artificial Intelligence'),
        (SELECT id FROM speaker WHERE name = 'Nadia Rossi')
    ),
    (
        (SELECT id FROM "session" WHERE title = 'Hybrid Cloud and Scalability'),
        (SELECT id FROM speaker WHERE name = 'Sophie Laurent')
    ),
    (
        (SELECT id FROM "session" WHERE title = 'Cybersecurity for Modern Applications'),
        (SELECT id FROM speaker WHERE name = 'Karim Benali')
    );


-- =========================
-- 6. QUESTIONS
-- =========================

INSERT INTO session_question (
    content,
    author_name,
    created_at,
    upvote_count,
    session_id
)
VALUES
    (
        'What are the main use cases of generative AI today?',
        'Lucas',
        NOW(),
        3,
        (SELECT id FROM "session" WHERE title = 'Introduction to Artificial Intelligence')
    ),
    (
        'How can we ensure the reliability of AI-generated results?',
        'Marie',
        NOW(),
        2,
        (SELECT id FROM "session" WHERE title = 'Introduction to Artificial Intelligence')
    ),
    (
        'Do you think generative AI will replace some jobs in the near future?',
        'Anonymous',
        NOW(),
        0,
        (SELECT id FROM "session" WHERE title = 'Introduction to Artificial Intelligence')
    );


-- =========================
-- 7. QUESTION VOTES
-- =========================

INSERT INTO question_vote (
    created_at,
    visitor_id,
    question_id
)
VALUES
    (
        NOW(),
        'visitor-test-001',
        (SELECT id FROM session_question WHERE content = 'What are the main use cases of generative AI today?')
    ),
    (
        NOW(),
        'visitor-test-002',
        (SELECT id FROM session_question WHERE content = 'What are the main use cases of generative AI today?')
    ),
    (
        NOW(),
        'visitor-test-003',
        (SELECT id FROM session_question WHERE content = 'What are the main use cases of generative AI today?')
    ),
    (
        NOW(),
        'visitor-test-001',
        (SELECT id FROM session_question WHERE content = 'How can we ensure the reliability of AI-generated results?')
    ),
    (
        NOW(),
        'visitor-test-004',
        (SELECT id FROM session_question WHERE content = 'How can we ensure the reliability of AI-generated results?')
    );

ALTER TABLE "event"
    ADD COLUMN IF NOT EXISTS image_url VARCHAR(500);
