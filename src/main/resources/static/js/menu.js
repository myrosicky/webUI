var app = angular.module('myApp', ["ngRoute"]);
app.controller('myCtrl',  function($scope, $http) {
	
	$scope.roleOpts = {
			'ADMIN' : '1',
			'APPLY' : '2',
	};

	$scope.alertContent = "";

	$scope.getMenu = function(){
		$http.get("/client/menu/getMenu.do",
	        	{
		        params: { 
						'name': $scope.name, 
						'role': $scope.role, 
						'time': $scope.time, 
						'owner': $scope.owner
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
	
	  $scope.saveMenu = function(){
		 $http.post("/client/menu/saveMenu.do",
	        	{
						'name': $scope.i_name, 
						'role': $scope.i_role
				}
	        ).then(
	       		function(response) {
	       			$scope.closeCreateNewMenuDialog();
	       			$scope.getMenu();
	   		    }, 
	   		    function(response) {
	   		    	 alert("error:"+JSON.stringify(response));
	   		    }
	       );
	       
	};	
	
	$scope.createNewMenu = function(){
		$scope.wannaCreateNew = true;
		$scope.i_name = "";
		$scope.i_role = "";
		$scope.alertContent = "";
	};
	
	$scope.closeCreateNewMenuDialog = function(){
		$scope.wannaCreateNew = false;
	};
	
	
});