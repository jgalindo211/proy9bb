package bandesal.gob.sv.cif.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "SesionUsuario", urlPatterns = { "/sesionusuario" })
public class SesionUsuario extends ComunServlet {
    @SuppressWarnings("compatibility:3455591950060002003")
    private static final long serialVersionUID = 3608083053188260585L;
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public SesionUsuario() {
        super();
    }

    @Override
        protected void ejecutar(HttpServletRequest request,
                                HttpServletResponse response) {
            String operacion = request.getParameter("operacion");
            if (operacion == null) {
                return;
            } else if (operacion.equals("cerrarSesion")) {
                cerrarSesion(request, response);
            }
        }
        
        /**
         * Cierra la sesion activa del usuario.
         * @param request Peticion.
         * @param response Respuesta.
         */
        private void cerrarSesion(HttpServletRequest request, 
                                  HttpServletResponse response) {
            HttpSession sesion = request.getSession(false);
            
            if (sesion != null) {
                sesion.invalidate();
                sesion = null;
            }
        }
}
