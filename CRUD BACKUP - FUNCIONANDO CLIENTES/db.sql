create database javamvc;

use javamvc;

CREATE TABLE categories(
   categoryId int AUTO_INCREMENT,
   description  varchar(50) ,
   
      PRIMARY KEY(categoryId)
);

CREATE TABLE products(
   productId int AUTO_INCREMENT,
   name varchar(50) ,
   price float     ,
   quantity int     ,
   category_id int    ,
   PRIMARY KEY(productId),
   FOREIGN KEY(category_id)
   REFERENCES categories(categoryId)
);

CREATE TABLE clients(
   clientId int AUTO_INCREMENT      ,
   name varchar(50) ,
   cpf varchar(50) ,
   telphone varchar(50) ,
   
      PRIMARY KEY(clientId)
);

CREATE TABLE sales(
   saleId int AUTO_INCREMENT ,
   dat date ,
   value float ,
   paid boolean ,
   client_id int,
   
      PRIMARY KEY(saleId),
   
      FOREIGN KEY(client_id)
      REFERENCES clients(clientId)
);

CREATE TABLE saleItens(
   saleItenId int AUTO_INCREMENT ,
   quantity int ,
   value float ,
   product_id int,
   sale_id int,
   
      PRIMARY KEY(saleItenId),
   
      FOREIGN KEY(product_id)
      REFERENCES products(productId),
   
      FOREIGN KEY(sale_id)
      REFERENCES sales(saleId)
);

INSERT INTO clients(name, cpf, telphone) VALUES('Rafael','111.111.111-11','(11) 1111-1111');
INSERT INTO clients(name, cpf, telphone) VALUES('João'  ,'222.222.222-22','(22) 2222-2222');
INSERT INTO clients(name, cpf, telphone) VALUES('Maria' ,'333.333.333-33','(33) 3333-3333');

INSERT INTO categories(description) VALUES('Eletrônicos');
INSERT INTO categories(description) VALUES('Vestuário');

INSERT INTO products(name, price, quantity, category_id) VALUES('TV 32 Sony', 2000.00, 10, 1);
INSERT INTO products(name, price, quantity, category_id) VALUES('TV 40 Sony', 3000.00, 10, 1);
INSERT INTO products(name, price, quantity, category_id) VALUES('Tênis Nike Tri Fusion Run 40', 550.00, 10, 2);
INSERT INTO products(name, price, quantity, category_id) VALUES('Tênis Adidas Galaxy Trainer 36', 215.00, 10, 2);

INSERT INTO sales(dat, value, paid, client_id) VALUES('30/04/2016', 5000.0, false, 1);
INSERT INTO sales(dat, value, paid, client_id) VALUES('01/04/2016', 765.0 , false, 1);

INSERT INTO saleItens(quantity, value, product_id, sale_id) VALUES(1, 2000.00, 1, 1);
INSERT INTO saleItens(quantity, value, product_id, sale_id) VALUES(1, 3000.00, 2, 1);
INSERT INTO saleItens(quantity, value, product_id, sale_id) VALUES(1, 550.00, 3, 2);
INSERT INTO saleItens(quantity, value, product_id, sale_id) VALUES(1, 215.00, 4, 2);
