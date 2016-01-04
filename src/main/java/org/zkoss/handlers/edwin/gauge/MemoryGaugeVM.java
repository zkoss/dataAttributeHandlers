package org.zkoss.handlers.edwin.gauge;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;

/**
 * This subclass calculate the current jvm memory usage, and show in the Gauge UI.
 * 
 * @author Edwin Yu
 */
@Init(superclass=true)
// It seems the parent methods are not picked up, so I redefine the following three annotations again in this subclass.
@NotifyCommand(value="doValueChange", onChange="_vm_.value")
@ToClientCommand({"doValueChange"})
@ToServerCommand({"gauge$saveClientInitValues"})
public class MemoryGaugeVM extends AbstractGaugeViewModel {

	@Override
	@NotifyChange("value")
	@Command @GlobalCommand
	public void updateGauge() {
		Runtime runtime = Runtime.getRuntime();
		long total = runtime.totalMemory();
		long free = runtime.freeMemory();
		_value = (int) ((total - free) * 100 / total);
	}

}
