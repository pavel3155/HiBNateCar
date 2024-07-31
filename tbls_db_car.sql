DROP TABLE if exists Engine CASCADE;

create table Engine 
(
	id serial primary key,
	volume real,
	horsePower integer,
	typeFuel varchar(10),
	title varchar(30)
);

INSERT INTO Engine (volume,horsePower,typeFuel,title ) 
VALUES (0,0,'---','---'),
(1.2,90,'бензин','ДВС90Б'),
(1.4,100,'бензин','ДВС100Б'),
(1.6,120,'бензин','ДВС120Б'),
(1.8,140,'бензин','ДВС140Б'),
(2.0,160,'бензин','ДВС160Б'),
(1.4,120,'дизель','ДВС120Д'),
(2.0,155,'бензин','ДВС155Д'),
(2.4,180,'дизель','ДВС180Д');

DROP TABLE if exists Body CASCADE;

create table Body 
(
	id serial primary key,
	type varchar(10)
);

INSERT INTO Body (type) 
VALUES ('---'),
('Седан'),
('Универсал'),
('Хетчбек');

DROP TABLE if exists Cars CASCADE;

create table Cars 
(
	id serial primary key,
	marka varchar(30),
	model varchar(30),
	id_eng integer default 1,
	id_body integer default 1,
	price numeric default 0,
	date varchar(10),
	foreign key (id_eng) references Engine (id) on delete set default on update cascade,
	foreign key (id_body) references Body (id) on delete set default on update cascade
);

INSERT INTO Cars (marka,model,id_eng,id_body,price,date) 
VALUES ('---','---',1,1,0,'---'),
('Тойота','Венза',9,3,1800000.0,'16.11.2018'),
('Тойота','Венза',6,3,1800000.0,'16.11.2018'),
('Тойота','Венза',9,3,1800000.0,'16.11.2018'),
('Тойота','Ярис',4,4,1700000.0,'15.11.2019'),
('Тойота','Ярис',4,4,1700000.0,'15.11.2019'),
('Тойота','Королла',6,2,1700000.0,'15.11.2019'),
('Тойота','Королла',6,2,1750000.0,'11.11.2019'),
('Тойота','Королла',4,2,1500000.0,'14.10.2018'),
('Тойота','Королла',4,2,1550000.0,'17.10.2018'),
('Тойота','Королла',6,2,1600000.0,'18.10.2019'),
('Тойота','Королла',4,2,1700000.0,'19.10.2019'),
('Тойота','Королла',4,2,1550000.0,'19.12.2018'),
('Тойота','Камри',6,2,1900000.0,'23.10.2020'),
('Тойота','Камри',6,2,1800000.0,'22.10.2020');