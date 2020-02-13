package bandesal.gob.sv.cif.anexos.util;

import bandesal.gob.sv.cif.anexos.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;

public class Validaciones {
    final static Logger logger = Logger.getLogger(Validaciones.class);
    public  String getEstado(String usuario, Integer reqId){
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String estadoActual="";
      String queryConsultaSolicitudes = "";
      
      queryConsultaSolicitudes = "SELECT " 
            + " REQ_ID_ESTADO AS estado"
            + " FROM CIF_REQ_REQUERIMIENTOS"                                 
            + " WHERE REQ_USER_ID = ?" 
            + " AND REQ_ID=?";
        //System.out.println(query_consulta_puestos);
      try{
            conn = ConnectionManager.getConnection() ;
            pstmt = conn.prepareStatement(queryConsultaSolicitudes);
            pstmt.setString(1, usuario);
            pstmt.setInt(2, reqId);
            rs = pstmt.executeQuery();

             while(rs.next())
            {
                estadoActual=StringUtils.trimToEmpty(rs.getString("estado"));
            }
          }      catch(SQLException e) {
                 logger.error(e);

             } catch (Exception ex) {
                 logger.error(ex);
             }
      finally {
          cerrarObjetoDb(rs);
          cerrarObjetoDb(pstmt);
          cerrarObjetoDb(conn);
      }
      return estadoActual;
      
    }

    /**
     * Intenta cerrar un objeto ResultSet siempre y cuando sea posible.
     * @param resultado ResultSet a cerrar.
     */
    public  void cerrarObjetoDb(ResultSet resultado) {
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
    public void cerrarObjetoDb(PreparedStatement instruccion) {
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
    public void cerrarObjetoDb(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }
    
    
}
