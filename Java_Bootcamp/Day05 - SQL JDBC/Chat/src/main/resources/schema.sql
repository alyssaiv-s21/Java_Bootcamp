drop schema if exists chat cascade;
create schema chat;

create table chat.user
(
  id bigint primary key generated always as identity,
  login varchar not null,
  password varchar not null
);

create table chat.chatroom
(
  id bigint primary key generated always as identity,
  name varchar not null,
  ownerId bigint,
  constraint fk_chatroom_ownerid_user_id foreign key (ownerId) references chat.user(id)
);

create table chat.message
( id bigint primary key generated always as identity,
  authorId bigint,
  roomId bigint,
  msg text,
  dateTime timestamp without time zone default now(),
  constraint fk_message_autorid_user_id foreign key (authorId) references chat.user(id),
  constraint fk_message_roomId_chatroom_id foreign key (roomId) references chat.chatroom(id)
);