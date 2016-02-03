function MainCtrl ($uibModal, moment) {

	//zk bind the client attribute parameters to the angular component
	var init = document.getElementById(
			zk.Widget.$(jq('$wmlCalendar')).uuid).getAttribute(
			"data-mwlcalendar");
	var jsonParams = JSON.parse(init);
	var vm = this;
	
	// These variables MUST be set as a minimum for the calendar
	// to work
	vm.calendarView = jsonParams.calendarView;
	vm.calendarTitle = jsonParams.calendarTitle;
	vm.calendarDay = new Date();
	vm.events = [];

	vm.isCellOpen = true;

	/*
	 * var currentYear = moment().year(); var currentMonth =
	 * moment().month();
	 * 
	 * function random(min, max) { return
	 * Math.floor((Math.random() * max) + min); }
	 * 
	 * for (var i = 0; i < 1000; i++) { var start = new
	 * Date(currentYear,random(0, 11),random(1, 28),random(0,
	 * 24),random(0, 59)); vm.events.push({ title: 'Event ' + i,
	 * type: 'warning', startsAt: start, endsAt:
	 * moment(start).add(2, 'hours').toDate() }) }
	 */

	function showModal(action, event) {
		$uibModal.open({
			templateUrl : 'modalContent.html',
			controller : function() {
				var vm = this;
				vm.action = action;
				vm.event = event;
			},
			controllerAs : 'vm'
		});
	}

	vm.eventClicked = function(event) {
		showModal('Clicked', event);
	};

	vm.eventEdited = function(event) {
		showModal('Edited', event);
	};

	vm.eventDeleted = function(event) {
		showModal('Deleted', event);
	};

	vm.eventTimesChanged = function(event) {
		showModal('Dropped or resized', event);
	};

	vm.toggle = function($event, field, event) {
		$event.preventDefault();
		$event.stopPropagation();
		event[field] = !event[field];
	};

}
angular
		.module('demo', [ 'mwl.calendar', 'ui.bootstrap' ])
		.controller(
				'MainCtrl',
				MainCtrl);

