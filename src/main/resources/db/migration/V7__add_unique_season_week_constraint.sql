ALTER TABLE game
ADD CONSTRAINT uq_game_season_week
UNIQUE (season_id, week);