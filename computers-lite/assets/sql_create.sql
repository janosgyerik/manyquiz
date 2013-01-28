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
INSERT INTO "quiz_level" VALUES(1,'Hobbyist/Professional',1,'2013-01-27 00:42:27','2013-01-27 00:42:27');
INSERT INTO "quiz_level" VALUES(2,'Expert',2,'2013-01-27 00:42:43','2013-01-27 00:42:43');
INSERT INTO "quiz_level" VALUES(3,'Nightmare!',5,'2013-01-27 00:42:51','2013-01-27 00:42:51');
INSERT INTO "quiz_question" VALUES(4,'Which is not an Operating System?','software',1,'','Microsoft Office XP is a suite of desktop applications, including text editor, spreadsheet editor, etc, NOT an operating system.',1,'2013-01-26 15:19:21','2013-01-26 15:19:21');
INSERT INTO "quiz_question" VALUES(5,'How many bytes is one kilobyte?','programming',1,'','Although kilobyte is formally equal to 1000 bytes, a second definition and usage of 1024 bytes has historically been in practice in the field of computer science and IT.',1,'2013-01-26 15:19:59','2013-01-26 15:19:59');
INSERT INTO "quiz_question" VALUES(6,'What type of data structure is a queue?','programming',1,'','First In First Out. Picture a queue of people -- the first person (data item) in the queue is the first to leave the queue at the front. Additional people (data items) join at the back of the queue.',1,'2013-01-26 15:24:20','2013-01-26 15:24:20');
INSERT INTO "quiz_answer" VALUES(13,6,'FIFO',1,1,'2013-01-26 15:26:59','2013-01-26 15:26:59');
INSERT INTO "quiz_answer" VALUES(14,6,'LIFO',0,1,'2013-01-26 15:27:26','2013-01-26 15:27:26');
INSERT INTO "quiz_answer" VALUES(15,6,'FILO',0,1,'2013-01-26 15:27:38','2013-01-26 15:27:38');
INSERT INTO "quiz_answer" VALUES(16,6,'LILO',0,1,'2013-01-26 15:28:07','2013-01-26 15:28:07');
INSERT INTO "quiz_answer" VALUES(17,5,'1000 bytes',0,1,'2013-01-26 15:28:21','2013-01-26 15:28:21');
INSERT INTO "quiz_answer" VALUES(18,5,'100 bytes',0,1,'2013-01-26 15:29:10','2013-01-26 15:29:10');
INSERT INTO "quiz_answer" VALUES(19,5,'1024 bytes',1,1,'2013-01-26 15:29:21','2013-01-26 15:29:21');
INSERT INTO "quiz_answer" VALUES(20,5,'1023 bytes',0,1,'2013-01-26 15:29:32','2013-01-26 15:29:32');
INSERT INTO "quiz_answer" VALUES(21,4,'HP-UX',0,1,'2013-01-26 15:29:40','2013-01-26 15:29:40');
INSERT INTO "quiz_answer" VALUES(22,4,'Windows 98',0,1,'2013-01-26 15:29:55','2013-01-26 15:29:55');
INSERT INTO "quiz_answer" VALUES(23,4,'Microsoft Office XP',1,1,'2013-01-26 15:30:03','2013-01-26 15:30:03');
INSERT INTO "quiz_answer" VALUES(24,4,'RedHat Linux',0,1,'2013-01-26 15:30:17','2013-01-26 15:30:17');
