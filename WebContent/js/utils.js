advancedFunction = {

        applyToAll: function( selector, toApply ){
        
                try{

                        var all = document.querySelectorAll( selector );
                        for( var i = 0; i < all.length; i++ ){ toApply( all[ i ] ); }

                } catch( error ){ console.log( error ); }

        },

        verifyField: function( selector, whitelist, callback ){
        
                field = document.querySelector( selector );
                
                for( character in field.value ){
                
                        exists = false;
                
                        for( characterToTest in whitelist ){

                                if( field.value[ character ] == whitelist[ characterToTest ] ){
                                
                                        exists = true;
                                        break;
                                
                                }
                        
                        }
                        
                        if( !exists ){
                        
                                callback();
                                break;
                        
                        }
                
                }

        },
        
        verifyEmail: function( selector, callback ){
        
                email       = document.querySelector( selector );
                positionAt  = 0;
                positionDot = 0;
                
                atNumber         = 0;
                
                invalidCharacter = false;
                
                for( character in email.value ){
                
                        if( email.value[ character ] == '@' ){
                        
                                positionAt  = character;
                                atNumber++;
                        
                        }
                        
                        if( email.value[ character ] == '@' ) positionDot = character;
                
                }
                
                advancedFunction.verifyField( selector, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@.-_", function(){
                
                        invalidCharacter = true;
                
                } )
                
                if( positionAt == 0 || positionAt > positionDot || invalidCharacter || atNumber > 1 ) callback();
        
        },
        
        compareFields: function( selector, selectorToCompare, callback ){
        
                field          = document.querySelector( selector ).value;
                fieldToCompare = document.querySelector( selectorToCompare ).value;
                
                if( field.localeCompare( fieldToCompare ) ) callback();
        
        },

        sendJson: function( destiny, toSend, callback ){

                var request;

                try{

                        request = new XMLHttpRequest();
                        console.log( "Foi criado o request" );

                } catch( error ){ console.log( "Não foi possivel criar o request: " + error ); }

                request.onreadystatechange = function(){

                        if( request.readyState != 4 ){ console.log( "Requisição não concluida. Status: " + request.readyState );}
                        else{

                                console.log( "Requisição aceita" );
                                jsonObject = request.responseText;

                                try{ jsonObject = JSON.parse( jsonObject ); }
                                catch( error ){ console.log( "Não foi possível criar o objeto Json: " + error );}

                                callback( jsonObject );

                        }

                };

                console.log( "Foi enviado o json" );
                
                request.open( "POST", destiny, true );
                request.send( toSend );
        }
        
};
