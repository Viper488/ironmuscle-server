INSERT INTO role VALUES(nextval('role_sequence'), 'USER');
INSERT INTO role VALUES(nextval('role_sequence'), 'EMPLOYEE');
INSERT INTO role VALUES(nextval('role_sequence'), 'ADMIN');

INSERT INTO iron_user VALUES (nextval('user_sequence'), 'admin@admin.com', true, false, '$2a$10$tpA0.LhVCAzZzN9jP90AAuokZOghqi3Pd3.hzTec8mCZKC8MdCaBy', 'admin');
INSERT INTO iron_user_roles VALUES (1, 3);
INSERT INTO confirmation_token VALUES (nextval('token_sequence'), '2021-09-09 19:32:43.223222', '2021-09-09 19:32:43.204219', '2022-09-09 19:47:43.204219', '1a9570cf-9d85-4609-bbe2-057fdf2905e9', 1);

INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Jumping jacks',                    '1b98WrRrmUs');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://i.imgur.com/UJAnRhJ.gif',                                              'Abdominal crunches',               '_YVhhXc2pSY');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Russian twist',                    'l4kQd9eWclE');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Mountain climber',                 '1b98WrRrmUs');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Heel touch',                       '1b98WrRrmUs');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://i.imgur.com/JtgnFH1.gif',                                              'Leg raises',                       'l4kQd9eWclE');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://i.pinimg.com/originals/cf/b5/67/cfb5677a755fe7288b608a4fec6f09a0.gif', 'Plank',                            'y1hXARQhHZM');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Cobra stretch',                    '1b98WrRrmUs');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Spine lumbar twist stretch left',  '1b98WrRrmUs');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Spine lumbar twist stretch right', '1b98WrRrmUs');

INSERT INTO training VALUES (nextval('training_sequence'), 'Abdominal Beginner', 'placeholder', 'Beginner');
INSERT INTO training VALUES (nextval('training_sequence'), 'Abdominal Intermediate', 'placeholder', 'Intermediate');

INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 20,  1, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  2, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 32,  0,  3, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30,  0,  4, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  5, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,  6, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  7, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  8, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  9, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30, 10, 2);

INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 1, 1);
INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 1, 2);

