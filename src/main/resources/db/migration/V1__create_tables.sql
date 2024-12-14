create table if not exists "user" (
    id varchar(36) not null primary key,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    sur_name varchar(100),
    status varchar(20) not null
);

create table if not exists chat (
    id varchar(36) not null primary key,
    user_id varchar(36) not null,
    support_ids varchar(36)[] default '{}',
    constraint fk_user_id foreign key (user_id) references "user" (id)
);

create table if not exists message (
    id varchar(36) not null primary key,
    chat_id varchar(36) not null,
    author_id varchar(36) not null,
    message text not null,
    number_in_chat int not null,
    created_at timestamp not null,
    status varchar(20) not null default 'ACTIVE',
    constraint fk_chat_id foreign key (chat_id) references chat (id),
    constraint fk_author_id foreign key (author_id) references "user" (id)
);