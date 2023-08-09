# Demo Kafka

O objetivo deste projeto é demonstrar alguns conceitos de kafka na prática, consumer group, processamento, falha no consumo e offsets.

## Como rodar

Primeira você vai precisar subir os containers contento o kafka e suas dependências, isso pode ser feito com o seguinte comando:
```bash
docker compose up
```

Depois de tudo online, basta rodar a classe `DemoKafkaApplication`, ou caso você já seja programador java com JAVA_HOME configuradinha, pode também executar o seguinte comando:

```bash
mvn spring-boot:run
```

Importante destacar que esta instância será apenas capaz de enfileirar mensagens no kafka, as instâncias responsáveis por consumir, precisam subir com os profiles `instance-with-consuming-01` e/ou `instance-with-consuming-02`.

## Como testar

Para interagir com o endpoint principal, onde vamos enviar as atualizações de preço, você pode user o seguinte `curl`, ou utilizar ferramenta que preferir como o Postman:
```bash
curl --location 'localhost:8080/price/' \
--header 'Content-Type: application/json' \
--data '{
  "id": 2,
  "price": 13.50
}'
```

Como regra arbitrária, valores abaixo de 0, serão considerados improcessáveis, para fins de teste do consumo.

Para ver como fica o consumo diretamente no kafka, você pode acessar a ferramenta `kowl` que sobe juntamente com as imagens docker no link http://localhost:8090/topics