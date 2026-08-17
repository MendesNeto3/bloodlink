#!/bin/bash
set -e

# Roda automaticamente na primeira vez que o container do Postgres sobe
# (docker-entrypoint-initdb.d executa qualquer .sh/.sql presente ali,
# mas só na inicialização do volume vazio -- se você já subiu o container
# antes sem esse script, vai precisar apagar o volume pra ele rodar).

for DB in auth_db center_db appointment_db eligibility_db checkin_db stock_db; do
  echo "Criando banco: $DB"
  psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE $DB;
EOSQL
done