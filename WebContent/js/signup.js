//document.addEventListener( "deviceready", function(){
window.addEventListener( "load", function(){

        var name;
        var email;
        var image;
        var passwordA;
        var confirmPasswordA;
        var passwordB;
        var confirmPasswordB;
        var contractTerms;
                
        var jsonToSend;
        var destiny;
        
        //destiny = "json/signup.json";
        destiny = "http://localhost:8080/ProjGuilda/SignupServlet";
        
        
        
        document.querySelector( "#email" ).onblur = function(){
        
                var isNull;
                
                if( this.value == "" ) isNull = true;
        
                advancedFunction.verifyEmail( "#email", function(){
                
                        if( !isNull ) alert( "O email inserido é inválido." );
                
                } );
        
        };
        
        document.querySelector( "#name" ).onblur = function(){
        
                advancedFunction.verifyField( "#name", "aáàãbcçdeéêfghiíjklmnoôpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ", function(){
        
                        alert( "Um nome pode conter apenas letras." );
        
                } );
                
        };
        
        document.querySelector( "#password-a" ).onblur = function(){
        
                advancedFunction.verifyField( "#password-a", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%&*?()[]{}<>", function(){
                
                        alert( "Você inseriu caracteres inválidos, insira uma nova senha." );
                
                } );
        
        };
        
        document.querySelector( "#confirm-password-a" ).onblur = function(){
        
                advancedFunction.compareFields( "#password-a", "#confirm-password-a", function(){
                
                        alert( "As senhas 'A' são diferentes." );
                
                } );
        
        };
        
        document.querySelector( "#password-b" ).onblur = function(){
            
            advancedFunction.verifyField( "#password-b", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%&*?()[]{}<>", function(){
            
                    alert( "Você inseriu caracteres inválidos, insira uma nova senha." );
            
            } );
    
    };
    
    document.querySelector( "#confirm-password-b" ).onblur = function(){
    
            advancedFunction.compareFields( "#password-b", "#confirm-password-b", function(){
            
                    alert( "As senhas 'B' são diferentes." );
            
            } );
    
    };

        document.querySelector( "#image" ).onclick = function(){
                
                getPhoto( pictureSource.SAVEDPHOTOALBUM, function( imageData ){
                        
                        image = "data:image/jpeg;base64," + imageData;
                
                } );
        
        };

        document.querySelector( "#submit" ).onclick = function(){
                
                name            = document.querySelector( "#name" ).value;
                email           = document.querySelector( "#email" ).value;
                passwordA        = document.querySelector( "#password-a" ).value;
                confirmPasswordA = document.querySelector( "#confirm-password-a" ).value;
                passwordB        = document.querySelector( "#password-b" ).value;
                confirmPasswordB = document.querySelector( "#confirm-password-b" ).value;
                contractTerms   = document.querySelector( "#contract-terms" ).checked;
                
                error        = false;
                errorMessage = "";
                
                
                
                advancedFunction.verifyEmail( "#email", function(){
                
                        var isNull;
                        
                        error                          = true;
                        if( email.value == "" ) isNull = true;
                        if( isNull ) errorMessage      = "O campo 'Email' está vazio";
                        else errorMessage              = "O email inserido é inválido";
                
                } );
                
                advancedFunction.verifyField( "#name", "aáàãbcçdeéêfghiíjklmnoôpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ", function(){
        
                        errorMessage = "Um nome pode conter apenas letras.";
                        error        = true;
        
                } );
                
                advancedFunction.verifyField( "#password-a", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%&*?()[]{}<>", function(){
                
                        errorMessage = "Você inseriu caracteres inválidos, insira uma nova senha A.";
                        error        = true;
                
                } );
                
                advancedFunction.verifyField( "#password-b", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%&*?()[]{}<>", function(){
                    
                    errorMessage = "Você inseriu caracteres inválidos, insira uma nova senha B.";
                    error        = true;
            
                } );
                
                advancedFunction.compareFields( "#password-a", "#confirm-password-a", function(){
                
                        errorMessage = "As senhas A são diferentes.";
                        error        = true;
                
                } );
                
                advancedFunction.compareFields( "#password-b", "#confirm-password-b", function(){
                    
                    errorMessage = "As senhas B são diferentes.";
                    error        = true;
            
                } );
                
                if( !contractTerms ){
                
                        errorMessage = "Você não aceitou os termos de contrato." ;
                        error        = true;
                
                }
                
                if( name == "" ){
                
                        errorMessage = "O campo 'Nome completo' está vazio.";
                        error        = true;
                
                }
                
                
                if( passwordA == "" ){
                
                        errorMessage = "O campo 'Senha A' está vazio.";
                        error        = true;
                
                }
                
                if( passwordB == "" ){
                    
                    errorMessage = "O campo 'Senha B' está vazio.";
                    error        = true;
            
            }
                if( error ){
                
                        alert( errorMessage );
                        return;
                        
                }
                
                jsonToSend = "{" +
                                     '"name": '     + '"' + name     + '"' + "," +
                                     '"email": '    + '"' + email    + '"' + "," +
                                     '"image": '    + '"' + image    + '"' + "," +
                                     '"password-a": ' + '"' + passwordA + '"' + "," +
                                     '"password-b": ' + '"' + passwordB + '"' +
                             "}";
                             
                advancedFunction.sendJson( destiny, jsonToSend, function( jsonObject ){
                
                        if( jsonObject.status == "200" ) window.location = "signin.html";
                        else alert( "Houve um problema, tente novamente." );
                
                } );
        
        };

}, false );
