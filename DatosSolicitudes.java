package bandesal.gob.sv.cif.anexos.dto;

import java.io.InputStream;
import java.sql.Blob;

public class DatosSolicitudes {
    private Integer reqId;
    private String reqIdEstado;
    private String reqIdInstitucion;
    private String reqFecha;
    private String reqTipoArea;
    private InputStream anexo;
    private String nombreArchivo;
    private String tipoDocumento;
    private Integer anrId;
    Blob blob;

    public DatosSolicitudes(){
        reqId=new Integer(0);
        reqIdEstado="";
        reqIdInstitucion="";
        reqFecha="";
        reqTipoArea="";
        anexo=null;
        nombreArchivo="";
        anrId=new Integer(0);
        blob=null;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setReqIdEstado(String reqIdEstado) {
        this.reqIdEstado = reqIdEstado;
    }

    public String getReqIdEstado() {
        return reqIdEstado;
    }

    public void setReqIdInstitucion(String reqIdInstitucion) {
        this.reqIdInstitucion = reqIdInstitucion;
    }

    public String getReqIdInstitucion() {
        return reqIdInstitucion;
    }

    public void setReqFecha(String reqFecha) {
        this.reqFecha = reqFecha;
    }

    public String getReqFecha() {
        return reqFecha;
    }

    public void setReqTipoArea(String reqTipoArea) {
        this.reqTipoArea = reqTipoArea;
    }

    public String getReqTipoArea() {
        return reqTipoArea;
    }

    public void setAnexo(InputStream anexo) {
        this.anexo = anexo;
    }

    public InputStream getAnexo() {
        return anexo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setAnrId(Integer anrId) {
        this.anrId = anrId;
    }

    public Integer getAnrId() {
        return anrId;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Blob getBlob() {
        return blob;
    }
}
