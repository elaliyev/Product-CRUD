--category
insert into CATEGORY (ID,CATEGORY_NAME)
values (1,'LAPTOP');

--product
insert into PRODUCT (ID,NAME, PRICE, CURRENCY, CREATION_DATE, DETAIL, CATEGORY_ID)
values (101,'DELL',220.0,'USD',{ts '2019-05-12'}, 'DELL DETAIL', 1);


