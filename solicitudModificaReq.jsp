<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<jsp:useBean id="utilidad" class="bandesal.gob.sv.cif.anexos.util.Util" scope="session"/>
<jsp:useBean id="us" class="bandesal.gob.sv.cif.anexos.dto.UserData" scope="session"/>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.lang.ArrayUtils"%>
<%@ page import="bandesal.gob.sv.cif.anexos.dto.DatosSolicitudes"%>
<%@ page import="org.owasp.esapi.*"%>
<%
String nombre = "";
String nombreArchivo="";
String val1="";
String val2="";
String sistema = utilidad.getProperty("id.sistema");
String usuario="";
String usuRequest="";
String institucion="";
String fecha="";
String disp= "";
List<DatosSolicitudes> lds = null;
String tDocu = "";
DatosSolicitudes ds = null;
String buildMenu="";
boolean aute=false;
StringBuffer sb = new StringBuffer();
StringBuffer sbGua = new StringBuffer();
StringBuffer sbEnv = new StringBuffer();
StringBuffer sbDoc = new StringBuffer();
int tamanio=0;
int indice=0;
String miLista="";
String myOpeGuarda="";
String myOpeEnvia="";
String myOpeBorra="";
String myOpeMuestra="";
HashMap<String, String> hmD= new HashMap<String, String>();
try{
hmD.put("D", "Dui");
hmD.put("N", "Nit");
hmD.put("J", "Declaracion Jurada");
hmD.put("F", "Formulario");
myOpeGuarda=utilidad.toHexString(utilidad.getSHA("GUARDAR"));
myOpeEnvia=utilidad.toHexString(utilidad.getSHA("ENVIAR"));
myOpeBorra=utilidad.toHexString(utilidad.getSHA("BORRAR"));
myOpeMuestra=utilidad.toHexString(utilidad.getSHA("MOSTRAR"));
//usuRequest = utilidad.getUser(request);
//usuario = utilidad.cleanEntrada(usuRequest);
usuario = utilidad.getUser(request);
us = utilidad.getDatosCifUsuario(usuario, sistema);
lds = utilidad.getDatosSolicitudesReenviado(usuario);
String estadoHash=utilidad.getProperty("estado.forward.hash.solicitud");
utilidad.eliminarAnexosHash(usuario, us, estadoHash);
utilidad.hashingOperacionModificacion(us, usuario);//vuelve a adicionarlos y a esperar nuevas modificaciones
tamanio = lds.size();
miLista=utilidad.getDocumentos(lds);
Iterator<DatosSolicitudes> iterator = lds.iterator();
  while(iterator.hasNext()) {
    ds = iterator.next();
    disp=ds.getReqId().toString();
    sb.append(ds.getTipoDocumento());
    indice++;
    if (tamanio>indice){
        sb.append(" ");
    }
  }
  if (tamanio>0) {
    tDocu=sb.toString();
  }

institucion="Instituci&oacute;n: " + us.getInstitucion();
nombre="Nombre Usuario : " + us.getNombre();
fecha=utilidad.getFechaAsString(new java.util.Date(), "dd/MM/yyyy");
buildMenu=utilidad.getMenu(usuario, sistema);
aute=(StringUtils.isNotBlank(usuario)?true:false);
if (!aute)
{
    throw new Exception ("Error de autenticacion");
}
}catch(Exception ex){
    out.print("Error de autenticacion");
}
%>
<%
if (aute)
{
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Banco de Desarrollo de El Salvador</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
        <link href="pages/styles/bmi.css" rel="stylesheet" type="text/css"/>
        <link href="pages/styles/bmi2.css" rel="stylesheet" type="text/css"/>
        
        <link href="styles/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="javascript/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="javascript/jquery-ui-1.9.2.custom.min.js"></script>
        <script type="text/javascript" src="pages/include/dialogo.js"></script>
        
    <%=utilidad.getHeaderPages()%>
    </head>
    <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

            <table width="769" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="466" height="30" rowspan="3" align="left" valign="top">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td height="10" colspan="5" align="left" valign="top">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                </tr>
                 
                <tr>
                    <td width="23" height="23" rowspan="2" align="left" valign="top">
                        <img src="pages/images/online_incli_top.gif" width="23" height="23"/>
                    </td>
                    <td height="3" colspan="3" align="left" valign="top" background="images/nav_rap_top.gif">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td height="23" rowspan="2" align="left" valign="top">
                        <img src="pages/images/nav_rap_der2.gif" width="14" height="23"/>
                    </td>
                </tr>
                 
                <tr>
                    <td width="122" height="17" align="left" valign="middle" bgcolor="C8D5DF">
                        <img src="pages/images/nav_rap.gif" width="122" height="15"/>
                    </td>
                    <td width="135" align="left" valign="middle" bgcolor="C8D5DF">&nbsp;</td>
                    <td width="9" align="left" valign="top" bgcolor="C8D5DF">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                </tr>
            </table>
            <table width="769" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="32" rowspan="3" align="left" valign="top">
                        <img src="pages/images/izq_cont.gif" width="10" height="32"/>
                    </td>
                    <td width="10" align="left" valign="top" bgcolor="DFDFDF">
                        <img src="pages/images/top_cont.gif" width="10" height="6"/>
                    </td>
                    <td width="446" align="left" valign="top" background="images/contc_pix.gif">
                        <img src="pages/images/shim.gif" width="1" height="1" style="background-repeat:repeat-x;"/>
                    </td>
                    <td width="293" align="left" valign="top" bgcolor="C8D5DF">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td height="32" rowspan="3" align="right" valign="top">
                        <img src="pages/images/contc_der.jpg" width="10" height="32"/>
                    </td>
                </tr>
                 
                <tr>
                    <td colspan="3" align="right" valign="top" bgcolor="C8D5DF">
                        <img src="pages/images/cont_punt.gif" width="293" height="1"/>
                    </td>
                </tr>
                 
                <tr>
                    <td bgcolor="C8D5DF">&nbsp;</td>
                    <td colspan="2" align="left" valign="middle" bgcolor="C8D5DF" class="verd10gris">
                         
                        
                         
                        
                    </td>
                </tr>
            </table>
            <table class="tablaBanner">
                <tr class="filaBanner">
                    <td class="celdaBannerAux ceroPadding">
                        <img src="pages/images/borde_izq.jpg" class="imgBordeIzq" alt=""/>
                    </td>
                    <td class="celdaBannerAux ceroPadding">&nbsp;</td>
                    <td class="celdaBanner ceroPadding">
                        <jsp:include page="pages/misc/banner.jsp"/>
                    </td>
                    <td class="celdaBannerAux2 ceroPadding">&nbsp;</td>
                    <td class="celdaBannerAux ceroPadding">
                        <img src="pages/images/borde_der.jpg" class="imgBordeIzq" alt=""/>
                    </td>
                </tr>
            </table>
            <table width="769" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="10" rowspan="3" align="left" valign="top" background="images/borde_izq_pix.gif">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td width="10" rowspan="3" align="left" valign="top" bgcolor="C8D5DF">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td width="6" rowspan="3" align="left" valign="top">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>

                </tr>
                 
                <tr>
                    <td width="718" align="left">
                        <table width="718" border="0" cellpadding="0" cellspacing="1" bgcolor="91A8C4">
                            <tr>
                                <td width="182" align="left" valign="top">&nbsp;</td>
                                <td width="196" align="left" valign="top">&nbsp;</td>
                                <td width="160" align="left" valign="top">&nbsp;</td>
                                <td width="177" align="left" valign="top">&nbsp;</td>
                            </tr>
                             
                            <tr>
                                <td>
                                    <img src="pages/images/g_barra01.gif" name="barra01" width="182" height="6"
                                         id="barra01"/>
                                </td>
                                <td>
                                    <img src="pages/images/g_barra02.gif" name="barra02" width="196" height="6"
                                         id="barra02"/>
                                </td>
                                <td>
                                    <img src="pages/images/g_barra03.gif" name="barra03" width="160" height="6"
                                         id="barra03"/>
                                </td>
                                <td>
                                    <img src="pages/images/g_barra04.gif" name="barra04" width="177" height="6"
                                         id="barra04"/>
                                </td>

                            </tr>
                        </table>
                    </td>
                    <td width="9" rowspan="3" align="left" valign="top" background="images/borde_der_pix.gif">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                </tr>
                 
                <tr>
                    <td height="10" align="left" valign="top">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                </tr>
            </table>
            <table width="769" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="10" align="left" valign="top" background="images/borde_izq_pix.gif">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td width="10" align="left" valign="top" bgcolor="C8D5DF">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td width="718" align="left">
                        <table width="733" border="0" cellpadding="0" cellspacing="0" bgcolor="C8D5DF">
                            <tr>
                                <td width="170" align="left" valign="top">
                                    <img src="pages/images/flecha_princ.gif" width="170" height="21"/>
                                </td>
                                <td width="14" align="left" valign="top">
                                    <img src="pages/images/shim.gif" width="1" height="1"/>
                                </td>
  
                                <td width="105" align="left" valign="middle">&nbsp;</td>

                            </tr>
                        </table>
                    </td>
                    <td width="6" align="left" valign="top" bgcolor="C8D5DF">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td width="10" align="left" valign="top" background="images/borde_der_pix.gif">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                </tr>
            </table>
            <table width="769" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="10" align="left" valign="top" background="images/borde_izq_pix.gif">
                        <img src="pages/images/shim.gif" width="10" height="10"/>
                    </td>
                    <td width="10" align="left" valign="top">
                        <img src="pages/images/deg_gris.jpg" width="10" height="267"/>
                    </td>
                    <td width="818" align="left" valign="top">
                        <table width="733" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td width="170" height="100%" align="left" valign="top">
                                    <table width="170" height="100%" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="left" valign="top">
                                                <table width="170" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="11" rowspan="4" align="left" valign="top">
                                                            <img src="pages/images/bajo_flecha_princ.jpg" width="11"
                                                                 height="11"/>
                                                        </td>
                                                        <td height="11" colspan="3" align="left" valign="top"
                                                            bgcolor="EDECEC">
                                                            <img src="pages/images/bajo_flecha_prin_der.jpg" width="12"
                                                                 height="11"/>
                                                        </td>
                                                        <td width="11" rowspan="4" align="left" valign="top">
                                                            <img src="pages/images/shim.gif" width="1" height="1"/>
                                                        </td>
                                                    </tr>
                                                     
                                                    <tr>
                                                        <td width="10" rowspan="2" align="left" valign="top"
                                                            bgcolor="EDECEC">
                                                            <img src="pages/images/shim.gif" width="1" height="1"/>
                                                        </td>
                                                        
                                                        <td width="129" height="15" align="left" valign="top"
                                                            bgcolor="EDECEC">
                                                            <img src="pages/images/bienvenido.gif" width="102"
                                                                 height="15"/>
                                                        </td>
                                                        
                                                        <td width="9" rowspan="2" align="left" valign="top"
                                                            bgcolor="EDECEC">
                                                            <img src="pages/images/shim.gif" width="1" height="1"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td height="11" colspan="3" align="left" valign="top"
                                                            bgcolor="#FFFFFF">
                                                            <img src="pages/images/shim.gif" width="1" height="1"/>
                                                        </td>
                                                    </tr>
                                                     
                                                    <tr>
                                                        <td colspan="5" align="left" valign="middle">
                                                            <img src="pages/images/punt_supizq.gif" width="170"
                                                                 height="1" vspace="0"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                         
                                        <tr>
                                            <td height="11" align="left" valign="top">
                                                <img src="pages/images/shim.gif" width="1" height="1"/>
                                            </td>
                                        </tr>
                                         
                                        <tr>
                                            <td align="center" valign="top">
                                                <table width="148" border="0" cellpadding="0" cellspacing="0">
                                                    <tr>
                                                        <td height="22" align="left" valign="middle">&nbsp;</td>
                                                        <td width="31" align="right" valign="middle">&nbsp;</td>
                                                    </tr>
                                                     
                                                    <tr>
                                                        <td height="11" colspan="2" align="left" valign="top">
                                                            <img src="pages/images/shim.gif" width="1" height="1"/>
                                                        </td>
                                                    </tr>
                                                     
                                                    <tr>
                                                        <td height="100%" align="center" valign="middle">
                                                            <table width="148" height="100%" border="0" cellpadding="0"
                                                                   cellspacing="0" bgcolor="EAF0F8">
                                                                <tr bgcolor="FFFFFF">
                                                                    <td colspan="3" height="6">
                                                                        <img src="pages/images/left_top_img.gif"
                                                                             width="148" height="6"/>
                                                                    </td>
                                                                </tr>
                                                                 
                                                                <tr>
                                                                    <td width="148" align="left" valign="top">
                                                                        <!-- Aqui comienza el detalle del menu -->
                                                                        <div id="menu">
                                                                            <ul>
                                                                                <li>
                                                                                    <h2>Opciones</h2>
                                                                                    <ul>
                                                                                        <li>
                                                                                            <a href='./closeSession.jsp'
                                                                                               title='Cerrar sesiÃ³n'>Cerrar
                                                                                                                     sesi&oacute;n</a>
                                                                                        </li>
                                                                                        <%=ESAPI.encoder().canonicalize(buildMenu)%>
                                                                                    </ul>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                        <!-- Aqui comienza el detalle del menu -->
                                                                    </td>
                                                                    <td height="11" align="left" valign="top"
                                                                        bgcolor="#FFFFFF">
                                                                        <img src="pages/images/shim.gif" width="1"
                                                                             height="1"/>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                         
                                        <tr>
                                            <td height="100%" align="center" valign="middle">
                                                <table width="148" height="100%" border="0" cellpadding="0"
                                                       cellspacing="0" bgcolor="EAF0F8">
                                                    <tr bgcolor="FFFFFF">
                                                        <td colspan="3" height="6">
                                                            <img src="pages/images/left_top_img.gif" width="148"
                                                                 height="6"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="365" height="100%" align="left" valign="top">
                                    <table width="558" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td class="verd12azul">
                                                <strong>Anexos de Requerimientos</strong>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td valign="top" align="left" class="blanco5">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td align="left" valign="top"  class="verd9azul">
                                                <%=ESAPI.encoder().canonicalize(institucion)%>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td valign="top" align="left" class="blanco5">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td align="left" valign="top"  class="verd9azul">
                                                <%=ESAPI.encoder().canonicalize(nombre)%>&nbsp;&nbsp;&nbsp;Fecha : <%=ESAPI.encoder().canonicalize(fecha)%>
                                            </td>
                                        </tr>
                                        <tr bgcolor="FFFFFF">
                                            <td colspan="3" height="6">
                                                <img src="pages/images/left_top_img.gif"
                                                        width="100%" height="6"/>
                                            </td>
                                        </tr>
                                         
                                        <tr>
                                            <td height="138" width="100%" align="left" valign="top">
                                            <html:form action='/solicitudReqModificaAction' method='post' enctype='multipart/form-data'>
                                                <input type="hidden" name="registrado" id="registrado" value="<%=ESAPI.encoder().encodeForHTMLAttribute(disp)%>" />
                                                <input type="hidden" name="operacion" id="operacion" value="<%=ESAPI.encoder().encodeForHTMLAttribute("")%>" />
                                                <input type="hidden" id="tipos" value="<%=ESAPI.encoder().encodeForHTMLAttribute(tDocu)%>" />
                                                <table cellspacing="0" cellpadding="0" width="100%" border="0">
                                                    <tbody>
                                                        <tr>
                                                            <td width="100%">
                                                                <div id="dlgMsj" title="Error">
                                                                    <div id="divTxtMsjError"></div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr align="left">
                                                          <td>
                                                            <table cellspacing="0" cellpadding="0" width="100%" border="0">
                                                                <tr>
                                                                    <td width="50%" class="verd9azul">
                                                                        <html:file property="file1" styleId="file1" onchange="loadMDoc(this)" accept="application/pdf" />
                                                                    </td>
                                                                    <td width="50%" class="rojo9">
                                                                        <html:messages id="message" property="common.file.err">
                                                                <script type="text/javascript">
                                                                var txt = '';
                                                                txt += '${message}';
                                                                $("#dlgMsj").dialog({
                                                                    height: 160,
                                                                    modal: true,
                                                                    autoOpen: false,
                                                                    resizable: false,
                                                                    buttons: {
                                                                        "Aceptar": function() {
                                                                            $(this).dialog("close");
                                                                        }
                                                                    }
                                                                });
                                                                $("#dlgMsj").dialog('option', 'title', 'Error');
                                                                $("#divTxtMsjError").html(txt);
                                                                $("#dlgMsj").dialog("open");
                                                                </script>
                                                                        </html:messages>
                                                                    </td>
                                                                  </tr>
                                                                  <tr>
                                                                    <td valign="top" align="left" class="blanco5">&nbsp;</td>
                                                                  </tr>
                                                                  <tr>
                                                                    <td class="verd9azul" width="100%">
                                                                    <%=ESAPI.encoder().canonicalize(miLista)%>
                                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Usuario de : <select id="tipoArea" name="tipoArea" class="select"><option value="">Seleccione...</option>
                                                                            <option value="PR">Pr&eacutestamos</option>
                                                                            <option value="GA">Garant&iacuteas Adicionales</option>
                                                                            <option value="GR">Garant&iacuteas</option>
                                                                        </select>

                                                                     </td>
                                                                    </tr>

                                                            </table>
                                                          </td>
                                                        </tr>
                                                        <tr>
                                                            <td valign="top" align="left" width="26">&nbsp;</td>
                                                        </tr>
                                                        <tr>
                                                        <%
                                                            sbGua = new StringBuffer("return valida('");
                                                            sbGua.append(myOpeGuarda);
                                                            sbGua.append("')");
                                                            sbEnv = new StringBuffer("return llamaCierre('");
                                                            sbEnv.append(myOpeEnvia);
                                                            sbEnv.append("')");

                                                        %>
                                                            <td width="50%">
                                                                <html:submit value="Guardar Anexo" onclick="<%=sbGua.toString()%>" />
                                                            </td>
                                                            <td width="50%">
                                                                <html:submit value="Enviar Solicitudes" onclick="<%=sbEnv.toString()%>" />
                                                            </td>
                                                        </tr>
                                                         
                                                        
                                                        <tr bgcolor="FFFFFF">
                                                            <td colspan="3" height="6">
                                                                <img src="pages/images/left_top_img.gif"
                                                                width="100%" height="6"/>
                                                            </td>
                                                        </tr>
                                                        
                                                        <tr>
                                                            <td width="20%">
                                                            </td>
                                                            <td width="80%" class="verd12azul">
                                                            <html:messages id="exito" message="true">
                                                                <script type="text/javascript">
                                                                var txt = '';
                                                                txt += '${exito}';
                                                                $("#dlgMsj").dialog({
                                                                    height: 160,
                                                                    modal: true,
                                                                    autoOpen: false,
                                                                    resizable: false,
                                                                    buttons: {
                                                                        "Aceptar": function() {
                                                                            $(this).dialog("close");
                                                                        }
                                                                    }
                                                                });
                                                                $("#dlgMsj").dialog('option', 'title', 'Error');
                                                                $("#divTxtMsjError").html(txt);
                                                                $("#dlgMsj").dialog("open");

                                                                </script>
                                                            </html:messages>
                                                            </td>
                                                        </tr>
                                                        
                                                    </tbody>
                                                </table>
                                                </html:form>
                                            </td>
                                        </tr>
                                    </table>
                                    <!-- segundo formulario -->
                                    <html:form styleId='ane' action='/solicitudReqModificaAnexosAction' method='post' enctype='multipart/form-data'>
                                    <input type="hidden" name="selId" id="selId" value="<%=ESAPI.encoder().encodeForHTMLAttribute("")%>" />
                                    <input type="hidden" name="anrId" id="anrId" value="<%=ESAPI.encoder().encodeForHTMLAttribute("")%>" />
                                    <input type="hidden" name="opcion" id="opcion" value="<%=ESAPI.encoder().encodeForHTMLAttribute("")%>" />
                                    <input type="hidden" name="val1" id="val1" value="<%=ESAPI.encoder().encodeForHTMLAttribute("")%>" />
                                    <input type="hidden" name="val2" id="val2" value="<%=ESAPI.encoder().encodeForHTMLAttribute("")%>" />

                                    <table width="100%" cellspacing="0" cellpadding="0" border="0">    
                                        <tr bgcolor="FFFFFF">
                                            <td colspan="10" height="6">
                                                <img src="pages/images/left_top_img.gif"
                                                    width="100%" height="6"/>
                                            </td>
                                        </tr>
                                        <tr class="verd11azul fondoCeleste1">
                                            <td align="left" valign="top">
                                                Id
                                            </td>
                                            <td align="left" valign="top">
                                                Tipo
                                            </td>
                                            <td align="left" valign="top">
                                                nombre del archivo anexo
                                            </td>
                                            <td align="left" valign="top">
                                                opciones
                                            </td>
                                        </tr>
                                    <%
                                        Iterator<DatosSolicitudes> iterator = lds.iterator();
                                        while(iterator.hasNext()) {
                                            ds = iterator.next();
                                            nombreArchivo = StringUtils.substring(ds.getNombreArchivo(), 0, 40).trim();
                                            val1=utilidad.toHexString(utilidad.getSHA(ds.getAnrId().toString()));
                                            val2=utilidad.toHexString(utilidad.getSHA(ds.getReqId().toString()));
                                    %>
                                    <tr class="verd11azul">
                                        <td>
                                            <%=ESAPI.encoder().encodeForHTMLAttribute(ds.getReqId().toString())%>
                                        </td>
                                        <td>
                                            <%=ESAPI.encoder().encodeForHTMLAttribute(hmD.get(ds.getTipoDocumento()))%>
                                        </td>
                                        <td>
                                            <%=ESAPI.encoder().encodeForHTMLAttribute(nombreArchivo)%>
                                        </td>
                                        <td>
                                            <input type="image" src="images/icon-delete.gif" onclick="selecBorrar('<%=ESAPI.encoder().encodeForJavaScript(ds.getAnrId().toString())%>', '<%=ESAPI.encoder().encodeForJavaScript(ds.getReqId().toString())%>','<%=val1%>','<%=val2%>','<%=myOpeBorra%>')" >
                                            <label>
                                                &nbsp;&nbsp;
                                            </label>
                                            <input type="image" src="images/icon_adjunto.gif" onclick="selecMostrar('<%=ESAPI.encoder().encodeForJavaScript(ds.getAnrId().toString())%>', '<%=ESAPI.encoder().encodeForJavaScript(ds.getReqId().toString())%>','<%=val1%>','<%=val2%>','<%=myOpeMuestra%>')" >
                                        </td>
                                    </tr>
                                    <%                                            
                                        }
                                    %>
                                </table>
                            </html:form>
                            <tr>
                                <td height="10" align="left" valign="top">&nbsp;</td>
                                <td align="left" valign="top">
                                    <table width="541" border="0" cellpadding="0" cellspacing="0" bgcolor="#EBF0F4">
                                        <tr>
                                            <td width="15" height="29">&nbsp;</td>
                                            <td class="verd10azul">&nbsp;</td>
                                        </tr>
                                         
                                        <tr>
                                            <td colspan="2" height="11" align="left" valign="top" bgcolor="#FFFFFF">
                                                <img src="pages/images/shim.gif" width="1" height="1"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                <td width="100%" align="left" valign="top" background="images/borde_der_pix.gif"><img src="pages/images/shim.gif" width="1" height="1"></td>
                </tr>
            </table>

            <table width="769" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="10" align="left" valign="top" background="images/borde_izq_pix.gif">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td width="10" align="left" valign="top">
                        <img src="pages/images/shim.gif" width="1" height="1"/>
                    </td>
                    <td width="718" align="left">
                        <table width="733" border="0" cellpadding="0" cellspacing="0">
                            <tr bgcolor="DCDCDC">
                                <td width="17" align="left" valign="top">
                                    <img src="pages/images/btm_izq.gif" width="17" height="27"/>
                                </td>
                                <td width="364" align="left" valign="middle" bgcolor="C8D5DF"><img src="pages/images/btm_banco.gif" width="336" height="5"></td>
                                <td width="342" align="right" valign="middle" bgcolor="C8D5DF"><a href="#"><img src="pages/images/btm_garantias.gif" width="164" height="5" border="0"></a></td>
                            </tr>
                        </table>
                    </td>
                    <td width="6" align="left" valign="top" bgcolor="C8D5DF"><img src="pages/images/shim.gif" width="1" height="1"></td>
                    <td width="10" align="left" valign="top" background="images/borde_der_pix.gif"><img src="pages/images/shim.gif" width="1" height="1"></td>
                </tr>
            </table>
            <table width="769" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="10" align="left" valign="top">
                        <img src="pages/images/btm02_izq.jpg" width="10" height="12"/>
                    </td>
                    <td width="744" align="left" valign="top" background="images/btm02_fdo.gif">
                        <img src="pages/images/btm02_izqB.jpg" width="26" height="12"/>
                    </td>
                    <td width="5" align="right" valign="top">
                        <img src="pages/images/btm02_derA.jpg" width="5" height="12"/>
                    </td>
                    <td width="10" align="right" valign="top">
                        <img src="pages/images/btm02_derB.jpg" width="10" height="12"/>
                    </td>
                </tr>
                 
                <tr>
                    <td colspan="4" align="left" valign="top">
                        <img src="pages/images/bottom_tip.jpg" width="769" height="17"/>
                    </td>
                </tr>
            </table>
    <script type="text/javascript">
     function selecBorrar(valorAnrId, valorReqId, val1, val2,oper)
        {
            document.getElementById('opcion').value=oper;
            document.getElementById('anrId').value=Number(valorAnrId);
            document.getElementById('selId').value=Number(valorReqId);
            document.getElementById('val1').value=val1;
            document.getElementById('val2').value=val2;
            var form = document.getElementById('ane');
            form.target="_self";
            form.submit();
        }
        
        function selecMostrar(valorAnrId, valorReqId, val1, val2, oper)
        {
            document.getElementById('opcion').value=oper;
            document.getElementById('anrId').value=Number(valorAnrId);
            document.getElementById('selId').value=Number(valorReqId);
            document.getElementById('val1').value=val1;
            document.getElementById('val2').value=val2;
            var form = document.getElementById('ane');
            form.target="_blank";
            form.submit();
        }
        
        function valida(oper)
        {
            var error="";
            var tDocu = document.getElementById('tipos').value;
            document.getElementById('operacion').value=oper;
            var tipo=document.getElementById('tipoDocumento');
            var tipoArea=document.getElementById('tipoArea');
            var inputF=document.getElementById('file1');
            if (!inputF.files[0]) {
                error = "Escoja un archivo";
                $("#dlgMsj").dialog('option', 'title', 'Error');
                $("#divTxtMsjError").html(error);
                $("#dlgMsj").dialog("open");
                return false;
            }
            var fileName = inputF.files[0].name;
            var extension=fileName.slice((fileName.lastIndexOf(".") - 1 >>> 0) + 2);
            var tama=inputF.files[0].size/1024/1024;
            if ("pdf"!=extension || tama>5) {
                error = "Escoja un archivo pdf o de tamanio hasta 5M";
                $("#dlgMsj").dialog('option', 'title', 'Error');
                $("#divTxtMsjError").html(error);
                $("#dlgMsj").dialog("open");
                return false;
            }
            
            if (tipoArea.value=="") {
                error = "Seleccion un usuario de area";
                $("#dlgMsj").dialog('option', 'title', 'Error');
                $("#divTxtMsjError").html(error);
                $("#dlgMsj").dialog("open");
                return false;
            }
            if (tipo.value!="") {
                var d=tipo.value;
                var descripcion=tipo.options[tipo.selectedIndex].text;
                var arrayTdocu=tDocu.split(" ");
                if (arrayTdocu.includes(d)){
                    error = descripcion.concat(" ya fue escogido antes");
                    $("#dlgMsj").dialog('option', 'title', 'Error');
                    $("#divTxtMsjError").html(error);
                    $("#dlgMsj").dialog("open");
                    return false;
                }
                return true;
            }
            error = "Seleccion un tipo de documento o adicione archivo";
            $("#dlgMsj").dialog('option', 'title', 'Error');
            $("#divTxtMsjError").html(error);
            $("#dlgMsj").dialog("open");
            return false;
        }
        
        function llamaCierre(oper){            
            var reg=document.getElementById('registrado').value;
            if (!isEmpty(reg)) {
                document.getElementById('operacion').value=oper;
                return true;
            }
            error = "No hay registros para enviar";
            $("#dlgMsj").dialog('option', 'title', 'Error');
            $("#divTxtMsjError").html(error);
            $("#dlgMsj").dialog("open");
            return false;
        }
        
        function checkSel(tDocu) {
            var elemento = document.getElementById("tipoDocumento");
            var d=elemento.value;
            alert(d);
            var arrayTdocu=tDocu.split(" ");
            alert(arrayTdocu);
            for (var i=0; i<elemento.length; i++) {
                d=elemento.value;
                if (arrayTdocu.includes(d)){
                    elemento.remove(i);
                }
            }
        }
        
        //valida si un string es nulo, vacio o undefined
        function isEmpty(str) {
            return (!str || 0 === str.length);
        }

function loadMDoc(archivo) {
var formData = new FormData();
formData.append('file1', $('#file1')[0].files[0]);

$.ajax({
       url : 'reqModAction.do',
       type : 'POST',
       async: false,
       cache: false,
       contentType: false,
       data: formData,
       enctype: 'multipart/form-data',
       processData:false,
       success: function() {
           
       },
       error: function(e) {
            
        }
});
};
      </script>    
    </body>
</html>
<%
}
%>