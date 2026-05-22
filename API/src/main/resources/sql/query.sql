SELECT id, title, location FROM event WHERE location LIKE '%Paris%';

SELECT s.id, s.title
FROM session s
WHERE s.event_id = 'c37c9d8c-5336-45cf-92f3-b85b67799ccf';

SELECT DISTINCT sp.id, sp.name
FROM session_speaker ss
         JOIN session s ON s.id = ss.session_id
         JOIN speaker sp ON sp.id = ss.speaker_id
WHERE s.event_id = 'c37c9d8c-5336-45cf-92f3-b85b67799ccf';