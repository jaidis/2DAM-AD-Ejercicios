DROP TABLE PARTIDOS;
DROP TABLE EQUIPOS;
DROP TABLE ARBITROS;

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
 EquLocal REF t_EQUIPO,
 EquVisit REF t_EQUIPO,
 PtosLocal NUMBER(10),
 PtosVisit NUMBER(10),
 MEMBER FUNCTION fGanador RETURN VARCHAR2,
 MEMBER FUNCTION fGanador2 RETURN VARCHAR2,
 MEMBER FUNCTION fDiferencia RETURN NUMBER
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
        xVarLocal t_Equipo;
    BEGIN
        IF fDiferencia() > 0 THEN
            SELECT DEREF(EquLocal) into xVarLocal FROM DUAL;
            RETURN xVarLocal.cNomEqu;
        ELSE
            SELECT DEREF(EquVisit) into xVarLocal FROM DUAL;
            RETURN xVarLocal.cNomEqu;
        END IF;
    END;
END;

CREATE TABLE ARBITROS OF t_ARBITRO (cCodArb PRIMARY KEY);
CREATE TABLE EQUIPOS OF t_EQUIPO (cCodEqu PRIMARY KEY);
CREATE TABLE PARTIDOS OF t_PARTIDO;
COMMIT;

ALTER TABLE PARTIDOS ADD (SCOPE FOR (EquLocal) is EQUIPOS);
ALTER TABLE PARTIDOS ADD (SCOPE FOR (EquLocal) is EQUIPOS);
COMMIT;

INSERT INTO EQUIPOS VALUES(1,'RM','MADRID','P MA');
INSERT INTO EQUIPOS VALUES(2,'UNI','MALAGA', 'MARTIN CARPENA');
INSERT INTO EQUIPOS VALUES(3,'JOV','BADALONA','P BADA');
COMMIT;

INSERT INTO PARTIDOS VALUES (
 (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 1), (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 2),50,70);

INSERT INTO PARTIDOS VALUES (
 (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 2), (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 3),85,70);
 
 INSERT INTO PARTIDOS VALUES (
(SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 1),(SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 3),33,33);

COMMIT;
 
 SELECT * FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia() FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia(),p.fGanador() FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia(),p.fGanador(), p.fGanador2() FROM PARTIDOS p;
 
UPDATE PARTIDOS SET 
EquLocal = (SELECT REF(e) FROM EQUIPOS e WHERE cCodEqu = 2)
WHERE PtosLocal = 33;

COMMIT;