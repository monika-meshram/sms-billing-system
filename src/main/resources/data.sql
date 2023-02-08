INSERT  INTO PLAN  (ID, FREE_MESSAGE_COUNT, NAME, PRICE_PER_MESSAGE)  VALUES ('1', '0', 'BASIC', '0.001');

INSERT  INTO PLAN  (ID, FREE_MESSAGE_COUNT, NAME, PRICE_PER_MESSAGE)  VALUES ('2', '0', 'SILVER', '0.002');

INSERT  INTO PLAN  (ID, FREE_MESSAGE_COUNT, NAME, PRICE_PER_MESSAGE)  VALUES ('3', '0', 'GOLD', '0.003');


INSERT INTO LOYALTY_PLAN (ID, DISCOUNTED_PRICE_PER_MESSAGE, MESSAGE_COUNT, PLAN_ID) VALUES ('1', '0.0005', '100000', '3');


INSERT INTO CUSTOMER (ID, CUSTOMER_NAME, PLAN_ID) VALUES ('1', 'SHOP', '3');

INSERT INTO CUSTOMER (ID, CUSTOMER_NAME, PLAN_ID) VALUES ('2', 'BANK', '2');

INSERT INTO CUSTOMER (ID, CUSTOMER_NAME, PLAN_ID) VALUES ('3', 'COMPANY', '1');