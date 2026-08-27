CREATE TABLE appointment (
    id UUID NOT NULL,
    center_id VARCHAR(255),
    date_hour DATE NOT NULL,
    time TIME NOT NULL,
    capacidadetotal VARCHAR(255) NOT NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);