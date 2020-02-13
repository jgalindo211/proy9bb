package bandesal.gob.sv.cif.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ComunServlet extends HttpServlet{
    @SuppressWarnings("compatibility:5029743548932928737")
    private static final long serialVersionUID = -6614683207374556235L;

    public ComunServlet() {
        super();
    }
    
    @Override
       protected void doGet(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException,
                                                                 IOException {
           ejecutar(request, response);
       }
     
       @Override
       protected void doPost(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException,
                                                                  IOException {
           ejecutar(request, response);
       }

       /**
        * Determina la acci?n a ajecutar por el Servlet.
        * @param request Peticion. 
        * @param response Respuesta.
        */
       protected abstract void ejecutar(HttpServletRequest request,
                                        HttpServletResponse response);
       
}
