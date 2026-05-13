--Executena daoly ze commande eto


ALTER TABLE   speaker drop column link;


--- mamafa anze données existant ao anaty base aloha
TRUNCATE TABLE public.speaker RESTART IDENTITY;
---zay vao manao anito alter table ito
ALTER TABLE public.speaker
    ADD COLUMN initials VARCHAR(100) NOT NULL;