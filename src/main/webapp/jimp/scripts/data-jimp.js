function (wgt, dataValue) {
	var binder = zkbind.$('$myWindow');
	
	Caman("#" + wgt.uuid, wgt.getSrc(), function () {
		this
			.contrast(dataValue.contrast)
			.brightness(dataValue.brightness)
			.colorize(dataValue.color, 20)
			.render(function() {
				var jpegData = document.getElementById(wgt.uuid).toDataURL("image/jpeg");
				
				binder.command('camanCalculationFinished', {data: jpegData});
			});
	});
}