package bandesal.gob.sv.cif.anexos.action;

import bandesal.gob.sv.cif.anexos.dto.UserData;
import bandesal.gob.sv.cif.anexos.form.ReqForm;

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

public class ReqAction  extends Action {
    final static Logger logger = Logger.getLogger(ReqAction.class);
    private final static String SUCCESS = "successReq";
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        ReqForm uploadForm = (ReqForm) form;
        InputStream is = null;
        UserData ud = new UserData();
        FormFile archivosForm = null;
        try{
            archivosForm=uploadForm.getFile1();
            is = archivosForm.getInputStream();
            byte[] b = new byte[is.available()];
            is.read(b);//le asigna al array
            String sistema = Util.getProperty("id.sistema");
            String usuario = StringUtils.trimToEmpty(Util.getUser(request));
            ud = Util.getDatosCifUsuario(usuario, sistema);

        } catch(Exception ex){
            logger.error(ex);
        }
        return mapping.findForward(SUCCESS);
    }
    
}
