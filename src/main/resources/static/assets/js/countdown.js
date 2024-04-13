var mensajeProgramado = mensajeProgramadoJson;
var mensajesDecodificados = JSON.parse(mensajeProgramado.replace(/&quot;/g, '"'));


mensajesDecodificados.forEach(function(menpro) {
	var idMensaje = menpro.idMensaje;
	var cuentaId = `#cuenta-${idMensaje}`;
	simplyCountdown(cuentaId, {
		year: menpro.anioView, // required
		month: menpro.mesView, // required
		day: menpro.diasView, // required
		hours: menpro.horasView, // Default is 0 [0-23] integer
		minutes: menpro.minutosView, // Default is 0 [0-59] integer
		seconds: menpro.segundosView, // Default is 0 [0-59] integer
		words: { //words displayed into the countdown
			days: 'Día',
			hours: 'Hora',
			minutes: 'Minuto',
			seconds: 'Segundo',
			pluralLetter: 's'
		},
		plural: true, //use plurals
		inline: false, //set to true to get an inline basic countdown like : 24 days, 4 hours, 2 minutes, 5 seconds
		inlineClass: 'simply-countdown-inline', //inline css span class in case of inline = true
		// in case of inline set to false
		enableUtc: true, //Use UTC as default
		onEnd: function() {
//			console.log("MENSAJE YA ENVIADO");
			return; 
		}, //Callback on countdown end, put your own function here
		refresh: 1000, // default refresh every 1s
		sectionClass: 'simply-section', //section css class
		amountClass: 'simply-amount', // amount css class
		wordClass: 'simply-word', // word css class
		zeroPad: false,
		countUp: false
	});
});