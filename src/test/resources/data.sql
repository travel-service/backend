INSERT INTO LOCATION (`name`, `type`, `address1`, `address2`, `area_code`, `image`, `latitude`, `longitude`,
                      `is_member`)
VALUES ('휴애리자연생활공원', 0, '제주특별자치도 서귀포시 남원읍 신례동로 256', 'null', 39, 'null', 33.3085, 126.634, false);
INSERT INTO LOCATION (`name`, `type`, `address1`, `address2`, `area_code`, `image`, `latitude`, `longitude`,
                      `is_member`)
VALUES ('섭지코지', 0, '제주특별자치도 서귀포시 성산읍 섭지코지로 107', 'null', 39, 'null', 33.4302, 126.928, false);
INSERT INTO LOCATION (`name`, `type`, `address1`, `address2`, `area_code`, `image`, `latitude`, `longitude`,
                      `is_member`)
VALUES ('천지연폭포 (제주도 국가지질공원)', 0, '제주특별자치도 서귀포시 천지동', 'null', 39, 'null', 33.2445, 126.559, false);
INSERT INTO LOCATION (`name`, `type`, `address1`, `address2`, `area_code`, `image`, `latitude`, `longitude`,
                      `is_member`)
VALUES ('성산일출봉 [유네스코 세계자연유산]', 0, '제주특별자치도 서귀포시 성산읍 일출로 284-12', 'null', 39, 'null', 33.4581, 126.942, false);

INSERT INTO INFORMATION (`IMAGE1`,`IMAGE2`,`LOCATION_ID`,`REPORT`,`SUMMARY`,`TEL`) VALUES ('url1','url2',1,'요약 타이틀','긴내용긴내용긴내용','010-1234-5678');

INSERT INTO INFORMATION (`IMAGE1`,`IMAGE2`,`LOCATION_ID`,`REPORT`,`SUMMARY`,`TEL`) VALUES ('url1','url2',2,'요약 타이틀','긴내용긴내용긴내용','010-1234-5678');

INSERT INTO INFORMATION (`IMAGE1`,`IMAGE2`,`LOCATION_ID`,`REPORT`,`SUMMARY`,`TEL`) VALUES ('url1','url2',3,'요약 타이틀','긴내용긴내용긴내용','010-1234-5678');

INSERT INTO INFORMATION (`IMAGE1`,`IMAGE2`,`LOCATION_ID`,`REPORT`,`SUMMARY`,`TEL`) VALUES ('url1','url2',4,'요약 타이틀','긴내용긴내용긴내용','010-1234-5678');


INSERT INTO ATTRACTION (`LOCATION_ID`,`PARKING`,`REST_DATE`,`USE_TIME`) VALUES (1, true, '매주 일요일', '09:00~22:00');

INSERT INTO ATTRACTION (`LOCATION_ID`,`PARKING`,`REST_DATE`,`USE_TIME`) VALUES (2, true, '매주 월요일', '09:00~13:01');

INSERT INTO ATTRACTION (`LOCATION_ID`,`PARKING`,`REST_DATE`,`USE_TIME`) VALUES (3, true, '매주 화요일', '09:30~23:02');

INSERT INTO ATTRACTION (`LOCATION_ID`,`PARKING`,`REST_DATE`,`USE_TIME`) VALUES (4, true, '매주 수요일', '19:00~22:03');


