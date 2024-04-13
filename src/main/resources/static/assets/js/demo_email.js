(function( $ ) {
	$( document ).ready( function() {
	
//	
//
//	
//	
//		$( '#email-list' ).tagList( 'create', {
//			//console.log("crear correo");
//			tagValidator : function( emailid ) {
//				console.log("crear correo");
//				// @warning: not sure if this RegExp is good enough for all types of email ids
//				var emailPat = /^[A-Za-z]+[A-Za-z0-9._]*@[A-Za-z0-9]+\.[A-Za-z.]*[A-Za-z]+$/;
//				var lala = emailPat.test( $.trim( emailid ) );
//				console.log("crear correo "+lala);
//				return lala;
//			}
//		});
//		
//		$( '#mobile-number-list' ).tagList( 'create', {
//			//console.log("crear numero");
//			tagValidator : function( mobileNumber ) {
//				console.log("VALIDACION crear numero "+mobileNumber);
//				//var mobileNumberPat = /^[1-9]{1}[0-9]{9}$/;
//				var mobileNumberPat = /^[0-9]+$/;
//				var lala1 = mobileNumberPat.test( $.trim( mobileNumber ) );
//				console.log("crear numero "+lala1);
//				return lala1;
//			}
//		});
//
//		$( '#email-list' ).on( 'tagadd', function( $event, tagText, opStatus, message ) {
//			if( opStatus === 'success' ) {
//				console.log( 'Email \'' + tagText + '\' added' );
//			} else if( opStatus === 'failure' ) {
//				alert( 'Email \'' + tagText + '\' could not be added [' + message + ']' );
//			}
//		});
//		
//		$( '#mobile-number-list' ).on( 'tagadd', function( $event, tagText, opStatus, message ) {
//			if( opStatus === 'success' ) {
//				console.log( 'numero \'' + tagText + '\' added' );
//			} else if( opStatus === 'failure' ) {
//				alert( 'El número \'' + tagText + '\' no se pudo agregar [' + message + ']' );
//			}
//		});
//		
//		$( '#email-list, #mobile-number-list' ).on( 'tagremove', function( $event, tagText ) {
//			console.log( 'Tag \'' + tagText + '\' removed' );	
//		});
	});
}( jQuery ));