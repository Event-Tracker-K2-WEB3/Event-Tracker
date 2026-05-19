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