package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.dto.UserData;
import bandesal.gob.sv.cif.anexos.dto.DatosSolicitudes;
import bandesal.gob.sv.cif.anexos.form.SolicitudReqModificaForm;
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

public class SolicitudReqModificaAction extends Action{
    private String message;
    final static Logger logger = Logger.getLogger(SolicitudReqModificaAction.class);

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        SolicitudReqModificaForm uploadForm = (SolicitudReqModificaForm) form;
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
        String estadoHash="";
        String SUCCESS = "successModifica";//cuando sea ENVIAR va a cambiar, porque vendra la de Reenvio
        try {
                archivosForm = uploadForm.getFile1();
                String sistema = Util.getProperty("id.sistema");
                //String usuario = StringUtils.trimToEmpty(Util.cleanEntrada(Util.getUser(request)));
                String usuario = StringUtils.trimToEmpty(Util.getUser(request));
                ud = Util.getDatosCifUsuario(usuario, sistema);
                //registrado = StringUtils.trimToEmpty(Util.cleanEntrada(uploadForm.getRegistrado()));
                //operacion = StringUtils.trimToEmpty(Util.cleanEntrada(uploadForm.getOperacion()));
                registrado = StringUtils.trimToEmpty(uploadForm.getRegistrado());
                operacion = StringUtils.trimToEmpty(uploadForm.getOperacion());
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
                    throw new Exception("Fallo en registro(operacion modif.) autenticidad requerimiento.");
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
                             throw new Exception("Fallo en Modificar requerimiento. Actualizar estado");
                        } else {
                            //en la operacion ENVIAR hay que eliminar los anexos hash de ese estado
                            estadoHash=Util.getProperty("estado.forward.hash.solicitud");
                            Util.eliminarAnexosHash(usuario, ud, estadoHash);
                            String desEstado=Util.getProperty("desc.estado.enviado.solicitud");
                            mensaje=Util.enviarCorreoIngresoRequerimiento(idReq, usuario, desEstado, sistema);
                            if (StringUtils.isNotBlank(mensaje)) {
                                mensajes.add("fallo", new ActionMessage("mensaje.correo.registro"));
                                saveMessages(request, mensajes);
                                 throw new Exception("Fallo en enviar requerimiento. Envio correo ingreso");
                            }
                        }
                         mensajes.add("exito", new ActionMessage("mensaje.modifica.exitoso"));
                         saveMessages(request, mensajes);
                    } else {
                        logger.info("invalid token envio de solicitud");
                    }
                } else if (archivosForm != null && archivosForm.getFileSize() > 0) {
                    ud.setUsuario(usuario);
                    ud.setAnexo(uploadForm.getFile1().getInputStream());

                    if ("GUARDAR".equals(operacion)) {
                        if(isTokenValid(request)) {
                            resetToken(request);
                            saveToken(request);
                            is = archivosForm.getInputStream();
                            byte[] b = new byte[is.available()];
                            is.read(b);//le asigna al array
                            String myFileHash = Util.toHexString(Util.getSHA(new String(b)));
                            estadoHash=Util.getProperty("estado.forward.hash.solicitud");
                            boolean igual = Util.compararHash(ud,usuario, myFileHash, estadoHash);
                            if (!igual){
                                mensajes.add("fallo", new ActionMessage("mensaje.fallo.auten.registro"));
                                saveMessages(request, mensajes);
                                throw new Exception("Fallo en registro modificacion autenticidad requerimiento.");
                            }
                            if (StringUtils.isBlank(registrado)) {
                                throw new Exception("Fallo en Guardar requerimiento. Id vacio");
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
                            }
                        } else {
                            logger.info("invalid token envio de solicitud");
                        }
                    }//if guardar
                }//if not null
        } catch (Exception ex) {
            System.out.println("exception " + ex.getMessage());
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
