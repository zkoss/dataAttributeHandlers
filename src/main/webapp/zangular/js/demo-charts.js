angular.module("chart", [ "chart.js" ]).controller(
		"LineCtrl",
		function($scope) {

			$scope.labels = [ "Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.",
					"Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." ];
			$scope.series = [ 'Series' ];
			$scope.onClick = function(points, evt) {
				console.log(points, evt);
			};
		}).controller("BarCtrl", function($scope) {
	$scope.labels = [ '2006', '2007', '2008', '2009', '2010', '2011', '2012' ];
	$scope.series = [ 'Series A', 'Series B' ];

}).controller("PieCtrl", function($scope) {
	$scope.labels = [ "Warning", "Error", "Info" ];
}).controller("DoughnutCtrl", function($scope) {
	$scope.labels = [ "Warning", "Error", "Info" ];
});
angular.bootstrap(angular.element("#angular-charts"), [ 'chart' ]);
