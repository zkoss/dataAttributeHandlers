function (wgt, dataValue) {
	Caman("#" + wgt.uuid, wgt.getSrc(), function () {
		this
			.contrast(dataValue.contrast)
			.brightness(dataValue.brightness)
			.render();
	});
}