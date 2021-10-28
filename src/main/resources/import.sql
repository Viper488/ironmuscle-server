INSERT INTO role VALUES(nextval('role_sequence'), 'ADMIN');
INSERT INTO role VALUES(nextval('role_sequence'), 'EMPLOYEE');
INSERT INTO role VALUES(nextval('role_sequence'), 'TRAINER');
INSERT INTO role VALUES(nextval('role_sequence'), 'USER');

INSERT INTO badge VALUES (nextval('badge_sequence'),  50, 'https://www.unrankedsmurfs.com/storage/YMoeDoxu3dKunABYwywDC05hf11tbWr6NQScWoWS.png', 'Silver'      );
INSERT INTO badge VALUES (nextval('badge_sequence'), 100, 'https://static.wikia.nocookie.net/leagueoflegends/images/9/96/Season_2019_-_Gold_1.png/revision/latest/scale-to-width-down/250?cb=20181229234920', 'Gold'        );
INSERT INTO badge VALUES (nextval('badge_sequence'), 150, 'https://static.wikia.nocookie.net/leagueoflegends/images/7/74/Season_2019_-_Platinum_1.png/revision/latest/scale-to-width-down/250?cb=20181229234932', 'Platinum'    );
INSERT INTO badge VALUES (nextval('badge_sequence'), 200, 'https://static.wikia.nocookie.net/leagueoflegends/images/9/91/Season_2019_-_Diamond_1.png/revision/latest/scale-to-width-down/250?cb=20181229234917', 'Diamond'     );
INSERT INTO badge VALUES (nextval('badge_sequence'), 250, 'https://www.unrankedsmurfs.com/storage/89fkTw6o2M5j8KQHL15wj46DGP2lETLKcM1j55Ci.png', 'Master'      );
INSERT INTO badge VALUES (nextval('badge_sequence'), 300, 'https://www.unrankedsmurfs.com/storage/kt7etpJ3zhnKrZCrhf2DNKvzpbIeiSBSTKSWMhaI.png', 'Grandmaster'    );
INSERT INTO badge VALUES (nextval('badge_sequence'), 350, 'https://www.unrankedsmurfs.com/storage/EULjmP1Hm1EXNZ6h75jBDcwWiLUXKDirJZS0XXej.png', 'Champion'    );

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

INSERT INTO exercise VALUES(nextval('exercise_sequence'), 'https://thumbs.gfycat.com/BlaringTornBelugawhale-small.gif','Jumping jacks',     '1b98WrRrmUs');--1
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Abdominal crunches',  '');--2
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Russian twist',  '');--3
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Mountain climber',  '');--4
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Heel touch',  '');--5
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Leg raises',  '');--6
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Plank',  '');--7
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Cobra stretch',  '');--8
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Spine lumbar twist stretch left',  '');--9
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Spine lumbar twist stretch right',  '');--10
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Crossover crunch',  '');--11
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Side bridges left',  '');--12
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Side bridges right',  '');--13
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Butt bridge',  '');--14
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','V-up',  '');--15
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Bicycle crunches',  '');--16
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Push-up & rotation',  '');--17
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Side plank left',  '');--18
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Side plank right',  '');--19
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Sit ups',  '');--20

INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Incline push-ups',  'cfns5VDVVvk');--21
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Knee push-ups',  '');--22
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Push-ups',  '');--23
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Wide arm push-ups',  '');--24
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Box push-ups',  '');--25
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Hindu push ups',  '');--26
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Cobra stretch',  '');--27
INSERT INTO exercise VALUES(nextval('exercise_sequence'), '','Chest stretch',  '');--28

---------------------------------------------------- ID -------------------REPS-TIME-EXID-TRID





INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner', 'https://www.bodybuilding.com/images/2018/april/the-best-ab-workout-for-a-six-pack-header-960x540.jpg', 'Abdominal' , 10, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner', 'https://presscable.com/files/uploaded_images/00120956f91c447c8988dbcab577a444.jpg', 'Chest'     , 10, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner', 'https://lepszytrener.pl/media/cache/thumb_blog_list3/articles/images/2672-article-1.png', 'Arms'      , 10, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner', 'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/bring-it-all-the-way-up-here-royalty-free-image-1625750638.jpg?crop=0.601xw:0.946xh;0.397xw,0.0103xh&resize=640:*', 'Legs'      , 10, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Beginner', 'https://www.aleanlife.com/wp-content/uploads/2014/03/back-workout-1200x720.jpg', 'Back '     , 10, 'standard');

INSERT INTO training VALUES (nextval('training_sequence'), 'Mediocre', 'https://cdn1.coachmag.co.uk/sites/coachmag/files/2016/06/obliques-core-workout-1-decline-plank-foot-touch.jpg', 'Abdominal' , 20, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Mediocre', 'https://www.bodybuilding.com/images/2016/december/brandan-fokkens-best-chest-workout-header-v2-DYMATIZE-960x540.jpg', 'Chest'     , 20, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Mediocre', 'https://passionsport.pl/wp-content/uploads/2016/04/Fotolia_1000x77-min.jpg', 'Arms'      , 20, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Mediocre', 'https://www.muscleandfitness.com/wp-content/uploads/2019/06/2-walking-lunge-1109.jpg?w=1109&h=614&crop=1&quality=86&strip=all', 'Legs'      , 20, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Mediocre', 'http://upl.stack.com/wp-content/uploads/2017/07/17010920/Strong-Back-STACK-654x368.jpg', 'Back '     , 20, 'standard');

INSERT INTO training VALUES (nextval('training_sequence'), 'Pro', 'https://ifbb-academy.com/wp-content/uploads/2020/04/abdominales-img-3.jpg', 'Abdominal' , 30, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Pro', 'https://cdn.shopify.com/s/files/1/1901/6815/articles/image_41.png?v=1538465967', 'Chest'     , 30, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Pro', 'https://www.bestbody.com.pl/blog/wp-content/uploads/2019/12/cwiczenia-na-biceps-2.jpg', 'Arms'      , 30, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Pro', 'https://www.bodybuilding.com/images/2016/june/5-leg-workouts-for-mass-header-v2-960x540.jpg', 'Legs'      , 30, 'standard');
INSERT INTO training VALUES (nextval('training_sequence'), 'Pro', 'https://www.muscleandfitness.com/wp-content/uploads/2017/10/1109-pullup-muscular-back.jpg?w=1109&quality=86&strip=all', 'Back '     , 30, 'standard');

--ABDOMINAL BEGINNER
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 20, 1, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16, 0, 2, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 3, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16, 0, 4, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 5, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16, 0, 6, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 20, 7, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12, 0, 2, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12, 0, 4, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 32, 0, 3, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 7, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 5, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 14, 0, 6, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 8, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 9, 1);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 10, 1);

--ABDOMINAL MEDIOCRE
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 20, 1, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 26, 0, 5, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 11, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 4, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12, 0, 12, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 12, 0, 13, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 14, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 16, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 15, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 26, 0, 5, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 2, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 7, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 11, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16, 0, 6, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 16, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 17, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 20, 19, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 20, 18, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 8, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 9, 6);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 10, 6);

--ABDOMINAL PRO
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 1, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 20, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 12, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 20, 0, 13, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30, 0, 2, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 24, 0, 16, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 20, 18, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 20, 19, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 18, 0, 15, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 24, 0, 17, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 48, 0, 3, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 28, 0, 2, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30, 0, 14, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 34, 0, 5, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 30, 0, 4, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 24, 0, 11, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 16, 0, 15, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 60, 7, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 8, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 9, 11);
INSERT INTO training_exercise VALUES(nextval('training_exercise_sequence'), 0, 30, 10, 11);

INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 3, 10);
--INSERT INTO user_trainings VALUES (nextval('user_trainings_sequence'), 3, 2);

