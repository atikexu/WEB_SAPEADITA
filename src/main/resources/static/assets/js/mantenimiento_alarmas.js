var listaAlimentariosCache = new Object();
var listaProductosCache = new Object();
var listaDocumentosCache = new Object();

var tbl_det_productos = $('#tbl_det_productos');
var tbl_det_documentos = $('#tbl_det_documentos');
var tbl_det_estados = $('#tbl_det_estados');


var frm_dat_generales = $('#frm_dat_generales');
var frm_det_documentos = $('#frm_det_documentos');
var frm_det_productos = $('#frm_det_productos');

var medTransporteDon = "";
var table11 = $('#dataTable3');

var $tagNumeros = "";
var tagListTpl='';
var tagListTagTpl='';
var indicadorNewAct='';

$(document).ready(function() {

//		let data = [
//		                "admin@jqueryscript.net",
//		      "admin@cssscript.com"
//		            ]
//            $("#essai").email_multiple({
//                data: data
//                // reset: true
//            });
	
	
	
//	var table = $('#dataTable3').DataTable();
//	$('#dataTable3 tbody').on( 'click', 'button', function () {
//		
//		var inputfile = $("#imagenMulti");    
//		inputfile.replaceWith(inputfile.val('').clone(true));
//	    $('#modal_titulo').html('Actualizar Producto');
//		//
//	
//		var datos= (table.row( this ).data());
//		$('#idProducto').val($.trim(datos[0]));
//		console.log("categria "+$.trim(datos[1]));
//		console.log("subcategria "+$.trim(datos[2]));
//	    $('#idCategoria1').val($.trim(datos[1]));
//	    $('#idSubCategoria1').val($.trim(datos[2]));
//	    console.log($.trim(datos[2])=='0');
//	    if($.trim(datos[1])!='9'){
//	    	$('#idSubCategoria1').prop('disabled', true);
//	    }else{
//	    	$('#idSubCategoria1').prop('disabled', false);
//	    }
//	    $('#nombre').val($.trim(datos[3]));
//	    $('#descripcion').val($.trim(datos[4]));
//	    $('#estado').val($.trim(datos[5]));
//	    var urlImagen = 'http://comprayventaperu.pe/alimentoselectos.pe/alimentosftp/'+$.trim(datos[6]);
//        $('#nombreArchivo').attr('src', urlImagen);
//        $('#nombreImagen').val($.trim(datos[6]));
//        $("#divlabel label[for=inputGroupFile01]").text($.trim(datos[6]));
//	    $('#div_producto').modal('show');
//	} );
//	
	
    $('#mensajeProgramadoContainer').on('click', '.btn_editar_alarmax', function() {
        // Acciones a realizar cuando se hace clic en el botón "Editar"
//        console.log("entrooooo");
        
        // Resto de tu código aquí
    });

	
	$('#href_doc_nuevo').click(function(e) {
		e.preventDefault();
		$('#h4_tit_no_alimentarios').html('Nuevo Documento');
		$('#frm_det_documentos').trigger('reset');
		
		$('#txt_doc_fecha').datepicker('setDate', new Date());
		$('#hid_cod_documento').val('0');
		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#txt_descripcion_doc').val('');
		$('#txt_sub_archivo').val(null);
		$('#div_det_documentos').modal('show');
		
	});
	
	$('#href_doc_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var tbl_det_documentos = $('#tbl_det_documentos').DataTable();
		tbl_det_documentos.rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else if (indices.length > 1) {
			addWarnMessage(null, mensajeValidacionSeleccionarSoloUnRegistro);
		} else {
			
			var obj = listaDocumentosCache[indices[0]];
			
			$('#h4_tit_documentos').html('Actualizar Documento');
			$('#frm_det_documentos').trigger('reset');
			$('#hid_cod_documento').val(obj.idDocumentoIngreso);			
			$('#sel_tipo_documento').val(obj.idTipoDocumento);
			$('#txt_nro_documento').val(obj.nroDocumento);
			$('#txt_doc_fecha').val(obj.fechaDocumento);
			$('#txt_descripcion_doc').val(obj.observacion);
			$('#hid_cod_act_alfresco').val(obj.codAlfresco);
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_lee_sub_archivo').val(obj.nombreArchivo);
			$('#txt_sub_archivo').val(null);
			
			$('#div_det_documentos').modal('show');
		}
		
	});
	
	$('#href_doc_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = ''
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDocumentoDonacion = listaDocumentosCache[index].idDocumentoIngreso;
				codigo = codigo + idDocumentoDonacion + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else {
			var msg = '';
			if (indices.length > 1) {
				msg = mensajeConfirmacionEliminacionVariosRegistros;
			} else {
				msg = mensajeConfirmacionEliminacionSoloUnRegistro;
			}
			
			swal({
				  title: 'Está seguro?',
				  text: msg,
				  type: 'warning',
				  showCancelButton: true,
				  confirmButtonColor: '#3085d6',
				  cancelButtonColor: '#d33',
				  confirmButtonText: 'Aceptar',
				  cancelButtonText: 'Cancelar',
				}).then(function () {
					loadding(true);
					
					var params = { 
						arrIdDocumentoDonacion : codigo,
						idIngreso : $('#hid_id_ingreso').val()
					};
			
					consultarAjax('POST', '/donacionesIngreso/registro-donacionesIngreso/eliminarDocumentoDonacionIngreso', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoDonacion(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
				  swal(
					'Eliminado!',
					'Se ha eliminado satisfactoriamente.',
					'success'
				  )
				});
			
			
		}
		
	});
	
});

function inicializarDatos() {
		

	$('#txt_sub_archivo').change(function(e) {
		e.preventDefault();
	    var file_name = $(this).val();
	    var file_read = file_name.split('\\').pop();
	    $('#txt_lee_sub_archivo').val($.trim(file_read).replace(/ /g, '_'));
	    if (esnulo($('#hid_cod_documento').val())) {
	    	$('#hid_cod_ind_alfresco').val('1'); // Nuevo registro
	    } else {
	    	$('#hid_cod_ind_alfresco').val('2'); // Registro modificado
	    }
	   
	    frm_det_documentos.bootstrapValidator('revalidateField', 'txt_lee_sub_archivo');	    
	});
	
	tbl_det_documentos.on('click', '.btn_exp_doc', function(e) {
		e.preventDefault();
		
		var id = $(this).attr('id');
		var name = $(this).attr('name');
		if (!esnulo(id) && !esnulo(name)) {
			descargarDocumento(id, name);
		} else {
			addInfoMessage(null, mensajeValidacionDocumento);
		}
		
	});

}

function descargarDocumento(codigo, nombre) {	
	loadding(true);
	var url = VAR_CONTEXT + '/common/archivo/exportarArchivo/'+codigo+'/'+nombre+'/';	
	$.fileDownload(url).done(function(respuesta) {
		loadding(false);	
		if (respuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, mensajeReporteError);
		} else {
			addInfoMessage(null, mensajeReporteExito);
		}		
	}).fail(function (respuesta) {
		loadding(false);
		addErrorMessage(null, mensajeReporteError);
	});	
}

function listarDocumentoDonacion(indicador) {
	var params = { 
		idIngreso : $('#hid_id_ingreso').val()
	};			
	
	consultarAjaxSincrono('GET', '/donacionesIngreso/registro-donacionesIngreso/listarDocumentoDonacionIngreso', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleDocumentos(respuesta);
			if (indicador) {
				loadding(false);
			}
			if (respuesta != null && respuesta.length > 0) {
				$('#sel_tip_movimiento').prop('disabled', true);
			} else {
				$('#sel_tip_movimiento').prop('disabled', false);
			}
		}
	});
}

function listarDetalleDocumentos(respuesta) {

	tbl_det_documentos.dataTable().fnDestroy();
	
	tbl_det_documentos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDocumentoIngreso',
			sClass : 'opc-center',
			render: function(data, type, row) {
				if (data != null) {
					return '<label class="checkbox">'+
								'<input type="checkbox"><i></i>'+
						   '</label>';	
				} else {
					return '';	
				}											
			}
		}, {	
			data : 'idTipoDocumento',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreDocumento'
		}, {
			data : 'nroDocumento',
				render: function(data, type, row) {
					if (data != null) {
						return '<button type="button" id="'+row.codigoArchivoAlfresco+'" name="'+row.nombreArchivo+'"'+ 
							   'class="btn btn-link input-sm btn_exp_doc">'+data+'</button>';
					} else {
						return '';	
					}											
				}
		}, {
			data : 'fechaDocumento'
		}, {
			data : 'nombreArchivo'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaDocumentosCache = respuesta;

}

$('#btn_gra_documento').click(function(e) {
	e.preventDefault();
	
	var bootstrapValidator = frm_det_documentos.data('bootstrapValidator');
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {			
		loadding(true);
		
		var params = { 
			idDocumentoIngreso : $('#hid_cod_documento').val(),
			idIngreso: $('#hid_id_ingreso').val(),
			idTipoDocumento : $('#sel_tipo_documento').val(),
			nroDocumento : $('#txt_nro_documento').val(),
			fechaDocumento : $('#txt_doc_fecha').val(),
			nombreArchivo : $('#txt_lee_sub_archivo').val(),
			observacion : $('#txt_descripcion_doc').val()
		};
		
		var cod_ind_alfresco = $('#hid_cod_ind_alfresco').val();
		if (cod_ind_alfresco == '1' || cod_ind_alfresco == '2') { // Archivo cargado
			var file_name = $('#txt_sub_archivo').val();
			var file_data = null;
			if (!esnulo(file_name) && typeof file_name !== 'undefined') {
				file_data = $('#txt_sub_archivo').prop('files')[0];
			}
			
			var formData = new FormData();
			formData.append('file_doc', file_data);
			// Carpeta contenedor, ubicado en config.properties
	    	formData.append('uploadDirectory', 'params.alfresco.uploadDirectory.donaciones');
	    	
			consultarAjaxFile('POST', '/common/archivo/cargarArchivo', formData, function(resArchivo) {
				
				if (resArchivo == NOTIFICACION_ERROR) {
					
					$('#div_det_documentos').modal('hide');
					frm_det_documentos.data('bootstrapValidator').resetForm();
					loadding(false);
					addErrorMessage(null, mensajeCargaArchivoError);						
				} else {
					
					params.codigoArchivoAlfresco = resArchivo;
				
					grabarDetalleDocumento(params);					
				}
			});
			
		} else { // Archivo no cargado
			
			params.codAlfresco = $('#hid_cod_act_alfresco').val();

			grabarDetalleDocumento(params);				
		}
	}
	
});

function grabarDetalleDocumento(params) {
	consultarAjax('POST', '/donacionesIngreso/registro-donacionesIngreso/grabarDocumentoDonacionIngreso', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoDonacion(true);
			addSuccessMessage(null, respuesta.mensajeRespuesta);	
		}
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
}

function readImage (input) {
	if (input.files && input.files[0]) {
	  var reader = new FileReader();
	  reader.onload = function (e) {
	      $('#nombreArchivo').attr('src', e.target.result); // Renderizamos la imagen
//	      $('#nombreImagen').val(e.target.result);
	  }
	  reader.readAsDataURL(input.files[0]);
	}
}
$("#imagenMulti").change(function () {
   // Código a ejecutar cuando se detecta un cambio de archivO
   var file_name = $(this).val();
   var file_read = file_name.split('\\').pop();
   $('#nombreImagen').val(file_read);
   readImage(this);
});

$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});

$('#btn_editar_alarmaxxx').click(function(e) {

	

	var numeros = editNumber;
	var numerosLista = numeros.split(",");		
	var fechaProgramadaFormat = editFecha; 
	var mensaje = editMensaje; 
	var usuario = $("#idUsuario");
	var inputfile = $("#imagenMulti");    
	inputfile.replaceWith(inputfile.val('').clone(true));
	$('#modal_titulo').html('Actualizar Programación');
	
	var idmensaje = $('#idMensaje').val();
//        console.log("IDMENSAJE "+idmensaje);
        $('#fechaProgramada').val(fechaProgramadaFormat);
        $('#mensaje').val(editMensaje);
        if (esnulo(idmensaje)) {
        	$('#titulo').val('');
        	$('#mensaje').val('');
        } 
        

	    $('#div_producto').modal('show');
});

$('#btn_editar_alarma').click(function(e) {
		indicadorNewAct = '1';
		var numeros = $('#numerosView').val();
		var numerosLista = numeros.split(",");		
		var fechaProgramadaFormat = $('#fechaProgramadaFormatView').val(); 
		var mensaje = $('#mensajeView').val(); ; 
		
//		console.log("MEMSAJE_ "+mensaje);
		
		var usuario = $("#idUsuario");
		var inputfile = $("#imagenMulti");    
		inputfile.replaceWith(inputfile.val('').clone(true));
		$('#modal_titulo').html('Actualizar Programación');
		
		
		
		
		var numeros1 = $('#numerosView').val();

		var numerosLista = numeros1.split(",");
		var sizeLista = numeros1.split(",").length - 1;
		tagListTpl='';
		tagListTagTpl='';
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
		
		tagListTagTpl = [
			'<div class="taglist-tag">',
				'<span class="taglist-tag-text"></span>',
				'<span class="taglist-tag-remove"><span class="icon-close"></span></span>',
			'</div>',
		].join( '\n' );
			
		$('#mobile-number-list').html(tagListTpl);
		var $el = $( tagListTpl );
		$tagNumeros = "";
		$tagNumeros = $( tagListTpl );
//		console.log($tagNumeros.html());
		bindEvents1( $el );

		
		
		
		
		
		
//		var datos= (table.row( this ).data());
//		$('#idProducto').val('');
//	    $('#idCategoria1').val(1);
//	    $('#idSubCategoria1').val(0);
//	    $('#idSubCategoria1').prop('disabled', true);
//	    $('#nombre').val(editTitulo);
//	    $('#descripcion').val('');
//	    $('#estado').val(1);
//	    var urlImagen = 'http://comprayventaperu.pe/alimentoselectos.pe/alimentosftp/logotipo-alimentos-selectos.png';
//        $('#nombreArchivo').attr('src', urlImagen);
//        $('#nombreImagen').val('');
//        $("#divlabel label[for=inputGroupFile01]").text('');

        var idmensaje = $('#idMensaje').val();
//        console.log("IDMENSAJE "+idmensaje);
        $('#fechaProgramada').val(fechaProgramadaFormat);
        $('#mensaje').val(mensaje);
        $('#titulo').val($('#tituloView').val());
//        if (esnulo(idmensaje)) {
//        	$('#titulo').val('');
//        	$('#mensaje').val('');
//        } 
        

	    $('#div_producto').modal('show');
} );

$('#btnCrear').click(function(e) {

		indicadorNewAct='';
		tagListTpl='';
		tagListTagTpl='';
		tagListTpl = [
				'<div class="taglist">',
					'<div class="taglist-tags" />',
					'<textarea class="taglist-input" rows="1" aria-describedby="emailHelp" placeholder="Ingrese aquí"></textarea>',
				'</div>'
				].join( '\n' );
				
		tagListTagTpl = [
			'<div class="taglist-tag">',
				'<span class="taglist-tag-text"></span>',
				'<span class="taglist-tag-remove"><span class="icon-close"></span></span>',
			'</div>',
		].join( '\n' );
		$('#mobile-number-list').html(tagListTpl);
		var $el = $( tagListTpl );
		bindEvents1( $el );
		
		
		var coduser = useruser;	
		var fechaProgramadaFormat = editFecha; 
		$('#modal_titulo').html('Nueva Programación');
        $('#fechaProgramada').val(fechaProgramadaFormat);
    	$('#titulo').val('');
    	$('#mensaje').val('');
    	$('#idMensaje').val('');
    	$('#idUsuario').val(coduser);
    	$('#switch1').prop('checked', false);

	    $('#div_producto').modal('show');
} );


$('#btn_guardarlala').click(function(e) {
//		console.log("FUARDAR");
		// Obtener el contenedor principal
		const emailList = document.getElementById('mobile-number-list');
		
		// Obtener todos los elementos de clase 'taglist-tag-text' dentro del contenedor principal
		const emailElements = emailList.getElementsByClassName('taglist-tag-text');
		
		// Recorrer los elementos y obtener el texto de cada correo electrónico
		const emails = [];
		for (let i = 0; i < emailElements.length; i++) {
		  const email = emailElements[i].textContent;
		  emails.push(email);
		}
		
		
		
		var fechaProgramada = $('#fechaProgramada').val();
		// Mostrar los correos electrónicos obtenidos
//		console.log(fechaProgramada);
//		console.log(emails);
	
} );



//$('#btn_guardarMensaje').click(function(e) {
////		console.log("GUARDAR_MENSAJE");
//		// Obtener el contenedor principal
//		const emailList = document.getElementById('mobile-number-list');
//		
//		// Obtener todos los elementos de clase 'taglist-tag-text' dentro del contenedor principal
//		const emailElements = emailList.getElementsByClassName('taglist-tag-text');
//		
//		// Recorrer los elementos y obtener el texto de cada correo electrónico
//		const emails = [];
//		var email_cadena = '';
//		for (let i = 0; i < emailElements.length; i++) {
//		  const email = emailElements[i].textContent;
//		  emails.push(email);
//		  email_cadena = email_cadena+email+',';
//		}
//		
//		var emailUltimo = email_cadena.slice(0, -1);
//		
////		console.log("CADENA_NMEROS "+emailUltimo);
//		
//		var titulo = $('#titulo').val();
//		var fechaProgramada = $('#fechaProgramada').val();
//		var mensaje = $('#mensaje').val();
//		// Mostrar los correos electrónicos obtenidos
////		console.log(fechaProgramada);
////		console.log(emails);
//
//		var params = {
//					titulo: titulo, 
//				      celulares: emailUltimo, 
//				      fechaProgramada: fechaProgramada, 
//				      mensaje: mensaje
//			};
//		
//		 $.ajax({
//	      url: '/private/grabarMensaje',
//	      type: 'POST',
//	      data: params,
//	      success: function(response) {
//	        // Manejar la respuesta del servidor
////	        console.log('Datos enviados correctamente');
//	      },
//	      error: function(error) {
//	        // Manejar errores
////	        console.error('Error al enviar los datos:', error);
//	      }
//	    });
//		
//		
//		
//	
//} );

$('#idCategoria1').on('change', function() {
  if(this.value!='9'){
    	$('#idSubCategoria1').prop('disabled', true);
    	$('#idSubCategoria1').val(0);
    }else{
    	$('#idSubCategoria1').prop('disabled', false);
    	$('#idSubCategoria1').val(0);
    }
});

function verificar() {
  if ( $("#email").val().trim().length > 0 ) {
    //alert("El campo contiene un string válido que no son espacios");
  }
  else {
    $("#email").val('');
    alert("El campo contiene espacios y está vacío");
  }
}

function AgregarCampos(){
//	console.log("AGREGAR CAMPO");
	var campo = "<input type=\"text\" name=\"email\" id=\"email\" class=\"enter-mail-id\" placeholder=\"Enter Email ...\" />";
	$("#textoemail").html(campo);
}

function miFuncionSubmit(){

	const est =  $('#switch1').prop('checked');
	const emailList = document.getElementById('mobile-number-list');
	$('#estado').val('3');
	if (est == false) { //cuando es false es activo
		$('#estado').val('1');
	}
	// Obtener todos los elementos de clase 'taglist-tag-text' dentro del contenedor principal
	const emailElements = emailList.getElementsByClassName('taglist-tag-text');
	
	// Recorrer los elementos y obtener el texto de cada correo electrónico
	const emails = [];
	var email_cadena = '';
	for (let i = 0; i < emailElements.length; i++) {
	  const email = emailElements[i].textContent;
	  emails.push(email);
	  email_cadena = email_cadena+email+',';
	}
	
	var emailUltimo = email_cadena.slice(0, -1);
	var user = useruser;
	
	var user  = $('#idUsuario').val();
	var mens  = $('#idMensaje').val();
	
	$('#celulares').val(emailUltimo);
	$('#idUsuario').val(user);
	$('#idMensaje').val(mens);
	if (esnulo(indicadorNewAct)) {
    	$('#idMensaje').val('');
    }
    
    var validTitulo = $('#titulo').val();
    var validCelulares = $('#celulares').val();
    var validMensaje = $('#mensaje').val();
    
    if(validTitulo != '' && validMensaje != ''){
    	if(validCelulares == ''){
    		const miInput = document.getElementById('lbl_numeros');
    		miInput.style.color = 'red';
    		return false;
    		
    	}
    }
    return true;
	
}

function esnulo(campo) {
    return (campo == null || campo == '' || campo == 'null');
}

$bbt = $('.mensajeProgramadoContainer');
$bbt.on( 'click', '.btn_editar_alarmax', function() {
	const miInput = document.getElementById('lbl_numeros');
    miInput.style.color = 'black';
	editarTag( $( this ).closest( '.mensajeProgramadoContainer' ) );
});

function editarTag( $tag ) {

  // Imprimir el estado del checkbox en la consola

	var idMensajeView = $tag.closest('.mensajeProgramadoContainer').find('#idMensajeView').val();
    var idUsuarioView = $tag.closest('.mensajeProgramadoContainer').find('#idUsuarioView').val();
    var tituloView = $tag.closest('.mensajeProgramadoContainer').find('#tituloView').val();
    var numerosView = $tag.closest('.mensajeProgramadoContainer').find('#numerosView').val();
    var fechaProgramadaFormatView = $tag.closest('.mensajeProgramadoContainer').find('#fechaProgramadaFormatView').val();
    var mensajeView = $tag.closest('.mensajeProgramadoContainer').find('#mensajeView').val();
    var estadoView = $tag.closest('.mensajeProgramadoContainer').find('#estadoView').val();

	$('#idMensaje').val(idMensajeView);
	$('#idUsuario').val(idUsuarioView);
	
	$('#switch1').prop('checked', false);
	if (estadoView == '3') {
		$('#switch1').prop('checked', true);
	}
	
	indicadorNewAct = '1';
	var numeros = numerosView;
	var numerosLista = numeros.split(",");		
	var fechaProgramadaFormat = fechaProgramadaFormatView; 
	var mensaje = mensajeView; 
	
	
	var usuario = idUsuarioView;
	
	$('#modal_titulo').html('Actualizar Programación');
	

	var numeros1 = numerosView;

	var numerosLista = numeros1.split(",");
	var sizeLista = numeros1.split(",").length - 1;
	tagListTpl='';
	tagListTagTpl='';
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
		      <textarea class="taglist-input" rows="1" aria-describedby="emailHelp" placeholder="Ingrese aquí" id="txt_numeros" name="txt_numeros"></textarea>
		   </div>
		`;
	}else{
		tagListTpl = [
			'<div class="taglist">',
				'<div class="taglist-tags" />',
				'<textarea class="taglist-input" rows="1" aria-describedby="emailHelp" placeholder="Ingrese aquí" id="txt_numeros" name="txt_numeros"></textarea>',
			'</div>'
			].join( '\n' );
	}
	
	tagListTagTpl = [
		'<div class="taglist-tag">',
			'<span class="taglist-tag-text"></span>',
			'<span class="taglist-tag-remove"><span class="icon-close"></span></span>',
		'</div>',
	].join( '\n' );
		
	$('#mobile-number-list').html(tagListTpl);
	var $el = $( tagListTpl );
	$tagNumeros = "";
	$tagNumeros = $( tagListTpl );

	bindEvents1( $el );



    var idmensaje = idMensajeView;
    $('#fechaProgramada').val(fechaProgramadaFormat);
    $('#mensaje').val(mensaje);
    $('#titulo').val(tituloView);
    

    $('#div_producto').modal('show');
	
	

} 

function miFuncionAyuda() {
	closeModal();
//	$('#div_ayuda').modal('show');
	var modal = document.getElementById('div_ayuda');
    modal.style.display = "block";

} 

function closeModal() {
    var modals = document.getElementsByClassName("modal");
    for (var i = 0; i < modals.length; i++) {
      modals[i].style.display = "none";
    }
  }

function bindEvents1( $el ) {

	

	$el = $('.taglist');
	var $input = $el.find( '.taglist-input' );
	
	var textarea = document.querySelector('.taglist-input');
	var valor = textarea.value;

	
	
	
	function removeTag( $tag ) {
		var $el = $tag.closest( '.taglist' ), tagText = $tag.find( '.taglist-tag-text' ).text();
		$tag.remove();
		setInputWidth( $el );
		var $container = $('.taglist');
		$el.data('$container', $container);
		$el.data( '$container' ).trigger( 'tagremove', tagText );
	}
	
//	$('.taglist-tag-remove').on('click', function() {
//		console.log("CERRAR CSMmmmmmmmmmmmmmmmmmm");
//		removeTag( $( this ).closest( '.taglist-tag' ) );
//	});
	
	$el.on( 'click', '.taglist-tag-remove', function() {
				removeTag( $( this ).closest( '.taglist-tag' ) );
			});
	
	$('.taglist-input').on({
		// handle keyboard events on textarea
		// handle text paste in textarea (avoiding use of 'paste' event due to poor support)
		input : function( $event ) {
			var $tagNumeros4 = $('.taglist');
//			console.log($tagNumeros4.html());
			var $input1 = $tagNumeros4.find( '.taglist-input' );
			var inputValue = $input1.val();

			var contieneEspacio = inputValue.includes(" ");
			
			var $valormensaje1 = $('.taglist-input');

			var soloNumeros = /^\d+$/;

			if (contieneEspacio) {

			  pushTag( $el );
			}else{
				isTagToBePopped( $el ) && unsetTagToBePopped( $el )
			}
			
			if( !isKeyStrokeHandled( $el ) ) {

			}
		},

		// handle focus & blur events to effect highlight of taglist when textarea is in focus
		focus : function() {
			$el.addClass( 'taglist-highlight' );
		},
		blur : function() {
			$el.removeClass( 'taglist-highlight' );
		}
	});
	
	function pushTag( $el, tagText ) {
			var $tag = null, $input = $el.find( '.taglist-input' ), isInputVal = false;
//			var tagValidator = $el.data().tagValidator;
			
			if( tagText === null || tagText === undefined ) {
				isInputVal = true;
				tagText = $input.val();
			}
			tagText = $.trim( tagText );
			var $container = $('.taglist');
			$el.data('$container', $container);
			if( tagValidatorr( tagText ) ) {
				isTagToBePopped( $el ) && unsetTagToBePopped( $el );

				$tag = $( tagListTagTpl );
				
				$tag.find( '.taglist-tag-text' ).text( tagText );
				$input.val( '' );
				$el.find( '.taglist-tags' ).append( $tag );
//				console.log("tag "+$el.html());
				setInputWidth( $el );
				
				var $dataTagList = $('.taglist');
				
//				$container.html( $dataTagList );
//				console.log("NEWCONTAINER "+$container.html());
				
				$el.data( '$container' ).trigger( 'tagadd', [ tagText, 'success' ] );
				return true;
			}
			
			$el.data( '$container' ).trigger( 'tagadd', [ tagText, 'failure', 'tag validation error' ] );
			return false;
		}
	
	function unsetTagToBePopped( $el ) {
		$el.find( '.taglist-tag' ).filter( ':last' ).removeClass( 'taglist-tag-highlight' );
		$el.data( 'isTagToBePopped', false );
	}
	
	function isTagToBePopped( $el ) {
		return $el.data( 'isTagToBePopped' );
	}
	
	function isKeyStrokeHandled( $el ) {
		return $el.data( 'isKeyStrokeHandled' );
	}
	
	function tagValidatorr( mobileNumber ) { // checks tagText is not empty after stripping it of leading and trailing whitespaces
//		console.log("VALIDACION crear numero "+mobileNumber);
		//var mobileNumberPat = /^[1-9]{1}[0-9]{9}$/;
		var mobileNumberPat = /^[0-9]+$/;
		var lala1 = mobileNumberPat.test( $.trim( mobileNumber ) );
//		console.log("crear numero "+lala1);
		return lala1;
	}
	
	
	
//	$.fn.tagList = function( action, options ) {
//    return this.each(function() {
//      var $this = $( this );
//      switch( action ) {
//        case 'create' :
//          options = $.extend( true, $this.tagList.defaults, options );
//          create( $this, options );
//          break;
//        case 'options' :
//          setOptions( $this, options );
//          break;
//      }
//    });
//  }
  
//  $( '#mobile-number-list' ).tagList( 'create', {
//		//console.log("crear numero");
//		tagValidator : function( mobileNumber ) {
//			console.log("crear numero "+mobileNumber);
//			//var mobileNumberPat = /^[1-9]{1}[0-9]{9}$/;
//			var mobileNumberPat = /^[0-9]+$/;
//			var lala1 = mobileNumberPat.test( $.trim( mobileNumber ) );
//			console.log("crear numero "+lala1);
//			return lala1;
//		}
//	});
	
	$( '#mobile-number-list' ).on( 'tagadd', function( $event, tagText, opStatus, message ) {
		if( opStatus === 'success' ) {
//			console.log( 'numero \'' + tagText + '\' added' );
			const miInput = document.getElementById('lbl_numeros');
    		miInput.style.color = 'black';
		} else if( opStatus === 'failure' ) {
			alert( 'El número \'' + tagText + '\' no se pudo agregar [' + message + ']' );
		}
	});
	
	$( '#mobile-number-list' ).on( 'tagremove', function( $event, tagText ) {
//		console.log( 'Tag \'' + tagText + '\' removed' );	
	});
  
	function create( $container, options ) {
//	console.log("INGRESO CREATE");
		var $el = $( tagListTpl );
		$container.html( $el );
//		console.log($container.html());
		$el.data( options ).data( '$container', $container );
		setInputWidth( $el );
//		bindEvents( $el, options );
	}
//	
//	// @todo - tagList could take in more options and these could be modified via 'options' argument to tagList()
//	function setOptions( $el, options ) {
//		for( var prop in options ) {
//			set( $el, prop, options[prop] );
//		}
//	}
//	
	function setInputWidth( $el ) {
		var $lastTag = $el.find( '.taglist-tag' ).filter( ':last' );
		$el.find( '.taglist-input' ).width( 
			$el.offset().left + $el.width() - ( $lastTag.offset() && $lastTag.offset().left + $lastTag.width() || 0 ) - 20
		);
	}
//	
//	function bindEvents( $el, options ) {
//		bindEvents1( $el );
//	}


		
}


