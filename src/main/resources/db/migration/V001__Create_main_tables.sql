CREATE TABLE sensor(
                       sensor_id int generated always as identity primary key,
                       name varchar(30) not null unique
);

create table measurement(
                            measurement_id int generated always as identity primary key,
                            value float8 check(value >= -100 and value <= 100) not null,
                            raining boolean not null,
                            sensor_id int references sensor(sensor_id) not null
)