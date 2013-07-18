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
    "text" varchar(200) NULL,
    "category" varchar(80) NULL,
    "level_id" integer NULL REFERENCES "quiz_level" ("_id"),
    "hint" text NULL,
    "explanation" text NULL,
    "is_active" bool NULL,
    "created_dt" datetime NULL,
    "updated_dt" datetime NULL,
    UNIQUE ("text", "level_id")
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

