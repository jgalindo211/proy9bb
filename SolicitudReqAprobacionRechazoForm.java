package bandesal.gob.sv.cif.anexos.form;

import bandesal.gob.sv.cif.anexos.util.Util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionMessage;

public class SolicitudReqAprobacionRechazoForm  extends org.apache.struts.action.ActionForm {
    @SuppressWarnings("compatibility:-5855420992446820017")
    private static final long serialVersionUID = -7642585617717504202L;

    private String general;
    private String detalle;
    private String operacion;

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
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (StringUtils.isBlank(general)) {
            errors.add("common.reqid.err", new ActionMessage("error.common.reqid"));
        }

        return errors;
    }
}
