CREATE OR REPLACE FUNCTION initialize_user() RETURNS trigger AS '
    BEGIN
        INSERT INTO iron_user_roles VALUES(NEW.id, 4);
        INSERT INTO point VALUES (nextval(''point_sequence''), 0, NEW.id);
        RETURN NULL;
    END;
' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS initialize_user_trigger ON iron_user;
CREATE TRIGGER initialize_user_trigger
    AFTER INSERT ON iron_user
    FOR EACH ROW
    EXECUTE PROCEDURE initialize_user();

CREATE OR REPLACE FUNCTION add_badge() RETURNS trigger AS '
DECLARE
d_badge_id BIGINT;
    d_user_badge BIGINT;
BEGIN
SELECT id INTO d_badge_id FROM badge WHERE goal <= NEW.points ORDER BY goal DESC LIMIT 1;
IF d_badge_id IS NOT NULL THEN
SELECT COUNT(*) INTO d_user_badge FROM user_badges AS ub WHERE ub.badge_id = d_badge_id AND ub.user_id = NEW.user_id;
IF d_user_badge = 0 THEN
            INSERT INTO user_badges(badge_id, user_id) VALUES(d_badge_id, NEW.user_id);
END IF;
END IF;
RETURN NULL;
END;
' LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS add_badge_trigger ON point;
CREATE TRIGGER add_badge_trigger
    AFTER UPDATE ON point
    FOR EACH ROW
    EXECUTE PROCEDURE add_badge();

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

INSERT INTO iron_user VALUES(nextval('user_sequence'), 'admin@admin.com',       true, false, '$2a$10$tpA0.LhVCAzZzN9jP90AAuokZOghqi3Pd3.hzTec8mCZKC8MdCaBy', 'admin');
INSERT INTO iron_user VALUES(nextval('user_sequence'), 'employee@employee.com', true, false, '$2a$10$uFsgfCqAfzywMaq1poI2MOe4VTbTsKVEleYp8qRoHv7xwzfbv5ZtG', 'employee');
INSERT INTO iron_user VALUES(nextval('user_sequence'), 'user@user.com',         true, false, '$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy', 'user');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Chris_Page 7101@gompie.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Page ');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Evie_Coates3745@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Coates');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Alison_Thomson3174@liret.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Thomson');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Eden_Allen7594@womeona.net',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Allen');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Charlize_Asher8497@womeona.net',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Asher');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Marilyn_Hogg9622@bungar.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Hogg');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Jacob_Groves4464@typill.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Groves');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Victoria_Shelton2495@typill.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Shelton');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Nick_Thomson204@fuliss.net',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Thoms1on');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Clarissa_Raven5831@bretoux.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Raven');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Maxwell_Irving8772@ubusive.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Irving');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Goldie_Harris1886@ubusive.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Harris');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Livia_Dowson1211@gembat.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Dowson');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Camellia_Owens3884@dionrab.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Owens');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Tom_Larsen3275@ovock.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Larsen');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Logan_Jeffery5465@liret.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Jeffery');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Enoch_Nicolas8946@bauros.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Nicolas');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Michaela_Morris103@bungar.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Morris');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Henry_Seymour8508@sveldo.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Seymour');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Ramon_Archer1707@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Archer');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Phillip_Farrant4584@brety.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Farrant');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Nick_Murphy6930@gompie.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Murphy');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Gil_Stark6332@womeona.net',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Stark');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'George_Thornton4050@nickia.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Thornton');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Alexander_Savage5791@sveldo.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Savage');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Lynn_Dempsey1843@supunk.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Dempsey');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Michael_Noon6686@brety.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Noon');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Aiden_Lakey9503@cispeto.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Lakey');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Selena_Mcleod2352@twace.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Mcleod');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Leanne_Simpson1767@dionrab.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Simpson');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Parker_Oatway3994@mafthy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Oatway');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Fiona_Fenton4581@kideod.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Fenton');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Lucas_Knight8261@muall.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Knight');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Wendy_Hammond7148@deavo.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Hammond');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Eduardo_Underhill8193@deavo.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Underhill');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Julian_Rixon2106@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Rixon');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Bernadette_Newman3425@gmail.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Newman');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Jacqueline_Becker767@jiman.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Becker');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Ada_Warden4070@elnee.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Warden');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Quinn_Overson833@joiniaa.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Overson');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Ronald_Rixon8706@sheye.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Rixo1n');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Carl_Vaughn1476@kideod.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Vaughn');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Lexi_Murray9641@brety.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Murray');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Eduardo_Mason4684@atink.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Mason');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Elijah_Phillips4820@infotech44.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Phillips');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Remy_Ellwood5623@bungar.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Ellwood');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Aiden_Ashley3761@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Ashley');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Wade_Baldwin9628@eirey.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Baldwin');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Kurt_Boden5838@womeona.net',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Boden');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Elly_Sherry7423@naiker.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Sherry');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Nathan_Plant999@bulaffy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Plant');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Mike_Reyes5995@twace.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Reyes');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Daron_Moore2393@bauros.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Moore');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Goldie_Rogers2077@liret.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Rogers');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Vera_Sanchez3459@typill.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Sanchez');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Lillian_Collingwood7228@mafthy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Collingwood');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Robyn_Eagle8542@bretoux.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Eagle');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Marina_Fall4568@vetan.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Fall');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'John_Potter2072@naiker.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Potter');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Ember_Thomas537@kideod.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Thomas');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Moira_Watson3618@nanoff.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Watson');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Leroy_Shelton7513@zorer.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Sh1elton');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Hayden_Wren910@sveldo.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Wren');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Charlotte_Stark2606@bulaffy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','St1ark');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Wade_Morrison7267@zorer.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Morrison');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Noah_Callan383@fuliss.net',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Callan');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Liam_Jefferson9502@extex.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Jefferson');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Tyler_Emerson6656@muall.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Emerson');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Jacob_Graham9508@extex.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Graham');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Moira_Crawford543@elnee.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Crawford');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Marvin_Blythe9387@deavo.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Blythe');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Sebastian_Doherty496@bauros.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Doherty');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Macy_Saunders8887@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Saunders');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Jack_Swan102@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Swan');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Matt_Hepburn4242@atink.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Hepburn');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Phillip_Michael3422@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Michael');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Carina_Emmett2245@bretoux.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Emmett');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Erick_Flynn6489@bauros.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Flynn');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Tony_Truscott203@qater.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Truscott');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Julian_Gray9499@ovock.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Gray');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Denny_Mason7763@bulaffy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','M1ason');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Daria_Groves6928@yahoo.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Gro1ves');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'William_Foxley2951@qater.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Foxley');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'David_Collins4986@irrepsy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Collins');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Dorothy_Windsor7519@infotech44.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Windsor');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Maxwell_Baker4597@bulaffy.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Baker');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Peter_Potts4441@jiman.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Potts');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Candice_Isaac7673@dionrab.com',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Isaac');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Rick_Hepburn980@womeona.net',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Hepburn1');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Domenic_Evans1883@elnee.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Evans');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Keira_Noon594@qater.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Noon1');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Bridget_Trent6005@brety.org',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Trent');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Michaela_Mullins4334@elnee.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Mullins');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Nathan_Butler5796@supunk.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Butler');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Anthony_Flynn7728@nanoff.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Flynn1');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Wendy_Payne5977@guentu.biz',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Payne');
INSERT INTO iron_user VALUES(nextval('user_sequence'),'Mayleen_Gavin5973@elnee.tech',true,false,'$2a$10$xcIdSU3V0eKuqdyJsvZxL.q6h6lYjgNAqzscqKbZUurZQ3iRzqrDy','Gavin');

INSERT INTO iron_user_roles VALUES (1, 1);
INSERT INTO iron_user_roles VALUES (1, 2);
INSERT INTO iron_user_roles VALUES (2, 2);
INSERT INTO iron_user_roles VALUES (2, 3);


--TRAINING REQUESTS
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 10);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'IN PROGRESS', 'Title', null, 10);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 10);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', NOW()::TIMESTAMP, 'DONE', 'Title', null, 10);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 11);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 11);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 11);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 11);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 11);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 10);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 13);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 13);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 13);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 12);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 12);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 12);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 12);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 12);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 14);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 14);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 14);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 14);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 16);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 16);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 16);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 16);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 16);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 16);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 17);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 17);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 17);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 17);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 17);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 17);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 17);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 21);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 22);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 22);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 22);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 22);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 23);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 23);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 23);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 23);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 34);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 45);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 45);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 45);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 45);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 67);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 67);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 67);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 67);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 55);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 55);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 55);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 55);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 72);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 72);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 72);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 72);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 87);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 87);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 87);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 87);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 6);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 6);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 6);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 6);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 90);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 90);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 90);
INSERT INTO training_request VALUES(nextval('training_request_sequence'), 'Arms', NOW()::TIMESTAMP, 'Description', 'Mediocre', null, 'NEW', 'Title', null, 90);































