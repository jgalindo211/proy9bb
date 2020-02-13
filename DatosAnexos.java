package bandesal.gob.sv.cif.anexos.dto;

//import java.io.InputStream;

//import java.sql.Blob;

public class DatosAnexos {
    private Integer reqId;
    private byte[] anexo;
    private String nombreArchivo;
    private String fecha;
    private String institucion;
    private Integer anrId;
//    Blob blob;

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    public Integer getReqId() {
        return reqId;
    }

    public void setAnexo(byte[] anexo) {
        this.anexo = anexo;
    }

    public byte[] getAnexo() {
        return anexo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setAnrId(Integer anrId) {
        this.anrId = anrId;
    }

    public Integer getAnrId() {
        return anrId;
    }
/*
    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Blob getBlob() {
        return blob;
    }
*/
}
