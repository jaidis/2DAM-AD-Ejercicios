DROP TABLE PARTIDOS;
DROP TABLE EQUIPOS;
DROP TABLE ARBITROS;

DROP TYPE t_PARTIDO;
DROP TYPE t_EQUIPO;
DROP TYPE t_ARBITRO;

COMMIT;

CREATE OR REPLACE TYPE t_ARBITRO AS OBJECT
(
 cCodArb NUMBER(10),
 cNomArb varchar2(100)
);

CREATE OR REPLACE TYPE t_EQUIPO AS OBJECT
(
 cCodEqu NUMBER(10),
 cNomEqu VARCHAR2(50),
 cCiudad VARCHAR2(20),
 cPabellon VARCHAR2(50)
);

CREATE OR REPLACE TYPE t_PARTIDO AS OBJECT
(
 nNumPartido NUMBER(10),
 EquLocal REF t_EQUIPO,
 EquVisit REF t_EQUIPO,
 Arbitro REF t_ARBITRO,
 PtosLocal NUMBER(10),
 PtosVisit NUMBER(10),
 MEMBER FUNCTION fGanador RETURN VARCHAR2,
 MEMBER FUNCTION fGanador2 RETURN VARCHAR2,
 MEMBER FUNCTION fDiferencia RETURN NUMBER,
 MEMBER PROCEDURE spImprimeFichaPartido
);

CREATE OR REPLACE TYPE BODY t_PARTIDO AS
    MEMBER FUNCTION fDiferencia RETURN NUMBER IS
    BEGIN
        RETURN PtosLocal - PtosVisit;
    END;
    MEMBER FUNCTION fGanador RETURN VARCHAR2 IS
    BEGIN
        IF fDiferencia() > 0 THEN
            RETURN 'Ha ganado el Local';
        ELSE
            RETURN 'Ha ganado el Visitante o han empatado';
        END IF;
    END;
    MEMBER FUNCTION fGanador2 RETURN VARCHAR2 IS
        xVarLocal t_EQUIPO;
    BEGIN
        IF fDiferencia() > 0 THEN
            SELECT DEREF(EquLocal) into xVarLocal FROM DUAL;
            RETURN xVarLocal.cNomEqu;
        ELSE
            SELECT DEREF(EquVisit) into xVarLocal FROM DUAL;
            RETURN xVarLocal.cNomEqu;
        END IF;
    END;
    
    MEMBER PROCEDURE spImprimeFichaPartido IS
        xLocal t_EQUIPO;
        xVisit t_EQUIPO;
        xArbitro t_ARBITRO;
    BEGIN
        SELECT DEREF(EquLocal) into xLocal FROM DUAL;
        SELECT DEREF(EquVisit) into xVisit FROM DUAL;
        SELECT DEREF(Arbitro) into xArbitro FROM DUAL;
        DBMS_OUTPUT.PUT_LINE(xLocal.cNomEqu || ' - ' || 
        xVisit.cNomEqu || ' - ' || 
        xArbitro.cNomArb || ' - ' || 
        SELF.PtosLocal || ' - ' || 
        SELF.PtosVisit || ' - ' || 
        SELF.fDiferencia || ' - ' ||
        SELF.fGanador);
    END;
END;

CREATE TABLE ARBITROS OF t_ARBITRO (cCodArb PRIMARY KEY);
CREATE TABLE EQUIPOS OF t_EQUIPO (cCodEqu PRIMARY KEY);
CREATE TABLE PARTIDOS OF t_PARTIDO;
COMMIT;
/*
ALTER TABLE PARTIDOS ADD (SCOPE FOR (EquLocal) is EQUIPOS);
ALTER TABLE PARTIDOS ADD (SCOPE FOR (EquLocal) is EQUIPOS);
COMMIT;
*/

INSERT INTO ARBITROS VALUES(1, 'Kyle Smith');
INSERT INTO ARBITROS VALUES(2, 'John McGregor');
INSERT INTO ARBITROS VALUES(3, 'Jayne Wayne');
COMMIT;

INSERT INTO EQUIPOS VALUES(1,'RM','MADRID','P MA');
INSERT INTO EQUIPOS VALUES(2,'UNI','MALAGA', 'MARTIN CARPENA');
INSERT INTO EQUIPOS VALUES(3,'JOV','BADALONA','P BADA');
COMMIT;

INSERT INTO PARTIDOS VALUES (
 1,
 (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 1), 
 (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 2),
 (SELECT REF(a) FROM ARBITROS a WHERE cCodArb = 1),
 50,70);

INSERT INTO PARTIDOS VALUES (
 2,
 (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 2), 
 (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 3),
 (SELECT REF(a) FROM ARBITROS a WHERE cCodArb = 2),
 85,70);
 
 INSERT INTO PARTIDOS VALUES (
 3,
(SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 1),
(SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 3),
(SELECT REF(a) FROM ARBITROS a WHERE cCodArb = 3),
33,33);

COMMIT;
 
 SELECT * FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia() FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia(),p.fGanador() FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia(),p.fGanador(), p.fGanador2() FROM PARTIDOS p;
 
UPDATE PARTIDOS SET 
EquLocal = (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 2)
WHERE PtosLocal = 33;

COMMIT;


DROP PROCEDURE sp_Imprime;

CREATE OR REPLACE PROCEDURE sp_Imprime (numero NUMBER) IS
    xPartido t_PARTIDO;
BEGIN
    SELECT VALUE(p) into xPartido FROM PARTIDOS p WHERE nNumPartido = numero;
    xPartido.spImprimeFichaPartido();
END;

CREATE OR REPLACE PROCEDURE sp_ImprimeTodos IS
    PART t_Partido;
    CURSOR C IS SELECT VALUE (P) FROM PARTIDOS P;
    BEGIN
        OPEN C;
        LOOP
            FETCH C INTO PART;
                C.spImprimeFichaPartido();
            EXIT WHEN C%NOTFOUND;
        END LOOP;
        CLOSE C;
    END;
END;

SET SERVEROUTPUT ON;

DECLARE

BEGIN
    sp_Imprime(1);
END;

/*
CREAR UNA TABLA XXX
*/

CREATE TABLE XXX(
    NUMPARTJUGADOS NUMBER(10);
);

/*
    AUTOINCREMENT VERSION PROPIA
*/

DROP SEQUENCE NUMPARTJUGADOS_AUTO;

CREATE SEQUENCE NUMPARTJUGADOS_AUTO;

CREATE OR REPLACE TRIGGER PARTIDOS_TRIGGER
BEFORE INSERT ON PARTIDOS
FOR EACH ROW
BEGIN
  SELECT NUMPARTJUGADOS_AUTO.nextval
  INTO :new.nNumPartido
  FROM dual;
END;

/*
    AUTOINCREMENT SIMPLIFICADO
*/

CREATE OR REPLACE TRIGGER trPartidos
AFTER INSERT ON PARTIDOS
BEGIN
    UPDATE XXX SET NUMPARTIDOSJUGADOS = NUMPARTJUGADOS + 1;
END;

/*
    COMPROBAR EL ULTIMO PARTIDO QUE SE HA JUGADO
*/

CREATE OR REPLACE TRIGGER trPartidosJugadosFinal
AFTER INSERT ON PARTIDOS
BEGIN
    INSERT INTO XXX VALUES (:NEW.NUMPARTJUGADOS);
END;



























