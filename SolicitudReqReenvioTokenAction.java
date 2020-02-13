package bandesal.gob.sv.cif.anexos.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;

public class SolicitudReqReenvioTokenAction extends Action{
    private final static String SUCCESS = "successReeSol";

    public ActionForward execute(ActionMapping mapping,ActionForm form,
    HttpServletRequest request,HttpServletResponse response)
    { 
        ActionForward forward;
        saveToken(request);
        forward=mapping.findForward(SUCCESS);
        return forward;
    }

}
