<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
      <title>Sesi&oacute;n finalizada</title>
      <link href="styles/bmi.css" rel="stylesheet" type="text/css"/>
      <link href="styles/bmi2.css" rel="stylesheet" type="text/css"/>
      <link href="styles/smart_wizard.css" rel="stylesheet" type="text/css"/>
      <link href="styles/smart_wizard_vertical.css" rel="stylesheet" type="text/css"/>
      <link href="styles/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css"/>
      <script type="text/javascript" src="javascript/jquery-1.9.1.min.js"></script>
      <script type="text/javascript" src="javascript/jquery.smartWizard-2.0.min.js"></script>
      <script type="text/javascript" src="javascript/jquery-ui-1.9.2.custom.min.js"></script>
      <script type="text/javascript" src="javascript/jquery.ui.datepicker-es.js"></script>
      <script type="text/javascript" src="javascript/jquery.maskedinput.js"></script>
      <script type="text/javascript" src="javascript/jquery.validate.js"></script>
      
      <script type="text/javascript">
          $(document).ready(function(){
              cerrarSesion();
          });
          
         
          function cerrarSesion() {
              $.ajax({
                  type: "post",
                  url: "SesionUsuario",
                  data: { "operacion" : "cerrarSesion" },
                  async: false,
                  success: function(response){
                      self.close();
                  },
                  complete: function(response) {
                      
                  }
                });
          }
      </script>
  </head>
  <body>
    <h1>Su sesi&oacute;n se ha cerrado correctamente.</h1>
  </body>
</html>