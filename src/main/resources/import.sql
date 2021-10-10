INSERT INTO role VALUES(nextval('role_sequence'), 'ADMIN');
INSERT INTO role VALUES(nextval('role_sequence'), 'EMPLOYEE');
INSERT INTO role VALUES(nextval('role_sequence'), 'TRAINER');
INSERT INTO role VALUES(nextval('role_sequence'), 'USER');

INSERT INTO badge VALUES (nextval('badge_sequence'),  50, '', 'Silver');
INSERT INTO badge VALUES (nextval('badge_sequence'), 100, '', 'Gold');
INSERT INTO badge VALUES (nextval('badge_sequence'), 150, '', 'Platinum');
INSERT INTO badge VALUES (nextval('badge_sequence'), 200, '', 'Diamond');
INSERT INTO badge VALUES (nextval('badge_sequence'), 250, '', 'Master');
INSERT INTO badge VALUES (nextval('badge_sequence'), 300, '', 'Champion');
INSERT INTO badge VALUES (nextval('badge_sequence'), 350, '', 'IFBB PRO');

INSERT INTO iron_user VALUES (nextval('user_sequence'), 'admin@admin.com',       true, 'admin',     false, 'admin',     '$2a$10$tpA0.LhVCAzZzN9jP90AAuokZOghqi3Pd3.hzTec8mCZKC8MdCaBy', 0, 'admin');
INSERT INTO iron_user VALUES (nextval('user_sequence'), 'employee@employee.com', true, 'employee',  false, 'employee',  '$2a$10$uFsgfCqAfzywMaq1poI2MOe4VTbTsKVEleYp8qRoHv7xwzfbv5ZtG', 0, 'employee');
INSERT INTO iron_user VALUES (nextval('user_sequence'), 'user@user.com',         true, 'user',      false, 'user',      '$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy', 0, 'user');

INSERT INTO iron_user_roles VALUES (1, 1);
INSERT INTO iron_user_roles VALUES (1, 2);
INSERT INTO iron_user_roles VALUES (2, 2);
INSERT INTO iron_user_roles VALUES (2, 3);
INSERT INTO iron_user_roles VALUES (3, 4);

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
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://i.imgur.com/UJAnRhJ.gif',                                              'Incline push-ups',                 '_YVhhXc2pSY');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Knee push-ups',                    'l4kQd9eWclE');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Push-ups',                         '1b98WrRrmUs');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Wide arm push-ups',                '1b98WrRrmUs');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://i.imgur.com/JtgnFH1.gif',                                              'Box push-ups',                     'l4kQd9eWclE');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://i.pinimg.com/originals/cf/b5/67/cfb5677a755fe7288b608a4fec6f09a0.gif', 'Hindu push-ups',                   'y1hXARQhHZM');
INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif',                   'Chest stretch',                    '1b98WrRrmUs');

INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'User#1',      10, 'custom',    1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'User#2',      10, 'custom',    1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Abdominal',   10, 'crossfit',  1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Arms',        10, 'crossfit',  1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Chest',       10, 'gym',       1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Legs',        10, 'gym',       1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Interval',    10, 'running',   1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Marathon',    10, 'running',   1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Relaxing',    10, 'yoga',      1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  '', 'Stretching',  10, 'yoga',      1);

INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 20,  1, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  2, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 32,  0,  3, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30,  0,  4, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  5, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,  6, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  7, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  2, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 32,  0,  3, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30,  0,  4, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  5, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,  6, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  7, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  8, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  9, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30, 10, 1);

INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 20,   1, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,  11, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12,  0,  12, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  13, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  14, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12,  0,  11, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12,  0,  15, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  14, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  16, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0,  20,  10, 2);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0,  20,  17, 2);

INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 3, 1);
INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 3, 2);

