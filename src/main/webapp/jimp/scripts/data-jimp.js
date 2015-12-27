function (wgt, dataValue) {
	Caman("#" + wgt.uuid, wgt.getSrc(), function () {
		this
			.contrast(dataValue.contrast)
			.brightness(dataValue.brightness)
			.colorize(dataValue.color, 20)
			.render();
	});
}