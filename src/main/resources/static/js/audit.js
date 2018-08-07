var app = angular.module('myApp', ["ngRoute"]);
app.controller('myCtrl',  function($scope, $http) {

	
	
	$scope.queryPendingApply = function(){
		$http.get("/audit/queryPendingApply.do",
	        	{
		        params: { 
						'start': $scope.start, 
						'destination': $scope.destination, 
						'time': $scope.time, 
						'operation': $scope.operation
				}
	        }).then(
	       		function(response) {
	   		        $scope.results  = response.data;
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	       
	};	
	$scope.pass = function(city){
		 $http.post("/audit/pass.do",
	        	{
						'city': city
				}
	        ).then(
	       		function(response) {
	   		        $scope.queryPendingApply();
	   		    }, 
	   		    function(response) {
	   		    	 //alert("error:"+JSON.stringify(response));
	   		    }
	       );
	       
	};	
	$scope.reject = function(city){
		 $http.post("/audit/reject.do",
	        	{
						'city': city
				}
	        ).then(
	       		function(response) {
	       			$scope.queryPendingApply();
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	       
	};	
	$scope.viewAuditHistory = function(city){
		jQuery("#createNewAuditDialog").show();
		$http.get("/audit/viewAuditHistory.do",
	        	{
		        params: { 
						'city': city
				}
	        }).then(
	       		function(response) {
	   		        $scope.approves  = response.data;
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	};	
	
	$scope.close = function(){
		jQuery("#createNewAuditDialog").hide();
	};
	
});