CREATE TABLE "quiz_level" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "name" varchar(80) NULL UNIQUE,
    "level" integer NULL UNIQUE,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "quiz_question" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "text" varchar(200) NULL UNIQUE,
    "category" varchar(80) NULL,
    "level_id" integer NULL REFERENCES "quiz_level" ("_id"),
    "hint" text NULL,
    "explanation" text NULL,
    "is_active" bool NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL
)
;
CREATE TABLE "quiz_answer" (
    "_id" integer NULL PRIMARY KEY AUTOINCREMENT,
    "question_id" integer NULL REFERENCES "quiz_question" ("_id"),
    "text" varchar(200) NULL,
    "is_correct" bool NULL,
    "is_active" bool NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("question_id", "text")
)
;
INSERT INTO "quiz_level" VALUES(1,'Easy',1,'2013-01-27 00:39:06','2013-01-27 00:39:06');
INSERT INTO "quiz_question" VALUES(1,'Which movie was NOT directed by Christopher Nolan?','stats',1,'','',1,'2013-01-26 15:05:22','2013-01-26 15:05:22');
INSERT INTO "quiz_question" VALUES(2,'What color was the Mazda MX5 Joe was driving in the movie Looper?','trivia',1,'','',1,'2013-01-26 15:14:19','2013-01-26 15:14:19');
INSERT INTO "quiz_question" VALUES(3,'From which movie is the quote: "Time travel has not yet been invented, but thirty years from now it will have been."','quotes',1,'','',1,'2013-01-26 15:15:30','2013-01-26 15:15:30');
INSERT INTO "quiz_answer" VALUES(1,1,'Inception',0,1,'2013-01-26 15:08:18','2013-01-26 15:08:18');
INSERT INTO "quiz_answer" VALUES(2,1,'The Dark Knight',0,1,'2013-01-26 15:09:01','2013-01-26 15:09:01');
INSERT INTO "quiz_answer" VALUES(3,1,'Looper',1,1,'2013-01-26 15:09:43','2013-01-26 15:09:43');
INSERT INTO "quiz_answer" VALUES(4,1,'Batman Begins',0,1,'2013-01-26 15:09:52','2013-01-26 15:09:52');
INSERT INTO "quiz_answer" VALUES(5,2,'Red',1,1,'2013-01-26 15:17:03','2013-01-26 15:17:03');
INSERT INTO "quiz_answer" VALUES(6,2,'Blue',0,1,'2013-01-26 15:17:18','2013-01-26 15:17:18');
INSERT INTO "quiz_answer" VALUES(7,2,'Black',0,1,'2013-01-26 15:17:30','2013-01-26 15:17:30');
INSERT INTO "quiz_answer" VALUES(8,2,'Silver',0,1,'2013-01-26 15:17:39','2013-01-26 15:17:39');
INSERT INTO "quiz_answer" VALUES(9,3,'Back to the future',0,1,'2013-01-26 15:17:50','2013-01-26 15:17:50');
INSERT INTO "quiz_answer" VALUES(10,3,'Back to the future 2',0,1,'2013-01-26 15:18:09','2013-01-26 15:18:09');
INSERT INTO "quiz_answer" VALUES(11,3,'Looper',1,1,'2013-01-26 15:18:24','2013-01-26 15:18:24');
INSERT INTO "quiz_answer" VALUES(12,3,'The time traveler''s wife',0,1,'2013-01-26 15:18:37','2013-01-26 15:18:37');
