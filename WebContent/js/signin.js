//document.addEventListener( "deviceready", function(){
window.addEventListener( "load", function(){

        document.querySelector( "#submit" ).onclick = function(){

                var email;
                var password;
                var jsonToSend;
                var destiny;

                email = document.querySelector( "#email" ).value;
                password = document.querySelector( "#password-a" ).value
                
                destiny  = "http://localhost:8080/ProjGuilda/SigninServlet";

                jsonToSend = "{" +
                                     '"email": ' + '"' + email + '"' + ',' +
                                     '"password-a": ' + '"' + password + '"' +
                             "}";

                advancedFunction.sendJson( destiny, jsonToSend, function( jsonObject ){
                
                        if( jsonObject.status == "200" ) window.location = "signinB.html";
                        else alert( "Houve um problema, tente novamente." );
                
                } );

        }

}, false );
