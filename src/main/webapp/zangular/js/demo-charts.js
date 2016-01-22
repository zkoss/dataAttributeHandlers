angular.module("chart", [ "chart.js" ]).controller(
		"LineCtrl",
		function($scope) {

			$scope.labels = [ "Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.",
					"Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." ];
			$scope.series = [ 'Series' ];
			$scope.onClick = function(points, evt) {
				console.log(points, evt);
			};
		}).controller(
		"BarCtrl",
		function($scope) {
			$scope.labels = [ '2006', '2007', '2008', '2009', '2010', '2011',
					'2012' ];
			$scope.series = [ 'Series A', 'Series B' ];

//			$scope.data = [ [ 65, 59, 80, 81, 56, 55, 40 ],
//					[ 28, 48, 40, 19, 86, 27, 90 ] ];
		});
angular.bootstrap(angular.element("#angular-charts"), [ 'chart' ]);
