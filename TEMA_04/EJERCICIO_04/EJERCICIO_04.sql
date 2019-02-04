DROP TABLE TABLA_ORDENADORES;
DROP TABLE TABLA_COMPONENTES;
DROP TABLE TABLA_FABRICANTES;

DROP TYPE TABLA_LISTA_COMPONENTES;
DROP TYPE TIPO_ORDENADOR;
DROP TYPE TIPO_COLORES;
DROP TYPE TIPO_COMPONENTE;
DROP TYPE TIPO_LISTA_COMPONENTES;
DROP TYPE TIPO_FABRICANTE;

COMMIT;

CREATE OR REPLACE TYPE TIPO_FABRICANTE AS OBJECT(
    codFab NUMBER(6),
    nombre VARCHAR2(50),
    pais VARCHAR2(50)
);

CREATE OR REPLACE TYPE TIPO_COLORES AS VARRAY(10) OF VARCHAR2(20);

CREATE OR REPLACE TYPE TIPO_COMPONENTE AS OBJECT(
    codComp NUMBER(6),
    descripcion VARCHAR2(300),
    refFab REF TIPO_FABRICANTE,
    precioFab NUMBER(6),
    precioPvp NUMBER(6),
    color TIPO_COLORES
);

CREATE OR REPLACE TYPE TIPO_LISTA_COMPONENTES AS OBJECT(
    cantidad NUMBER(6),
    lista REF TIPO_COMPONENTE
);

CREATE OR REPLACE TYPE TABLA_LISTA_COMPONENTES AS TABLE OF TIPO_LISTA_COMPONENTES;

CREATE OR REPLACE TYPE TIPO_ORDENADOR AS OBJECT(
    codOrd NUMBER(6),
    descripcion VARCHAR2(1000),
    componentes TABLA_LISTA_COMPONENTES,
    MEMBER FUNCTION fBeneficio RETURN NUMBER,
    MEMBER PROCEDURE spComposicion,
    MEMBER PROCEDURE spComposicionDetallada
);

CREATE OR REPLACE TYPE BODY TIPO_ORDENADOR AS
    MEMBER FUNCTION fBeneficio RETURN NUMBER IS
        total NUMBER;
        componente TIPO_COMPONENTE;
    BEGIN
        total := 0;
        FOR item IN 1..componentes.COUNT LOOP
            SELECT DEREF(componentes(item).lista) INTO componente FROM DUAL;
            --DBMS_OUTPUT.PUT_LINE('Cantidad: ' || componentes(item).cantidad);
            --DBMS_OUTPUT.PUT_LINE('Articulo Fabrica: ' || componente.precioFab);
            --DBMS_OUTPUT.PUT_LINE('Articulo Pvp: ' || componente.precioPvp);
            total := total + ((componente.precioPvp - componente.precioFab)*componentes(item).cantidad);
        END LOOP;
        RETURN total;
    END;
    MEMBER PROCEDURE spComposicion AS
    BEGIN
        DBMS_OUTPUT.PUT_LINE('Identificador Ordenador: '|| SELF.codOrd);
        DBMS_OUTPUT.PUT_LINE('Descripción: '|| SELF.descripcion);
    END;
    MEMBER PROCEDURE spComposicionDetallada AS
        componente TIPO_COMPONENTE;
        fabricante TIPO_FABRICANTE;
    BEGIN
        DBMS_OUTPUT.PUT_LINE('Identificador Ordenador: '|| SELF.codOrd);
        DBMS_OUTPUT.PUT_LINE('Descripción: '|| SELF.descripcion);
        FOR item IN 1..componentes.COUNT LOOP
            SELECT DEREF(componentes(item).lista) INTO componente FROM DUAL;
            SELECT DEREF(componente.refFab) INTO fabricante FROM DUAL;
            DBMS_OUTPUT.PUT_LINE('Componente: '|| componente.descripcion);
            DBMS_OUTPUT.PUT_LINE('Cantidad: ' || componentes(item).cantidad);
            DBMS_OUTPUT.PUT_LINE('Fabricante: ' || fabricante.nombre);      
        END LOOP;
    END;
END;


CREATE TABLE TABLA_FABRICANTES OF TIPO_FABRICANTE (codFab PRIMARY KEY);
CREATE TABLE TABLA_COMPONENTES OF TIPO_COMPONENTE (codComp PRIMARY KEY);
CREATE TABLE TABLA_ORDENADORES OF TIPO_ORDENADOR NESTED TABLE componentes STORE AS TABLA_ORDENADORES_COMPONENTES;

INSERT INTO TABLA_FABRICANTES VALUES(1, 'Asus', 'Malasia');
INSERT INTO TABLA_FABRICANTES VALUES(2, 'Gigabyte', 'Malasia');
INSERT INTO TABLA_FABRICANTES VALUES(3, 'MSI', 'Malasia');
INSERT INTO TABLA_FABRICANTES VALUES(4, 'Western Digital', 'USA');
INSERT INTO TABLA_FABRICANTES VALUES(5, 'Intel', 'USA');
INSERT INTO TABLA_FABRICANTES VALUES(6, 'AMD', 'USA');

INSERT INTO TABLA_COMPONENTES VALUES(
    1,
    'Intel Core I5-8600K LGA 1151',
    (SELECT REF(TF) FROM TABLA_FABRICANTES TF WHERE codFab=5),
    180,
    250,
    TIPO_COLORES('GRIS')
);

INSERT INTO TABLA_COMPONENTES VALUES(
    2,
    'AMD Ryzen 7 2700X',
    (SELECT REF(TF) FROM TABLA_FABRICANTES TF WHERE codFab=6),
    240,
    300,
    TIPO_COLORES('GRIS')
);


INSERT INTO TABLA_COMPONENTES VALUES(
    3,
    'Motherboard XT3345 LGA 1151 (Socket H4)',
    (SELECT REF(TF) FROM TABLA_FABRICANTES TF WHERE codFab=1),
    45,
    120,
    TIPO_COLORES('VERDE')
);

INSERT INTO TABLA_COMPONENTES VALUES(
    4,
    'Motherboard AM9880 (Socket AM4)',
    (SELECT REF(TF) FROM TABLA_FABRICANTES TF WHERE codFab=1),
    35,
    100,
    TIPO_COLORES('VERDE', 'AZUL')
);

INSERT INTO TABLA_COMPONENTES VALUES(
    5,
    'WD Blue 1TB 3.5" 7500 RPM',
    (SELECT REF(TF) FROM TABLA_FABRICANTES TF WHERE codFab=4),
    65,
    79,
    TIPO_COLORES('GRIS', 'AZUL', 'BLANCO')
);

INSERT INTO TABLA_ORDENADORES VALUES(
    1,
    'Ordenador Intel Basico',
    TABLA_LISTA_COMPONENTES(
        TIPO_LISTA_COMPONENTES(
            1,
            (SELECT REF(TC) FROM TABLA_COMPONENTES TC WHERE codComp=1)
        ),
        TIPO_LISTA_COMPONENTES(
            1,
            (SELECT REF(TC) FROM TABLA_COMPONENTES TC WHERE codComp=3)
        ),
        TIPO_LISTA_COMPONENTES(
            3,
            (SELECT REF(TC) FROM TABLA_COMPONENTES TC WHERE codComp=4)
        )
    )
);

COMMIT;

CREATE OR REPLACE PROCEDURE sp_FABRICANTE (codigo NUMBER) IS
    contador NUMBER;
    separador VARCHAR2(60);
    fabricante TIPO_FABRICANTE;
    fabricanteTemporal TIPO_FABRICANTE;
    CURSOR CURSOR1 IS SELECT * FROM TABLA_COMPONENTES;
    xCURSOR1 CURSOR1%ROWTYPE;
BEGIN
    separador := '------------------------------------------------------------';
    DBMS_OUTPUT.PUT_LINE(separador);
    SELECT VALUE(F) INTO fabricante FROM TABLA_FABRICANTES F WHERE codFab = codigo;
    DBMS_OUTPUT.PUT_LINE('Fabricante: '||fabricante.nombre);
    DBMS_OUTPUT.PUT_LINE(separador);
    DBMS_OUTPUT.PUT_LINE('Listado de Componentes');
    OPEN CURSOR1;
        LOOP 
            FETCH CURSOR1 INTO XCURSOR1;    
            EXIT WHEN CURSOR1%NOTFOUND;
                SELECT DEREF(xCURSOR1.refFab) INTO fabricanteTemporal FROM DUAL;
                IF fabricante.codFab = fabricanteTemporal.codFab THEN
                    DBMS_OUTPUT.PUT_LINE(separador);
                    DBMS_OUTPUT.PUT_LINE('Código: ' || xCURSOR1.codComp);
                    DBMS_OUTPUT.PUT_LINE('Descripción: ' || xCURSOR1.descripcion);
                    DBMS_OUTPUT.PUT_LINE('Precio Fabricante: ' || xCURSOR1.precioFab);
                    DBMS_OUTPUT.PUT_LINE('Precio Pvp: ' || xCURSOR1.precioPvp);
                    DBMS_OUTPUT.PUT('Colores: ');
                    contador := 1;
                    WHILE contador <=xCURSOR1.color.COUNT LOOP
                        DBMS_OUTPUT.PUT(xCURSOR1.color(contador)|| ' ');
                        contador := contador + 1;
                    END LOOP;
                    DBMS_OUTPUT.PUT_LINE('');
                END IF;
        END LOOP;
    CLOSE CURSOR1;
    DBMS_OUTPUT.PUT_LINE(separador);
END;

SET SERVEROUTPUT ON;
BEGIN
    sp_FABRICANTE(5);
END; 

SET SERVEROUTPUT ON;
DECLARE
    ordenador TIPO_ORDENADOR;
BEGIN
     SELECT VALUE(T_O) into ordenador FROM TABLA_ORDENADORES T_O WHERE codOrd = 1;
     --ordenador.spComposicion();
     ordenador.spComposicionDetallada();
END; 

SELECT T_O.fBeneficio() FROM TABLA_ORDENADORES T_O WHERE codOrd = 1;
