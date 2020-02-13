package bandesal.gob.sv.cif.anexos.form;

import bandesal.gob.sv.cif.anexos.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.apache.pdfbox.preflight.utils.ByteArrayDataSource;

public class SolicitudReqModificaForm extends org.apache.struts.action.ActionForm {
    @SuppressWarnings("compatibility:1450804149301922451")
    private static final long serialVersionUID = 1L;

    private FormFile file1;
    private String registrado;
    private String operacion;
    private String tipoDocumento;
    private String tipoArea;
    private String message;

    public void setMessage(String message) {
        //this.message = Util.cleanEntrada(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        //this.operacion = Util.cleanEntrada(operacion);
        this.operacion = operacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        //this.tipoDocumento = Util.cleanEntrada(tipoDocumento);
        this.tipoDocumento = tipoDocumento;
    }

    public String getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(String tipoArea) {
        //this.tipoArea = Util.cleanEntrada(tipoArea);
        this.tipoArea = tipoArea;
    }

    public String getRegistrado() {
        return registrado;
    }

    public void setRegistrado(String registrado) {
        //this.registrado = Util.cleanEntrada(registrado);
        this.registrado = registrado;
    }

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
        int tamanio = Integer.parseInt(Util.getProperty("tamanio.file.size"));
        InputStream is = null;
        PreflightDocument preflightDocument=null;
        try{
            String ope="";
            String myOpeGuarda=Util.toHexString(Util.getSHA("GUARDAR"));
            String myOpeEnvia=Util.toHexString(Util.getSHA("ENVIAR"));
            if (myOpeGuarda.equals(operacion)){
                ope="GUARDAR";
            } else if (myOpeEnvia.equals(operacion)){
                ope="ENVIAR";
            } else {
                System.out.println("else operacion");
                errors.add("common.file.err", new ActionMessage("mensaje.fallo.auten.registro"));
            }

            if (!"ENVIAR".equals(ope)) {
                if (file1 == null) {
                    errors.add("common.file.err", new ActionMessage("error.common.file.required"));
                } else if (file1.getFileSize() > 0 && file1.getFileSize() > tamanio) {
                    errors.add("common.file.err", new ActionMessage("error.common.file.size"));
                } else if (StringUtils.isBlank(file1.getFileName())) {
                        errors.add("common.file.err", new ActionMessage("error.common.file.required"));
                } else if (file1.getContentType().equals("application/pdf")) {
                    is = file1.getInputStream();
                    try {
                        PreflightParser parser = new PreflightParser(new ByteArrayDataSource(is));
                        parser.parse();
                        preflightDocument = parser.getPreflightDocument();
                        preflightDocument.validate();
                    } catch (Exception vex) {
                        errors.add("common.file.err", new ActionMessage("error.common.file.type"));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();;
        } finally {
            try {
                if (is!=null) {
                    is.close();
                }
                if (preflightDocument!=null) {
                    preflightDocument.close();
                }
            } catch (Exception ioe){
                ioe.printStackTrace();;
            }
        }

        return errors;
    }
}
