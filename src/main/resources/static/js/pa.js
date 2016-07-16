angular
		.module('paApp', [ 'ui.utils', 'ui.bootstrap' ])
		.factory(
				'AccessService',
				[
						'$http',
						function($http) {
							return {
								load : function(callback) {
									$http.get('/app/user/all').success(
											function(data, status, header,
													config) {
												callback(data);
											}).error(
											function(data, status, header,
													config) {
												alert("error");
											});
								},
								currentUser : function(callback) {
									$http.get('/app/user/current').success(
											function(data, status, header,
													config) {
												callback(data);
											}).error(
											function(data, status, header,
													config) {
												alert("error");
											});
								},
								updateUser : function(user, callback) {
									$http.post('/app/user/update', user)
											.success(
													function(data, status,
															header, config) {
														callback(data);
													}).error(
													function(data, status,
															header, config) {
														alert("error");
													});
								},
								getLogs : function(callback) {
									$http.get('/auth/logs').success(
											function(data, status, header,
													config) {
												callback(data);
											}).error(
											function(data, status, header,
													config) {
												alert("error");
											});
								}
							}
						} ])
		.controller(
				'AccessController',
				[
						'$scope',
						'AccessService',
						function($scope, AccessService) {

							$scope.editMode = false;

							AccessService.currentUser(function(user) {
								$scope.user = user;
								if (user.admin) {
									AccessService.load(function(users) {
										$scope.users = users;
									});
								}
							});

							$scope.updateUser = function(user) {
								AccessService.updateUser(user, function(data) {
								});
							};

							$scope.update = function() {
								if ($scope.editMode) {
									// save stuff
									AccessService.updateUser($scope.user,
											function(data) {
												$scope.editMode = false;
											});
								} else {
									// enable editing
									$scope.editMode = true;
								}
							};

							$scope.keyCallback = function($event) {
								// $event.preventDefault();
								alert("Asd");
							};

						} ])
		.controller(
				'LogsController',
				[
						'$scope',
						'AccessService',
						function($scope, AccessService) {

							google.charts.load('current', {
								packages : [ 'corechart', 'bar' ]
							});
							google.charts.setOnLoadCallback(drawAnnotations);

							function drawAnnotations() {
								AccessService.getLogs(function(data) {
									var dataArray = [ [ 'Element', 'Density', {
										role : 'style'
									} ] ];
									for (var i = 0; i < data.length; i++) {
										dataArray.push([ data[i].dateTime, parseInt(data[i].count),
												'blue' ])
									}

									var data = google.visualization
											.arrayToDataTable(dataArray);

									var options = {
										chartArea : {
											//left : 0,
											//right : 0,
											//top : 0,
											//bottom : 30
										},
										legend : {
											position : 'none'
										},
										hAxis : {
											//title : 'Time of Day',
											format : 'h:mm a',

											viewWindow : {
											}
										},
										vAxis : {
											//minValue : 200,
										}
									};

									var chart = new google.visualization.ColumnChart(
											document.getElementById('chart_div'));
									chart.draw(data, options);
								})

							}

						} ]);