//document.addEventListener( "deviceready", function(){
window.addEventListener( "load", function(){

        var idTipoTarefa;
        var descTipoTarefa;
		var descTarefa;
        var requisitos;
        var dataEntrega;
        var dataCadastro;
        var extimativa;
        var preco;
        var date;      
        var nroPag = 1;
        var order;
        var offset = 1;
        var url;
        var where = 0;
        var urlmural = "mural.html";
        var status;
        
        var jsonToSend;
        
        var destinyFilter;
        var destinyReceive;
        
        var teste;
        
        //destinyReceive = "json/mural.json";
        destinyReceive = "http://localhost:8080/ProjGuilda/MuralServlet";
       
        destinyFilter = "http://localhost:8080/ProjGuilda/TipoTarefaServlet";        
        
        date = new Date();

        document.querySelector( "#tipo-job" ).onchange = function(){
        	where	= document.querySelector( "#tipo-job" ).value;
        	//alert("where: "+ where);
        	window.location = urlmural + "?where=" + where + "?order=" + order + "?offset=" + offset + "/";
        }
        
        order = document.querySelector( "#order" ).value;
        document.querySelector( "#order" ).onchange = function(){
            
        	order = document.querySelector( "#order" ).value;
        	//window.location = "mural.html?order=" + order + "?offset=" + offset + "/";
        	window.location = urlmural + "?where=" + where + "?order=" + order + "?offset=" + offset + "/";
        }
                
        //urlmural += "/"
        url      = document.URL;
        
        if(url.search("offset")!= -1){
        	offset = url.match(/offset=(.+)\//)[1];
		}
        
        if(url.search("order")!= -1){
        	order = url.match(/order=(.+)\?/)[1];
        	document.getElementById("order").value = order;
  		}
        
        if(url.search("where")!= -1){
        	where = url.match(/where=(.+)\?or/)[1];
        	document.getElementById("tipo-job").value = where;
        	//alert(where);
  		}
        
        advancedFunction.sendJson( destinyFilter, "", function( json ){
            
        	var list = document.querySelector( "#tipo-job" );
    		
    		for( i in json ){
    		
    			var option = document.createElement( "option" );
    			
    			option.value = json[ i ].id;
    			if(option.value == where){
    				option.selected = true;
    			}
    				
    			option.innerHTML = json[ i ].desc;
    			//option.id = "option-" + i;
    			list.appendChild( option );
    		}
    		
        });
                
        jsonToSend = "{" 	+
        					'"where": ' + '"' + where + '"' + ',' +
					        '"order": ' + '"' + order + '"' + ',' +
					        '"offset": ' + '"' + offset + '"' +
					 "}";
        
        
        
        advancedFunction.sendJson( destinyReceive, jsonToSend, function( json ){
    			
			var aux = 0;	
    		for( i in json ){
    			nroPag = json[ i ].nroPag;
    			//alert(nroPag);
				
    			var element 				= document.createElement( "div" );
    	
    			element.id 					= json[ i ].id;
    			element.className 			= "col-md-3 portfolio-item";
    			
    			idTipoTarefa 				= json[ i ].idTipoJob;
    			//alert(idTipoTarefa);
    			
    			job_image	           			= document.createElement( "div" );
    			job_image.id 	     			= "job_image";
                
    			image           			= document.createElement( "img" );
    			if(idTipoTarefa == 1){
    				image.src       			= "img/tipo_tarefa/backend.png";
    			}else if(idTipoTarefa == 2){
    				image.src       			= "img/tipo_tarefa/frontend.png";
    			}else if(idTipoTarefa == 3){
    				image.src       			= "img/tipo_tarefa/Banco_de_dados.png";
    			}else if(idTipoTarefa == 4){
    				image.src       			= "img/tipo_tarefa/design.png";
    			}else if(idTipoTarefa == 5){
    				image.src       			= "img/tipo_tarefa/mobile2.png";
    			}else if(idTipoTarefa == 6){
    				image.src       			= "img/tipo_tarefa/java.png";
    			}else if(idTipoTarefa == 7){
    				image.src       			= "img/tipo_tarefa/c#1.jpg";
    			}else if(idTipoTarefa == 8){
    				image.src       			= "img/tipo_tarefa/php.png";
    			}else if(idTipoTarefa == 9){
    				image.src       			= "img/tipo_tarefa/javascript.png";
    			}else if(idTipoTarefa == 10){
    				image.src       			= "img/tipo_tarefa/teste.jpg";
    			}
    			
                image.id 		       			= json[ i ].id + "-image";
                image.className 				= "img-responsive";
                
                describe	           			= document.createElement( "div" );
                describe.id 	     			= "describe";
                describe.className 				= "black describe";
                
                desc_li						= document.createElement( "li" );
                
                desc_label     				= document.createElement( "label" );
                desc_label.innerHTML 		= "Descrição: ";
                desc_label.className 		= "bold";
                
                desc           				= document.createElement( "span" );
                desc.innerHTML 				= json[ i ].desc;
                desc.id       				= json[ i ].id + "-desc";
                desc.className 				= "left_margin";
                
                preco_li					= document.createElement( "li" );
                
                preco_label     			= document.createElement( "label" );
                preco_label.innerHTML 		= "Preço: ";
                preco_label.className 		= "bold";
                
                preco           			= document.createElement( "span" );
                preco.innerHTML 			= json[ i ].preco;
                preco.id        			= json[ i ].id + "-preco";
                preco.className 			= "left_margin";
                
                dataEntrega_li				= document.createElement( "li" );
                
                dataEntrega_label     		= document.createElement( "label" );
                dataEntrega_label.innerHTML = "Data de entrega: ";
                dataEntrega_label.className = "bold";
                
                dataEntrega           		= document.createElement( "span" );
                dataEntrega.innerHTML 		= json[ i ].data_entrega;
                dataEntrega.id        		= json[ i ].id + "-data-entrega";
                dataEntrega.className 		= "left_margin";
                
                status						= json[ i ].status;
                
                link       					= document.createElement( "a" );
                link.id						= json[i].id;
                link.onclick 				= function(){ validaLogin( this.id ) };            
	            
               
                
                desc_li.appendChild( desc_label );
                desc_li.appendChild( desc );
                
                preco_li.appendChild( preco_label );
                preco_li.appendChild( preco );
                
                dataEntrega_li.appendChild( dataEntrega_label );
                dataEntrega_li.appendChild( dataEntrega );
                
                describe.appendChild( desc_li );
                describe.appendChild( preco_li );
                describe.appendChild( dataEntrega_li );
                
                job_image.appendChild( image );
                
                element.appendChild( job_image );
                element.appendChild( describe );
                
                link.appendChild( element );
                
    			document.querySelector( "#output" ).appendChild( link );

    		}
    		
    		function validaLogin(id) {
            	if(status == 200){
	            	window.location = "jobDetail.html?id=" + id + "/";
                }else if(status == 403){
                	alert("Necessário fazer Login para acessar a JOB!");
                }else{
                	alert("Erro no Sistema, contate o desenvolvedor");
                }
            }
    		
    		if(offset > 1){
    			var anterior = offset - 1;
    			var pag 				= document.createElement( "li" );
    			
    			var linkPg      		= document.createElement( "a" );
    			linkPg.href  			= urlmural + "?where=" + where + "?order=" + order + "?offset=" + anterior + "/";
    			linkPg.innerHTML		= "&laquo;";
    			
    			pag.appendChild( linkPg );
    			
    			document.querySelector( ".pagination" ).appendChild( pag );
    		}
    		
    		for( k = 1; k <= nroPag ; k++ ){
    			
    			var pag 				= document.createElement( "li" );
    			pag.id					= k;
    			
    			
	    			var linkPg      		= document.createElement( "a" );
	    			if(k != offset){
	    			linkPg.href  			= urlmural + "?where=" + where + "?order=" + order  + "?offset=" + pag.id + "/";
	    			}else{
	    				linkPg
	    			}
	    			
	    			linkPg.innerHTML		= k;
    			
    			
    			pag.appendChild( linkPg );
    			
    			document.querySelector( ".pagination" ).appendChild( pag );
    			
    		}
    		
    		if(offset < nroPag){
    			//offset += 1;
    			var proxima = parseInt(offset) +1;
    			
    			var pag 				= document.createElement( "li" );
    			
    			var linkPg      		= document.createElement( "a" );
    			linkPg.href  			= urlmural + "?where=" + where + "?order=" + order + "?offset=" + proxima + "/";
    			linkPg.innerHTML		= "&raquo;";
    			
    			pag.appendChild( linkPg );
    			
    			document.querySelector( ".pagination" ).appendChild( pag );
    		}
    		
        });
       

}, false );
