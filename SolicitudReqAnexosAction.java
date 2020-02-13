package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.form.SolicitudReqAnexosForm;
import bandesal.gob.sv.cif.anexos.dto.DatosSolicitudes;
import bandesal.gob.sv.cif.anexos.util.Util;

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

public class SolicitudReqAnexosAction extends Action {
    private final static String SUCCESS = "success";
    final static Logger logger = Logger.getLogger(SolicitudReqAnexosAction.class);

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        SolicitudReqAnexosForm anexos = (SolicitudReqAnexosForm) form;
        Integer anrId = new Integer(0);
        Integer idReq = new Integer(0);
        String usuario = "";
        String opcion = "";
        Integer idBir=new Integer(-1);
        ActionMessages mensajes = new ActionMessages();

        try{
                //String s = StringUtils.trimToEmpty(Util.cleanEntrada(anexos.getSelId()));
                //opcion = StringUtils.trimToEmpty(Util.cleanEntrada(anexos.getOpcion()));
                String s = StringUtils.trimToEmpty(anexos.getSelId());
                opcion = StringUtils.trimToEmpty(anexos.getOpcion());
                idReq = Integer.parseInt(s);
                //s = StringUtils.trimToEmpty(Util.cleanEntrada(anexos.getAnrId()));
                s = StringUtils.trimToEmpty(anexos.getAnrId());
                anrId = Integer.parseInt(s);
                String estado=Util.getProperty("estado.enviado.solicitud");
                //usuario = StringUtils.trimToEmpty(Util.cleanEntrada(Util.getUser(request)));
                usuario = StringUtils.trimToEmpty(Util.getUser(request));
                String myOpeBorra=Util.toHexString(Util.getSHA("BORRAR"));
                String myOpeMuestra=Util.toHexString(Util.getSHA("MOSTRAR"));
                String req1=Util.toHexString(Util.getSHA(idReq.toString()));
                String anr1=Util.toHexString(Util.getSHA(anrId.toString()));
                opcion = StringUtils.trimToEmpty(anexos.getOpcion());
                if (myOpeBorra.equals(opcion)){
                    opcion="BORRAR";
                } else if (myOpeMuestra.equals(opcion)){
                    opcion="MOSTRAR";
                } else {
                    mensajes.add("common.file.err", new ActionMessage("mensaje.fallo.auten.items"));
                    saveMessages(request, mensajes);
                    throw new Exception("Fallo en autenticidad(opcion) operaciones items.");
                }
                if (!anexos.getVal1().equals(anr1) || !anexos.getVal2().equals(req1)){
                    mensajes.add("common.file.err", new ActionMessage("mensaje.fallo.auten.items"));
                    saveMessages(request, mensajes);
                    throw new Exception("Fallo en autenticidad operaciones items.");
                }
                if (opcion.equals("BORRAR")) {
                    if(isTokenValid(request)) {
                        resetToken(request);
                        saveToken(request);
                        String mensaje = Util.EliminarRequerimiento(anrId);
                        if (StringUtils.isNotBlank(mensaje)) {
                            throw new Exception("Problemas al eliminar solicitud de requerimiento");
                        } else {
                            idBir = Util.guardarBitacoraRequerimiento(idReq, usuario, estado );
                            //System.out.println("idBir " + idBir);
                        }
                    } else {
                        logger.info("invalid token Anexo borrar solicitud");
                    }
                } else if (opcion.equals("MOSTRAR")) {
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
        } catch (Exception e) {
            mensajes.add("common.file.err", new ActionMessage("mensaje.fallo.item.otro"));
            saveMessages(request, mensajes);
            logger.error(e);
        }
        return mapping.findForward(SUCCESS);

    }
}
