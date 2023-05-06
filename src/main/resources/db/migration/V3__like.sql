CREATE OR REPLACE FUNCTION article_like_count_function()
RETURNS TRIGGER
LANGUAGE PLPGSQL
AS
$$
BEGIN
  IF TG_OP = 'UPDATE' THEN
    IF NEW.status = 'LIKE' THEN
UPDATE article SET like_count = like_count + 1 WHERE id = NEW.article_id;
ELSE
UPDATE article SET like_count = like_count - 1 WHERE id = NEW.article_id;
END IF;
END IF;
RETURN NEW;
END;
$$;

CREATE TRIGGER article_like_count_trigger
    BEFORE INSERT OR UPDATE
                         ON article_like
                         FOR EACH ROW
                         EXECUTE PROCEDURE article_like_count_function();
