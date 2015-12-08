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
    			
    			image           			= document.createElement( "img" );
                image.src       			= json[ i ].image;
                image.id        			= json[ i ].id + "-image";
                image.className 			= "img-responsive";
                
                describe           			= document.createElement( "span" );
                describe.id      			= json[ i ].id + "-describe";
                
                
                desc           				= document.createElement( "span" );
                desc.innerHTML 				= json[ i ].desc;
                desc.id       				= json[ i ].id + "-desc";
                desc.className 				= "br";
                
                preco           			= document.createElement( "span" );
                preco.innerHTML 			= json[ i ].preco;
                preco.id        			= json[ i ].id + "-preco";
                preco.className 			= "br";
                
                dataEntrega           		= document.createElement( "span" );
                dataEntrega.innerHTML 		= json[ i ].data_entrega;
                dataEntrega.id        		= json[ i ].id + "-data-entrega";
                dataEntrega.className 		= "br";
                
                link       			= document.createElement( "a" );
                link.href  			= "jobDetail.html?id=" + json[ i ].id + "/";
                
                
                element.appendChild( image );
                element.appendChild( describe );
                
                describe.appendChild( desc );
                describe.appendChild( preco );
                describe.appendChild( dataEntrega );
                
                
                
                link.appendChild( element );
                
    			//if(nroRegPag > aux) document.querySelector( "#output" ).appendChild( link );
    			//aux++;
    			document.querySelector( "#output" ).appendChild( link );

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
