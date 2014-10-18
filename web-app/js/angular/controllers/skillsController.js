var app=angular.module("volcare");
app.controller("skills",["$scope","personService",   function($scope,homepageService){
	
	$scope.skills=[{text:"java",value:"java"},
	                    {text:"grails",value:"grails"},
	                    {text:"c++",value:"c++"}
	      
	                    ];
	
	$scope.saveSkills=function(data){
		console.log(data)
		homepageService.saveSkills(data,function(data){
			alert(data.data);
		})
	}
	
}])