-- BORRAR TIPOS Y TABLAS

DROP TABLE TABLA_VENTAS;
DROP TABLE TABLA_PRODUCTOS;
DROP TABLE TABLA_CLIENTES;

DROP TYPE TIPO_VENTAS;
DROP TYPE TABLA_LVENTAS;
DROP TYPE TIPO_LVENTA;
DROP TYPE TIPO_PRODUCTO;
DROP TYPE TIPO_CLIENTE;
DROP TYPE TIPO_DIRECCION;
DROP TYPE TIPO_TELEFONOS;

COMMIT;

-- 1. Creamos TIPO_TELEFONOS de tipo VARRAY que contenga los 3 teléfonos ya que todos
-- sus atributos son del mismo tipo.

CREATE OR REPLACE TYPE TIPO_TELEFONOS AS VARRAY(3) OF VARCHAR2(9);

-- 2. Creamos TIPO_DIRECCION que contiene los atributos que hacen referencia a la dirección del cliente.

CREATE OR REPLACE TYPE TIPO_DIRECCION AS OBJECT ( 
  Nombre VARCHAR2(100),
  Poblacion VARCHAR2(50),
  CodPostal NUMBER(5),
  Provincia VARCHAR2(15)
 );
 
-- 3. Creamos TIPO_CLIENTE que haciendo uso de los dos tipos anteriores guarda toda la información del cliente.

CREATE OR REPLACE TYPE TIPO_CLIENTE AS OBJECT ( 
  IdCliente NUMBER,
  Nombre    VARCHAR2(50),
  Direccion TIPO_DIRECCION,
  Telefonos TIPO_TELEFONOS
 );

-- 4. Creamos TIPO_PRODUCTO que contiene los atributos que hacen referencia a cada producto.  

CREATE OR REPLACE TYPE TIPO_PRODUCTO AS OBJECT (
  IdProducto  NUMBER,
  Descripcion VARCHAR2(50),
  Pvp         NUMBER,
  StockActual NUMBER
);

-- 5. Creamos TIPO_LVENTA que contiene los atributos que hacen referencia a una línea de venta.
-- Teniendo en cuenta que: 
--    a. No hace falta el atributo IDVENTA (FK) porque las líneas las almacenaremos
--    como TABLA ANIDADA a cada registro de la tabla TABLA_VENTAS que crearemos más tarde. 
--    b. El atributo IDPRODUCTO hará referencia a un objeto TIPO_PRODUCTO que se
--    almacenarán en otra tabla (TABLA_PRODUCTOS). 
-- PRIMARY KEY (IdVenta,NumeroLinea)

CREATE OR REPLACE TYPE TIPO_LVENTA AS OBJECT (
  IdVenta     NUMBER,
  NumeroLinea NUMBER,
  IdProducto  REF TIPO_PRODUCTO,
  Cantidad    NUMBER,
  CONSTRUCTOR FUNCTION TIPO_LVENTA RETURN SELF AS RESULT
);

CREATE OR REPLACE TYPE BODY TIPO_LVENTA AS
    CONSTRUCTOR FUNCTION TIPO_LVENTA RETURN SELF AS RESULT IS
    BEGIN
        SELF.IdVenta :=0;
        SELF.NumeroLinea:= 0;
        SELF.IdProducto:= NULL;
        SELF.Cantidad:= 0;
        RETURN;
    END;
END;

-- 6. Creamos TABLA_LVENTAS que será una tabla con objetos de tipo TIPO_LVENTAS y que
-- posteriormente utilizaremos como TABLA ANIDADA de la tabla TABLA_VENTAS. 

CREATE OR REPLACE TYPE TABLA_LVENTAS AS TABLE OF TIPO_LVENTA;

-- 7. Creamos TIPO_VENTAS que almacenará todos los datos de una venta, teniendo en
-- cuenta que el atributo IdCliente hará referencia a un objeto TIPO_CLIENTE que se
-- almacenarán en otra tabla (TABLA_CLIENTES) que se creará posteriormente. 
--    a. Ademas se incluye un atributo LINEAS que será una TABLA ANIDADA del tipo
--    anterior para almacenar las lineas de ventas de esa venta. 
--    b. Creamos el metodo TOTAL_VENTA que nos devolverá el total de esa venta.

CREATE OR REPLACE TYPE TIPO_VENTAS AS OBJECT (
  IdVenta NUMBER,
  IdCcliente REF TIPO_CLIENTE,
  FechaVenta DATE,
  LINEAS TABLA_LVENTAS,
  MEMBER FUNCTION TOTAL_VENTA RETURN NUMBER
);

CREATE OR REPLACE TYPE BODY TIPO_VENTAS AS
    MEMBER FUNCTION TOTAL_VENTA RETURN NUMBER IS
        --xTABLAVENTAS TABLA_LVENTAS;
        xProducto TIPO_PRODUCTO;
        TOTAL NUMBER;
        CURSOR CURSOR1 IS SELECT * FROM TABLE (SELECT TV.LINEAS FROM TABLA_VENTAS TV WHERE IdVenta = SELF.IdVenta);
        xCURSOR1 CURSOR1%ROWTYPE;
    BEGIN
        TOTAL:= 0;
        OPEN CURSOR1;
            LOOP 
                FETCH CURSOR1 INTO XCURSOR1;    
                EXIT WHEN CURSOR1%NOTFOUND;
                    SELECT DEREF(xCURSOR1.IdProducto) INTO xProducto FROM DUAL;
                    TOTAL := TOTAL + (xProducto.PVP * xCursor1.cantidad);
            END LOOP;
        CLOSE CURSOR1;
        --SELECT VALUE(TV) INTO xTABLAVENTAS FROM TABLA_VENTAS TV WHERE IdVenta = SELF.IdVenta;
        --SELECT COUNT(*) INTO TOTAL FROM THE( SELECT XTV.LINEAS FROM TABLA_VENTAS XTV WHERE IdVenta = SELF.IdVenta);
        --DBMS_OUTPUT.PUT_LINE(TOTAL);
        RETURN TOTAL;
        
        /*
        FOR X IN 1..LINEAS.COUNT LOOP
            SELECT DEREF(LINEAS(X.IDPRODUCTO)) INTO XPRODUCTO FROM DUAL;
            
        END LOOP;
        */
    END;
END;

-- 8. Creamos las tablas para almacenar la informacion de la aplicacion. 
CREATE TABLE TABLA_CLIENTES OF TIPO_CLIENTE (IdCliente PRIMARY KEY);
CREATE TABLE TABLA_PRODUCTOS OF TIPO_PRODUCTO (IdProducto PRIMARY KEY);
CREATE TABLE TABLA_VENTAS OF TIPO_VENTAS (IdVenta PRIMARY KEY) NESTED TABLE LINEAS STORE AS TIPO_VENTAS_STORAGE;

COMMIT;

/*9.  Actualizaremos datos :
    a. Insertar 4 artículos.
    b. Insertar el cliente con IdCliente 1 que tengan 2 teléfonos.
    c. Insertar el cliente con IdCliente 2 que tenga 1 teléfono.
    d. Insertar en la TABLA_VENTAS la venta con IdVenta 1 para el Idcliente 1 con 
    fecha actual y sin líneas de ventas.
    e. Insertar en la TABLA_VENTAS dos líneas de ventas para la venta con IdVenta 1:
        i. La primera línea con el producto con Idproducto 4 y cantidad 2.
        ii. La segunda línea con el producto  con Idproducto 3 y cantidad 11. */
        
INSERT INTO TABLA_PRODUCTOS VALUES(1,'Teclado Bluetooth',25,100);
INSERT INTO TABLA_PRODUCTOS VALUES(2,'Raton Bluetooth',15,100);
INSERT INTO TABLA_PRODUCTOS VALUES(3,'Pendrive 32GB',5,100);
INSERT INTO TABLA_PRODUCTOS VALUES(4,'Cargador USB',10,100);

INSERT INTO TABLA_CLIENTES VALUES(1,'Juan García', 
TIPO_DIRECCION('Camino de Ronda 123', 'Granada', 18001, 'Granada'),
TIPO_TELEFONOS('958010101','600123123'));

INSERT INTO TABLA_CLIENTES VALUES(2,'María Pérez', 
TIPO_DIRECCION('Joaquina Eguaras 48', 'Granada', 18001, 'Granada'),
TIPO_TELEFONOS('958990099'));

INSERT INTO TABLA_VENTAS VALUES(1, 
(SELECT REF(TC) FROM TABLA_CLIENTES TC WHERE IdCliente = 1) , 
(SELECT CURRENT_DATE FROM DUAL), 
TABLA_LVENTAS());


--MODIFICAR PARA AÑADIR UNA A UNA LINEA SELECCIONANDO LA ID 1
INSERT INTO TABLA_VENTAS VALUES(1, 
(SELECT REF(TC) FROM TABLA_CLIENTES TC WHERE IdCliente = 1) , 
(SELECT CURRENT_DATE FROM DUAL), 
TABLA_LVENTAS(
    TIPO_LVENTA(1,1,(SELECT REF(TP) FROM TABLA_PRODUCTOS TP WHERE IdProducto = 4),2),
    TIPO_LVENTA(1,2,(SELECT REF(TP) FROM TABLA_PRODUCTOS TP WHERE IdProducto = 3),11)
    )
);

COMMIT;

-- 10. Crear el procedimiento almacenado sp_IMPRIME_VENTA que pasándole como parámetro 
-- el número de una venta la imprima con todos sus datos. 

CREATE OR REPLACE PROCEDURE sp_IMPRIME_VENTA (venta NUMBER) IS
    xCliente TIPO_CLIENTE;
    xProducto TIPO_PRODUCTO;
    xVenta TIPO_VENTAS;
    separador VARCHAR2(60);
    contador NUMBER;
    CURSOR CURSOR1 IS SELECT * FROM TABLE (SELECT TV.LINEAS FROM TABLA_VENTAS TV WHERE IdVenta = venta);
    lineaCursor CURSOR1%ROWTYPE;
BEGIN
    separador := '------------------------------------------------------------';
    DBMS_OUTPUT.PUT_LINE(separador);
    DBMS_OUTPUT.PUT_LINE('Informe de venta detallado');
    DBMS_OUTPUT.PUT_LINE(separador);
    SELECT VALUE(TV) INTO xVenta FROM TABLA_VENTAS TV WHERE IdVenta = venta;
    DBMS_OUTPUT.PUT_LINE('Número de Venta: '|| xVenta.IdVenta);
    DBMS_OUTPUT.PUT_LINE('Fecha de Venta: '|| xVenta.FechaVenta);
    DBMS_OUTPUT.PUT_LINE(separador);
    SELECT DEREF(xVenta.IdCcliente) INTO xCliente FROM DUAL;
    DBMS_OUTPUT.PUT_LINE('Nombre del cliente: '|| xCliente.Nombre);
    DBMS_OUTPUT.PUT_LINE('Dirección: '|| xCliente.Direccion.Nombre);
    DBMS_OUTPUT.PUT_LINE('Población: '|| xCliente.Direccion.Poblacion);
    DBMS_OUTPUT.PUT_LINE('Provincia: '|| xCliente.Direccion.Provincia);
    DBMS_OUTPUT.PUT_LINE('Código Postal: '|| xCliente.Direccion.CodPostal);
    contador := 1;
    WHILE contador <=xCliente.Telefonos.COUNT LOOP
        DBMS_OUTPUT.PUT_LINE('Teléfono: '|| xCliente.Telefonos(contador));
        contador := contador + 1;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE(separador);
    DBMS_OUTPUT.PUT_LINE('Detalle de productos');
    DBMS_OUTPUT.PUT_LINE(separador);
    OPEN CURSOR1;
        LOOP 
            FETCH CURSOR1 INTO lineaCursor;    
            EXIT WHEN CURSOR1%NOTFOUND;
                SELECT DEREF(lineaCursor.IdProducto) INTO xProducto FROM DUAL;
                DBMS_OUTPUT.PUT_LINE('Id Producto: '|| xProducto.IdProducto);
                DBMS_OUTPUT.PUT_LINE('Descripcion: '|| xProducto.PVP);
                DBMS_OUTPUT.PUT_LINE('Precio unitario: '|| xProducto.PVP);
                DBMS_OUTPUT.PUT_LINE('Cantidad: '|| lineaCursor.cantidad);
                DBMS_OUTPUT.PUT_LINE('Subtotal: '|| (xProducto.PVP * lineaCursor.cantidad));
                DBMS_OUTPUT.PUT_LINE('');
        END LOOP;
    CLOSE CURSOR1;
    DBMS_OUTPUT.PUT_LINE(separador);
    DBMS_OUTPUT.PUT_LINE('Total a pagar: ' || xVenta.TOTAL_VENTA() || '€');
END;

SET SERVEROUTPUT ON;
BEGIN
   sp_IMPRIME_VENTA (1);
END; 


SELECT * FROM TABLA_PRODUCTOS;
SELECT * FROM TABLA_VENTAS;
SELECT V.TOTAL_VENTA() FROM TABLA_VENTAS V;

/*
ARBITROS
EQUIPOS
PARTIDOS
TABLA_ANIDADA
*/


