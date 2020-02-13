package bandesal.gob.sv.cif.anexos.form;

import bandesal.gob.sv.cif.anexos.util.Util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionMessage;

public class SolicitudReqReenvioForm  extends org.apache.struts.action.ActionForm {
    @SuppressWarnings("compatibility:-2474506609119572060")
    private static final long serialVersionUID = 1L;

    private String general;
    private String detalle;
    private String operacion;
    private String val1;
    private String val2;

    public void setGeneral(String general) {
        //this.general = Util.cleanEntrada(general);
        this.general = general;
    }

    public String getGeneral() {
        return general;
    }

    public void setDetalle(String detalle) {
        //this.detalle = Util.cleanEntrada(detalle);
        this.detalle = detalle;
    }

    public String geDetalle() {
        return detalle;
    }

    public void setOperacion(String operacion) {
        //this.operacion = Util.cleanEntrada(operacion);
        this.operacion = operacion;
    }

    public String getOperacion() {
        return operacion;
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
        if (StringUtils.isBlank(general)) {
            errors.add("common.reqid.err", new ActionMessage("error.common.reqid"));
        }

        return errors;
    }
    
}
