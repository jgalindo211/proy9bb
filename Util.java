package bandesal.gob.sv.cif.anexos.util;


import bandesal.gob.sv.cif.anexos.connection.ConnectionManager;


import bandesal.gob.sv.cif.anexos.dto.DatosAnexos;
import bandesal.gob.sv.cif.anexos.dto.DatosSolicitudes;
import bandesal.gob.sv.cif.anexos.dto.UserData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.io.Reader;

import java.math.BigInteger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.CallableStatement;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import java.nio.charset.StandardCharsets; 
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;

import java.sql.Clob;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.owasp.validator.html.*;
import org.owasp.esapi.*;


public class Util {
    final static Logger logger = Logger.getLogger(Util.class);
    final static String FILE_PROPERTIES = "ApplicationResource.properties";
    
    public static String getProperty(String llave) {
    Properties prop = new Properties();
    String result = "";
    try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE_PROPERTIES);
            prop.load(inputStream);
            result = prop.getProperty(llave);

        } catch (IOException e) {
            logger.error("Util. Error de lectura propiedades", e);
        }
        return result;
    }

    public static String getPathFile(String nombreArchivo) {
    String result = "";
        File file = new File(Util.class.getClassLoader().getResource(nombreArchivo).getFile());
        result = file.getPath();
        return result;
    }
    
    public static String getFechaAsString(Date fecha, String formato) {
        String fec="";
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        fec=sdf.format(fecha);
        return fec;
    }
    
    public static java.sql.Date getFechaSql(Date fecha, String formato) {
        java.sql.Date fec=null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            fec=new java.sql.Date(sdf.parse(sdf.format(fecha)).getTime());
        } catch (Exception pa){
            logger.error(pa);
        }
        return fec;
    }
    
    public static String getUser(HttpServletRequest request) {
        String l_user = null;
        try {
            
            //logger.info("Previo Usuario logueado: " + request.getHeader("Proxy-Remote-User"));
            //l_user=StringUtils.trimToEmpty(cleanEntrada(request.getHeader("Proxy-Remote-User")));
            l_user=StringUtils.trimToEmpty(request.getHeader("Proxy-Remote-User"));
            //logger.info("After Usuario logueado: " + l_user);
            logger.debug("usuario remoto: " + l_user);
            l_user="JGALINDO";
        } catch (Exception e) {
            logger.error(e);
            l_user = null;
        }

        if ((l_user == null) || (l_user.length() <= 0)) {
            l_user = null; //no es valido
        }

        //request.getSession().setAttribute("usuarioSession" + getSistema(), l_user);
        return l_user;
    }
    
    /**
         * Guarda los datos de un requerimiento.
         * @param usr datos asociados al usuario.
         * @return Integer que lleva el correlativo generado.
         * @throws SQLException Si se produce un error al guardar los datos.
         */
        public static Integer guardarRequerimiento(UserData usr, String estado) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            Integer idRequerimiento = -1;
            try {
                conexion = ConnectionManager.getConnection();
                String fecha = getFechaAsString(new Date(), "yyyy-MM-dd");
                String mensaje="";
                String call = " {call GUARDAR_REQUERIMIENTO(?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?,TO_DATE(?,'YYYY-MM-DD'),?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, estado);
                instruccion.setString(3, usr.getUsuario());
                instruccion.setString(4, usr.getIdInstitucion());
                instruccion.setString(5, fecha);
                instruccion.setString(6, usr.getUsuario());
                instruccion.setString(7, fecha);
                instruccion.setString(8, mensaje);
                instruccion.registerOutParameter(1, Types.NUMERIC);
                instruccion.registerOutParameter(8, Types.VARCHAR);
                instruccion.execute();
                idRequerimiento = instruccion.getInt(1);
                String resultado = instruccion.getString(8);
                
                if (StringUtils.isNotEmpty(resultado)) {
                    throw new SQLException(resultado);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }  
            
            return idRequerimiento;
        }
            
    /**
         * Guarda los datos de anexos de un requerimiento.
         * @param idRequerimiento el requerimiento para detalle
         * @param usuario asociado al requerimiento
         * @param archivo del anexo a grabar
         * @return Integer que lleva el correlativo generado.
         * @throws SQLException Si se produce un error al guardar los datos.
         */
    
    public static Integer guardarAnexosRequerimiento(Integer idRequerimiento, String usuario, InputStream archivo, 
                                                     String nombreArchivo, String tipoDocumento, String tipoArea) throws SQLException {
        Connection conexion = null;
        CallableStatement instruccion = null;
        Integer idAnexoRequerimiento = -1;
        try {
            conexion = ConnectionManager.getConnection();
            String fecha = getFechaAsString(new Date(), "yyyy-MM-dd");
            String mensaje="";
            String call = " {call GUARDAR_ANEXOS_REQUERIMIENTO(?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'),?)} ";
            instruccion = conexion.prepareCall(call);
            instruccion.setInt(1, idAnexoRequerimiento);
            instruccion.setInt(2, idRequerimiento);
            instruccion.setBlob(3, archivo);
            instruccion.setString(4, nombreArchivo);
            instruccion.setString(5, tipoDocumento);
            instruccion.setString(6, tipoArea);
            instruccion.setString(7, usuario);
            instruccion.setString(8, fecha);
            instruccion.setString(9, mensaje);
            instruccion.registerOutParameter(1, Types.NUMERIC);
            instruccion.registerOutParameter(9, Types.VARCHAR);
            instruccion.execute();
            idAnexoRequerimiento = instruccion.getInt(1);
            String resultado = instruccion.getString(9);
            
            if (StringUtils.isNotEmpty(resultado)) {
                throw new SQLException(resultado);
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            cerrarObjetoDb(instruccion);
            cerrarObjetoDb(conexion);
            try{
                if (archivo != null) {
                    archivo.close();
                }
            } catch (IOException ioe) {
                logger.error(ioe);
            }
            
        }  
        
        return idAnexoRequerimiento;
    }
    
    
    /**
         * Guarda los datos de bitacora de un requerimiento.
         * @param idRequerimiento el requerimiento para detalle
         * @param usuario asociado al requerimiento
         * @return Integer que lleva el correlativo generado.
         * @throws SQLException Si se produce un error al guardar los datos.
         */
    
    public static Integer guardarBitacoraRequerimiento(Integer idRequerimiento, String usuario, String estado) throws SQLException {
        Connection conexion = null;
        CallableStatement instruccion = null;
        Integer idBitacoraRequerimiento = -1;
        try {
            conexion = ConnectionManager.getConnection();
            String fecha = getFechaAsString(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
            String mensaje="";
            String call = " {call GUARDAR_BITACORA_REQUERIMIENTO(?,?,?,?,TO_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS,FF9'),?)} ";
            instruccion = conexion.prepareCall(call);
            instruccion.setInt(1, idBitacoraRequerimiento);
            instruccion.setInt(2, idRequerimiento);
            instruccion.setString(3, estado);
            instruccion.setString(4, usuario);
            instruccion.setString(5, fecha);
            instruccion.setString(6, mensaje);
            instruccion.registerOutParameter(1, Types.NUMERIC);
            instruccion.registerOutParameter(6, Types.VARCHAR);
            instruccion.execute();
            idBitacoraRequerimiento = instruccion.getInt(1);
            String resultado = instruccion.getString(6);
            
            if (StringUtils.isNotEmpty(resultado)) {
                throw new SQLException(resultado);
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            cerrarObjetoDb(instruccion);
            cerrarObjetoDb(conexion);
        }  
        
        return idBitacoraRequerimiento;
    }
    

    /**
         * Guarda los datos de un hash.
         * @param usr datos asociados al usuario.
         * @return Integer que lleva el correlativo generado.
         * @throws SQLException Si se produce un error al guardar los datos.
         */
        public static Integer guardarAnexosHash(UserData usr, String usuario, String hash, String estado) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            Integer idRequerimiento = -1;
            try {
                conexion = ConnectionManager.getConnection();
                String mensaje="";
                String call = " {call GUARDAR_ANEXOS_HASH(?,?,?,?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, usuario);
                instruccion.setString(3, usr.getIdInstitucion());
                instruccion.setString(4, hash);
                instruccion.setString(5, estado);
                instruccion.setString(6, mensaje);
                instruccion.registerOutParameter(1, Types.NUMERIC);
                instruccion.registerOutParameter(6, Types.VARCHAR);
                instruccion.execute();
                idRequerimiento = instruccion.getInt(1);
                String resultado = instruccion.getString(6);
                
                if (StringUtils.isNotEmpty(resultado)) {
                    throw new SQLException(resultado);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }  
            
            return idRequerimiento;
        }
            

    /**
         * Eliminar anexos de los Requerimientos.
         * @param idRequerimiento.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al guardar los datos.
         */
        public static String EliminarRequerimiento(Integer idRequerimiento) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                conexion = ConnectionManager.getConnection();
                String call = " {call ELIMINAR_REQUERIMIENTO(?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, mensaje);
                instruccion.registerOutParameter(2, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(2);
                
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new SQLException(mensaje);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            
            return mensaje;
        }

    /**
         * eliminar anexos hash
         * @param datos de usuario.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al guardar los datos.
         */
        public static String eliminarAnexosHash(String usuario, UserData usr, String estado) {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                conexion = ConnectionManager.getConnection();
                String call = " {call ELIMINAR_HASH(?,?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setString(1, usuario);
                instruccion.setString(2, usr.getIdInstitucion());
                instruccion.setString(3, estado);
                instruccion.setString(4, mensaje);
                instruccion.registerOutParameter(4, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(4);
                
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new SQLException(mensaje);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            return mensaje;
        }


    /**
         * ActualizarEstadoRequerimiento
         * @param idRequerimiento.
         * @param estado.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al guardar los datos.
         */
        public static String actualizarEstadoRequerimiento(Integer idRequerimiento, String estado) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                conexion = ConnectionManager.getConnection();
                String call = " {call ACTUALIZAR_EST_REQUERIMIENTO(?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, estado);
                instruccion.setString(3, mensaje);
                instruccion.registerOutParameter(3, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(3);
                
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new SQLException(mensaje);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            
            return mensaje;
        }
    
    /**
         * enviarCorreoIngresoRequerimiento
         * @param idRequerimiento.
         * @param usuario.
         * @param estado.
         * @param sistema.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al guardar los datos.
         * ENVIAR_CORREO_INGRESO_REQ
         */
        public static String enviarCorreoIngresoRequerimiento(Integer idRequerimiento, 
                                                             String usuario,String estado, String sistema) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                List<String> l = getTiposAreasSolicitud(usuario,idRequerimiento);
                String codigoGarantias = getProperty("id.admin.garantias");
                String codigoComercio = getProperty("id.comercio.segundo.piso");
                int tieneGar = (l.contains(codigoGarantias)?1:0);
                int tieneCom = (l.contains(codigoComercio)?1:0);
                conexion = ConnectionManager.getConnection();
                String call = " {call ENVIAR_CORREO_INGRESO_REQ(?,?,?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, usuario);
                instruccion.setString(3, estado);
                instruccion.setString(4, sistema);
                instruccion.setString(5, mensaje);
                instruccion.registerOutParameter(5, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(5);
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new Exception(mensaje);
                }
                call = " {call ENVIAR_CORREO_AREAS(?,?,?,?,?,?)} ";
                if (tieneGar>0) {

                    instruccion = conexion.prepareCall(call);
                    instruccion.setInt(1, idRequerimiento);
                    instruccion.setString(2, usuario);
                    instruccion.setString(3, estado);
                    instruccion.setString(4, sistema);
                    instruccion.setString(5, codigoGarantias);
                    instruccion.setString(6, mensaje);
                    instruccion.registerOutParameter(6, Types.VARCHAR);
                    instruccion.execute();
                    mensaje = instruccion.getString(6);

                    if (StringUtils.isNotEmpty(mensaje)) {
                        throw new Exception(mensaje);
                    }
                } 
                if (tieneCom>0) {
                    instruccion = conexion.prepareCall(call);
                    instruccion.setInt(1, idRequerimiento);
                    instruccion.setString(2, usuario);
                    instruccion.setString(3, estado);
                    instruccion.setString(4, sistema);
                    instruccion.setString(5, codigoComercio);
                    instruccion.setString(6, mensaje);
                    instruccion.registerOutParameter(6, Types.VARCHAR);
                    instruccion.execute();
                    mensaje = instruccion.getString(6);
                    
                    if (StringUtils.isNotEmpty(mensaje)) {
                        throw new Exception(mensaje);
                    }
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            
            return mensaje;
        }
    
    
    /**
         * EnviarCorreoCierreRequerimiento
         * @param idRequerimiento.
         * @param usuario.
         * @param estado.
         * @param sistema.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al enviar el correo
         * ENVIAR_CORREO_CIERRE_REQ
         */
        public static String enviarCorreoCierreRequerimiento(Integer idRequerimiento, 
                                                             String usuario,String estado, String sistema) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                conexion = ConnectionManager.getConnection();
                String call = " {call ENVIAR_CORREO_CIERRE_REQ(?,?,?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, usuario);
                instruccion.setString(3, estado);
                instruccion.setString(4, sistema);
                instruccion.setString(5, mensaje);
                instruccion.registerOutParameter(5, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(5);
                
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new SQLException(mensaje);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            
            return mensaje;
        }
    

    /**
         * EnviarCorreoHelpDesk
         * @param idRequerimiento.
         * @param usuario.
         * @param estado.
         * @param sistema.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al enviar el correo
         * ENVIAR_CORREO_ACEPTA_REQ
         */
        public static String enviarCorreoHelpDesk(String usuario, Integer idRequerimiento, 
                                                  String sistema, String estado,
                                                  String contentType) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                conexion = ConnectionManager.getConnection();
                String call = " {call ENVIAR_CORREO_CON_ARCHIVO_HTML(?,?,?,?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setString(1, usuario);
                instruccion.setInt(2, idRequerimiento);
                instruccion.setString(3, sistema);
                instruccion.setString(4, estado);
                instruccion.setString(5, contentType);
                instruccion.setString(6, mensaje);
                instruccion.registerOutParameter(6, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(6);
                
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new SQLException(mensaje);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            
            return mensaje;
        }
    

    /**
         * EnviarCorreoRechazoRequerimiento
         * @param idRequerimiento.
         * @param usuario.
         * @param estado.
         * @param sistema.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al enviar el correo
         * ENVIAR_CORREO_RECHAZO_REQ
         */
        public static String enviarCorreoRechazoRequerimiento(Integer idRequerimiento, 
                                                             String usuario,String estado, String sistema) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                conexion = ConnectionManager.getConnection();
                String call = " {call ENVIAR_CORREO_RECHAZO_REQ(?,?,?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, usuario);
                instruccion.setString(3, estado);
                instruccion.setString(4, sistema);
                instruccion.setString(5, mensaje);
                instruccion.registerOutParameter(5, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(5);
                
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new SQLException(mensaje);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            
            return mensaje;
        }
    
    /**
         * enviarCorreoAprobacionRequerimiento
         * @param idRequerimiento.
         * @param usuario.
         * @param estado.
         * @param sistema.
         * @return String que lleva el resultado del proceso
         * @throws SQLException Si se produce un error al guardar los datos.
         * ENVIAR_CORREO_APROBACION_REQ
         */
        public static String enviarCorreoAprobacionRequerimiento(Integer idRequerimiento, 
                                                             String usuario,String estado, String sistema) throws SQLException {
            Connection conexion = null;
            CallableStatement instruccion = null;
            String mensaje="";
            try {
                conexion = ConnectionManager.getConnection();
                String call = " {call ENVIAR_CORREO_APROBACION_REQ(?,?,?,?,?)} ";
                instruccion = conexion.prepareCall(call);
                instruccion.setInt(1, idRequerimiento);
                instruccion.setString(2, usuario);
                instruccion.setString(3, estado);
                instruccion.setString(4, sistema);
                instruccion.setString(5, mensaje);
                instruccion.registerOutParameter(5, Types.VARCHAR);
                instruccion.execute();
                mensaje = instruccion.getString(5);
                
                if (StringUtils.isNotEmpty(mensaje)) {
                    throw new SQLException(mensaje);
                }
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                cerrarObjetoDb(instruccion);
                cerrarObjetoDb(conexion);
            }
            
            return mensaje;
        }
    
    
       
    public static UserData getDatosCifUsuario(String usuario, String sistema){
      Connection conn = null;
      PreparedStatement pstmt = null;
      UserData us = new UserData();
      ResultSet rs = null;
      String queryConsultaUsuario = "";
      String fecha = getFechaAsString(new Date(), "dd/MM/yyyy");
      us.setFecha(fecha);
      
      queryConsultaUsuario = "SELECT " 
            + " IUS_NOMBRE_USUARIO AS nombre,"
            + " IUS_EMAIL AS correo,"
            + " CIF_ID_USUARIO.IUS_INSTITUCION AS idInstitucion,"
            + " NOMBRE_INSTITUCION AS institucion" 
            + " FROM CIF_ID_USUARIO INNER JOIN CIF_INSTITUCIONES"
            + " ON CIF_ID_USUARIO.IUS_INSTITUCION = CIF_INSTITUCIONES.COD_CLIENTE"
            + " WHERE IUS_ID_USUARIO = ?" 
            + " AND IUS_COD_SISTEMA = ?";
        //System.out.println(query_consulta_puestos);
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaUsuario);
            pstmt.setString(1, usuario);
            pstmt.setString(2, sistema);
            rs = pstmt.executeQuery();

             while(rs.next())
            {
                us.setEmail(StringUtils.trimToEmpty((rs.getString("correo"))));
                us.setIdInstitucion(StringUtils.trimToEmpty((rs.getString("idInstitucion"))));
                us.setInstitucion(StringUtils.trimToEmpty((rs.getString("institucion"))));
                us.setNombre(StringUtils.trimToEmpty((rs.getString("nombre"))));
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return us;
      
    }

    public static LinkedHashMap<String, List<DatosAnexos>> getDatosSolicitudesAnexoAprobacion(String usuario){
      Connection conn = null;
      PreparedStatement pstmt = null;
      DatosAnexos da = new DatosAnexos();
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      String estado=Util.getProperty("estado.enviado.solicitud");
      List<DatosAnexos> lda = new ArrayList<DatosAnexos>();
      LinkedHashMap<String, List<DatosAnexos>> hm = new LinkedHashMap<String, List<DatosAnexos>>();
      
      queryConsultaSolicitudes = "SELECT " 
            + " REQ_ID AS idReq,"
            + " TO_CHAR(REQ_FECHA, 'dd/mm/yyyy') AS fecha," 
            + " NOMBRE_INSTITUCION AS institucion," 
            + " ANR_ID as anrId,"
            + " ANR_NOMBRE_ARCHIVO as nombreArchivo"
            + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_ANR_ANEXOS_REQ"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_ID = CIF_ANR_ANEXOS_REQ.ANR_REQ_ID"
            + " INNER JOIN CIF_INSTITUCIONES"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_INSTITUCION = CIF_INSTITUCIONES.COD_CLIENTE"                                 
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_ID_ESTADO=?"
            + " ORDER BY CIF_ANR_ANEXOS_REQ.ANR_REQ_ID, CIF_ANR_ANEXOS_REQ.ANR_ID";
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setString(2, estado);
            rs = pstmt.executeQuery();
            Integer i=new Integer(0);
             while(rs.next())
            {
                i=rs.getInt("idReq");
                if (! hm.containsKey(i.toString())) {
                     lda = new ArrayList<DatosAnexos>();
                }
                da = new DatosAnexos();
                da.setReqId(i);
                da.setAnrId(rs.getInt("anrId"));
                da.setNombreArchivo(StringUtils.trimToEmpty((rs.getString("nombreArchivo"))));
                da.setFecha(StringUtils.trimToEmpty((rs.getString("fecha"))));
                da.setInstitucion(StringUtils.trimToEmpty((rs.getString("institucion"))));
                lda.add(da);
                hm.put(i.toString(), lda);
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return hm;
      
    }
    
    public static LinkedHashMap<String, List<DatosAnexos>> getDatosSolicitudesAnexoRechazo(String usuario, String institucion){
      Connection conn = null;
      PreparedStatement pstmt = null;
      DatosAnexos da = new DatosAnexos();
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      String estado=Util.getProperty("estado.denegado.solicitud");
      List<DatosAnexos> lda = new ArrayList<DatosAnexos>();
      LinkedHashMap<String, List<DatosAnexos>> hm = new LinkedHashMap<String, List<DatosAnexos>>();
      
      queryConsultaSolicitudes = "SELECT " 
            + " REQ_ID AS idReq,"
            + " TO_CHAR(REQ_FECHA, 'dd/mm/yyyy') AS fecha," 
            + " NOMBRE_INSTITUCION AS institucion," 
            + " ANR_ID as anrId,"
            + " ANR_NOMBRE_ARCHIVO as nombreArchivo"
            + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_ANR_ANEXOS_REQ"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_ID = CIF_ANR_ANEXOS_REQ.ANR_REQ_ID"
            + " INNER JOIN CIF_INSTITUCIONES"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_INSTITUCION = CIF_INSTITUCIONES.COD_CLIENTE"                                 
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_INSTITUCION=?"
            + " AND REQ_ID_ESTADO=?"
            + " ORDER BY CIF_ANR_ANEXOS_REQ.ANR_REQ_ID, CIF_ANR_ANEXOS_REQ.ANR_ID";
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setString(2, institucion);
            pstmt.setString(3, estado);
            rs = pstmt.executeQuery();
            Integer i=new Integer(0);
             while(rs.next())
            {
                i=rs.getInt("idReq");
                if (! hm.containsKey(i.toString())) {
                     lda = new ArrayList<DatosAnexos>();
                }
                da = new DatosAnexos();
                da.setReqId(i);
                da.setAnrId(rs.getInt("anrId"));
                da.setNombreArchivo(StringUtils.trimToEmpty((rs.getString("nombreArchivo"))));
                da.setFecha(StringUtils.trimToEmpty((rs.getString("fecha"))));
                da.setInstitucion(StringUtils.trimToEmpty((rs.getString("institucion"))));
                lda.add(da);
                hm.put(i.toString(), lda);
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return hm;
      
    }
    
    public static List<DatosSolicitudes> getDatosSolicitudesReenviado(String usuario){
      Connection conn = null;
      PreparedStatement pstmt = null;
      DatosSolicitudes ds = new DatosSolicitudes();
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      List<DatosSolicitudes> lds = new ArrayList<DatosSolicitudes>();
      String estado=Util.getProperty("estado.forward.solicitud");
      
      queryConsultaSolicitudes = "SELECT " 
            + " REQ_ID AS idReq,"
            + " REQ_ID_ESTADO AS estado,"
            + " REQ_INSTITUCION AS idInstitucion,"
            + " TO_CHAR(REQ_FECHA, 'dd/mm/yyyy') AS fecha," 
            + " ANR_ID as anrId,"
            + " ANR_ARCHIVO as anexo,"
            + " ANR_NOMBRE_ARCHIVO as nombreArchivo,"
            + " ANR_TIPO_DOC as tipoDocumento,"
            + " ANR_TIPO_AREA as tipoArea"
            + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_ANR_ANEXOS_REQ"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_ID = CIF_ANR_ANEXOS_REQ.ANR_REQ_ID"
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_ID_ESTADO=?"
            + " ORDER BY CIF_ANR_ANEXOS_REQ.ANR_ID";
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setString(2, estado);
            rs = pstmt.executeQuery();

             while(rs.next())
            {
                ds = new DatosSolicitudes();
                ds.setAnexo(rs.getBlob("anexo").getBinaryStream());
                ds.setAnrId(rs.getInt("anrId"));
                ds.setNombreArchivo(StringUtils.trimToEmpty((rs.getString("nombreArchivo"))));
                ds.setTipoDocumento(StringUtils.trimToEmpty((rs.getString("tipoDocumento"))));
                ds.setReqTipoArea(StringUtils.trimToEmpty((rs.getString("tipoArea"))));
                ds.setReqFecha(StringUtils.trimToEmpty((rs.getString("fecha"))));
                ds.setReqId(rs.getInt("idReq"));
                ds.setReqIdEstado(StringUtils.trimToEmpty((rs.getString("estado"))));
                ds.setReqIdInstitucion(StringUtils.trimToEmpty((rs.getString("idInstitucion"))));
                lds.add(ds);
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return lds;
      
    }
    
    public static LinkedHashMap<String, List<DatosAnexos>> getDatosSolicitudesAnexoCierre(String usuario){
      Connection conn = null;
      PreparedStatement pstmt = null;
      DatosAnexos da = new DatosAnexos();
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      String estado=Util.getProperty("estado.aprobado.solicitud");
      List<DatosAnexos> lda = new ArrayList<DatosAnexos>();
      LinkedHashMap<String, List<DatosAnexos>> hm = new LinkedHashMap<String, List<DatosAnexos>>();
      
      queryConsultaSolicitudes = "SELECT " 
            + " REQ_ID AS idReq,"
            + " TO_CHAR(REQ_FECHA, 'dd/mm/yyyy') AS fecha," 
            + " NOMBRE_INSTITUCION AS institucion," 
            + " ANR_ID as anrId,"
            + " ANR_NOMBRE_ARCHIVO as nombreArchivo"
            + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_ANR_ANEXOS_REQ"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_ID = CIF_ANR_ANEXOS_REQ.ANR_REQ_ID"
            + " INNER JOIN CIF_INSTITUCIONES"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_INSTITUCION = CIF_INSTITUCIONES.COD_CLIENTE"                                 
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_ID_ESTADO=?"
            + " ORDER BY CIF_ANR_ANEXOS_REQ.ANR_REQ_ID, CIF_ANR_ANEXOS_REQ.ANR_ID";
        //System.out.println(query_consulta_puestos);
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setString(2, estado);
            rs = pstmt.executeQuery();
            Integer i=new Integer(0);
            while(rs.next()) {
                i=rs.getInt("idReq");
                if (! hm.containsKey(i.toString())) {
                     lda = new ArrayList<DatosAnexos>();
                }
                da = new DatosAnexos();
                da.setReqId(i);
                da.setAnrId(rs.getInt("anrId"));
                da.setNombreArchivo(StringUtils.trimToEmpty((rs.getString("nombreArchivo"))));
                da.setFecha(StringUtils.trimToEmpty((rs.getString("fecha"))));
                da.setInstitucion(StringUtils.trimToEmpty((rs.getString("institucion"))));
                lda.add(da);
                hm.put(i.toString(), lda);
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return hm;
      
    }

    public static List<DatosSolicitudes> getDatosSolicitudes(String usuario){
      Connection conn = null;
      PreparedStatement pstmt = null;
      DatosSolicitudes ds = new DatosSolicitudes();
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      List<DatosSolicitudes> lds = new ArrayList<DatosSolicitudes>();
      String estado=Util.getProperty("estado.inicial.solicitud");
      
      queryConsultaSolicitudes = "SELECT " 
            + " REQ_ID AS idReq,"
            + " REQ_ID_ESTADO AS estado,"
            + " REQ_INSTITUCION AS idInstitucion,"
            + " TO_CHAR(REQ_FECHA, 'dd/mm/yyyy') AS fecha," 
            + " ANR_ID as anrId,"
            + " ANR_ARCHIVO as anexo,"
            + " ANR_NOMBRE_ARCHIVO as nombreArchivo,"
            + " ANR_TIPO_DOC as tipoDocumento,"
            + " ANR_TIPO_AREA as tipoArea"
            + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_ANR_ANEXOS_REQ"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_ID = CIF_ANR_ANEXOS_REQ.ANR_REQ_ID"
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_ID_ESTADO=?"
            + " ORDER BY CIF_ANR_ANEXOS_REQ.ANR_ID";
        //System.out.println(query_consulta_puestos);
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setString(2, estado);
            rs = pstmt.executeQuery();

             while(rs.next())
            {
                ds = new DatosSolicitudes();
                ds.setAnexo(rs.getBlob("anexo").getBinaryStream());
                ds.setAnrId(rs.getInt("anrId"));
                ds.setNombreArchivo(StringUtils.trimToEmpty((rs.getString("nombreArchivo"))));
                ds.setTipoDocumento(StringUtils.trimToEmpty((rs.getString("tipoDocumento"))));
                ds.setReqTipoArea(StringUtils.trimToEmpty((rs.getString("tipoArea"))));
                ds.setReqFecha(StringUtils.trimToEmpty((rs.getString("fecha"))));
                ds.setReqId(rs.getInt("idReq"));
                ds.setReqIdEstado(StringUtils.trimToEmpty((rs.getString("estado"))));
                ds.setReqIdInstitucion(StringUtils.trimToEmpty((rs.getString("idInstitucion"))));
                lds.add(ds);
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return lds;
      
    }

    public static DatosSolicitudes getDatosSolicitudesById(Integer id){
      Connection conn = null;
      PreparedStatement pstmt = null;
      DatosSolicitudes ds = new DatosSolicitudes();
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      
      
      queryConsultaSolicitudes = "SELECT " 
            + " ANR_ID as anrId,"
            + " ANR_ARCHIVO as anexo,"
            + " ANR_NOMBRE_ARCHIVO as nombreArchivo"
            + " FROM CIF_ANR_ANEXOS_REQ"
            + " WHERE ANR_ID = ?";
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

             while(rs.next())
            {
                ds = new DatosSolicitudes();
                ds.setBlob(rs.getBlob("anexo"));
                ds.setAnrId(rs.getInt("anrId"));
                ds.setNombreArchivo(StringUtils.trimToEmpty((rs.getString("nombreArchivo"))));
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return ds;
      
    }


    public static List<DatosSolicitudes> getDatosRechazoSolicitudes(String usuario){
      Connection conn = null;
      PreparedStatement pstmt = null;
      DatosSolicitudes ds = new DatosSolicitudes();
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      List<DatosSolicitudes> lds = new ArrayList<DatosSolicitudes>();
      
      queryConsultaSolicitudes = "SELECT " 
            + " REQ_ID AS idReq,"
            + " REQ_ID_ESTADO AS estado,"
            + " NOMBRE_INSTITUCION AS nombreInstitucion,"
            + " TO_CHAR(REQ_FECHA, 'dd/mm/yyyy') AS fecha" 
            + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_INSTITUCIONES"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_INSTITUCION = CIF_INSTITUCIONES.COD_CLIENTE"
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_ID_ESTADO='R'"
            + " ORDER BY REQ_ID";
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            rs = pstmt.executeQuery();

             while(rs.next())
            {
                ds = new DatosSolicitudes();
                ds.setReqFecha(StringUtils.trimToEmpty((rs.getString("fecha"))));
                ds.setReqId(rs.getInt("idReq"));
                ds.setReqIdEstado(StringUtils.trimToEmpty((rs.getString("estado"))));
                ds.setReqIdInstitucion(StringUtils.trimToEmpty((rs.getString("nombreInstitucion"))));
                lds.add(ds);
            }
          }      catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return lds;
      
    }

    public static List<String> getTiposAreasSolicitud(String usuario, Integer idRequerimiento){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String queryConsultaSolicitudes = "";
      List<String> lda = new ArrayList<String>();
      
      queryConsultaSolicitudes = "SELECT " 
            + " DECODE(ANR_TIPO_AREA, 'GR', '5102'," 
            + "'PR','4003',"
            + "'GA','4003') AS tipoArea"
            + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_ANR_ANEXOS_REQ"
            + " ON CIF_REQ_REQUERIMIENTOS.REQ_ID = CIF_ANR_ANEXOS_REQ.ANR_REQ_ID"
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_ID=?";
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setInt(2, idRequerimiento);
            rs = pstmt.executeQuery();
             while(rs.next())
            {
                lda.add(rs.getString("tipoArea"));
            }
          
          } catch(SQLException ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return lda;
      
    }
 
    /*
     * La operacion modificacion necesita insertar 
     * registros con el hash de los anexos existentes
     * para luego compararlos 
     */
    public static String hashingOperacionModificacion(UserData usr, String usuario){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String mensaje="";
      String queryConsultaSolicitudes = "";
      String estadoForward=Util.getProperty("estado.forward.solicitud");//para filtrar las que han sido reenviadas
      String estadoHash=Util.getProperty("estado.forward.hash.solicitud");
      List<DatosAnexos> lda = new ArrayList<DatosAnexos>();
      LinkedHashMap<String, List<DatosAnexos>> hm = new LinkedHashMap<String, List<DatosAnexos>>();
      InputStream is=null;
      queryConsultaSolicitudes = "SELECT " 
              + " ANR_ARCHIVO as anexo"
              + " FROM CIF_REQ_REQUERIMIENTOS INNER JOIN CIF_ANR_ANEXOS_REQ"
              + " ON CIF_REQ_REQUERIMIENTOS.REQ_ID = CIF_ANR_ANEXOS_REQ.ANR_REQ_ID"
              + " WHERE REQ_USER_ID = ?"
              + " AND REQ_INSTITUCION=?"
              + " AND REQ_ID_ESTADO=?"
              + " ORDER BY CIF_ANR_ANEXOS_REQ.ANR_ID";
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setString(2, usr.getIdInstitucion());
            pstmt.setString(3, estadoForward);
            rs = pstmt.executeQuery();
            byte[] b = null;
            String myFileHash = "";
            Integer idHash=new Integer(0);

            while(rs.next()) {
                is=rs.getBlob("anexo").getBinaryStream();
                b = new byte[is.available()];
                is.read(b);//le asigna al array
                myFileHash = Util.toHexString(Util.getSHA(new String(b)));
                idHash = guardarAnexosHash(usr, usuario, myFileHash, estadoHash);//para las que van a ser modificadas
                if (idHash.intValue()==-1) {
                    logger.info("Modificacion. Fallo en insertar hash");
                }
            }
          }      catch(Exception ex) {
              mensaje="Fallo en obtener e insertar hash para Modificaciones";
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return mensaje;
    }
   
    public static boolean compararHash(UserData usr, String usuario, String hash, String estado){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String queryConsultaHash = "";
      boolean coincide=false;
      
      queryConsultaHash = "SELECT ANH_HASH hs"
            + " FROM CIF_ANH_ANEXOS_HASH"
            + " WHERE ANH_USER_ID = ?" 
            + " AND ANH_INSTITUCION=?"
            + " AND TO_CHAR(ANH_FECHA,'yyyymmdd')=?"
            + " AND ANH_ESTADO=?";
      try{
            String formato="yyyyMMdd";
            String sqld = getFechaAsString(new Date(), formato);
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaHash);
            pstmt.setString(1, usuario);
            pstmt.setString(2, usr.getIdInstitucion());
            pstmt.setString(3, sqld);
            pstmt.setString(4, estado);
            rs = pstmt.executeQuery();
            StringBuffer buffer;
            int ch;
            Clob clob = null;
            Reader r = null;
            while(rs.next())
            {
                buffer = new StringBuffer();
                r = null;
                clob=rs.getClob("hs");
                r = clob.getCharacterStream();
                ch=0;
                while ((ch = r.read())!=-1) {
                   buffer.append(""+(char)ch);
                }
                if (buffer.toString().equals(hash)){
                     coincide=true;
                     break;
                 }
            }
          } catch(Exception ex) {
              logger.error(ex);
          }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
          //vamos a eliminar esos registros porque ya comparamos
          eliminarAnexosHash(usuario, usr, estado);
      }
      return coincide;
      
    }
    

    /**
     * Intenta cerrar un objeto ResultSet siempre y cuando sea posible.
     * @param resultado ResultSet a cerrar.
     */
    public static void cerrarObjetoDb(ResultSet resultado) {
        if (resultado != null) {
            try {
                resultado.close();
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }

    /**
    * Intenta cerrar un objeto Statement siempre y cuando sea posible.
    * @param instruccion PreparedStatement a cerrar.
    */
    public static void cerrarObjetoDb(PreparedStatement instruccion) {
        if (instruccion != null) {
            try {
                instruccion.close();
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }

    /**
    * Intenta cerrar un objeto Connection siempre y cuando sea posible.
    * @param conexion Statement a cerrar.
    */
    public static void cerrarObjetoDb(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }
    
    
    /**
     * Efectua el rollback de una conexion.
     * @param conexion Conexion a efectuar rollback.
     */
    public static void rollbackConnection(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.rollback();
            } catch(Exception ex) {
                logger.error(ex);
            }
        }
    }
    
    /**
     * Devuelve el valor que recibe como argumento. Si tal argumento es un null
     * entonces devuelve una cadena vacia.
     * @param cadena Nueva cadena
     */
    public static String nullToBlank(String cadena) {
        if (cadena == null) return "";
        return cadena;
    }
    
    
    
    /**
     * Devuelve una fecha en forma de String con el formato que recibe. 
     * @param fecha Fecha a formatear.
     * @param formato Formato a proporcionar a la fecha.
     * @return La fecha formateada.
     */
    public static String formatearFecha(Date fecha, String formato) {
        if (fecha == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(fecha);
    }
    
    /**
     * Devuelve una fecha a partir de la cadena utilizando la mascara que se le 
     * envia como argumento
     * @param fecha Fecha a formatear.
     * @param formato Formato a proporcionar a la fecha.
     * @return La fecha formateada.
     */
    public static Date convertirEnDate(String fecha, String formato) throws ParseException {
        if (Util.esCadenaVacia(fecha)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.parse(fecha);
    }
    
    /**
     * Determina si una cadena es vacia considerandola como tal si la variable 
     * String que recibe como parametro es nula o el contenido es una cadena de 
     * longitud cero.
     * @param cadena Cadena a verificar.
     * @return true si la cadena es nula o si su longitud es cero, false si las 
     * dos condiciones anteriores no se cumplen.
     */
    public static boolean esCadenaVacia(String cadena) {
        return (cadena == null || cadena.isEmpty());
    }
    
    /**
     * Devuelve el nombre de un idioma a partir del codigo ISO 639 a partir de 
     * la API de Java.
     * @param codigoISO639 Codigo ISO 639
     * @return Nombre del idioma.
     */
    public static String obtenerNombreIdioma(String codigoISO639) {
        String nombre = new Locale(codigoISO639).getDisplayLanguage(new Locale("es"));
        if (nombre == null) nombre = "";
        return nombre;
    }
    
    /**
     * Devuelve un arreglo que contiene todos los codigos ISO de los idiomas.
     * @return Arreglo de idiomas.
     */
    public static String[] obtenerCodigosISOIdiomas() {
        return Locale.getISOLanguages();
    }
    
    
    /**
     * Determina si un arreglo de objetos es viene vacío
     * @param arreglo
     * @return
     */
    public static <T> boolean esArregloVacio(T[] arreglo) {
        if (arreglo == null) return true;
        if (arreglo.length == 0) return true;
        return false;
    }
    

  /**
   * Esta funcion crea el header de las pantallas
   * para esamblar el menu
   * @return String con formato de HTML
   */
   public static String getHeaderPages()
  {
  
    String header = "<style>" + 
    "\n" + 
    "/**************** menu coding *****************/\n" + 
    "#menu {\n" + 
    "width: 150px;\n" + 
    "background: #COCOCO;\n" + 
    "}\n" + 
    "\n" + 
    "#menu ul {\n" + 
    "list-style: none;\n" + 
    "margin: 0;\n" + 
    "padding: 0;\n" + 
    "z-index: 1000;\n" + 
    "}\n" + 
    "\n" + 
    "#menu a, #menu h2 {\n" + 
    "font: bold 11px/16px arial, helvetica, sans-serif;\n" + 
    "display: block;\n" + 
    "border-width: 0px;\n" + 
    "border-style: solid;\n" + 
    "border-color: #ccc #888 #555 #bbb;\n" + 
    "margin: 0;\n" + 
    "padding: 2px 3px;\n" + 
    "}\n" + 
    "\n" + 
    "#menu h2 {\n" + 
    "color: #666666;\n" + 
    "background: #efefef;\n" + 
    "text-transform: uppercase;\n" + 
    "}\n" + 
    "\n" + 
    "#menu a {\n" + 
    "color: #174e90;\n" + 
    "background: #efefef;\n" + 
    "text-decoration: none;\n" + 
    "}\n" + 
    "\n" + 
    "#menu a:hover {\n" + 
    "color: #COCOCO;\n" + 
    "background: #E0CD7B;\n" + 
    "}\n" + 
    "\n" + 
    "#menu li {\n" + 
    "position: relative;\n" + 
    "}\n" + 
    "\n" + 
    "#menu ul ul ul {\n" + 
    "position: absolute;\n" + 
    "top: 0;\n" + 
    "left: 100%;\n" + 
    "width: 100%;\n" + 
    "}\n" + 
    "\n" + 
    "div#menu ul ul ul,\n" + 
    "div#menu ul ul li:hover ul ul\n" + 
    "{display: none;}\n" + 
    "\n" + 
    "div#menu ul ul li:hover ul,\n" + 
    "div#menu ul ul ul li:hover ul\n" + 
    "{display: block;}\n" + 
    "\n" + 
    "</style>\n" + 
    "<!--[if IE]>\n" + 
    "<style type=\"text/css\" media=\"screen\">\n" + 
    " #menu ul li {float: left; width: 100%;}\n" + 
    "</style>\n" + 
    "<![endif]-->\n" + 
    "<!--[if lt IE 7]>\n" + 
    "<style type=\"text/css\" media=\"screen\">\n" + 
    "body {\n" + 
    "behavior: url(/sponline/csshover.htc);\n" + 
    "font-size: 100%;\n" + 
    "} \n" + 
    "#menu ul li {float: left; width: 100%;}\n" + 
    "#menu ul li a {height: 1%;} \n" + 
    "\n" + 
    "#menu a, #menu h2 {\n" + 
    "font: bold 0.7em/1.4em arial, helvetica, sans-serif;\n" + 
    "} \n" + 
    "\n" + 
    "</style>\n" + 
    "<![endif]-->";
              
  return header;
  }

    /**
       *  Esta Funcion devuelve la fecha Actual del sistema, la cual es configurada
       *  en el sistema. 
       * @return */
      public static String getActualizacion()
      {
      String fecha = "<span style='color: rgb(147, 172, 191);'>v3.0.43</span>";
      return fecha;
      }

    /**
     * Esta funcion crea el header de las pantallas
     * para esamblar el menu
     * @return String con formato de HTML
     */
     public static String getMenu(String cod_emp, String sistema)
    {
        //<html:link forward="import">
      String menu="";
      StringBuffer sbOpcionesCli = new StringBuffer();
      sbOpcionesCli.append("<li><a href='./solReqTAction.do' title='Solicitud Requerimientos'>Solicitud Requerimientos</a></li>");
      sbOpcionesCli.append("<li><a href='./solReenTAction.do' title='Reenviar Solicitudes'>Reenviar Solicitudes</a></li>");
      StringBuffer sbOpcionesBdes = new StringBuffer();
      sbOpcionesBdes.append("<li><a href='./solCieTAction.do' title='Cerrar Solicitudes'>Cerrar Solicitudes</a></li>");
      sbOpcionesBdes.append("<li><a href='./solAudTAction.do' title='Aprobar/Denegar Solicitudes'>Aprobar/Denegar Solicitudes</a></li>");
      boolean abilitar=false;
      String paraId="";
      try{
          paraId=getProperty("id.institucion");
          UserData us = getDatosCifUsuario(cod_emp, sistema);
          abilitar=(StringUtils.trimToEmpty(us.getIdInstitucion()).equals(paraId)?true:false);
          if (abilitar) {
              menu=sbOpcionesBdes.toString();
          } else {
              menu=sbOpcionesCli.toString();
          }
      } catch (Exception ex){
          logger.error(ex);
      }
      return menu;
    }

    /**
     * Esta funcion crea de forma dinámica los datos de  
     * encabezado formularios y otras etiquetas
     * 1-formulario
     * 2-message
     * 3-submit
     * 4-file
     * 5-image
     * @return String
     */
     public static String getEtiquetasHt(int form, int etiqueta)
    {
        StringBuffer sb = new StringBuffer();
        try{
            switch (form) {
            case 1:
               if (etiqueta==1) {
                    sb.append("<html:form action=\"/solicitudReqAction\" method=\"post\" enctype=\"multipart/form-data\">");
                } else if (etiqueta==2) {
                    sb.append(ESAPI.encoder().encodeForHTMLAttribute("<html:messages id='message' property='common.file.err'>"));
                } else if (etiqueta==3) {
                    sb.append("<td width='25%'>");
                    sb.append(ESAPI.encoder().encodeForHTMLAttribute("<html:submit value='Guardar Anexo' onclick='return valida()'/>"));
                    sb.append("</td>");
                    sb.append("<td width='25%'>");
                    sb.append(ESAPI.encoder().encodeForHTMLAttribute("<html:submit value='Enviar Solicitudes' onclick='return llamaCierre()'/>"));
                    sb.append("</td>");
                } else if (etiqueta==4) {
                    sb.append("<html:file property=\"file1\" styleId=\"file1\" accept=\"application/pdf\" />");
                } else if (etiqueta==6) {
                    sb.append("</html:form>");
                } else if (etiqueta==7) {
                    sb.append(ESAPI.encoder().encodeForHTMLAttribute("</html:message>"));
                }
                break;
                case 2:
                   if (etiqueta==1) {
                        sb.append("<html:form styleId='ane' action='/solicitudReqAnexosAction' method='post' enctype='multipart/form-data'>");
                    } else if (etiqueta==5) {
                        sb.append("<input type='image' src='images/icon-delete.gif' onclick='selecBorrar('<%=ds.getAnrId()%>', '<%=ds.getReqId()%>') >");
                        sb.append("<label>");
                        sb.append("&nbsp;&nbsp;");
                        sb.append("</label>");
                        sb.append("<input type='image' src='images/icon_adjunto.gif' onclick='selecMostrar('<%=ds.getAnrId()%>', '<%=ds.getReqId()%>') >");
                    }
                break;
            }
        } catch (Exception eapi) {
            logger.error(eapi);
        }

      return sb.toString();
    }

    /**
     * Esta funcion crea de forma dinámica los datos de  
     * campos ocultos formularios
     * @return String
     */
     public static String getCamposOcultosFormularios(int form)
    {
        StringBuffer sb = new StringBuffer();
        switch (form) {
        case 1:
            sb.append("<input type='hidden' name='registrado' id='registrado' value='<%=disp%>' style='display:none'");
            sb.append("<input type='hidden' name='operacion' id='operacion' value='' style='display:none'");
            sb.append("<input type='hidden' name='tipos' id='tipos' value='<%=tDocu%>' style='display:none'");

        case 2:
            sb.append("<input type='hidden' name='selId' id='selId' value='' style='display:none'");
            sb.append("<input type='hidden' name='anrId' id='anrId' value='' style='display:none'");
            sb.append("<input type='hidden' name='opcion' id='opcion' value='' style='display:none'");

        }

      return sb.toString();
    }
    /**
     * Esta funcion crea de forma dinámica la lista de valores 
     * para el tipo documento. Si un tipo se usó no se icluye
     * @return String
     */
     public static String getDocumentos(List<DatosSolicitudes> lds)
    {
        Map<String, String> hmOption=new LinkedHashMap<String, String>();
        hmOption.put("D", "<option value='D'>Dui</option>");
        hmOption.put("N", "<option value='N'>Nit</option>");
        hmOption.put("J", "<option value='J'>Declaracion Jurada</option>");
        hmOption.put("F", "<option value='F'>Formulario</option>");

          StringBuffer sb = new StringBuffer("Tipo Documento : <select id='tipoDocumento' name='tipoDocumento' class='select'>");
          sb.append("<option value=''>Seleccione...</option>");
          List<String> ls = new ArrayList<String>();
          for (DatosSolicitudes ds:lds) {
              ls.add(ds.getTipoDocumento());
          }
          
          String key;
              for (Map.Entry<String, String> entry : hmOption.entrySet()) {
                  key = entry.getKey();
                  if (!ls.contains(key)) {
                      sb.append(entry.getValue());
                  }
              }
              sb.append("</select>");

      return sb.toString();
    }

    /**
     * Esta funcion valida para objetos String
     * con el metodo getCleanHtml. Se usa en jsp
     * Utiliza la libreria antisami
     * @return String
     * 
     
     public static String cleanEntrada(String input)
    {
        String limpio="";
        AntiSamy as = new AntiSamy(); // Create AntiSamy object
        InputStream is=null;
        try{
           is = Thread.currentThread().getContextClassLoader().getResourceAsStream("antisamy-ebay.xml");
           Policy policy = Policy.getInstance(is);
           CleanResults cr = as.scan(input, policy);
           limpio = cr.getCleanHTML();
        } catch (Exception ex) {
            logger.error(ex);
        }
        finally {
            try{
                if (is!=null){
                    is.close();
                }
            } catch (Exception ioe) {
                logger.error(ioe);
            }
        }
        return limpio;
    }    
     */
    
    /**
     * Esta funcion valida para objetos String
     * con el metodo getCleanHtml. Se usa en jsp
     * Utiliza la libreria antisami
     * @return String
     * 
     
     public static String cleanEntradaJavascript(String input)
    {
        String limpio="";
        AntiSamy as = new AntiSamy(); // Create AntiSamy object
        try{
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("antisamy-ebay.xml");

            Policy policy = Policy.getInstance(inputStream);
            CleanResults cr = as.scan(input, policy);
            limpio = cr.getCleanHTML();
        } catch (Exception ex) {
            logger.error(ex);
        }
        return limpio;
    }    
    */
    
    
    public static String toHexString(byte[] hash) 
        { 
            // Convert byte array into signum representation  
            BigInteger number = new BigInteger(1, hash);  
      
            // Convert message digest into hex value  
            StringBuilder hexString = new StringBuilder(number.toString(16));  
      
            // Pad with leading zeros 
            while (hexString.length() < 32)  
            {  
                hexString.insert(0, '0');  
            }  
      
            return hexString.toString();  
        } 
    
    public static byte[] getSHA(String input) {  
        byte[] b=null;
        try{
            String whi=getProperty("algoritm.digest");
            MessageDigest md = MessageDigest.getInstance(whi);  
            
            b= md.digest(input.getBytes(StandardCharsets.UTF_8));
            
        } catch (NoSuchAlgorithmException nos){
            logger.error(nos); 
        }
        return b;
    } 
    
    public static void asignaStat(List<String> a, String b) {
        a.add(b);
    }

}