
CREATE TYPE DIRECCION AS OBJECT ( 
  Tipo VARCHAR2(2),
  Nombre VARCHAR2(100),
  Numero NUMBER(3)
 );
 
-- Creacion de tabla anidada que almacenará objetos de tipo dirección

CREATE TYPE TABLA_ANIDADA AS TABLE OF DIRECCION;

-- Creacion de una tabla que contendrá una tabla anidada como uno de sus 
-- atributos

CREATE TABLE EJEMPLO_TABLA_ANIDADA ( 
  Codigo    NUMBER,
  Apellidos VARCHAR2(35),
  Direcc    TABLA_ANIDADA
)
NESTED TABLE Direcc STORE AS DIREC_ANIDADA;

-- Insertar datos en la tabla EJEMPLO_TABLA_ANIDADA;

INSERT INTO EJEMPLO_TABLA_ANIDADA VALUES (1,'Juan',
  TABLA_ANIDADA(
    DIRECCION ('AV','Andalucia',8),
    DIRECCION ('CL','Poeta Lorca',22),
    DIRECCION ('CL','Cervantes',36)
   )
);


INSERT INTO EJEMPLO_TABLA_ANIDADA VALUES (2,'Marta',
  TABLA_ANIDADA(
    DIRECCION ('PZ','España',1),
    DIRECCION ('CL','Golondrinas',2)
   )
);

-- Mostrar datos 

SELECT * FROM EJEMPLO_TABLA_ANIDADA;
 
-- Mostrar datos especificos de la tabla anidada
/*
En este caso utilizamos un CURSOR que recorra los registros de la tabla 
anidada de nuestra tabla. 
El operador TABLE hace referencia a la TABLA ANIDADA de nuestra tabla.
*/

SELECT 
Codigo, Apellidos, CURSOR (SELECT nombre FROM TABLE(T.Direcc)) AS Nom_Calle
FROM EJEMPLO_TABLA_ANIDADA T;

SELECT Codigo, Apellidos, CURSOR (
  SELECT nombre FROM TABLE(T.Direcc) WHERE tipo = 'AV') AS Nombre_Calle
FROM EJEMPLO_TABLA_ANIDADA T;

SELECT Codigo, Apellidos, CURSOR (
  SELECT nombre FROM TABLE(T.Direcc) WHERE tipo = 'AV') AS Nombre_Calle
FROM EJEMPLO_TABLA_ANIDADA T WHERE T.Codigo = 1;

-- Utilizamos la cláusula THE para obtener datos de la TABLA_ANIDADA
-- sobre un registro de la tabla_padre

-- La sintaxis es SELECT ... FROM (subconsulta_sobre_tabla_padre) WHERE ...;

SELECT Nombre FROM 
  THE ( SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE Codigo=1);

-- Si la subconsulta_sobre_tabla_padre devolviera más de un registro no daría
-- el error
-- ORA-01427: single-row subquery returns more than one row

SELECT Nombre FROM 
  THE ( SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T);

SELECT Nombre FROM 
  THE ( SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE Codigo=1)
WHERE Tipo = 'AV';

-- Como dijimos antes, el operador TABLE hace referencia a la TABLA ANIDADA
-- de nuestra tabla.
-- De esta forma, para insertar un registro en la TABLA ANIDADA habrá que
-- hacer uso de TABLE.

INSERT INTO TABLE (SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE
Codigo=1)
VALUES ( DIRECCION ('CL','Poeta Machado',47));

-- Comprobamos

SELECT * FROM 
  THE ( SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE Codigo=1);

-- Modificamos los datos de la TABLA ANIDADA

UPDATE TABLE (SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE Codigo=1)
SET Numero = Numero + 2;

UPDATE TABLE (SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE Codigo=1)
SET Numero = Numero + 6 
WHERE Tipo = 'CL';

-- Comprobamos

SELECT * FROM 
  THE ( SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE Codigo=1);

-- Borramos registro de la TABLA ANIDADA

DELETE TABLE (SELECT T.Direcc FROM EJEMPLO_TABLA_ANIDADA T WHERE Codigo=1)
WHERE Tipo = 'CL';
 