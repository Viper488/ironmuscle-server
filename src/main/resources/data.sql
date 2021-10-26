DROP TABLE IF EXISTS ranking;
CREATE OR REPLACE VIEW ranking AS SELECT ROW_NUMBER () OVER (ORDER BY points DESC) AS rank, (SELECT username FROM iron_user WHERE id = user_id), points FROM point;

CREATE OR REPLACE FUNCTION add_badge() RETURNS trigger AS '
DECLARE
d_badge_id BIGINT;
    d_user_badge BIGINT;
BEGIN
SELECT id INTO d_badge_id FROM badge WHERE goal <= NEW.points ORDER BY goal DESC LIMIT 1;
IF d_badge_id IS NOT NULL THEN
SELECT COUNT(*) INTO d_user_badge FROM user_badges AS ub WHERE ub.badge_id = d_badge_id AND ub.user_id = NEW.user_id;
IF d_user_badge = 0 THEN
            INSERT INTO user_badges(badge_id, user_id) VALUES(d_badge_id, NEW.user_id);
END IF;
END IF;
RETURN NULL;
END;
' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS add_badge_trigger ON point;
CREATE TRIGGER add_badge_trigger
    AFTER UPDATE ON point
    FOR EACH ROW
    EXECUTE PROCEDURE add_badge();
