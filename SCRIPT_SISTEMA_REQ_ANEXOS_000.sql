GRANT SELECT, UPDATE, INSERT ON PR.PR_CORREOS TO CREDIFIS;
GRANT SELECT, UPDATE, INSERT ON PR.PR_PLANTILLA_CORREOS TO CREDIFIS;
GRANT SELECT, UPDATE, INSERT ON PR.PR_DETALLE_X_CORREOS TO CREDIFIS;
GRANT SELECT, UPDATE, INSERT ON PA.SISTEMAS TO CREDIFIS;
GRANT SELECT, UPDATE, INSERT ON PA.PARAM_GENERALES TO CREDIFIS;

GRANT EXECUTE ON PR.ENVIAR_CORREO_HTML TO CREDIFIS;
GRANT EXECUTE ON PA.PA_OBT_PARAMGENERAL TO CREDIFIS;

grant select on "tabla" to CONSULTA; 