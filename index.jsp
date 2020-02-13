<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:useBean id="utilidad" class="bandesal.gob.sv.cif.anexos.util.Util" scope="session"/>
<jsp:useBean id="us" class="bandesal.gob.sv.cif.anexos.dto.UserData" scope="session"/>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%
String cod_emp = new String();
String cod_puesto = "";
String cod_emp_sol = "";
String nombre = "";
String paraId="";
String sistema = "";
String buildMenu="";
boolean abilitar=false;
boolean aute=false;

try{
sistema=utilidad.getProperty("id.sistema");
cod_emp = utilidad.getUser(request);
us = utilidad.getDatosCifUsuario(cod_emp, sistema);
buildMenu=utilidad.getMenu(cod_emp, sistema);
aute=(StringUtils.isNotBlank(cod_emp)?true:false);
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
        <script language="JavaScript" src="pages/include/calendario.js"></script>
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
                <a href="#"class="verd10azul"></a>
                <a href="#"class="verd10azul"></a>
                <a href="#"class="verd10azul"></a>
                </td>
            </tr>
        </table>
        <table class="tablaBanner">
            <tr class="filaBanner">
                <td class="celdaBannerAux ceroPadding">
                    <img src="pages/images/borde_izq.jpg" class="imgBordeIzq" alt=""/>
                </td>
                <td class="celdaBannerAux ceroPadding"></td>
                <td class="celdaBanner ceroPadding">
                    <jsp:include page="pages/misc/banner.jsp"/>
                </td>
                <td class="celdaBannerAux2 ceroPadding"></td>
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
                <td width="718" height="6" align="left" valign="top">
                    <img src="pages/images/shim.gif" width="1" height="1"/>
                </td>
                <td width="6" rowspan="3" align="left" valign="top">
                    <img src="pages/images/shim.gif" width="1" height="1"/>
                </td>
                <td width="6" rowspan="3" align="left" valign="top" bgcolor="C8D5DF">
                    <img src="pages/images/shim.gif" width="1" height="1"/>
                </td>
                <td width="9" rowspan="3" align="left" valign="top" background="images/borde_der_pix.gif">
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
                            <td width="27" align="left" valign="middle"></td>
                            <td width="118" align="left" valign="middle"></td>
                            <td width="105" align="left" valign="middle">&nbsp;</td>
                            <td align="right" valign="middle" class="verd9azul">
                                <%=utilidad.getActualizacion()%>
                            </td>
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
                <td width="718" align="left" valign="top">
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
                                                        <img src="pages/images/bienvenido.gif" width="102" height="15"/>
                                                    </td>
                                                    <td width="9" rowspan="2" align="left" valign="top"
                                                        bgcolor="EDECEC">
                                                        <img src="pages/images/shim.gif" width="1" height="1"/>
                                                    </td>
                                                </tr>
                                                 
                                                <tr>
                                                    <td width="129" height="25" align="left" valign="middle"
                                                        bgcolor="EDECEC" class="verd10gris">
                                                        <%= us.getNombre()%>
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
                                                        <img src="pages/images/punt_supizq.gif" width="170" height="1"
                                                             vspace="0"/>
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
                                                                    <img src="images/left_top_img.gif" width="148"
                                                                         height="6"/>
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
                                                                                           title='Cerrar sesión'>Cerrar
                                                                                                                 sesi&oacute;n</a>
                                                                                    
                                                                                    </li>
                                                                                        <%=buildMenu%>
                                                                                </ul>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                    <!-- Aqui comienza el detalle del menu -->
                                                                </td>
                                                                <td height="11" align="left" valign="top"
                                                                    bgcolor="#FFFFFF">
                                                                    <img src="images/shim.gif" width="1" height="1"/>
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
                                            <table width="148" height="100%" border="0" cellpadding="0" cellspacing="0"
                                                   bgcolor="EAF0F8">
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
                                <table width="558" height="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                        <td width="12" rowspan="3" align="left" valign="top">
                                            <img src="pages/images/sombra.jpg" width="12" height="233"/>
                                        </td>
                                        <td height="11" align="left" valign="top">
                                            <img src="pages/images/shim.gif" width="1" height="1"/>
                                        </td>
                                    </tr>
                                     
                                    <tr>
                                        <td height="238" align="left" valign="top">
                                            <table cellspacing="0" cellpadding="0" width="500" border="0">
                                                <tbody>
                                                    <tr>
                                                        <td valign="top" align="left" width="26">
                                                            <img height="21" src="images/int_flecha_cont.jpg"
                                                                 width="26"/>
                                                        </td>
                                                        <td class="verd15azul">
                                                            <strong>Bienvenido al Sistema de Anexos de Requerimientos</strong>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                     
                                    <tr>
                                        <td height="20" align="left" valign="top">
                                            <img src="pages/images/shim.gif" width="1" height="20"/>
                                        </td>
                                    </tr>
                                     
                                    <tr>
                                        <td height="10" align="left" valign="top"></td>
                                        <td align="left" valign="top">
                                            <table width="541" border="0" cellpadding="0" cellspacing="0"
                                                   bgcolor="#EBF0F4">
                                                <tr>
                                                    <td width="15" height="29">&nbsp;</td>
                                                    <td class="verd10azul"></td>
                                                </tr>
                                                 
                                                <tr>
                                                    <td colspan="2" height="11" align="left" valign="top"
                                                        bgcolor="#FFFFFF">
                                                        <img src="pages/images/shim.gif" width="1" height="1"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td align="left" valign="top">&nbsp;</td>
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
                            <td width="364" align="left" valign="middle" bgcolor="C8D5DF">
                                <img src="pages/images/btm_banco.gif" width="336" height="5"/>
                            </td>
                            <td width="342" align="right" valign="middle" bgcolor="C8D5DF">
                                <a href="#">
                                    <img src="pages/images/btm_garantias.gif" width="164" height="5" border="0"/></a>
                            </td>
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
        
    </body>
</html>
<%
}
%>