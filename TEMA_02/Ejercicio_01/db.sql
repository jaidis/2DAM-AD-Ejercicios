DROP TABLE IF exists `articulos`;

CREATE TABLE `articulos` (
	`referencia`	varchar(13) NOT NULL,
	`descripcion`	varchar(50) NOT NULL,
	`stock`	INTEGER(10) NOT NULL,
	`precio`	INTEGER(10) NOT NULL,
	PRIMARY KEY(`referencia`)
);

DROP TABLE IF exists `clientes`;

CREATE TABLE `clientes` (
	`codigo`	INTEGER(10) NOT NULL,
	`dni`	varchar(10) NOT NULL,
	`nombre`	varchar(50) NOT NULL,
	`fecnac`	date NOT NULL,
	PRIMARY KEY(`codigo`)
);

DROP TABLE IF exists `facturas`;

CREATE TABLE `facturas` (
	`nfactura`	INTEGER ( 10 ) NOT NULL,
	`fecfactura`	date NOT NULL,
	`codcliente`	INTEGER ( 10 ) NOT NULL,
	`totalfactura`	INTEGER ( 12 ) NOT NULL,
	`pagada`	varchar ( 1 ) NOT NULL,
	PRIMARY KEY(`nfactura`),
	FOREIGN KEY(`codcliente`) REFERENCES `clientes`(`codigo`)
);

DROP TABLE IF exists `linfacturas`;

CREATE TABLE `linfacturas` (
	`id`	INTEGER(10) NOT NULL,
	`nfactura`	INTEGER(10) NOT NULL,
	`referencia`	varchar(13) NOT NULL,
	`cantidad`	INTEGER(10) NOT NULL,
	`descuento`	INTEGER(10) NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`nfactura`) REFERENCES `facturas`(`nfactura`)
);

INSERT INTO `clientes` values (1, '75123456P', 'manuel munoz', '1988-12-20'),
(2, '75111333P', 'luis crespo', '1961-01-01'),
(3, '75222444P', 'francisco ayala', '1950-08-01');

COMMIT;
