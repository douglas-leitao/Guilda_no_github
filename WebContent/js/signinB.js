//document.addEventListener( "deviceready", function(){
window.addEventListener( "load", function(){

        document.querySelector( "#submit" ).onclick = function(){

                
                var password;
                var jsonToSend;
                var destiny;
                
                password = document.querySelector( "#password-b" ).value
                
                destiny  = "http://localhost:8080/ProjGuilda/SigninBServlet";

                jsonToSend = "{" +
                                     '"password-b": ' + '"' + password + '"' +
                             "}";

                advancedFunction.sendJson( destiny, jsonToSend, function( jsonObject ){
                
                        if( jsonObject.status == "200" ) window.location = "index.html";
                        else alert( "Houve um problema, tente novamente." );
                
                } );

        }

}, false );
