package bandesal.gob.sv.cif.anexos.form;

import bandesal.gob.sv.cif.anexos.util.Util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionMessage;

public class SolicitudReqCierreForm extends org.apache.struts.action.ActionForm {
    @SuppressWarnings("compatibility:5943450227636974893")
    private static final long serialVersionUID = 8848649053913238990L;

    private String padreId;
    private String detalleId;
    private String operacion;

    public void setPadreId(String padreId) {
        //this.padreId = Util.cleanEntrada(padreId);
        this.padreId = padreId;
    }

    public String getPadreId() {
        return padreId;
    }

    public void setDetalleId(String detalleId) {
        //this.detalleId = Util.cleanEntrada(detalleId);
        this.detalleId = detalleId;
    }

    public String getDetalleId() {
        return detalleId;
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
        if (StringUtils.isBlank(padreId)) {
            errors.add("common.reqid.err", new ActionMessage("error.common.reqid"));
        }

        return errors;
    }
    
}
