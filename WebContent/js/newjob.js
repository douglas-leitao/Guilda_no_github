//document.addEventListener( "deviceready", function(){
window.addEventListener( "load", function(){

        var descTarefa;
        var requisitos;
        var dataEntrega;
        var extimativa;
        var preco;
                
        var jsonToSend;
        var destiny;
        
        //destiny = "json/signup.json";
        destiny = "http://localhost:8080/ProjGuilda/NewjobServlet";
        

        document.querySelector( "#submit" ).onclick = function(){
                
        	descTarefa		= document.querySelector( "#desc-tarefa" ).value;
        	requisitos      = document.querySelector( "#requisitos" ).value;
        	dataEntrega     = document.querySelector( "#data-entrega" ).value;
        	extimativa 		= document.querySelector( "#extimativa" ).value;
        	preco       	= document.querySelector( "#preco" ).value;
                           
            error        = false;
            errorMessage = "";
            
            
            
            advancedFunction.verifyField( "#desc-tarefa", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@.-", function(){
            
                var isNull;
                
                error                          = true;
                if( descTarefa.value == "" ) isNull = true;
                if( isNull ) errorMessage      = "O campo 'Descrição' está vazio";
        
            } );
            
            advancedFunction.verifyField( "#requisitos", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@.,-!?*&()$#%'", function(){
                    
                var isNull;
                
                error                          = true;
                if( requisitos.value == "" ) isNull = true;
                if( isNull ) errorMessage      = "O campo 'Requisitos' está vazio";
        
            } );
                
            advancedFunction.verifyField( "#preco", "1234567890.", function(){
                
                var isNull;
                
                error                          = true;
                if( preco.value == "" ) isNull = true;
                if( isNull ) errorMessage      = "O campo 'Preço' está vazio";
        
            } );
                       
    
            
                
            jsonToSend = "{" +
                                 '"desc-tarefa": '   + '"' + 	descTarefa  + '"' + "," +
                                 '"requisitos": '    + '"' + 	requisitos  + '"' + "," +
                                 '"data-entrega": '  + '"' + 	dataEntrega + '"' + "," +
                                 '"extimativa": ' 	+ '"' + 	extimativa 	+ '"' + "," +
                                 '"preco": ' 		+ '"' + 	preco 		+ '"' +
                         "}";
                         
            advancedFunction.sendJson( destiny, jsonToSend, function( jsonObject ){
            
                if( jsonObject.status == "200" ) window.location = "index.html";
                else alert( "Houve um problema, tente novamente." );
            
            } );
        
        };

}, false );
