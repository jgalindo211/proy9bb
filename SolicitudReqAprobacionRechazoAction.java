package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.dto.DatosSolicitudes;
import bandesal.gob.sv.cif.anexos.form.SolicitudReqAprobacionRechazoForm;
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

public class SolicitudReqAprobacionRechazoAction extends Action{
    private final static String SUCCESS = "successAprobacion";
    final static Logger logger = Logger.getLogger(SolicitudReqAprobacionRechazoAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        SolicitudReqAprobacionRechazoForm aprobacionForm = (SolicitudReqAprobacionRechazoForm) form;
        String mensaje="";
        //String usuario = Util.cleanEntrada(Util.getUser(request));
        String usuario = Util.getUser(request);
        Validaciones vc = new Validaciones();
        Integer anrId = new Integer(0);
        Integer idBir = new Integer(-1);
        ActionMessages mensajes = new ActionMessages();

        try {
                //String reqId = StringUtils.trimToEmpty(Util.cleanEntrada(aprobacionForm.getGeneral()));
                //String operacion=StringUtils.trimToEmpty(Util.cleanEntrada(aprobacionForm.getOperacion()));
                String reqId = StringUtils.trimToEmpty(aprobacionForm.getGeneral());
                String operacion=StringUtils.trimToEmpty(aprobacionForm.getOperacion());
                String estado=Util.getProperty("estado.aprobado.solicitud");
                Integer p=Integer.parseInt(reqId);
                String estadoActual=vc.getEstado(usuario, p);
                if ("DENEGAR".equals(operacion)) {
                    estado=Util.getProperty("estado.denegado.solicitud");
                }
                if ("APROBAR".equals(operacion) && StringUtils.isNotBlank(reqId) && !estado.equals(estadoActual)) {
                    if(isTokenValid(request)) {
                        resetToken(request);
                        saveToken(request);
                        String sistema = Util.getProperty("id.sistema");
                        Integer idReq = Integer.parseInt(reqId);
                        mensaje = Util.actualizarEstadoRequerimiento(idReq, estado);
                        if (StringUtils.isNotBlank(mensaje)) {
                            
                             throw new Exception("Fallo en aprobacion requerimiento " + mensaje);
                            } else {
                                String desEstado=Util.getProperty("desc.estado.aprobado.solicitud");
                                mensaje=Util.enviarCorreoAprobacionRequerimiento(idReq, usuario, desEstado, sistema);
                                if (StringUtils.isNotBlank(mensaje)) {
                                     mensajes.add("fallo", new ActionMessage("mensaje.correo.aprobacion"));
                                     saveMessages(request, mensajes);
                                     throw new Exception("Fallo en envio correo de aprobacion " + mensaje);
                                    } else {
                                        String contentType=Util.getProperty("content.type");
                                        mensaje=Util.enviarCorreoHelpDesk(usuario, idReq, sistema, desEstado, contentType);
                                        if (StringUtils.isNotBlank(mensaje)) {
                                            mensajes.add("fallo", new ActionMessage("mensaje.correo.helpDesk"));
                                            saveMessages(request, mensajes);
                                             throw new Exception("Fallo en aprobacion requerimiento. Envio correo helpdesk");
                                        } else {
                                            estado=Util.getProperty("estado.aprobado.solicitud");
                                            idBir = Util.guardarBitacoraRequerimiento(idReq, usuario, estado);
                                        }
                                    }
                            }
                    } else {
                        logger.info("invalid token aprobacion de solicitud");
                    }
                } else if ("DENEGAR".equals(operacion) && StringUtils.isNotBlank(reqId) && !estado.equals(estadoActual)) {
                    if(isTokenValid(request)) {
                        resetToken(request);
                        saveToken(request);
                        String sistema = Util.getProperty("id.sistema");
                        Integer idReq = Integer.parseInt(reqId);
                        mensaje = Util.actualizarEstadoRequerimiento(idReq, estado);
                        if (StringUtils.isNotBlank(mensaje)) {
                             throw new Exception("Fallo en denegar requerimiento " + mensaje);
                            } else {
                                String desEstado=Util.getProperty("desc.estado.denegada.solicitud");
                                mensaje=Util.enviarCorreoRechazoRequerimiento(idReq, usuario, desEstado, sistema);
                                if (StringUtils.isNotBlank(mensaje)) {
                                     mensajes.add("fallo", new ActionMessage("mensaje.correo.rechazo"));
                                     saveMessages(request, mensajes);
                                     throw new Exception("Fallo en envio correo de denegar " + mensaje);
                                } else {
                                    estado=Util.getProperty("estado.denegado.solicitud");
                                    idBir = Util.guardarBitacoraRequerimiento(idReq, usuario, estado);
                                }
                            }
                    } else {
                        logger.info("invalid token rechazo de solicitud");
                    }
                } else if ("MOSTRAR".equals(operacion)) {
                    //String s = StringUtils.trimToEmpty(Util.cleanEntrada(aprobacionForm.geDetalle()));
                    String s = StringUtils.trimToEmpty(aprobacionForm.geDetalle());
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
