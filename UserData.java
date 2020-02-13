package bandesal.gob.sv.cif.anexos.dto;

import java.io.InputStream;

public class UserData {
    private String usuario;
    private String nombre;
    private String idInstitucion;
    private String institucion;
    private String email;
    private String fecha;
    private InputStream anexo;

    public UserData(){
        usuario="";
        nombre="";
        idInstitucion="";
        institucion="";
        email="";
        fecha="";
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdInstitucion(String idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public String getIdInstitucion() {
        return idInstitucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setAnexo(InputStream anexo) {
        this.anexo = anexo;
    }

    public InputStream getAnexo() {
        return anexo;
    }

}
