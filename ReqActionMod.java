package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.dto.UserData;
import bandesal.gob.sv.cif.anexos.form.ReqForm;

import bandesal.gob.sv.cif.anexos.form.ReqModForm;
import bandesal.gob.sv.cif.anexos.util.Util;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.upload.FormFile;
import org.apache.log4j.Logger;

public class ReqActionMod  extends Action {
    final static Logger logger = Logger.getLogger(ReqActionMod.class);
    private final static String SUCCESS = "successModReq";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        ReqModForm uploadForm = (ReqModForm) form;
        
        InputStream is = null;
        UserData ud = new UserData();
        FormFile archivosForm = null;
        Integer idReq = new Integer(-1);
        String mensaje="";
        try{
            archivosForm=uploadForm.getFile1();
            is = archivosForm.getInputStream();
            byte[] b = new byte[is.available()];
            is.read(b);//le asigna al array
            String myFileHash = Util.toHexString(Util.getSHA(new String(b)));
            String sistema = Util.getProperty("id.sistema");
            //String usuario = StringUtils.trimToEmpty(Util.cleanEntrada(Util.getUser(request)));
            String usuario = StringUtils.trimToEmpty(Util.getUser(request));
            ud = Util.getDatosCifUsuario(usuario, sistema);
            //System.out.println("ajx hash " + myFileHash);
            String estadoHash=Util.getProperty("estado.forward.hash.solicitud");
            idReq = Util.guardarAnexosHash(ud, usuario, myFileHash, estadoHash);
            if (StringUtils.isNotBlank(mensaje)) {
                logger.info("Fallo en grabar el hash modifica");
            }


        } catch(Exception ex){
            logger.error(ex);
        }
        return null;//se necesita para que no vuelva a recargar la pagina
    }
        
}
