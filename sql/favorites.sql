/*
 Navicat Premium Data Transfer

 Source Server         : localpg
 Source Server Type    : PostgreSQL
 Source Server Version : 140002
 Source Host           : localhost:5432
 Source Catalog        : favorites
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140002
 File Encoding         : 65001

 Date: 12/06/2022 10:17:46
*/


-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS "public"."collect";
CREATE TABLE "public"."collect" (
  "id" int8 NOT NULL,
  "category" varchar(255) COLLATE "pg_catalog"."default",
  "charset" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) NOT NULL,
  "description" text COLLATE "pg_catalog"."default",
  "favorites_id" int8 NOT NULL,
  "is_delete" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "last_modify_time" timestamp(6) NOT NULL,
  "logo_url" varchar(300) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "title" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(600) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for collector_view
-- ----------------------------
DROP TABLE IF EXISTS "public"."collector_view";
CREATE TABLE "public"."collector_view" (
  "id" int8 NOT NULL,
  "counts" int8,
  "user_id" int8
)
;

-- ----------------------------
-- Records of collector_view
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."comment";
CREATE TABLE "public"."comment" (
  "id" int8 NOT NULL,
  "collect_id" int8 NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "reply_user_id" int8,
  "user_id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS "public"."config";
CREATE TABLE "public"."config" (
  "id" int4 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "default_collect_type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "default_favorties" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "default_model" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "last_modify_time" timestamp(6) NOT NULL,
  "user_id" varchar(205) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO "public"."config" VALUES (22, '2022-01-20 17:56:57', 'public', '21', 'simple', '2022-01-20 17:56:57', '20');
INSERT INTO "public"."config" VALUES (25, '2022-01-20 18:02:09', 'public', '24', 'simple', '2022-01-20 18:02:09', '23');
INSERT INTO "public"."config" VALUES (30, '2022-01-21 05:05:14', 'public', '29', 'simple', '2022-01-21 05:05:14', '28');

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS "public"."favorites";
CREATE TABLE "public"."favorites" (
  "id" int4 NOT NULL,
  "count" int8 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "last_modify_time" timestamp(6) NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "public_count" int8 NOT NULL,
  "user_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of favorites
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS "public"."feedback";
CREATE TABLE "public"."feedback" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "feedback_advice" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "feedback_name" varchar(255) COLLATE "pg_catalog"."default",
  "last_modify_time" timestamp(6) NOT NULL,
  "phone" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" int8
)
;

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS "public"."follow";
CREATE TABLE "public"."follow" (
  "id" int8 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "follow_id" int8 NOT NULL,
  "last_modify_time" timestamp(6) NOT NULL,
  "status" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO "public"."follow" VALUES (2, '2022-01-21 05:55:56', 20, '2022-01-21 05:55:56', 'FOLLOW', 23);

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS "public"."hibernate_sequence";
CREATE TABLE "public"."hibernate_sequence" (
  "next_val" int8
)
;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO "public"."hibernate_sequence" VALUES (31);

-- ----------------------------
-- Table structure for letter
-- ----------------------------
DROP TABLE IF EXISTS "public"."letter";
CREATE TABLE "public"."letter" (
  "id" int8 NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "pid" int8,
  "receive_user_id" int8 NOT NULL,
  "send_user_id" int8 NOT NULL,
  "type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of letter
-- ----------------------------
INSERT INTO "public"."letter" VALUES (1, 'sfasfdfs', '2022-01-21 05:14:00', 1, 23, 28, 'REPLY');

-- ----------------------------
-- Table structure for look_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."look_record";
CREATE TABLE "public"."look_record" (
  "id" int8 NOT NULL,
  "collect_id" int8 NOT NULL,
  "create_time" time(6) NOT NULL,
  "last_modify_time" timestamp(6) NOT NULL,
  "user_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of look_record
-- ----------------------------

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS "public"."notice";
CREATE TABLE "public"."notice" (
  "id" int8 NOT NULL,
  "collect_id" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" int8 NOT NULL,
  "oper_id" varchar(255) COLLATE "pg_catalog"."default",
  "readed" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO "public"."notice" VALUES (1, NULL, 1642742039702, '1', 'unread', 'letter', '23');

-- ----------------------------
-- Table structure for praise
-- ----------------------------
DROP TABLE IF EXISTS "public"."praise";
CREATE TABLE "public"."praise" (
  "id" int8 NOT NULL,
  "collect_id" int8 NOT NULL,
  "create_time" int8 NOT NULL,
  "user_id" int8 NOT NULL
)
;

-- ----------------------------
-- Records of praise
-- ----------------------------

-- ----------------------------
-- Table structure for url_library
-- ----------------------------
DROP TABLE IF EXISTS "public"."url_library";
CREATE TABLE "public"."url_library" (
  "id" int8 NOT NULL,
  "count" int4,
  "logo_url" varchar(300) COLLATE "pg_catalog"."default",
  "url" varchar(600) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of url_library
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS "public"."user";
CREATE TABLE "public"."user" (
  "id" int4 NOT NULL,
  "background_picture" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) NOT NULL,
  "email" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "introduction" text COLLATE "pg_catalog"."default",
  "last_modify_time" timestamp(6) NOT NULL,
  "out_date" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "profile_picture" varchar(255) COLLATE "pg_catalog"."default",
  "username" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "validata_code" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO "public"."user" VALUES (20, NULL, '2022-01-20 17:56:57', 'string', NULL, '2022-01-20 17:56:57', NULL, 'd66a26f19f8208393bc2ce2c2099ab92', 'img/favicon.png', 'string', NULL);
INSERT INTO "public"."user" VALUES (23, NULL, '2022-01-20 18:02:09', 'string1', NULL, '2022-01-20 18:02:09', NULL, '0ef07baeb975f471da4375f8282c3acf', 'img/favicon.png', 'string1', NULL);
INSERT INTO "public"."user" VALUES (28, NULL, '2022-01-21 05:05:14', 'ccc', NULL, '2022-01-21 05:05:14', NULL, 'f5398cdcfa468fd1780ca7fefa3a6ba2', 'img/favicon.png', 'aaa', NULL);

-- ----------------------------
-- Table structure for user_is_follow
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_is_follow";
CREATE TABLE "public"."user_is_follow" (
  "id" int8 NOT NULL,
  "is_follow" varchar(255) COLLATE "pg_catalog"."default",
  "profile_picture" varchar(255) COLLATE "pg_catalog"."default",
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "user_name" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of user_is_follow
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table collect
-- ----------------------------
ALTER TABLE "public"."collect" ADD CONSTRAINT "collect_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table collector_view
-- ----------------------------
ALTER TABLE "public"."collector_view" ADD CONSTRAINT "collector_view_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table comment
-- ----------------------------
ALTER TABLE "public"."comment" ADD CONSTRAINT "comment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table config
-- ----------------------------
ALTER TABLE "public"."config" ADD CONSTRAINT "config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table favorites
-- ----------------------------
ALTER TABLE "public"."favorites" ADD CONSTRAINT "favorites_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table feedback
-- ----------------------------
ALTER TABLE "public"."feedback" ADD CONSTRAINT "feedback_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table follow
-- ----------------------------
ALTER TABLE "public"."follow" ADD CONSTRAINT "follow_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table letter
-- ----------------------------
ALTER TABLE "public"."letter" ADD CONSTRAINT "letter_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table look_record
-- ----------------------------
ALTER TABLE "public"."look_record" ADD CONSTRAINT "look_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table notice
-- ----------------------------
ALTER TABLE "public"."notice" ADD CONSTRAINT "notice_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table praise
-- ----------------------------
ALTER TABLE "public"."praise" ADD CONSTRAINT "praise_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table url_library
-- ----------------------------
ALTER TABLE "public"."url_library" ADD CONSTRAINT "url_library_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table user
-- ----------------------------
CREATE UNIQUE INDEX "UK_lqjrcobrh9jc8wpcar64q1bfh" ON "public"."user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "UK_ob8kqyqqgmefl0aco34akdtpe" ON "public"."user" USING btree (
  "email" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table user
-- ----------------------------
ALTER TABLE "public"."user" ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_is_follow
-- ----------------------------
ALTER TABLE "public"."user_is_follow" ADD CONSTRAINT "user_is_follow_pkey" PRIMARY KEY ("id");
