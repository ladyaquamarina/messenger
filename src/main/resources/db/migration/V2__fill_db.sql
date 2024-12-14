insert into "user" (id, first_name, last_name, sur_name, status)
values ('ab0d34ea-7773-4ec7-961b-5e4d543b0928','Иванов','Иван','Иванович','USER'),
       ('1d6663a0-458e-414f-b215-6aa43cafe3a7','Петров','Петр','Петрович','SUPPORT'),
       ('aee0a8a1-6a0f-4b1e-a33c-9313ee164536','Сидоров','Сидор','Сидорович','USER'),
       ('7e2d83d4-05f0-4632-8d12-20b11243bfa8','Тестов','Тест','Тестович','SUPPORT');

insert into chat (id, user_id, support_ids)
values ('b71f3f33-a28e-495b-b1db-ad3921d9e60f','ab0d34ea-7773-4ec7-961b-5e4d543b0928','{1d6663a0-458e-414f-b215-6aa43cafe3a7}'),
       ('425e2a44-f031-4549-820e-6da694b10a4d','aee0a8a1-6a0f-4b1e-a33c-9313ee164536','{}');

insert into message (id, chat_id, author_id, message, number_in_chat, created_at, status)
values ('1528d330-77b2-4f1f-8e6e-cbbd703745b4','b71f3f33-a28e-495b-b1db-ad3921d9e60f','ab0d34ea-7773-4ec7-961b-5e4d543b0928','Здравствуйте! Мне нравится ваше приложение :)',1,now(),'ACTIVE'),
       ('6dcf15ec-3338-44e2-9248-35b458d12efa','b71f3f33-a28e-495b-b1db-ad3921d9e60f','1d6663a0-458e-414f-b215-6aa43cafe3a7','Здравствуйте! Мы очень рады, напишите нам, когда захотите что-нибудь уточнить или если вам понадобится помощь.',2,now(),'ACTIVE'),
       ('577f5340-2566-40e2-9b23-78b6c98e388d','b71f3f33-a28e-495b-b1db-ad3921d9e60f','ab0d34ea-7773-4ec7-961b-5e4d543b0928','Ого, вы отвечаете на сообщения',3,now(),'ACTIVE'),
       ('1a6a2acf-4a67-4df1-b844-49a53bdc9cc7','b71f3f33-a28e-495b-b1db-ad3921d9e60f','1d6663a0-458e-414f-b215-6aa43cafe3a7','Конечно, это наша основная задача. Я могу вам чем-нибудь помочь?',4,now(),'ACTIVE'),
       ('e4dab418-9579-49e6-8237-8ce8aadd45cc','b71f3f33-a28e-495b-b1db-ad3921d9e60f','ab0d34ea-7773-4ec7-961b-5e4d543b0928','Нет, спасибо, хорошего дня)',5,now(),'ACTIVE'),
       ('bc6c8a9d-fa7d-423f-ad74-cc380e73f315','425e2a44-f031-4549-820e-6da694b10a4d','aee0a8a1-6a0f-4b1e-a33c-9313ee164536','Подскажите, как добавить счет',1,now(),'ACTIVE');