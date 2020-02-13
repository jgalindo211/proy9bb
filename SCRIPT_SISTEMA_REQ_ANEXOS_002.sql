---------------------------------------------------------------------------
-- TABLES
---------------------------------------------------------------------------
-- CIF_REF_REQUERIMIENTOS
CREATE TABLE CREDIFIS.CIF_REQ_REQUERIMIENTOS
  (
    REQ_ID NUMBER(10,0) NOT NULL,
    REQ_ID_ESTADO VARCHAR2(3 BYTE) NOT NULL,
	REQ_USER_ID VARCHAR2(30 BYTE) NOT NULL,
	REQ_INSTITUCION VARCHAR2(15 BYTE),
	REQ_FECHA DATE,
	REQ_ADICIONADO_POR VARCHAR2(30),
	REQ_FECHA_ADICION DATE,
	REQ_MODIFICADO_POR VARCHAR2(30),
	REQ_FECHA_MODIFICACION DATE,
	CONSTRAINT CIF_REQ_PK PRIMARY KEY (REQ_ID),
	CONSTRAINT CIF_REQ_INSTITUCION_FK FOREIGN KEY (REQ_INSTITUCION)
	  REFERENCES CREDIFIS.CIF_INSTITUCIONES (COD_CLIENTE) ENABLE
	
  );
  

CREATE OR REPLACE PUBLIC SYNONYM CIF_REQ_REQUERIMIENTOS FOR CREDIFIS.CIF_REF_REQUERIMIENTOS;
 
-- CIF_ANR_ANEXOS_REQ
CREATE TABLE CREDIFIS.CIF_ANR_ANEXOS_REQ
  (
    ANR_ID NUMBER(10,0) NOT NULL,
    ANR_REQ_ID NUMBER(10,0),
	ANR_ARCHIVO BLOB,
	ANR_NOMBRE_ARCHIVO VARCHAR2(250),
	ANR_TIPO_DOC VARCHAR2(1),
	ANR_TIPO_AREA VARCHAR2(2 BYTE),
	ANR_ADICIONADO_POR VARCHAR2(30),
	ANR_FECHA_ADICION DATE,
	ANR_MODIFICADO_POR VARCHAR2(30),
	ANR_FECHA_MODIFICADO DATE,
	CONSTRAINT CIF_ANR_PK PRIMARY KEY (ANR_ID),
	CONSTRAINT CIF_ANR_REQ_FK FOREIGN KEY (ANR_REQ_ID)
	  REFERENCES CREDIFIS.CIF_REQ_REQUERIMIENTOS (REQ_ID) ENABLE
  );

CREATE OR REPLACE PUBLIC SYNONYM CIF_ANR_ANEXOS_REQ FOR CREDIFIS.CIF_ANR_ANEXOS_REQ; 

-- CIF_BIR_BITACORA_REQ
CREATE TABLE CREDIFIS.CIF_BIR_BITACORA_REQ
  (
    BIR_ID NUMBER(10,0) NOT NULL,
	BIR_REQ_ID NUMBER(10,0),
    BIR_ID_ESTADO VARCHAR2(3 BYTE) NOT NULL,
	BIR_USER_ID VARCHAR2(30 BYTE) NOT NULL,
	BIR_FECHA TIMESTAMP,
	CONSTRAINT CIF_BIR_PK PRIMARY KEY (BIR_ID),
	CONSTRAINT CIF_BIR_REQ_ID_FK FOREIGN KEY (BIR_REQ_ID)
	  REFERENCES CREDIFIS.CIF_REQ_REQUERIMIENTOS (REQ_ID) ENABLE
	
  );

CREATE OR REPLACE PUBLIC SYNONYM CIF_BIR_BITACORA_REQ FOR CREDIFIS.CIF_BIR_BITACORA_REQ;

-- CIF_MAU_MAIL_UNIT
CREATE TABLE CREDIFIS.CIF_MAU_MAIL_UNIT
  (
    MAU_ID NUMBER(10,0) NOT NULL,
    MAU_VAO_ID VARCHAR2(4) NOT NULL,
    MAU_USER_ID VARCHAR2(8) NOT NULL,
    MAU_EMAIL VARCHAR2(50) NOT NULL,
    CONSTRAINT CIF_MAU_PK PRIMARY KEY (MAU_ID)
  );

CREATE OR REPLACE PUBLIC SYNONYM CIF_MAU_MAIL_UNIT FOR CREDIFIS.CIF_MAU_MAIL_UNIT;

CREATE TABLE CREDIFIS.CIF_ANH_ANEXOS_HASH
  (
    ANH_ID NUMBER(10,0) NOT NULL,
	ANH_USER_ID VARCHAR2(30 BYTE) NOT NULL,
	ANH_INSTITUCION VARCHAR2(15 BYTE),
	ANH_FECHA DATE,
	ANH_HASH CLOB,
	ANH_ESTADO VARCHAR2(3 BYTE),
	CONSTRAINT CIF_ANH_PK PRIMARY KEY (ANH_ID)
  );

CREATE OR REPLACE PUBLIC SYNONYM CIF_ANH_ANEXOS_HASH FOR CREDIFIS.CIF_ANH_ANEXOS_HASH; 
  
--SECUENCIA PARA LOS REQUERIMIENTOS
CREATE SEQUENCE CREDIFIS.CIF_REQ_PK_SEQ MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;

--SECUENCIA PARA LOS ANEXOS DE REQUERIMIENTOS
CREATE SEQUENCE CREDIFIS.CIF_ANR_PK_SEQ MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;

--SECUENCIA PARA BITACORA DE REQUERIMIENTOS
CREATE SEQUENCE CREDIFIS.CIF_BIR_PK_SEQ MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;

--SECUENCIA PARA MANEJO DE CORREO DE AREAS
CREATE SEQUENCE CREDIFIS.CIF_MAU_PK_SEQ MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;

--SECUENCIA PARA LOS ANEXOS HASH
CREATE SEQUENCE CREDIFIS.CIF_ANH_PK_SEQ MINVALUE 1 MAXVALUE 9999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;


---------------------------
--insert CIF_MAU_MAIL_UNIT
---------------------------
  
  INSERT INTO CREDIFIS.CIF_MAU_MAIL_UNIT (MAU_ID,MAU_VAO_ID,MAU_USER_ID,MAU_EMAIL)
  VALUES (
  CREDIFIS.CIF_MAU_PK_SEQ.NEXTVAL,
  '5102',
  '3358',
  'JAIME.GALINDO@BANDESAL.GOB.SV'
  );
    
  INSERT INTO CREDIFIS.CIF_MAU_MAIL_UNIT  (MAU_ID,MAU_VAO_ID,MAU_USER_ID,MAU_EMAIL)
  VALUES (
  CREDIFIS.CIF_MAU_PK_SEQ.NEXTVAL,
  '4003',
  '3306',
  'ROXANA.MASSIN@BANDESAL.GOB.SV'
  );
    
  
---------------------------
--update PR_CORREOS
---------------------------

UPDATE PR.PR_CORREOS SET COR_ESTADO='A' WHERE COR_CORREO='ROXANA.MASSIN@BANDESAL.GOB.SV';
UPDATE PR.PR_CORREOS SET COR_ESTADO='A' WHERE COR_CORREO='JAIME.GALINDO@BANDESAL.GOB.SV';


---------------------------
-- CREDIFIS.CIF_ID_USUARIO
---------------------------
UPDATE CREDIFIS.CIF_ID_USUARIO SET IUS_INSTITUCION='51002108699' WHERE IUS_ID_USUARIO='RMASSIN';
UPDATE CREDIFIS.CIF_ID_USUARIO SET IUS_INSTITUCION='51002108699' WHERE IUS_ID_USUARIO='JGALINDO';
  


---------------------------
--insert tablas de correos
---------------------------

INSERT INTO PR.PR_SERVICIOS_X_CORREOS (
COD_SERVICIO,
DESCRIPCION,
ESTADO,
UTL_NOTIFICADOR,
UTL_EMAIL,
PERIODICIDAD) VALUES (
'CIF_NOTIFICACION_AREAS',
'REGISTRO DE NOTIFICACION DE AREAS',
'A',
'N',
'S',
'D'
);

INSERT INTO PR.PR_PLANTILLA_CORREOS (
CODIGO_EMPRESA,
COD_SERVICIO,
ASUNTO,
CUERPO,
PIE_PAGINA
) VALUES (
32,
'CIF_NOTIFICACION_AREAS',
'Solicitud de Requerimiento <#REQ_ESTADO#>',
'<p> <span style="font-family:verdana;font-size:12">Estimados:</span><br/><br/><span style="font-family:verdana;font-size:12">Se le comunica que la solicitud <#REQ_ID#> a nombre de <#REQ_NOMBRE#> ha sido</span> <span style="font-family:verdana;font-size:12;font-weight:bold"><u><#REQ_ESTADO#></u></span><span style="font-family:verdana;font-size:12">. Proceda a aprobarla.</span></p>',
'<span style="font-family:verdana;font-size:12">Atentamente, <br/> Sistema de Anexos de Requerimientos</span>'
);

INSERT INTO PR.PR_DETALLE_X_CORREOS 
VALUES (
32,
23,
'CIF_NOTIFICACION_AREAS'
);

UPDATE PR.PR_PLANTILLA_CORREOS SET
CUERPO='<p> <span style="font-family:verdana;font-size:12">Estimado(a) <#REQ_NOMBRE#>:</span><br/><br/><span style="font-family:verdana;font-size:12">Se le comunica que su solicitud <#REQ_ID#> ha sido</span> <span style="font-family:verdana;font-size:12;font-weight:bold"><u><#REQ_ESTADO#></u></span>.</p>'
WHERE COD_SERVICIO='CIF_NOTIFICA_RECHAZO';


create or replace PROCEDURE          CREDIFIS.ELIMINAR_HASH (P_USUARIO			    VARCHAR2,
                                                    P_ID_INSTITUCION  VARCHAR2,
                                                    P_ESTADO          VARCHAR2,
                                                    P_ERROR           OUT VARCHAR2) 
   IS

   BEGIN
	
    DELETE FROM CIF_ANH_ANEXOS_HASH
    WHERE 
      ANH_USER_ID      = P_USUARIO AND
      ANH_INSTITUCION  = P_ID_INSTITUCION AND
      ANH_FECHA        = TRUNC(SYSDATE) AND
      ANH_ESTADO       = P_ESTADO;

   EXCEPTION
      WHEN OTHERS
      THEN
         P_ERROR := 'No se pudo eliminar anexos hash. ' || SQLERRM;
   END;

   
create or replace PROCEDURE          CREDIFIS.GUARDAR_ANEXOS_HASH (
                                P_SOLICITUD             OUT NUMBER,
                                P_USUARIO			          VARCHAR2,
                                P_ID_INSTITUCION        VARCHAR2,
                                P_HASH                  VARCHAR2,
                                P_ESTADO                VARCHAR2,
                                P_ERROR                 OUT VARCHAR2)                                                                -- 26
   IS
      V_CORR        NUMBER (10);

   BEGIN
         -- ID DEL ANEXOS HASH
         SELECT CIF_ANH_PK_SEQ.NEXTVAL INTO V_CORR FROM DUAL;
	
         INSERT INTO CIF_ANH_ANEXOS_HASH    (ANH_ID,
                                             ANH_USER_ID,
                                             ANH_INSTITUCION,
                                             ANH_FECHA,
                                             ANH_HASH,
                                             ANH_ESTADO)
              VALUES (V_CORR,
                      P_USUARIO,
                      P_ID_INSTITUCION,
                      TRUNC(SYSDATE),
                      P_HASH,
                      P_ESTADO);
            P_SOLICITUD := V_CORR;

   EXCEPTION
      WHEN OTHERS
      THEN
         P_ERROR := 'No se pudieron guardar los datos hash. ' || SQLERRM;
   END;
   



create or replace PROCEDURE 
   CREDIFIS.GUARDAR_ANEXOS_REQUERIMIENTO (P_ANR_ID		            IN OUT NUMBER,
                                P_ANR_REQ_ID				    NUMBER,
                                P_ANR_ARCHIVO	          BLOB,
                                P_NOMBRE_ARCHIVO        VARCHAR2,
                                P_ANR_TIPO_DOC          VARCHAR2,
								P_ANR_TIPO_AREA			VARCHAR2,
                                P_ANR_ADICIONADO_POR    VARCHAR2,
                                P_ANR_FECHA_ADICION     DATE,
                                P_ERROR              	  OUT VARCHAR2)                                                                -- 26
   IS
      V_ANR_ANEXO_ID     NUMBER (10);

   BEGIN
         -- ID DEL REQUERIMIENTO
         SELECT CIF_ANR_PK_SEQ.NEXTVAL INTO V_ANR_ANEXO_ID FROM DUAL;

		INSERT INTO CIF_ANR_ANEXOS_REQ (ANR_ID,
										ANR_REQ_ID,
										ANR_ARCHIVO,
                    ANR_NOMBRE_ARCHIVO,
                    ANR_TIPO_DOC,
					ANR_TIPO_AREA,
										ANR_ADICIONADO_POR,
										ANR_FECHA_ADICION)
              VALUES (V_ANR_ANEXO_ID,
					  P_ANR_REQ_ID,
                      P_ANR_ARCHIVO,
                      P_NOMBRE_ARCHIVO,
                      P_ANR_TIPO_DOC,
					  P_ANR_TIPO_AREA,
                      P_ANR_ADICIONADO_POR,
                      P_ANR_FECHA_ADICION);
										
         P_ANR_ID := V_ANR_ANEXO_ID;

   EXCEPTION
      WHEN OTHERS
      THEN
         P_ERROR := 'No se pudo guardar el anexo de este requerimiento. ' || SQLERRM;
   END;


create or replace PROCEDURE 
   CREDIFIS.GUARDAR_BITACORA_REQUERIMIENTO (P_BIR_ID		            IN OUT NUMBER,
								P_BIR_REQ_ID				NUMBER,
                                P_BIR_ESTADO	            VARCHAR2,
                                P_BIR_USUARIO		        VARCHAR2,
                                P_BIR_FECHA			        TIMESTAMP,
                                P_ERROR              	    OUT VARCHAR2)                                                                -- 26
   IS
      V_BIR_BITACORA_ID     NUMBER (10);

   BEGIN
         -- ID DE BITACORA DEL REQUERIMIENTO
         SELECT CIF_BIR_PK_SEQ.NEXTVAL INTO V_BIR_BITACORA_ID FROM DUAL;

		INSERT INTO CIF_BIR_BITACORA_REQ (BIR_ID,
										BIR_REQ_ID,
										BIR_ID_ESTADO,
										BIR_USER_ID,
										BIR_FECHA)
              VALUES (V_BIR_BITACORA_ID,
					  P_BIR_REQ_ID,
                      P_BIR_ESTADO,
                      P_BIR_USUARIO,
                      P_BIR_FECHA);
										
         P_BIR_ID := V_BIR_BITACORA_ID;

   EXCEPTION
      WHEN OTHERS
      THEN
         P_ERROR := 'No se pudo guardar bitacora de este requerimiento. ' || SQLERRM;
   END;


create or replace PROCEDURE 
		 CREDIFIS.GUARDAR_REQUERIMIENTO (P_SOLICITUD	            IN OUT NUMBER,
                                P_ESTADO	            VARCHAR2,
                                P_USUARIO			    VARCHAR2,
                                P_ID_INSTITUCION        VARCHAR2,
                                P_FECHA	                DATE,
                                P_ADICIONADO_POR        VARCHAR2,
                                P_FECHA_ADICION         DATE,
                                P_ERROR                 OUT VARCHAR2)
   IS
      V_SOLICITUD        NUMBER (10);

   BEGIN
         -- ID DEL REQUERIMIENTO
         SELECT CIF_REQ_PK_SEQ.NEXTVAL INTO V_SOLICITUD FROM DUAL;
	
         INSERT INTO CIF_REQ_REQUERIMIENTOS (REQ_ID,
                                             REQ_ID_ESTADO,
                                             REQ_USER_ID,
                                             REQ_INSTITUCION,
                                             REQ_FECHA,
                                             REQ_ADICIONADO_POR,
                                             REQ_FECHA_ADICION)
              VALUES (V_SOLICITUD,
                      P_ESTADO,
                      P_USUARIO,
                      P_ID_INSTITUCION,
                      P_FECHA,
                      P_ADICIONADO_POR,
                      P_FECHA_ADICION);

         P_SOLICITUD := V_SOLICITUD;

   EXCEPTION
      WHEN OTHERS
      THEN
         P_ERROR := 'No se pudieron guardar los datos generales del requerimiento. ' || SQLERRM;
   END;

create or replace PROCEDURE 
		 CREDIFIS.ACTUALIZAR_EST_REQUERIMIENTO (P_SOLICITUD	  NUMBER,
                                      P_ESTADO      VARCHAR2,
                                      P_ERROR       OUT VARCHAR2)
   IS

   BEGIN
	
    UPDATE CIF_REQ_REQUERIMIENTOS
    SET REQ_ID_ESTADO = P_ESTADO
    WHERE 
      REQ_ID = P_SOLICITUD;

   EXCEPTION
      WHEN OTHERS
      THEN
         P_ERROR := 'No se pudo modificar estado del requerimiento. ' || SQLERRM;
   END;


create or replace PROCEDURE 
		 CREDIFIS.ELIMINAR_REQUERIMIENTO (P_SOLICITUD	          NUMBER,
                             P_ERROR                OUT VARCHAR2)
   IS

   BEGIN
	
    DELETE FROM CIF_ANR_ANEXOS_REQ
    WHERE 
      ANR_ID          = P_SOLICITUD;

   EXCEPTION
      WHEN OTHERS
      THEN
         P_ERROR := 'No se pudo modificar el requerimiento. ' || SQLERRM;
   END;

      
---------------------------------------------------
--creación store procedure para enviar correo áreas
---------------------------------------------------

create or replace PROCEDURE 
		          CREDIFIS.ENVIAR_CORREO_AREAS  (P_SOLICITUD	        NUMBER,
                                P_USUARIO			          VARCHAR2,
                                P_ESTADO                VARCHAR2,
                                P_SISTEMA               VARCHAR2,
                                P_CODIGO_AREA           VARCHAR2,
                                P_ERROR                 OUT VARCHAR2)
   IS
      CURSOR SOLICITUDES
      IS
         SELECT IUS_NOMBRE_USUARIO AS NOMBRE
           FROM CIF_REQ_REQUERIMIENTOS
                INNER JOIN CIF_ID_USUARIO IUS ON (REQ_USER_ID = IUS.IUS_ID_USUARIO AND IUS.IUS_COD_SISTEMA = P_SISTEMA)
          WHERE REQ_ID = P_SOLICITUD AND REQ_USER_ID=P_USUARIO;

      V_ID_REQ        VARCHAR2(10);
      V_ASUNTO        VARCHAR2 (2000) := '';
      V_CUERPO        VARCHAR2 (2000) := '';
      V_PIE           VARCHAR2 (2000) := '';
      V_REMITENTE     VARCHAR2 (255)  := '';
      V_DESTINATARIO  VARCHAR2 (255)  := '';

   BEGIN
      V_ID_REQ := TO_CHAR(P_SOLICITUD);
      
      --BUSCAR EL CORREO DEL DESTINATARIO DEL AREA RESPONSABLE DE AUTORIZAR
      BEGIN
           SELECT MAU_EMAIL AS CORREO
           INTO V_DESTINATARIO
           FROM CIF_MAU_MAIL_UNIT UNI
		   INNER JOIN PR.PR_CORREOS COR ON (UNI.MAU_EMAIL = COR.COR_CORREO)
          WHERE MAU_VAO_ID = P_CODIGO_AREA
		  AND COR.COR_ESTADO='A';
      EXCEPTION
        WHEN NO_DATA_FOUND
            THEN
              P_ERROR := 'Fallo correo areas. No encuentra correo del area responsable. ' || SQLERRM;
      END;
      
      FOR S IN SOLICITUDES
      LOOP
         -- Correo del empleado
         BEGIN
            IF P_ERROR IS NULL THEN
            BEGIN
              SELECT PLA.ASUNTO, PLA.CUERPO, PLA.PIE_PAGINA, COR.COR_CORREO
                INTO V_ASUNTO, V_CUERPO, V_PIE, V_REMITENTE
                FROM PR.PR_DETALLE_X_CORREOS DET 
                INNER JOIN PR.PR_PLANTILLA_CORREOS PLA ON (DET.COD_SERVICIO = PLA.COD_SERVICIO)
                INNER JOIN PR.PR_CORREOS COR ON (DET.ID_CORREO=COR.COR_ID_CORREO)
              WHERE DET.COD_SERVICIO = 'CIF_NOTIFICACION_AREAS'
              AND COR.COR_ESTADO='A';
            EXCEPTION
              WHEN NO_DATA_FOUND
              THEN
                P_ERROR := 'Fallo correo areas. No encuentra datos plantilla o correo. ' || SQLERRM;
              END;
          END IF;
        IF P_ERROR IS NULL THEN
            V_ASUNTO := REPLACE (V_ASUNTO, '<#REQ_ESTADO#>', P_ESTADO);

            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_NOMBRE#>', S.NOMBRE);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ID#>', V_ID_REQ);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ESTADO#>', P_ESTADO);

            pr.ENVIAR_CORREO_HTML (V_REMITENTE,
                                   V_DESTINATARIO,
                                   V_ASUNTO,
                                   V_CUERPO || V_PIE);
        END IF;
            

         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo areas. ' || SQLERRM;
         END;
        END LOOP;
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo areas. ' || SQLERRM;
END;

create or replace PROCEDURE 
		          CREDIFIS.ENVIAR_CORREO_INGRESO_REQ  (P_SOLICITUD	            NUMBER,
                                P_USUARIO			          VARCHAR2,
                                P_ESTADO                VARCHAR2,
                                P_SISTEMA               VARCHAR2,
                                P_ERROR                 OUT VARCHAR2)
   IS
      CURSOR SOLICITUDES
      IS
         SELECT IUS_NOMBRE_USUARIO AS NOMBRE,
                IUS_EMAIL AS CORREO
           FROM CIF_REQ_REQUERIMIENTOS
                INNER JOIN CIF_ID_USUARIO IUS ON (REQ_USER_ID = IUS.IUS_ID_USUARIO AND IUS.IUS_COD_SISTEMA = P_SISTEMA)
          WHERE REQ_ID = P_SOLICITUD AND REQ_USER_ID=P_USUARIO;

      V_ID_REQ        VARCHAR2(10);
      V_ASUNTO        VARCHAR2 (2000) := '';
      V_CUERPO        VARCHAR2 (2000) := '';
      V_PIE           VARCHAR2 (2000) := '';
      V_REMITENTE     VARCHAR2 (255)  := '';

   BEGIN
      V_ID_REQ := TO_CHAR(P_SOLICITUD);
      FOR S IN SOLICITUDES
      LOOP
         -- Correo del empleado
         BEGIN
            BEGIN
              SELECT PLA.ASUNTO, PLA.CUERPO, PLA.PIE_PAGINA, COR.COR_CORREO
                INTO V_ASUNTO, V_CUERPO, V_PIE, V_REMITENTE
                FROM PR.PR_DETALLE_X_CORREOS DET 
                INNER JOIN PR.PR_PLANTILLA_CORREOS PLA ON (DET.COD_SERVICIO = PLA.COD_SERVICIO)
                INNER JOIN PR.PR_CORREOS COR ON (DET.ID_CORREO=COR.COR_ID_CORREO)
                WHERE DET.COD_SERVICIO = 'CIF_NOTIFICACION_ENVIO'
                AND COR.COR_ESTADO='A';
            EXCEPTION
              WHEN NO_DATA_FOUND
              THEN
                P_ERROR := 'Fallo enviar correo ingreso. No encuentra datos plantilla o correo. ' || SQLERRM;
              END;

          IF P_ERROR IS NULL THEN
            V_ASUNTO := REPLACE (V_ASUNTO, '<#REQ_ESTADO#>', P_ESTADO);

            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_NOMBRE#>', S.NOMBRE);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ID#>', V_ID_REQ);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ESTADO#>', P_ESTADO);

            pr.ENVIAR_CORREO_HTML (V_REMITENTE,
                                   S.CORREO,
                                   V_ASUNTO,
                                   V_CUERPO || V_PIE);
          END IF;
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo ingreso. ' || SQLERRM;
         END;
        END LOOP;
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo ingreso. ' || SQLERRM;
END;

create or replace PROCEDURE 
		          CREDIFIS.ENVIAR_CORREO_APROBACION_REQ  (P_SOLICITUD	            NUMBER,
                                P_USUARIO			          VARCHAR2,
                                P_ESTADO                VARCHAR2,
                                P_SISTEMA               VARCHAR2,
                                P_ERROR                 OUT VARCHAR2)
   IS
      CURSOR SOLICITUDES
      IS
         SELECT IUS_NOMBRE_USUARIO AS NOMBRE,
                IUS_EMAIL AS CORREO
           FROM CIF_REQ_REQUERIMIENTOS
                INNER JOIN CIF_ID_USUARIO IUS ON (REQ_USER_ID = IUS.IUS_ID_USUARIO AND IUS.IUS_COD_SISTEMA = P_SISTEMA)
          WHERE REQ_ID = P_SOLICITUD AND REQ_USER_ID=P_USUARIO;

      V_ID_REQ        VARCHAR2(10);
      V_ASUNTO        VARCHAR2 (2000) := '';
      V_CUERPO        VARCHAR2 (2000) := '';
      V_PIE           VARCHAR2 (2000) := '';
      V_REMITENTE     VARCHAR2 (255)  := '';

   BEGIN
      V_ID_REQ := TO_CHAR(P_SOLICITUD);
      FOR S IN SOLICITUDES
      LOOP
         -- Correo del empleado
         BEGIN
            BEGIN
              SELECT PLA.ASUNTO, PLA.CUERPO, PLA.PIE_PAGINA, COR.COR_CORREO
                INTO V_ASUNTO, V_CUERPO, V_PIE, V_REMITENTE
                FROM PR.PR_DETALLE_X_CORREOS DET 
                INNER JOIN PR.PR_PLANTILLA_CORREOS PLA ON (DET.COD_SERVICIO = PLA.COD_SERVICIO)
                INNER JOIN PR.PR_CORREOS COR ON (DET.ID_CORREO=COR.COR_ID_CORREO)
                WHERE DET.COD_SERVICIO = 'CIF_APROBACION_REQ'
                AND COR.COR_ESTADO='A';
            EXCEPTION
              WHEN NO_DATA_FOUND
              THEN
                P_ERROR := 'Fallo correo aprobacion. No encuentra datos plantilla o correo. ' || SQLERRM;
              END;

        IF P_ERROR IS NULL THEN
            V_ASUNTO := REPLACE (V_ASUNTO, '<#REQ_ESTADO#>', P_ESTADO);

            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_NOMBRE#>', S.NOMBRE);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ID#>', V_ID_REQ);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ESTADO#>', P_ESTADO);

            pr.ENVIAR_CORREO_HTML (V_REMITENTE,
                                   S.CORREO,
                                   V_ASUNTO,
                                   V_CUERPO || V_PIE);
        END IF;
                                   
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo aprobacion. ' || SQLERRM;
         END;
        END LOOP;
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo aprobacion. ' || SQLERRM;
END;

create or replace PROCEDURE 
		          CREDIFIS.ENVIAR_CORREO_CIERRE_REQ  (P_SOLICITUD	            NUMBER,
                                P_USUARIO			          VARCHAR2,
                                P_ESTADO                VARCHAR2,
                                P_SISTEMA               VARCHAR2,
                                P_ERROR                 OUT VARCHAR2)
   IS
      CURSOR SOLICITUDES
      IS
         SELECT IUS_NOMBRE_USUARIO AS NOMBRE,
                IUS_EMAIL AS CORREO
           FROM CIF_REQ_REQUERIMIENTOS
                INNER JOIN CIF_ID_USUARIO IUS ON (REQ_USER_ID = IUS.IUS_ID_USUARIO AND IUS.IUS_COD_SISTEMA = P_SISTEMA)
          WHERE REQ_ID = P_SOLICITUD AND REQ_USER_ID=P_USUARIO;

      V_ID_REQ        VARCHAR2(10);
      V_ASUNTO        VARCHAR2 (2000) := '';
      V_CUERPO        VARCHAR2 (2000) := '';
      V_PIE           VARCHAR2 (2000) := '';
      V_REMITENTE     VARCHAR2 (255)  := '';

   BEGIN
      V_ID_REQ := TO_CHAR(P_SOLICITUD);
      FOR S IN SOLICITUDES
      LOOP
         -- Correo del empleado
         BEGIN
            BEGIN
              SELECT PLA.ASUNTO, PLA.CUERPO, PLA.PIE_PAGINA, COR.COR_CORREO
                INTO V_ASUNTO, V_CUERPO, V_PIE, V_REMITENTE
                FROM PR.PR_DETALLE_X_CORREOS DET 
                INNER JOIN PR.PR_PLANTILLA_CORREOS PLA ON (DET.COD_SERVICIO = PLA.COD_SERVICIO)
                INNER JOIN PR.PR_CORREOS COR ON (DET.ID_CORREO=COR.COR_ID_CORREO)
              WHERE DET.COD_SERVICIO = 'CIF_NOTIFICA_CIERRE'
              AND COR.COR_ESTADO='A';
            EXCEPTION
              WHEN NO_DATA_FOUND
              THEN
                P_ERROR := 'Fallo correo cierre. No encuentra datos plantilla o correo. ' || SQLERRM;
              END;

          IF P_ERROR IS NULL THEN
            V_ASUNTO := REPLACE (V_ASUNTO, '<#REQ_ESTADO#>', P_ESTADO);

            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_NOMBRE#>', S.NOMBRE);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ID#>', V_ID_REQ);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ESTADO#>', P_ESTADO);

            pr.ENVIAR_CORREO_HTML (V_REMITENTE,
                                   S.CORREO,
                                   V_ASUNTO,
                                   V_CUERPO || V_PIE);
          END IF;
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo cierre. ' || SQLERRM;
         END;
        END LOOP;
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo cierre. ' || SQLERRM;
END;


create or replace PROCEDURE          CREDIFIS.ENVIAR_CORREO_CON_ARCHIVO_HTML	  (p_usuario			 VARCHAR2,
										   p_solicitud           NUMBER,
										   p_sistema			 VARCHAR2,
										   p_estado				 VARCHAR2,
										   p_content_type        VARCHAR2,
                                           p_error		     OUT VARCHAR2)
   IS
      CURSOR SOLICITUDES
      IS
         SELECT IUS_NOMBRE_USUARIO AS NOMBRE,
                IUS_EMAIL AS CORREO,
				ANR_ARCHIVO AS ARCHIVO,
				ANR_NOMBRE_ARCHIVO AS NOMBRE_ARCHIVO
           FROM CIF_REQ_REQUERIMIENTOS 
                INNER JOIN CIF_ID_USUARIO IUS ON (REQ_USER_ID = IUS.IUS_ID_USUARIO AND IUS.IUS_COD_SISTEMA = p_sistema)
				INNER JOIN CIF_ANR_ANEXOS_REQ ANR ON (REQ_ID = ANR.ANR_REQ_ID)
          WHERE REQ_ID = p_solicitud AND REQ_USER_ID=p_usuario;

      v_id_req        VARCHAR2(10);
      v_asunto        VARCHAR2 (2000) := '';
      v_cuerpo        VARCHAR2 (2000) := '';
      v_pie           VARCHAR2 (2000) := '';
      v_remitente     VARCHAR2 (255)  := '';
      v_destinatario  VARCHAR2 (255)  := '';

      v_attach_name   VARCHAR2 (400);
      v_attach_mime   VARCHAR2 (400);
      v_attach_blob   BLOB;
      v_smtp_host     VARCHAR2 (400);
      v_smtp_port     NUMBER;
      l_mail_conn     UTL_SMTP.connection;
      l_boundary      VARCHAR2 (50) := '----=*#abc1234321cba#*=';
      l_step          PLS_INTEGER := 12000;
	  crlf VARCHAR2( 2 ):= CHR( 13 ) || CHR( 10 );
	  
   BEGIN
	  v_id_req := TO_CHAR(p_solicitud);
    v_smtp_host := pa_obt_paramgeneral ('PA', 'NOMBRE_SVR_COR');
    v_smtp_port := TO_NUMBER (pa_obt_paramgeneral ('PA', 'PUERTO_SVR_COR'));
    v_remitente := pa_obt_paramgeneral ('CI', 'CIF_EML_RECEIPT');  
      BEGIN
        SELECT PLA.ASUNTO, PLA.CUERPO, PLA.PIE_PAGINA, COR.COR_CORREO
              INTO v_asunto, v_cuerpo, v_pie, v_destinatario
              FROM PR.PR_DETALLE_X_CORREOS DET 
              INNER JOIN PR.PR_PLANTILLA_CORREOS PLA ON (DET.COD_SERVICIO = PLA.COD_SERVICIO)
              INNER JOIN PR.PR_CORREOS COR ON (DET.ID_CORREO=COR.COR_ID_CORREO)
             WHERE DET.COD_SERVICIO = 'CIF_NOTIFIC_ENVIO_HLP'
             AND COR.COR_ESTADO='A';
        EXCEPTION
            WHEN NO_DATA_FOUND
            THEN
                p_error := 'Fallo correo con adjuntos. No encuentra datos plantilla o correo. ' || SQLERRM;
            END;

  IF P_ERROR IS NULL THEN
    v_asunto := REPLACE (v_asunto, '<#REQ_ID#>', v_id_req);

    v_cuerpo := REPLACE (v_cuerpo, '<#REQ_ID#>', v_id_req);
    v_cuerpo := REPLACE (v_cuerpo, '<#REQ_ESTADO#>', p_estado);

      l_mail_conn := UTL_SMTP.open_connection (v_smtp_host, v_smtp_port);
      UTL_SMTP.helo (l_mail_conn, v_smtp_host);
      UTL_SMTP.mail (l_mail_conn, v_remitente);
      UTL_SMTP.rcpt (l_mail_conn, v_destinatario);
      UTL_SMTP.open_data (l_mail_conn);

      UTL_SMTP.write_data (
         l_mail_conn,
            'Date: '
         || TO_CHAR (SYSDATE, 'DD-MON-YYYY HH24:MI:SS')
         || crlf);
      UTL_SMTP.write_data (l_mail_conn, 'cc: ' || v_destinatario || crlf);
      UTL_SMTP.write_data (l_mail_conn, 'From: ' || v_remitente || crlf);
      UTL_SMTP.write_data (l_mail_conn,
                           'Subject: ' || v_asunto || crlf);
      UTL_SMTP.write_data (l_mail_conn,
                           'Reply-To: ' || v_remitente || crlf);
      UTL_SMTP.write_data (l_mail_conn, 'MIME-Version: 1.0' || crlf);
      UTL_SMTP.write_data (
         l_mail_conn,
            'Content-Type: multipart/mixed; boundary="'
         || l_boundary
         || '"'
         || crlf
         || crlf);

         UTL_SMTP.write_data (l_mail_conn,
                              '--' || l_boundary || crlf);

         UTL_SMTP.write_data (
            l_mail_conn,
               'Content-Type: text/html; charset="iso-8859-1"'
            || crlf
            || crlf);
         UTL_SMTP.write_data (l_mail_conn, v_cuerpo);

		 
         UTL_SMTP.write_data (l_mail_conn, v_pie);

         UTL_SMTP.write_data (l_mail_conn, crlf || crlf);
         
    FOR S IN SOLICITUDES
    LOOP
      BEGIN
		 --   ESCRITURA DEL BLOB  ---

         UTL_SMTP.write_data (l_mail_conn,
                              '--' || l_boundary || crlf);
         UTL_SMTP.write_data (
            l_mail_conn,
               'Content-Type: '
            || p_content_type
            || crlf);
         UTL_SMTP.write_data (
            l_mail_conn,
            'Content-Transfer-Encoding: base64' || crlf);
         UTL_SMTP.write_data (
            l_mail_conn,
               'Content-Disposition: attachment; filename="'
            || S.nombre_archivo
            || '"'
            || crlf
            || crlf);

         FOR i IN 0 ..
                  TRUNC ( (DBMS_LOB.getlength (s.archivo) - 1) / l_step)
         LOOP
            UTL_SMTP.write_data (
               l_mail_conn,
               UTL_RAW.cast_to_varchar2 (
                  UTL_ENCODE.base64_encode (
                     DBMS_LOB.SUBSTR (S.archivo, l_step, i * l_step + 1))));
         END LOOP;

         UTL_SMTP.write_data (l_mail_conn, crlf || crlf);

      END;
      END LOOP;

      UTL_SMTP.write_data (l_mail_conn,
                           '--' || l_boundary || '--' || crlf);
      UTL_SMTP.close_data (l_mail_conn);
      UTL_SMTP.quit (l_mail_conn);
  END IF;

   EXCEPTION
      WHEN OTHERS
      THEN                          --Validacion por si se produce algun error
         p_error := 'Error envio correo con adjunto : ' || SQLERRM;
END;

create or replace PROCEDURE 
		          CREDIFIS.ENVIAR_CORREO_RECHAZO_REQ  (P_SOLICITUD	            NUMBER,
                                P_USUARIO			          VARCHAR2,
                                P_ESTADO                VARCHAR2,
                                P_SISTEMA               VARCHAR2,
                                P_ERROR                 OUT VARCHAR2)
   IS
      CURSOR SOLICITUDES
      IS
         SELECT IUS_NOMBRE_USUARIO AS NOMBRE,
                IUS_EMAIL AS CORREO
           FROM CIF_REQ_REQUERIMIENTOS
                INNER JOIN CIF_ID_USUARIO IUS ON (REQ_USER_ID = IUS.IUS_ID_USUARIO AND IUS.IUS_COD_SISTEMA = P_SISTEMA)
          WHERE REQ_ID = P_SOLICITUD AND REQ_USER_ID=P_USUARIO;

      V_ID_REQ        VARCHAR2(10);
      V_ASUNTO        VARCHAR2 (2000) := '';
      V_CUERPO        VARCHAR2 (2000) := '';
      V_PIE           VARCHAR2 (2000) := '';
      V_REMITENTE     VARCHAR2 (255)  := '';

   BEGIN
      V_ID_REQ := TO_CHAR(P_SOLICITUD);
      FOR S IN SOLICITUDES
      LOOP
         -- Correo del empleado
         BEGIN
            BEGIN
              SELECT PLA.ASUNTO, PLA.CUERPO, PLA.PIE_PAGINA, COR.COR_CORREO
                INTO V_ASUNTO, V_CUERPO, V_PIE, V_REMITENTE
                FROM PR.PR_DETALLE_X_CORREOS DET 
                INNER JOIN PR.PR_PLANTILLA_CORREOS PLA ON (DET.COD_SERVICIO = PLA.COD_SERVICIO)
                INNER JOIN PR.PR_CORREOS COR ON (DET.ID_CORREO=COR.COR_ID_CORREO)
              WHERE DET.COD_SERVICIO = 'CIF_NOTIFICA_RECHAZO'
              AND COR.COR_ESTADO='A';
            EXCEPTION
              WHEN NO_DATA_FOUND
              THEN
                P_ERROR := 'Fallo enviar correo rechazo. No encuentra datos plantilla o correo. ' || SQLERRM;
              END;

          IF P_ERROR IS NULL THEN
            V_ASUNTO := REPLACE (V_ASUNTO, '<#REQ_ESTADO#>', P_ESTADO);

            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_NOMBRE#>', S.NOMBRE);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ID#>', V_ID_REQ);
            V_CUERPO := REPLACE (V_CUERPO, '<#REQ_ESTADO#>', P_ESTADO);

            pr.ENVIAR_CORREO_HTML (V_REMITENTE,
                                   S.CORREO,
                                   V_ASUNTO,
                                   V_CUERPO || V_PIE);
          END IF;

         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo rechazo. ' || SQLERRM;
         END;
        END LOOP;
         EXCEPTION
            WHEN OTHERS
            THEN
               P_ERROR := 'No se pudo enviar correo rechazo. ' || SQLERRM;
END;


