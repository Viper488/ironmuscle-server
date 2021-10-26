INSERT INTO role VALUES(nextval('role_sequence'), 'ADMIN');
INSERT INTO role VALUES(nextval('role_sequence'), 'EMPLOYEE');
INSERT INTO role VALUES(nextval('role_sequence'), 'TRAINER');
INSERT INTO role VALUES(nextval('role_sequence'), 'USER');

INSERT INTO badge VALUES (nextval('badge_sequence'),  50, '', 'Silver'      );
INSERT INTO badge VALUES (nextval('badge_sequence'), 100, '', 'Gold'        );
INSERT INTO badge VALUES (nextval('badge_sequence'), 150, '', 'Platinum'    );
INSERT INTO badge VALUES (nextval('badge_sequence'), 200, '', 'Diamond'     );
INSERT INTO badge VALUES (nextval('badge_sequence'), 250, '', 'Master'      );
INSERT INTO badge VALUES (nextval('badge_sequence'), 300, '', 'Champion'    );
INSERT INTO badge VALUES (nextval('badge_sequence'), 350, '', 'IFBB PRO'    );

INSERT INTO iron_user VALUES (nextval('user_sequence'), 'admin@admin.com',       true, 'admin',     false, 'admin',     '$2a$10$tpA0.LhVCAzZzN9jP90AAuokZOghqi3Pd3.hzTec8mCZKC8MdCaBy', 'admin');
INSERT INTO iron_user VALUES (nextval('user_sequence'), 'employee@employee.com', true, 'employee',  false, 'employee',  '$2a$10$uFsgfCqAfzywMaq1poI2MOe4VTbTsKVEleYp8qRoHv7xwzfbv5ZtG', 'employee');
INSERT INTO iron_user VALUES (nextval('user_sequence'), 'user@user.com',         true, 'user',      false, 'user',      '$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy', 'user');

INSERT INTO iron_user_roles VALUES (1, 1);
INSERT INTO iron_user_roles VALUES (1, 2);
INSERT INTO iron_user_roles VALUES (2, 2);
INSERT INTO iron_user_roles VALUES (2, 3);
INSERT INTO iron_user_roles VALUES (3, 4);

INSERT INTO point VALUES (nextval('point_sequence'), 0, 1);
INSERT INTO point VALUES (nextval('point_sequence'), 0, 2);
INSERT INTO point VALUES (nextval('point_sequence'), 0, 3);

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

INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://static01.nyt.com/images/2019/10/20/world/18running-print/12marathon-sub-videoSixteenByNineJumbo1600.jpg', 'User#1',      10, 'custom',    1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://static01.nyt.com/images/2019/10/20/world/18running-print/12marathon-sub-videoSixteenByNineJumbo1600.jpg', 'User#2',      10, 'custom',    1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://ifbb-academy.com/wp-content/uploads/2020/04/abdominales-img-3.jpg', 'Abdominal',   10, 'crossfit',  1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://static01.nyt.com/images/2019/10/20/world/18running-print/12marathon-sub-videoSixteenByNineJumbo1600.jpg', 'Arms',        10, 'crossfit',  1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Mediocre',  'https://kompaktowytrening.pl/wp-content/uploads/2020/04/dwuboj-olimpijski-robto-wdomu.jpg', 'Short',        10, 'gym',       1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/bench-press-spot-1571829590.jpg?crop=1.00xw:0.752xh;0,0.0577xh&resize=1200:*', 'Chest',       10, 'gym',       1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Mediocre',  'https://kompaktowytrening.pl/wp-content/uploads/2020/04/dwuboj-olimpijski-robto-wdomu.jpg', 'Legs',        10, 'gym',       1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://static01.nyt.com/images/2019/10/20/world/18running-print/12marathon-sub-videoSixteenByNineJumbo1600.jpg', 'Interval',    10, 'running',   1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://static01.nyt.com/images/2019/10/20/world/18running-print/12marathon-sub-videoSixteenByNineJumbo1600.jpg', 'Marathon',    10, 'running',   1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner',  'https://i.cdn.newsbytesapp.com/images/l83920210625003952.jpeg', 'Relaxing',    10, 'yoga',      1);
INSERT INTO training VALUES (nextval('training_sequence'), 'Pro',  'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlCfg0uRGEkGneh0IXlSQJ1WeEey4v9YahWA&usqp=CAU', 'Stretching',  10, 'yoga',      1);

INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 20,  1, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  2, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 32,  0,  3, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30,  0,  4, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  5, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,  6, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  7, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  2, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 32,  0,  3, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30,  0,  4, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20,  0,  5, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,  6, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  7, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  8, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30,  9, 5);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 30, 10, 5);

INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 20,   1, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,  11, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12,  0,  12, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  13, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  14, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12,  0,  11, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12,  0,  15, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  14, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 10,  0,  16, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0,  20,  10, 3);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0,  20,  17, 3);

INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'),  0, 20,   1, 4);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16,  0,   3, 4);

INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 3, 1);
--INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 3, 2);

