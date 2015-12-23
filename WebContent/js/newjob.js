//document.addEventListener( "deviceready", function(){
window.addEventListener( "load", function(){

        var idTipoTarefa;
        var descTipoTarefa;
		var descTarefa;
        var requisitos;
        var dataEntrega;
        var dataCadastro;
        var estimativa;
        var preco;
        var date;      
        
        var jsonToSend;
        
        var destinySend;
        var destinyReceive;
        
        
        //destinyReceive = "json/newjob.json";
        destinyReceive = "http://localhost:8080/ProjGuilda/TipoTarefaServlet";
       
        destinySend = "http://localhost:8080/ProjGuilda/NewjobServlet";
        
        date = new Date();
        
        advancedFunction.sendJson( destinyReceive, "", function( json ){
        
        	var list = document.querySelector( "#tipo-job" );
    		
    		for( i in json ){
    		
    			var option = document.createElement( "option" );
    			option.value = json[ i ].id;
    			option.innerHTML = json[ i ].desc;
    			option.id = "option-" + i;
    			list.appendChild( option );
    		}
    		
        });
        
        document.querySelector( "#submit" ).onclick = function(){
                
        	descTarefa		= document.querySelector( "#desc-tarefa" ).value;
        	requisitos      = document.querySelector( "#requisitos" ).value.replace(/\n/g, " ");;
        	dataEntrega     = document.querySelector( "#data-entrega" ).value;
        	estimativa 		= document.querySelector( "#estimativa" ).value;
        	preco       	= document.querySelector( "#preco" ).value;
        	descTipoTarefa	= document.querySelector( "#tipo-job" ).value;
                           
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
                                 '"desc-tarefa": '   	+ '"' + 	descTarefa  	+ '"' + "," +
                                 '"requisitos": '    	+ '"' + 	requisitos  	+ '"' + "," +
                                 '"data-entrega": '  	+ '"' + 	dataEntrega 	+ '"' + "," +
                                 '"data-cadastro": '  	+ '"' + 	dataCadastro 	+ '"' + "," +
                                 '"estimativa": ' 		+ '"' + 	estimativa 		+ '"' + "," +
                                 '"tipo-job": ' 		+ '"' + 	descTipoTarefa 	+ '"' + "," +
                                 '"preco": ' 			+ '"' + 	preco 			+ '"' +
                         "}";
                         
            advancedFunction.sendJson( destinySend, jsonToSend, function( jsonObject ){
            
                if( jsonObject.status == "200" ) window.location = "index.html";
                else if( jsonObject.status == "403" ) alert( "É necessário estar logado para cadastrar uma JOB!" );
                else alert( "Houve um problema, tente novamente." );
            
            } );
        
        };

}, false );
