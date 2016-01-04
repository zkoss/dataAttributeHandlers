function xxx(zkWgt, dataValue) { // the function name has no impact but defined to satisfy IDE's style check
	
	// Client-binding with parameters.  Tell the ViewModel about its id from the client zul tag.
	// $saveClientInitValues is the method name on the ViewModel.
	this.command("$saveClientInitValues", dataValue);
	
	// Figure out the best size for the gauge.
	var width = parseInt(zkWgt.getWidth(), 10);
	var height = parseInt(zkWgt.getHeight(), 10);
	var size = 200; // default
	if (isNaN(width) && isNaN(height)) {
		size =  width < height ? width : height;
	} else if (!isNaN(width)) {
		size = width;
	} else if (!isNaN(height)) {
		size = height;
	}
	
	// Create the gauge js object using the 3rd party js library.
	var config = 
	{
		size: size,
		label: undefined != dataValue ? dataValue.label : '',
		min: undefined != dataValue && undefined != dataValue.min ? dataValue.min : 0,
		max: undefined != dataValue && undefined != dataValue.max ? dataValue.max : 100,
		minorTicks: 5
	}
	var range = config.max - config.min;
	config.yellowZones = [{ from: config.min + range*0.75, to: config.min + range*0.9 }];
	config.redZones = [{ from: config.min + range*0.9, to: config.max }];
	
	var gauge = new Gauge(zkWgt.uuid, config);
	gauge.render();

	// Override sclass on the <span/> to trigger the refresh
	zkWgt.setOverride("setSclass", function(val) {
        gauge.redraw(val);
	});
	
	// The callback handler to receive 'doValueChange' command from the view model
	zkWgt.$binder().after('doValueChange', function(val) {
        gauge.redraw(val);
	});
}
