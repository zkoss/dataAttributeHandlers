function (wgt, option) {
	  var self = this;
	  if (self.command) {
		  // get the data from the view model
		  self.command("$getLineBarData");
		  self.command("$getBarData");
		  self.command("$getPieData");
	  }
	  // MVVM only
	  if (self.after) {
	    // refresh the calendar by the events gotten from the VM
	    self.after('$initLineChart', function (evt) {
	    	if (evt != null) {
	    		angular.element("#line-chart").scope().data = evt;
	    		angular.element("#line-chart").scope().$apply();
	      }
	    });
	    self.after('$initBarChart', function (evt) {
	    	if (evt != null) {
	    		angular.element("#bar-chart").scope().data = evt;
	    		angular.element("#bar-chart").scope().$apply();
	      }
	    });
	    self.after('$initPieChart', function (evt) {
	    	if (evt != null) {
	    		angular.element("#pie-chart").scope().data = evt;
	    		angular.element("#pie-chart").scope().$apply();
	    		angular.element("#doughnut-chart").scope().data = evt;
	    		angular.element("#doughnut-chart").scope().$apply();
	      }
	    });
	  }
}