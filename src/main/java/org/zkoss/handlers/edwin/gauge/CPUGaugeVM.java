package org.zkoss.handlers.edwin.gauge;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.ToServerCommand;

/**
 * This subclass can be used to calculate the current CPU load, and show in the Gauge UI.
 * 
 * @author Edwin Yu
 */
@Init(superclass = true)
@ToServerCommand({"gauge$saveClientInitValues"}) // It seems the parent method is not picked up, so I'm redefine again in the subclass.
public class CPUGaugeVM extends AbstractGaugeViewModel {

	@Override
	@NotifyChange("value")
	@Command @GlobalCommand
	public void updateGauge() {
		// Obviously this is not how CPU load is calculated and it's beyond
		// the scope of this sample project.
		_value = (int) Math.ceil(Math.random() * 100);
	}
}
