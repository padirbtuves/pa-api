angular
    .module('paApp', ['ui.utils', 'ui.bootstrap'])
    .factory(
        'AccessService', [
      '$http',
      function ($http) {
                return {
                    load: function (callback) {
                        $http.get('/app/user/all').success(
                            function (data, status, header,
                                config) {
                                callback(data);
                            }).error(
                            function (data, status, header,
                                config) {
                                alert("error");
                            });
                    },
                    currentUser: function (callback) {
                        $http.get('/app/user/current').success(
                            function (data, status, header,
                                config) {
                                callback(data);
                            }).error(
                            function (data, status, header,
                                config) {
                                alert("error");
                            });
                    },
                    updateUser: function (user, callback) {
                        $http.post('/app/user/update', user)
                            .success(
                                function (data, status,
                                    header, config) {
                                    callback(data);
                                }).error(
                                function (data, status,
                                    header, config) {
                                    alert("error");
                                });
                    },
                    getHourlyLogs: function (callback) {
                        $http.get('/stats/hourlyLogs').success(
                            function (data, status, header,
                                config) {
                                callback(data);
                            }).error(
                            function (data, status, header,
                                config) {
                                alert("error");
                            });
                    },
                    getDailyLogs: function (callback) {
                        $http.get('/stats/dailyLogs').success(
                            function (data, status, header,
                                config) {
                                callback(data);
                            }).error(
                            function (data, status, header,
                                config) {
                                alert("error");
                            });
                    }
                }
      }])
    .controller(
        'AccessController', [
      '$scope',
      'AccessService',
      function ($scope, AccessService) {

                $scope.editMode = false;

                AccessService.currentUser(function (user) {
                    $scope.user = user;
                    if (user.admin) {
                        AccessService.load(function (users) {
                            $scope.users = users;
                        });
                    }
                });

                $scope.updateUser = function (user) {
                    AccessService.updateUser(user, function (data) {});
                };

                $scope.update = function () {
                    if ($scope.editMode) {
                        // save stuff
                        AccessService.updateUser($scope.user,
                            function (data) {
                                $scope.editMode = false;
                            });
                    } else {
                        // enable editing
                        $scope.editMode = true;
                    }
                };

                $scope.keyCallback = function ($event) {
                    // $event.preventDefault();
                    alert("Asd");
                };

      }])
    .controller(
        'LogsController', [
      '$scope',
      'AccessService',
      function ($scope, AccessService) {

                google.charts.load('current', {
                    packages: ['corechart', 'bar']
                });
                google.charts.setOnLoadCallback(drawAnnotations);

                function drawAnnotations() {
                	loadHourlyLog();
                	loadDailyLog();
                }
                
                function loadHourlyLog() {
                    AccessService.getHourlyLogs(function (data) {
                        var dataArray = [['Date', 'Visits', {
                            role: 'style'
         }]];
                        for (var i = 0; i < data.length; i++) {
                            dataArray.push([new Date(data[i].dateTime), parseInt(data[i].count),
            'blue'])
                        }

                        var data = google.visualization
                            .arrayToDataTable(dataArray);

                        var options = {
                            chartArea: {
                                left : 0,
                                right : 0,
                                top : 0,
                                //bottom : 30
                            },
                            legend: {
                                position: 'none'
                            },
                            hAxis: {
                                gridlines: {
                                    count: 7,
                                },
                                minorGridlines: {
                                    count: 12,
                                    color: '#f5f5f5' 

                                },
                                format: 'E'
                            },
                            vAxis:{
                            	textPosition: 'none'
                            }
                        };

                        var chart = new google.visualization.ColumnChart(
                            document.getElementById('hourlyLog'));
                        chart.draw(data, options);
                    })
                }
                
                function loadDailyLog() {
                    AccessService.getDailyLogs(function (data) {
                        var dataArray = [['Date', 'Visits', {
                            role: 'style'
         }]];
                        for (var i = 0; i < data.length; i++) {
                            dataArray.push([new Date(data[i].dateTime), parseInt(data[i].count),
            'blue'])
                        }

                        var data = google.visualization
                            .arrayToDataTable(dataArray);

                        var options = {
                            chartArea: {
                                left : 0,
                                right : 0,
                                top : 0,
                                //bottom : 30
                            },
                            legend: {
                                position: 'none'
                            },
                            hAxis: {
                                gridlines: {
                                    count: 4,
                                },
                                minorGridlines: {
                                    count: 12,
                                    color: '#f5f5f5' 

                                },
                                format: 'E'
                            },
                            vAxis:{
                            	textPosition: 'none'
                            }
                        };

                        var chart = new google.visualization.ColumnChart(
                            document.getElementById('dailyLog'));
                        chart.draw(data, options);
                    })
                }

      }]);