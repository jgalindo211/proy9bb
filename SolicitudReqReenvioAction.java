package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.dto.DatosSolicitudes;
import bandesal.gob.sv.cif.anexos.form.SolicitudReqReenvioForm;
import bandesal.gob.sv.cif.anexos.util.Util;

import bandesal.gob.sv.cif.anexos.util.Validaciones;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.log4j.Logger;

public class SolicitudReqReenvioAction extends Action{
    private final static String SUCCESS = "successReenvio";
    final static Logger logger = Logger.getLogger(SolicitudReqReenvioAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        SolicitudReqReenvioForm reenvioForm = (SolicitudReqReenvioForm) form;
    //String reqId = StringUtils.trimToEmpty(Util.cleanEntrada(reenvioForm.getGeneral()));
    //String operacion=StringUtils.trimToEmpty(Util.cleanEntrada(reenvioForm.getOperacion()));
    String reqId = StringUtils.trimToEmpty(reenvioForm.getGeneral());
    String operacion=StringUtils.trimToEmpty(reenvioForm.getOperacion());
    String estado=Util.getProperty("estado.forward.solicitud");

    String mensaje="";
    Integer p=Integer.parseInt(reqId);
    //String usuario = StringUtils.trimToEmpty(Util.cleanEntrada(Util.getUser(request)));
    String usuario = StringUtils.trimToEmpty(Util.getUser(request));
    Validaciones vc = new Validaciones();
    String estadoActual=vc.getEstado(usuario, p);
    Integer anrId = new Integer(0);
    try {
            if ("REENVIAR".equals(operacion) && StringUtils.isNotBlank(reqId) && !estado.equals(estadoActual)) {
                if(isTokenValid(request)) {
                    resetToken(request);
                    saveToken(request);
                    Integer idReq = Integer.parseInt(reqId);
                    mensaje = Util.actualizarEstadoRequerimiento(idReq, estado);
                    if (StringUtils.isNotBlank(mensaje)) {
                         throw new Exception("Fallo en reenvio requerimiento " + mensaje);
                    }
                }
            } else if ("MOSTRAR".equals(operacion)) {
                //String s = StringUtils.trimToEmpty(Util.cleanEntrada(reenvioForm.geDetalle()));
                String s = StringUtils.trimToEmpty(reenvioForm.geDetalle());
                anrId = Integer.parseInt(s);
                DatosSolicitudes ds = Util.getDatosSolicitudesById(anrId);
                if (ds.getBlob() != null) {
                    response.setHeader("Content-Type", "application/pdf");
                    response.setHeader("Content-Length", String.valueOf(ds.getBlob().length()));
                    response.setHeader("Content-Disposition", "inline; filename=\"" + "archivo" + ".pdf" + "\"");
                    response.setHeader("Cache-control", "private, max-age=0");

                    InputStream is = null;
                    OutputStream os= null;
                    try {
                        os=response.getOutputStream();
                        is = ds.getBlob().getBinaryStream();
                    
                        byte[] bytes = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(bytes)) != -1) {
                            os.write(bytes, 0, bytesRead);
                        }
                        } catch (IOException ioe) {
                            logger.error(ioe);

                        } finally {
                            try {
                                if (is!=null) {
                                    is.close();
                                }
                                if (os!=null) {
                                    os.close();
                                }
                            } catch (IOException ioe) {
                                logger.error(ioe);
                            }
                        }
                }//if blob not null
            }//if MOSTRAR
    } catch (Exception ex) {
        logger.error(ex);

    }
    return mapping.findForward(SUCCESS);
    }
}
