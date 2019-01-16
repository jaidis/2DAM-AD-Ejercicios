CREATE OR REPLACE TYPE t_ARBITRO AS OBJECT
(
 cCodArb NUMBER(10),
 cNomArb varchar2(100)
);

CREATE TABLE ARBITROS OF t_ARBITRO (cCodArb PRIMARY KEY);

CREATE OR REPLACE TYPE t_EQUIPO AS OBJECT
(
 cCodEqu NUMBER(10),
 cNomEqu VARCHAR2(50),
 cCiudad VARCHAR2(20),
 cPabellon VARCHAR2(50)
);

CREATE TABLE EQUIPOS OF t_EQUIPO (cCodEqu PRIMARY KEY);

CREATE OR REPLACE TYPE t_PARTIDO AS OBJECT
(
 EquLocal t_EQUIPO,
 EquVisit t_EQUIPO,
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
    BEGIN
        IF fDiferencia() > 0 THEN
            RETURN SELF.EquLocal.cNomEqu;
        ELSE
            RETURN SELF.EquVisit.cNomEqu;
        END IF;
    END;
END;

CREATE TABLE PARTIDOS OF t_PARTIDO;

DROP TABLE PARTIDOS;

INSERT INTO PARTIDOS VALUES (
t_EQUIPO(1,'RM','MADRID','P MA'), t_EQUIPO(2,'UNI','MALAGA', 'MARTIN CARPENA'),50,70);

INSERT INTO PARTIDOS VALUES (
 t_EQUIPO(2,'UNI','MALAGA', 'MARTIN CARPENA'),t_EQUIPO(3,'JOV','BADALONA','P BADA'),85,70);
 
 INSERT INTO PARTIDOS VALUES (
 t_EQUIPO(1,'RM','MADRID','P MA'),t_EQUIPO(3,'JOV','BADALONA','P BADA'),33,33);
 
 SELECT * FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia() FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia(),p.fGanador() FROM PARTIDOS p;
 
 SELECT p.*, p.fDiferencia(),p.fGanador(), p.fGanador2() FROM PARTIDOS p;