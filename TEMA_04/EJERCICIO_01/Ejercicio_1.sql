DROP TABLE ALUMNOS;

DROP TYPE T_ALUMNOS;
DROP TYPE V_NOTAS;
DROP TYPE T_NOTA;

COMMIT;

CREATE OR REPLACE TYPE T_NOTA AS OBJECT (
    codAsig VARCHAR2(5),
    curAsig NUMBER(1),
    curAcad VARCHAR2(9),
    nPrEval NUMBER(2),
    nSgEval NUMBER(2),
    nFnEval NUMBER(2)
);

CREATE OR REPLACE TYPE V_NOTAS AS VARRAY(10) OF T_NOTA;

CREATE OR REPLACE TYPE T_ALUMNOS AS OBJECT (
    nMatricula NUMBER(10),
    nombre VARCHAR2(100),
    fechNac DATE,
    fechIng DATE,
    notas V_NOTAS,
    MEMBER FUNCTION Get_DatosAlumno RETURN VARCHAR2,
    MEMBER PROCEDURE Set_FechaIngreso (matricula NUMBER, fecha VARCHAR2),
    MEMBER FUNCTION Num_Asignaturas RETURN VARCHAR2,
    MEMBER FUNCTION Asig_Suspensas RETURN VARCHAR2,
    MEMBER FUNCTION Notas_Curso (curso NUMBER) RETURN VARCHAR2,
    MEMBER FUNCTION Aprobado_Curso (curso NUMBER) RETURN VARCHAR2,
    MEMBER FUNCTION Aprobar_Eval RETURN VARCHAR2,
    MEMBER FUNCTION Alta_Notas (codAsig VARCHAR2, curAsig NUMBER, curAcad VARCHAR2, nPrEval NUMBER, nSgEval NUMBER, nFnEval NUMBER) RETURN VARCHAR2
);

CREATE TABLE ALUMNOS OF T_ALUMNOS (nMatricula PRIMARY KEY);

CREATE OR REPLACE TYPE BODY T_ALUMNOS AS
    MEMBER FUNCTION Get_DatosAlumno RETURN VARCHAR2 IS
        edad NUMBER(10);
    BEGIN
        select floor(months_between(CURRENT_DATE, fechNac) /12) INTO edad from dual;
        RETURN 'Matricula: ' || nMatricula || ', Nombre: ' || nombre || ', Edad: ' || edad;
    END;
    
    MEMBER PROCEDURE Set_FechaIngreso (matricula NUMBER, fecha VARCHAR2) AS
    BEGIN
        UPDATE ALUMNOS SET fechnac =  TO_DATE(fecha, 'yyyy/mm/dd') WHERE nMatricula = matricula;
    END;
    MEMBER FUNCTION Num_Asignaturas RETURN VARCHAR2 IS
        total V_NOTAS;
    BEGIN
        SELECT notas INTO total FROM ALUMNOS WHERE nMatricula = SELF.nMatricula;
        RETURN 'Total asignaturas: ' || notas.COUNT;
    END;
    
    MEMBER FUNCTION Asig_Suspensas RETURN VARCHAR2 IS
        vAlumnos T_ALUMNOS;
        contador NUMBER;
        temp VARCHAR2(500);
    BEGIN
        SELECT VALUE(a) INTO vAlumnos FROM ALUMNOS a WHERE nMatricula = SELF.nMatricula;
        contador := 1;
        WHILE contador <=vAlumnos.notas.COUNT LOOP
            IF vAlumnos.notas(contador).nFnEval < 5 THEN
                temp :=  temp || vAlumnos.notas(contador).codAsig ||', ';
            END IF;
            contador := contador + 1;
        END LOOP;
        IF temp = '' THEN
            temp:= 'Ninguna asignatura suspensa';
        ELSE
            temp:= 'Asignaturas suspendidas: ' || temp; 
        END IF;
        RETURN temp;
    END;
    
    MEMBER FUNCTION Notas_Curso (curso NUMBER) RETURN VARCHAR2 IS
        vAlumnos T_ALUMNOS;
        contador NUMBER;
        temp VARCHAR2(1000);
    BEGIN
        SELECT VALUE(a) INTO vAlumnos FROM ALUMNOS a WHERE nMatricula = SELF.nMatricula;
        contador := 1;
        WHILE contador <=vAlumnos.notas.COUNT LOOP
            IF vAlumnos.notas(contador).curAsig = curso THEN
                temp:= temp || 'Asignatura: ' || vAlumnos.notas(contador).codAsig 
                || ', Nota 1: ' || vAlumnos.notas(contador).nPrEval
                || ', Nota 2: ' || vAlumnos.notas(contador).nSgEval
                || ', Nota Final: ' || vAlumnos.notas(contador).nFnEval || ' --- ';
            END IF;
            contador := contador + 1;
        END LOOP;
        RETURN temp;
    END;
    
    MEMBER FUNCTION Aprobado_Curso (curso NUMBER) RETURN VARCHAR2 IS
        vAlumnos T_ALUMNOS;
        contador NUMBER;
        temp VARCHAR2(500);
        aprobado BOOLEAN;
    BEGIN
        SELECT VALUE(a) INTO vAlumnos FROM ALUMNOS a WHERE nMatricula = SELF.nMatricula;
        contador := 1;
        aprobado := TRUE;
        WHILE contador <=vAlumnos.notas.COUNT LOOP
            IF vAlumnos.notas(contador).curAsig = curso AND vAlumnos.notas(contador).nFnEval <= 4 THEN
                aprobado:= FALSE;
            END IF;
            contador := contador + 1;
        END LOOP;
        
        IF aprobado = TRUE THEN
            temp:= 'Curso Aprobado';
        ELSE
            temp:= 'Curso Suspenso'; 
        END IF;
        RETURN temp;
    END;
    
    MEMBER FUNCTION Aprobar_Eval RETURN VARCHAR2 IS
        vAlumnos T_ALUMNOS;
        contador NUMBER;
        arrayNotas V_NOTAS;
        PRAGMA AUTONOMOUS_TRANSACTION; --Permite Realizar insert, update,...
    BEGIN
        SELECT VALUE(a) INTO vAlumnos FROM ALUMNOS a WHERE nMatricula = SELF.nMatricula;
        contador := 1;
        arrayNotas := vAlumnos.notas;
        WHILE contador <=vAlumnos.notas.COUNT LOOP
            IF arrayNotas(contador).nFnEval >= 5 THEN
                IF arrayNotas(contador).nPrEval <= 5 THEN
                    arrayNotas(contador).nPrEval := 5;
                END IF;
                IF arrayNotas(contador).nSgEval <= 5 THEN
                    arrayNotas(contador).nSgEval := 5;
                END IF;
            END IF;
            contador := contador + 1;
        END LOOP;
        UPDATE ALUMNOS a SET a.notas = arrayNotas WHERE nMatricula = SELF.nMatricula;
        COMMIT;
        RETURN 'Notas actualizadas';
    END;
    
    MEMBER FUNCTION Alta_Notas (codAsig VARCHAR2, curAsig NUMBER, curAcad VARCHAR2, nPrEval NUMBER, nSgEval NUMBER, nFnEval NUMBER) RETURN VARCHAR2 IS
        vAlumnos T_ALUMNOS;
        arrayNotas V_NOTAS;
        PRAGMA AUTONOMOUS_TRANSACTION; --Permite Realizar insert, update,...
    BEGIN
        SELECT VALUE(a) INTO vAlumnos FROM ALUMNOS a WHERE nMatricula = SELF.nMatricula;
        arrayNotas := vAlumnos.notas;
        IF arrayNotas.COUNT < arrayNotas.LIMIT THEN
            arrayNotas.EXTEND;
            arrayNotas(arrayNotas.COUNT):= T_NOTA(codAsig, curAsig, curAcad, nPrEval, nSgEval, nFnEval);
        END IF;
        UPDATE ALUMNOS a SET a.notas = arrayNotas WHERE nMatricula = SELF.nMatricula;
        COMMIT;
        RETURN 'Notas insertada';
    END;
END;

COMMIT;

INSERT INTO ALUMNOS VALUES (
    1, 
    'PRUEBA', 
    TO_DATE('1990/01/01', 'yyyy/mm/dd'),
    TO_DATE('2010/01/01', 'yyyy/mm/dd'), 
    V_NOTAS(
    T_NOTA('BD',1,'2018',5,5,5), 
    T_NOTA('EMP',1,'2018',2,2,3),
    T_NOTA('FOL',1,'2018',2,2,3)
    )
);

SELECT a.* FROM ALUMNOS a;
SELECT a.Get_DatosAlumno() FROM ALUMNOS a;
SELECT a.Num_Asignaturas() FROM ALUMNOS a;
SELECT a.Asig_Suspensas() FROM ALUMNOS a;
SELECT a.Notas_Curso(1) FROM ALUMNOS a;
SELECT a.Aprobado_Curso(1) FROM ALUMNOS a;
SELECT a.Aprobar_Eval() FROM ALUMNOS a WHERE nmatricula = 1;
SELECT a.Alta_Notas('SGBD',1,'2018',5,5,5) FROM ALUMNOS a WHERE nmatricula = 1;

SET SERVEROUTPUT ON;

DECLARE
xAlumno T_ALUMNOS;
BEGIN
     SELECT VALUE(a) into xAlumno FROM ALUMNOS a WHERE nmatricula = 1;
     xAlumno.Set_FechaIngreso(1,'2012-01-01');
END;


