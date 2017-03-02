# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "EMPLOYEE" (
"id" BIGSERIAL NOT NULL PRIMARY KEY,
"name" VARCHAR(254) NOT NULL,
"address" VARCHAR(254) NOT NULL,
"city" VARCHAR(254) NOT NULL,
"county" VARCHAR(254) NOT NULL,
"country" VARCHAR(254) NOT NULL,
"postcode" VARCHAR(10) NOT NULL,
"phone" BIGINT,
"email" VARCHAR(254),
"status" VARCHAR(10));

# --- !Downs

drop table "EMPLOYEE";
