package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.dto.UserData;
import bandesal.gob.sv.cif.anexos.form.SolicitudReqForm;

import bandesal.gob.sv.cif.anexos.util.Util;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.log4j.Logger;

public class SolicitudReqAction extends Action {
    final static Logger logger = Logger.getLogger(SolicitudReqAction.class);

    private final static String SUCCESS = "success";
    
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        SolicitudReqForm uploadForm = (SolicitudReqForm) form;
        InputStream inputStream = null;
        FormFile archivosForm = null;
        UserData ud = new UserData();
        Integer idReq = new Integer(-1);
        Integer idAnr = new Integer(-1);
        Integer idBir = new Integer(-1);
        String nombreArchivo = "";
        String tipoDocumento = "";
        String tipoArea = "";
        String registrado = "";
        String operacion = "";
        String estado="";
        String mensaje="";
        ActionMessages mensajes = new ActionMessages();
        InputStream is=null;
        try {
            archivosForm = uploadForm.getFile1();
            String sistema = Util.getProperty("id.sistema");
            //String usuario = StringUtils.trimToEmpty(Util.cleanEntrada(Util.getUser(request)));
            String usuario = StringUtils.trimToEmpty(Util.getUser(request));
            ud = Util.getDatosCifUsuario(usuario, sistema);
            //registrado = StringUtils.trimToEmpty(Util.cleanEntrada(uploadForm.getRegistrado()));
            registrado = StringUtils.trimToEmpty(uploadForm.getRegistrado());
            String myOpeGuarda=Util.toHexString(Util.getSHA("GUARDAR"));
            String myOpeEnvia=Util.toHexString(Util.getSHA("ENVIAR"));
            operacion = StringUtils.trimToEmpty(uploadForm.getOperacion());
            if (myOpeGuarda.equals(operacion)){
                operacion="GUARDAR";
            } else if (myOpeEnvia.equals(operacion)){
                operacion="ENVIAR";
            } else {
                mensajes.add("common.file.err", new ActionMessage("mensaje.fallo.auten.registro"));
                saveMessages(request, mensajes);
                throw new Exception("Fallo en registro(operacion) autenticidad requerimiento.");
            }
            //verificar el hash
            
                if (operacion.equals("ENVIAR")) {
                    if(isTokenValid(request)) {
                        resetToken(request);
                        saveToken(request);
                        estado=Util.getProperty("estado.enviado.solicitud");
                        idReq = Integer.parseInt(registrado);
                        mensaje=Util.actualizarEstadoRequerimiento(idReq, estado);
                        if (StringUtils.isNotBlank(mensaje)) {
                            mensajes.add("fallo", new ActionMessage("mensaje.fallo.registro"));
                            saveMessages(request, mensajes);
                             throw new Exception("Fallo en enviar requerimiento. Actualizar estado");
                        } else {
                            String desEstado=Util.getProperty("desc.estado.enviado.solicitud");
                            mensaje=Util.enviarCorreoIngresoRequerimiento(idReq, usuario, desEstado, sistema);
                            if (StringUtils.isNotBlank(mensaje)) {
                                mensajes.add("fallo", new ActionMessage("mensaje.correo.registro"));
                                saveMessages(request, mensajes);
                                 throw new Exception("Fallo en enviar requerimiento. Envio correo ingreso");
                            }
                        }
                         mensajes.add("exito", new ActionMessage("mensaje.exitoso"));
                         saveMessages(request, mensajes);
                    } else {
                        logger.info("invalid token envio de solicitud");
                    }
                } else if (archivosForm != null && archivosForm.getFileSize() > 0) {
                    ud.setUsuario(usuario);
                    ud.setAnexo(uploadForm.getFile1().getInputStream());

                    if (operacion.equals("GUARDAR")) {
                        if(isTokenValid(request)) {
                            resetToken(request);
                            saveToken(request);
                            estado=Util.getProperty("estado.inicial.solicitud");
                            is = archivosForm.getInputStream();
                            byte[] b = new byte[is.available()];
                            is.read(b);//le asigna al array
                            String myFileHash = Util.toHexString(Util.getSHA(new String(b)));
                            boolean igual = Util.compararHash(ud,usuario, myFileHash, estado);
                            if (!igual){
                                mensajes.add("fallo", new ActionMessage("mensaje.fallo.auten.registro"));
                                saveMessages(request, mensajes);
                                throw new Exception("Fallo en registro autenticidad requerimiento.");
                            }
                            if (StringUtils.isBlank(registrado)) {
                                idReq = Util.guardarRequerimiento(ud, estado);
                            } else {
                                idReq = Integer.parseInt(registrado);
                            }
                            idAnr = new Integer(-1);
                            idBir = new Integer(-1);
                            if (idReq > 0) {
                                //nombreArchivo = StringUtils.trimToEmpty(Util.cleanEntrada(archivosForm.getFileName()));
                                //tipoDocumento = StringUtils.trimToEmpty(Util.cleanEntrada(uploadForm.getTipoDocumento()));
                                //tipoArea = StringUtils.trimToEmpty(Util.cleanEntrada(uploadForm.getTipoArea()));
                                nombreArchivo = StringUtils.trimToEmpty(archivosForm.getFileName());
                                tipoDocumento = StringUtils.trimToEmpty(uploadForm.getTipoDocumento());
                                tipoArea = StringUtils.trimToEmpty(uploadForm.getTipoArea());
                                inputStream = archivosForm.getInputStream();
                                idAnr = Util.guardarAnexosRequerimiento(idReq, ud.getUsuario(), inputStream, nombreArchivo, tipoDocumento, tipoArea);
                                if (idAnr > 0) {
                                    estado=Util.getProperty("estado.enviado.solicitud");
                                    idBir = Util.guardarBitacoraRequerimiento(idReq, ud.getUsuario(), estado);
                                }
                            }//>0
                        } else {
                            logger.info("invalid token envio de solicitud");
                        }
                        
                    }//operacion
                }//form diferente null
        } catch (Exception ex) {
            logger.info("exception " + ex.getMessage());
            mensajes.add("common.file.err", new ActionMessage("mensaje.fallo.item.otro"));
            saveMessages(request, mensajes);
            logger.error(ex);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return mapping.findForward(SUCCESS);
    }
}
