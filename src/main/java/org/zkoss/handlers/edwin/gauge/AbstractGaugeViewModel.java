package org.zkoss.handlers.edwin.gauge;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.NotifyCommand;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.bind.annotation.ToServerCommand;
import org.zkoss.zk.ui.Component;

// @NotifyCommand specifies view model will trigger the doValueChange command whenever _value is changed.
// The _vm_ here stands for the current view model.
@NotifyCommand(value="doValueChange", onChange="_vm_.value")

// @ToClientCommand specifies that every time the 'doValueChange' command executes, ZK will notify our 
// client, and if there is a binder.after callback at client, it will be invoked. Notice that we do not 
// have a 'doValueChange' command in our view model, we put @NotifyCommand above just because we want to 
// trigger the callback function at the client.
@ToClientCommand({"doValueChange"})

// @ToServerCommand Specifies that the client-side binder.command("$saveClientInitValues", dataValue) 
// will invoke this view model.  There must be a method below declared with 
// @Command("gauge$saveClientInitValues").  The command name must match to zk.xml -> data-handler -> name
@ToServerCommand({"gauge$saveClientInitValues"})
public abstract class AbstractGaugeViewModel {
	protected String _viewModelId;	// the @id set in the viewModel
	protected int _min;				// gauge's minimum display value
	protected int _max;				// gauge's maximum display value
	protected int _value = 0;		// gauge's current value
	
	/**
	 * Store the view Model ID from the client viewModel="@id()" attribute.
	 * 
	 * @param component The current component that the binder has been applied to.
	 */
	@Init
	public void init(@ContextParam(ContextType.COMPONENT) Component inComponent) {
		// Save the vm id
		_viewModelId = (String) inComponent.getAttribute("$VM_ID$");
	}
	
	/**
	 * Save the initial values defined in the client "ca:data-gauge" attribute, which is passed to this java method
	 * via the Client-binding command(commandName, data) event.  See data-gauge.js.
	 * 
	 * @param min minimum display value of the gauge
	 * @param max maximum display value of the gauge.
	 */
	@Command("gauge$saveClientInitValues") // the command name must match to zk.xml -> data-handler -> name
	public void saveClientInitAttributes(@BindingParam("min") int inMin, @BindingParam("max") int inMax) {
		_min = inMin;
		_max = inMax;
	}

	/**
	 * This method is loaded by the <span/> sclass attribute.
	 * @return int percentage
	 */
	public int getValue() {
		return _value;
	}
	
	/**
	 * This method receive "@command('setValue', stringValue=self.previousSibling.value)" event from the client.
	 * It sets the value to the view model, and callback the client after() to refresh the gauge js widget.
	 * 
	 * @param inValue string integer value
	 */
	@Command
	@NotifyChange("value") // declared to invoke client-side binder.after('doValueChange') callback.
	public void setValue(@BindingParam("stringValue") String inValue) {
		try {
			_value = Integer.valueOf(inValue);
		} catch (Exception e) {
			// report error.
		};
	}

	/**
	 * Subclasses needs to implement this method to set the gauge value for a specific purpose.
	 */
	public abstract void updateGauge();
	
}
