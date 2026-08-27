CREATE TABLE donation (
    id UUID NOT NULL,
    nome VARCHAR(50) NOT NULL,
    adress VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    CONSTRAINT pk_donation PRIMARY KEY (id)
);