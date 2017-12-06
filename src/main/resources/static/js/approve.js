var approveApp = angular.module('myApp', ["ngRoute"]);
approveApp.controller('myCtrl',  function($scope, $http) {

	$scope.query = function(){
		$http.get("/client/approve/query.do",
	        	{
		        params: { 
						'id': $scope.id, 
						'time': $scope.time, 
						'approver': $scope.approver, 
						'city': $scope.city, 
						'comment': $scope.comment, 
						'operation': $scope.operation, 
						'flow': $scope.flow
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
	$scope.queryPendingApply = function(){
		 $http.get("/client/approve/queryPendingApply.do",
	        	{
			 		params: {
			 		}
				}
	        ).then(
	       		function(response) {
	   		        $scope.results  = response.data;
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	       
	};	
	$scope.approve = function(city){
		 $http.post("/client/approve/approve.do",
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
	$scope.reject = function(city){
		 $http.post("/client/approve/reject.do",
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
	$scope.viewApproveHistory = function(city){
		 jQuery("#approveHistoryDialog").show();
		 $http.get("/client/approve/viewApproveHistory.do",
	        	{
			 		params: {
						'city': city 
			 		}
				}
	        ).then(
	       		function(response) {
	       			$scope.approves = response.data;
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	       
	};	
	
	$scope.closeApproveHistoryDialog = function(){
		jQuery("#approveHistoryDialog").hide();
	};
});
