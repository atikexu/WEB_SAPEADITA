/**
 * @file Defines tagList jQuery plugin.
 * A tag list input widget that allows user to enter free text and automatically create tags out of them, that get added inside the widget.
 *
 * Events fired
 * ------------
 * 1. tagadd 	: Fired after tag add operation.
 *				  Handler gets $event, tagText, status, message
 *						$event  {object} The auto-generated jQuery event object
 *						tagText {string} The text of the tag that was attempted to be added
 *						status  {string} 'success' | 'failure'
 *						message {string} undefined in case of success, and the error message in case of failure
 * 2. tagremove : Fired after successful removal of tag.
 *				  Handler gets $event, tagText
 *						$event  {object} The auto-generated jQuery event object
 *						tagText {string} The text of the tag that was removed
 */
(function( $ ) {
	$.fn.tagList = function( action, options ) {

		var numeros1 = editNumber;

		var numerosLista = numeros1.split(",");
		var sizeLista = numeros1.split(",").length - 1;
	
		var keyCode = {
			'backspace' : 8,
			'tab'		: 9,
			'enter'		: 13,
			'ctrl'		: 17,
			'alt'		: 18,
			'delete'  	: 46,
			'space'	  	: 32,
			'comma'		: 60
		}
		
		var tagListTpl='';

		if(sizeLista > 0 || numeros1 != ''){
			tagListTpl = `
			   <div class="taglist">
			      <div class="taglist-tags">
			         ${numerosLista.map(celular => `
			            <div class="taglist-tag">
			               <span class="taglist-tag-text">${celular}</span>
			               <span class="taglist-tag-remove"><span class="icon-close"></span></span>
			            </div>
			         `).join('')}
			      </div>
			      <textarea class="taglist-input" rows="1" aria-describedby="emailHelp" placeholder="Ingrese aquí"></textarea>
			   </div>
			`;
		}else{
			tagListTpl = [
				'<div class="taglist">',
					'<div class="taglist-tags" />',
					'<textarea class="taglist-input" rows="1" aria-describedby="emailHelp" placeholder="Ingrese aquí"></textarea>',
				'</div>'
				].join( '\n' );
		}

		
		var tagListTplxx = [
			'<div class="taglist">',
				'<div class="taglist-tags" />',
				'<textarea class="taglist-input" rows="1" aria-describedby="emailHelp" placeholder="Ingrese aquí"></textarea>',
			'</div>'
		].join( '\n' );
		
		var tagListTagTpl = [
			'<div class="taglist-tag">',
				'<span class="taglist-tag-text"></span>',
				'<span class="taglist-tag-remove"><span class="icon-close"></span></span>',
			'</div>',
		].join( '\n' );
		
		// *** helper functions ***
		function setInputWidth( $el ) {
			var $lastTag = $el.find( '.taglist-tag' ).filter( ':last' );
			$el.find( '.taglist-input' ).width( 
				$el.offset().left + $el.width() - ( $lastTag.offset() && $lastTag.offset().left + $lastTag.width() || 0 ) - 20
			);
		}
		
		/*
		 * @function pushTag
		 * Pushes a new tag if the passed tag text passes the validation as specified by tagValidator (validatorFn takes tag text and return true iff tag is (according to whatever criteria) 'valid').
		 * Chooses the current input value as tagText, if one is not passed.
		 * Thus, this method can be used to add tags via others means too (i.e. apart from text entered in input field)
		 * (simply pass the tagText in that case).
		 * @return {boolean} true if pushed, false otherwise.
		 */
		function pushTag( $el, tagText ) {
			var $tag = null, $input = $el.find( '.taglist-input' ), isInputVal = false;
			var tagValidator = $el.data().tagValidator;
			
			if( tagText === null || tagText === undefined ) {
				isInputVal = true;
				tagText = $input.val();
			}
			tagText = $.trim( tagText );
			
			if( tagValidator( tagText ) ) {
				isTagToBePopped( $el ) && unsetTagToBePopped( $el );

				$tag = $( tagListTagTpl );
				$tag.find( '.taglist-tag-text' ).text( tagText );
				$input.val( '' );
				$el.find( '.taglist-tags' ).append( $tag );
				setInputWidth( $el );

				$el.data( '$container' ).trigger( 'tagadd', [ tagText, 'success' ] );
				return true;
			}
			
			$el.data( '$container' ).trigger( 'tagadd', [ tagText, 'failure', 'tag validation error' ] );
			return false;
		}
		
		function removeTag( $tag ) {
			var $el = $tag.closest( '.taglist' ), tagText = $tag.find( '.taglist-tag-text' ).text();
			$tag.remove();
			setInputWidth( $el );
			
			$el.data( '$container' ).trigger( 'tagremove', tagText );
		}
		
		function popTag( $el ) {
			var $tag = $el.find( '.taglist-tag' ).filter( ':last' ), tagText = $tag.find( '.taglist-tag-text' ).text();
			removeTag( $tag );
			$el.data( 'isTagToBePopped', false );
			
			$el.data( '$container' ).trigger( 'tagremove', tagText );
		}
		
		function setTagToBePopped( $el ) {
			$el.find( '.taglist-tag' ).filter( ':last' ).addClass( 'taglist-tag-highlight' );
			$el.data( 'isTagToBePopped', true );		
		}
		
		function unsetTagToBePopped( $el ) {
			$el.find( '.taglist-tag' ).filter( ':last' ).removeClass( 'taglist-tag-highlight' );
			$el.data( 'isTagToBePopped', false );
		}
		
		function isTagToBePopped( $el ) {
			return $el.data( 'isTagToBePopped' );
		}
		
		function setKeyStrokeHandled( $el ) {
			$el.data( 'isKeyStrokeHandled', true );		
		}
		
		function unsetKeyStrokeHandled( $el ) {
			$el.data( 'isKeyStrokeHandled', false );
		}
		
		function isKeyStrokeHandled( $el ) {
			return $el.data( 'isKeyStrokeHandled' );
		}
		
		function bindEvents( $el, options ) {
			var $input = $el.find( '.taglist-input' );
			
			// remove tag when delete button is clicked
			$el.on( 'click', '.taglist-tag-remove', function() {
				console.log("CERRAR CSM OOOOOOOOLLLLLLLLDDDDDDDD");
				removeTag( $( this ).closest( '.taglist-tag' ) );
			});
			
			$input.on({
				// handle keyboard events on textarea
//				keydown : function( $event ) {
//					if( !isKeyStrokeHandled( $el ) ) {
//						var inputKeyCode = $event.which;
//
//						if( ( inputKeyCode === 8 || inputKeyCode === 46 ) && !$input.val() ) {
//							isTagToBePopped( $el ) ? popTag( $el ) : ( inputKeyCode === 8 ) && setTagToBePopped( $el );
//						}
//						
//						!$event.ctrlKey && setKeyStrokeHandled( $el );
//					}
//				},
				// handle text paste in textarea (avoiding use of 'paste' event due to poor support)
				input : function( $event ) {
					var inputValue = $input.val();

					var contieneEspacio = inputValue.includes(" ");

						if (contieneEspacio) {

						  pushTag( $el );
						}else{
							isTagToBePopped( $el ) && unsetTagToBePopped( $el )
						}

					
					if( !isKeyStrokeHandled( $el ) ) {
//						var tagDelimiter = new RegExp( '[, ]+' );
//						var tags = $input.val().split( tagDelimiter );
//						
//						for( var i = 0, invalidTags = []; i < tags.length; i++ ) {
//							pushTag( $el, tags[i] );
//						}
//						
//						setKeyStrokeHandled( $el );
					}
				},
//				keyup : function( $event ) {
//					var inputKeyCode = $event.which || $event.keyCode;
//					
//					alert("CODIGO "+inputKeyCode);
//					if(inputKeyCode === 32){
//						alert("CODIGO1 "+inputKeyCode);
//					}
//
//					if( !( inputKeyCode === 8 || inputKeyCode === 46 ) ) {
//						( inputKeyCode === 32 ) ? pushTag( $el ) :  isTagToBePopped( $el ) && unsetTagToBePopped( $el );
//					}
//					
//					unsetKeyStrokeHandled( $el );
//				},
				// handle focus & blur events to effect highlight of taglist when textarea is in focus
				focus : function() {
					$el.addClass( 'taglist-highlight' );
				},
				blur : function() {
					$el.removeClass( 'taglist-highlight' );
				}
			});
		}

		function create( $container, options ) {
			var $el = $( tagListTpl );
			$container.html( $el );
			console.log($container.html());
			$el.data( options ).data( '$container', $container );
			setInputWidth( $el );
			bindEvents( $el, options );
		}

		// @todo - tagList could take in more options and these could be modified via 'options' argument to tagList()
		function setOptions( $el, options ) {
			for( var prop in options ) {
				set( $el, prop, options[prop] );
			}
		}

		// @todo
		function set( $el, configProperty, configValue ) {
			switch( configProperty ) {
				case 'blah' :
					break;
			}
		}		
		// *** end of helper functions ***

		return this.each(function() {
			var $this = $( this );
			console.log("CONTAINER: "+$this);
			console.log("CONTAINER: "+$this.val());
			switch( action ) {
				case 'create' :
					options = $.extend( true, $this.tagList.defaults, options );
					create( $this, options );
					break;
				case 'options' :
					setOptions( $this, options );
					break;
			}
		});
	}

	// user-configurable defaults
	$.fn.tagList.defaults = {
		tagValidator : function( tagText ) { // checks tagText is not empty after stripping it of leading and trailing whitespaces
			return $.trim( tagText ) !== '';
		}
	};
})( jQuery );