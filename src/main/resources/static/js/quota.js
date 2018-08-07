var app = angular.module('myApp', ["ngRoute"]);
app.controller('myCtrl',  function($scope, $http) {

		
	$scope.monthOpts = {
			'Jan' : '1',
			'Feb' : '2',
			'Mar' : '3',
			'Apr' : '4',
			'May' : '5',
			'Jun' : '6',
			'Jul' : '7',
			'Aug' : '8',
			'Sep' : '9',
			'Oct' : '10',
			'Nov' : '11',
			'Dec' : '12',
	};

	$scope.alertContent = "";

	$scope.query = function(){
		$http.get("/quota/query.do",
	        	{
		        params: { 
						'user': $scope.user, 
						'month': $scope.month, 
						'time': $scope.time, 
						'year': $scope.year, 
						'state': $scope.state
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
	  $scope.saveMonthQuota = function(){
		 $http.post("/quota/saveMonthQuota.do",
	        	{
						'month': $scope.i_month, 
						'amount': $scope.i_amount, 
						'year': $scope.i_year,
						'id': $scope.id
				}
	        ).then(
	       		function(response) {
	       			$scope.closeCreateNewQuotaDialog();
	       			$scope.query();
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	       
	};	
	   $scope.changeMonthQuota = function(id){
		 $scope.createNewQuota();
		 $http.get("/quota/query.do",
		        	{	params: {
							'id': id
		        		}
					}
		        ).then(
		       		function(response) {
		       			var quota = response.data[0];
		       			$scope.i_amount  = quota.amount;
		       			$scope.id  = quota.id;
		       			$scope.i_year  = quota.year;
		       			$scope.i_month  = quota.month;
		   		    }, 
		   		    function(response) {
		   		    	 alert("error:"+JSON.stringify(response));
		   		    }
		    );
	       
	};	
	$scope.deleteMonthQuota = function(id){
		   $http.post("/quota/deleteMonthQuota.do",
		        	{
							'id': id 
					}
		        ).then(
		       		function(response) {
		       			$scope.query();
		   		    }, 
		   		    function(response) {
		   		    	 alert("error:"+JSON.stringify(response));
		   		    }
		       );
	       
	};	
	
	$scope.createNewQuota = function(){
		$scope.wannaCreateNew = true;
		$scope.i_amount  = null;
		$scope.i_year  = "2017";
		$scope.i_month  = "9";
		$scope.id  = "";
		$scope.alertContent = "";
	};
	
	$scope.closeCreateNewQuotaDialog = function(){
		$scope.wannaCreateNew = false;
	};
	
	
});