package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.dto.DatosSolicitudes;
import bandesal.gob.sv.cif.anexos.form.SolicitudReqCierreForm;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class SolicitudReqCierreAction extends Action {
    private final static String SUCCESS = "successCierre";
    final static Logger logger = Logger.getLogger(SolicitudReqCierreAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        SolicitudReqCierreForm cierreForm = (SolicitudReqCierreForm) form;
        //String reqId = StringUtils.trimToEmpty(Util.cleanEntrada(cierreForm.getPadreId()));
        String reqId = StringUtils.trimToEmpty(cierreForm.getPadreId());
        Integer idReq = Integer.parseInt(reqId);
        String mensaje="";
        Integer p=Integer.parseInt(reqId);
        //String usuario = Util.cleanEntrada(Util.getUser(request));
        String usuario = Util.getUser(request);
        Integer anrId = new Integer(0);
        Integer idBir = new Integer(-1);
        ActionMessages mensajes = new ActionMessages();
        try {
                //String operacion=StringUtils.trimToEmpty(Util.cleanEntrada(cierreForm.getOperacion()));
                String operacion=StringUtils.trimToEmpty(cierreForm.getOperacion());
                String estado=Util.getProperty("estado.cerrado.solicitud");
                Validaciones vc = new Validaciones();
                String estadoActual=vc.getEstado(usuario, p);
                if ("CERRAR".equals(operacion) && StringUtils.isNotBlank(reqId) && !estado.equals(estadoActual)) {
                        if(isTokenValid(request)) {
                            resetToken(request);
                            saveToken(request);
                            String sistema = Util.getProperty("id.sistema");
                            mensaje = Util.actualizarEstadoRequerimiento(idReq, estado);
                            if (StringUtils.isNotBlank(mensaje)) {
                                 throw new Exception("Fallo en cierre requerimiento " + mensaje);
                                } else {
                                    String desEstado=Util.getProperty("desc.estado.cerrado.solicitud");
                                    mensaje=Util.enviarCorreoCierreRequerimiento(idReq, usuario, desEstado, sistema);
                                    if (StringUtils.isNotBlank(mensaje)) {
                                        mensajes.add("fallo", new ActionMessage("mensaje.correo.cierre"));
                                        saveMessages(request, mensajes);
                                         throw new Exception("Fallo en envio correo de cierre " + mensaje);
                                    } else {
                                        estado=Util.getProperty("estado.cerrado.solicitud");
                                        idBir = Util.guardarBitacoraRequerimiento(idReq, usuario, estado);
                                    }
                                }
                        } else {
                            logger.info("invalid token cierre de solicitud");
                        }
                }
                if ("MOSTRAR".equals(operacion)) {
                        //String s = StringUtils.trimToEmpty(Util.cleanEntrada(cierreForm.getDetalleId()));
                        String s = StringUtils.trimToEmpty(cierreForm.getDetalleId());
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
                                int bytesRead=0;
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
                                } catch (IOException ex) {
                                    logger.error(ex);

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
