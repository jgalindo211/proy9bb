package bandesal.gob.sv.cif.anexos.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

public class SolicitudReqTokenAction  extends Action{
    private final static String SUCCESS = "successSol";

    public ActionForward execute(ActionMapping mapping,ActionForm form,
    HttpServletRequest request,HttpServletResponse response)
    { 
        ActionForward forward;
        saveToken(request);
        forward=mapping.findForward(SUCCESS);// this is the jsp page where you want to struts tokens.
        return forward;
    }
}
