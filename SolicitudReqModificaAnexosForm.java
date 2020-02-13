package bandesal.gob.sv.cif.anexos.form;

import bandesal.gob.sv.cif.anexos.util.Util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public class SolicitudReqModificaAnexosForm extends org.apache.struts.action.ActionForm{
    @SuppressWarnings("compatibility:2750712724993857032")
    private static final long serialVersionUID = 1L;

    private String anrId;
    private String selId;
    private String opcion;
    private String val1;
    private String val2;

    public void setAnrId(String anrId) {
        //this.anrId = Util.cleanEntrada(anrId);
        this.anrId = anrId;
    }

    public String getAnrId() {
        return anrId;
    }
    
    public void setSelId(String selId) {
        //this.selId = Util.cleanEntrada(selId);
        this.selId = selId;
    }

    public String getSelId() {
        return selId;
    }
    
    public void setOpcion(String opcion) {
        //this.opcion = Util.cleanEntrada(opcion);
        this.opcion = opcion;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal2(String val2) {
        this.val2 = val2;
    }

    public String getVal2() {
        return val2;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        //String id=StringUtils.trimToEmpty(Util.cleanEntrada(request.getParameter("anrId")));
        String id=StringUtils.trimToEmpty(request.getParameter("anrId"));
        return errors;
    }
}
