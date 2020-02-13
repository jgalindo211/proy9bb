package bandesal.gob.sv.cif.anexos.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

import org.apache.struts.upload.FormFile;

public class ReqModForm  extends ValidatorForm {
    @SuppressWarnings("compatibility:-2061270184495427385")
    private static final long serialVersionUID = -2313057397594381679L;
    private FormFile file1;
    /**
     * @return the FormFile
     */
    public FormFile getFile1() {
        return file1;
    }

    /**
     * @param file the files to set
     */
    public void setFile1(FormFile file1) {
        this.file1 = file1;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        return errors;
    }
    
}
