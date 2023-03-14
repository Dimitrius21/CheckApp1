--Вывести к каждому самолету класс обслуживания и количество мест этого класса
SELECT pl.model->>'en', s.fare_conditions,  count(s.seat_no) FROM aircrafts_data as pl
INNER JOIN seats as s
USING (aircraft_code)
GROUP BY  pl.aircraft_code, s.fare_conditions
ORDER BY pl.aircraft_code

--Найти 3 самых вместительных самолета (модель + кол-во мест)
SELECT aircrafts_data.model->>'en', count(*) as seat_qt FROM seats
INNER JOIN aircrafts_data
USING (aircraft_code)
GROUP BY aircrafts_data.aircraft_code
ORDER BY seat_qt DESC LIMIT 3

--Вывести код,модель самолета и места не эконом класса для самолета 'Аэробус A321-200' с сортировкой по местам
SELECT pl.aircraft_code, pl.model->>'en' as model, s.seat_no FROM aircrafts_data as pl
INNER JOIN seats as s
USING (aircraft_code)
WHERE pl.model->>'ru' = 'Аэробус A321-200'AND s.fare_conditions <> 'Economy'
ORDER BY seat_no

--Вывести города в которых больше 1 аэропорта ( код аэропорта, аэропорт, город)
SELECT city->>'en', count(*) as qt FROM airports_data GROUP BY city->>'en' HAVING count(*) > 1

-- Найти ближайший вылетающий рейс из Екатеринбурга в Москву, на который еще не завершилась регистрация
SELECT flight_no, departure_airport, arrival_airport, scheduled_departure FROM flights as fl
INNER JOIN airports_data as ap
ON (fl.departure_airport=ap.airport_code)
WHERE ap.city->>'ru' = 'Екатеринбург'
AND fl.arrival_airport IN (SELECT airport_code FROM airports_data WHERE city->>'ru' = 'Москва' )
AND fl.status IN ('Scheduled','On Time','Delayed')
ORDER BY scheduled_departure LIMIT 1/*and extract(epoch from ((scheduled_departure - bookings.now() + interval '01:00:00'))) > 0 */

--Вывести самый дешевый и дорогой билет и стоимость ( в одном результирующем ответе)
(SELECT amount, ticket_no  FROM ticket_flights WHERE amount=(SELECT max(amount) FROM ticket_flights) LIMIT 1)
UNION (SELECT amount, ticket_no  FROM ticket_flights WHERE amount=(SELECT min(amount) FROM ticket_flights) LIMIT 1)

-- Написать DDL таблицы Customers, должны быть поля id, firstName, LastName, email, phone. Добавить ограничения на поля ( constraints).
CREATE TABLE Customers (
	id bigserial PRIMARY KEY,
	firstName VARCHAR(100) CHECK (length(trim(firstName))>0),
	lastName VARCHAR (100) CHECK (length(trim(lastName))>0),
	email VARCHAR(254),
	phone VARCHAR(50) NOT NULL
	);

-– Написать DDL таблицы Orders, должен быть id, customerId,	quantity. Должен быть внешний ключ на таблицу customers + ограничения
CREATE TABLE Orders (
	id bigserial PRIMARY KEY,
	customerId bigint,
	quantity int,
	FOREIGN KEY (customerId) REFERENCES Customers (id),
	CONSTRAINT CHC_QTITY CHECK (quantity>=0)
);

-- Написать 5 insert в эти таблицы
INSERT INTO Customers (firstName, lastName, email, phone) VALUES ('Семен', 'Горбунков', '', '123987'),
	('Геннадий', 'Козодоев', 'gen@mail.com','456123'), ('Алиса','Селезнева','a.selezneva@mail.com','963741'),
	('Джон','Смит','jon@mail.com','85296378'), ('кот','Матроскин','matroskin@mail.com','7411213');

INSERT INTO Orders (customerId, quantity) VALUES (1, 11), (2, 22), (3, 33), (4, 44), (5, 55);

-- удалить таблицы
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Customers;

-- Написать свой кастомный запрос ( rus + sql)
-- Подсчитать количество билетов и их сумму,на места по всем тарифам, кроме Экономный,
-- проданных на рейсы что были осуществлены за последние 30 дней,
SELECT sum(amount), count(*) FROM ticket_flights as tk
INNER JOIN flights as fl
USING(flight_id)
WHERE tk.fare_conditions <> 'Economy'
AND fl.scheduled_departure IN (bookings.now(), bookings.now()-interval '30 days' )