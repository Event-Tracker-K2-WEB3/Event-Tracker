--Executena daoly ze commande eto


ALTER TABLE   speaker drop column link;


--- mamafa anze données existant ao anaty base aloha
TRUNCATE TABLE public.speaker RESTART IDENTITY;
---zay vao manao anito alter table ito
ALTER TABLE public.speaker
    ADD COLUMN initials VARCHAR(100) NOT NULL;

----------------------------------------------------------------------------------------------------------------------------

INSERT INTO event (
    id,
    title,
    description,
    start_date,
    end_date,
    location
) VALUES
      (
          'tech-summit-paris-2026',
          'Tech Summit Paris 2026',
          'Le rendez-vous des leaders tech, de l''innovation et des nouvelles tendances numériques.',
          '2026-06-12 09:00:00+03',
          '2026-06-14 18:00:00+03',
          'Paris, France'
      ),
      (
          'ux-design-conference-2026',
          'UX Design Conference',
          'Trois jours pour explorer le futur du design, des interfaces et de l''expérience utilisateur.',
          '2026-08-22 09:00:00+03',
          '2026-08-24 17:00:00+03',
          'Lyon, France'
      ),
      (
          'ai-data-workshop-2026',
          'AI & Data Workshop',
          'Des ateliers pratiques autour de l''intelligence artificielle, de la data et de l''automatisation.',
          '2026-10-10 08:30:00+03',
          '2026-10-11 17:30:00+03',
          'Bordeaux, France'
      ),
      (
          'future-of-work-summit-2026',
          'Future of Work Summit',
          'Un sommet dédié aux nouveaux modes de collaboration, au travail hybride et aux outils numériques.',
          '2026-11-05 09:00:00+03',
          '2026-11-06 18:00:00+03',
          'Lille, France'
      ),
      (
          'cloud-devops-days-2026',
          'Cloud & DevOps Days',
          'Infrastructure cloud, déploiement continu, automatisation et bonnes pratiques DevOps.',
          '2026-11-18 09:00:00+03',
          '2026-11-20 18:00:00+03',
          'Marseille, France'
      ),
      (
          'cyber-security-forum-2026',
          'Cyber Security Forum',
          'Protection des données, sécurité des systèmes et prévention des cybermenaces.',
          '2026-12-03 09:00:00+03',
          '2026-12-04 17:00:00+03',
          'Toulouse, France'
      ),
      (
          'startup-growth-meetup-2027',
          'Startup Growth Meetup',
          'Stratégies de croissance, financement et retours d''expérience d''entrepreneurs.',
          '2027-01-11 09:00:00+03',
          '2027-01-11 18:00:00+03',
          'Nantes, France'
      ),
      (
          'digital-product-expo-2027',
          'Digital Product Expo',
          'Produits digitaux, innovation logicielle et expériences utilisateurs ambitieuses.',
          '2027-02-24 09:00:00+03',
          '2027-02-25 18:00:00+03',
          'Nice, France'
      )
ON CONFLICT (id) DO NOTHING;

-------------------------------------------------------------------------------------------------

ALTER TABLE room DROP COLUMN IF EXISTS capacity;
ALTER TABLE session ADD COLUMN capacity integer;

----------------------------------------------------------------------------------------------------

TRUNCATE TABLE public.session RESTART IDENTITY;
delete from session where id= 1;
select * from room;



-- Session 1 : Associée à "AI & Data Workshop" (ai-data-workshop-2026), Amphi A (2), Speaker 8
INSERT INTO session (title, description, start_time, end_time, type, event_id, room_id, speaker_id, capacity) VALUES
    ('Introduction à l''Intelligence Artificielle', 'Découverte des concepts fondamentaux de l''IA, du Machine Learning et des réseaux de neurones.', '2026-10-10 09:00:00', '2026-10-10 10:30:00', 'Conférence', 'ai-data-workshop-2026', 2, 8, 100);

-- Session 2 : Associée à "AI & Data Workshop" (ai-data-workshop-2026), Amphi A (2), Speaker 8
INSERT INTO session (title, description, start_time, end_time, type, event_id, room_id, speaker_id, capacity) VALUES
    ('Atelier Pratique Next.js 14 & App Router', 'Prenez en main le routage dynamique, le Server-Side Rendering et l''optimisation des composants Front.', '2026-10-10 11:00:00', '2026-10-10 12:30:00', 'Atelier', 'ai-data-workshop-2026', 2, 8, 40);

-- Session 3 : Associée à "Tech Summit Paris 2026" (tech-summit-paris-2026), Amphi A (2), Speaker 1
INSERT INTO session (title, description, start_time, end_time, type, event_id, room_id, speaker_id, capacity) VALUES
    ('Sécuriser ses API Spring Boot', 'Découvrez les meilleures pratiques pour implémenter OAuth2, JWT et contrer les failles OWASP courantes.', '2026-06-12 14:00:00', '2026-06-12 15:30:00', 'Conférence', 'tech-summit-paris-2026', 2, 1, 120);

-- Session 4 : Associée à "Cloud & DevOps Days" (cloud-devops-days-2026), Amphi A (2), Speaker 2
INSERT INTO session (title, description, start_time, end_time, type, event_id, room_id, speaker_id, capacity) VALUES
    ('Le futur du Cloud Computing', 'Table ronde et retours d''expérience sur les architectures Serverless et le multi-cloud en production.', '2026-11-18 16:00:00', '2026-11-18 17:30:00', 'Keynote', 'cloud-devops-days-2026', 2, 2, 200);

-- Session 5 : Associée à "Tech Summit Paris 2026" (tech-summit-paris-2026), Amphi A (2), Speaker 1
INSERT INTO session (title, description, start_time, end_time, type, event_id, room_id, speaker_id, capacity) VALUES
    ('Masterclass : Clean Architecture en Java', 'Session avancée sur le découpage en couches, le domain-driven design et l''écriture de code maintenable.', '2026-06-13 09:30:00', '2026-06-13 11:30:00', 'Atelier', 'tech-summit-paris-2026', 2, 1, 50);