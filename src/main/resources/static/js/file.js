var app = angular.module('myApp', ["ngRoute"]);

app.controller('myCtrl',  function($scope, $http) {
	$scope.analyzeLog = function() {
        $http.get("/file/analyzeLog.do",
        	{
	        params: { name: $scope.name, logLevel: $scope.logLevel, dateFrom: $scope.dateFrom, dateTo: $scope.dateTo, amount: $scope.amount, searchContent: $scope.searchContent }
        }).then(
       		function(response) {
   		        $scope.logs  = response.data;
   		    }, 
   		    function(response) {
   		    	 alert("error:"+JSON.stringify(response));
   		    }
       );
   }
});