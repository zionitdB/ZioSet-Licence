angular.module('myApp', [
	'ui.router',
	
	'LocalStorageModule',
	'angularUtils.directives.dirPagination',
	'ngCookies',
	'toastr',
	'chart.js',
	'ngJsonExportExcel',
	'ui.bootstrap',
//	'myApp.barcode',
	'myApp.main',
	'myApp.home',
	'myApp.login',
	'myApp.generic',
	'myApp.user',
	'myApp.asset',
	'myApp.tagUpload',
	'myApp.assetEmployeeMapped',
	'myApp.assetInfo',
	'myApp.mailManger',
	'myApp.device',
	'myApp.notification',
	'myApp.assetTagMapping',
	'myApp.allocationReport',
	'myApp.profile',
	'myApp.assetSummaryReport',
	'myApp.assetMovementReport',
	'myApp.employee',
	'myApp.deallocationAsset',
	'myApp.updateTagRegistration',
	'myApp.AllAsset',
	'myApp.UnRegisterAsset',
	'myApp.help',
	'myApp.allocatedAsset',
	'myApp.unAllocatedAsset',
	'myApp.assetLife',
	'myApp.endOfLifeReport',
	'myApp.employeeBranchWise',
	'myApp.eol',
	'myApp.assetData1',
	'myApp.assetInOfficeReport',
	'myApp.assetInOfficeReport2',
	'myApp.reportAssetNoDetection',
	'myApp.assetReport',
	'myApp.unAllocatedAssetDetectByReader',
	'myApp.rawDataReport',
	'myApp.reportTransaction',
	'myApp.reportLastlocations',
	'myApp.storageLocation',
	'myApp.kittingArea',	
	'myApp.transferStoreLocation',
	'myApp.pullingAsset',
	'myApp.pushingAsset',
	'myApp.reportAssetTrasnfer',
	'myApp.reportStoreRoomWise',
	'myApp.stockByKittingArea',
	'myApp.emailTemplate',
	'myApp.updateMac',
	'myApp.reportDailyTransactionReaderWise',
	'myApp.reportRawDataReaderWise',
	'myApp.licence',
	'myApp.licenceAllocation',
	'myApp.licenceDashboard',
	'myApp.AllLicence',
	'myApp.AssignedLicence',
	'myApp.InstockLicence',
	'myApp.reportInstallLicence',
	'myApp.reportExpiryLicence',
	'myApp.reportActiveLicence',
	'myApp.licenceLifeNotification',
	'myApp.fetchInstallLicence',
	'myApp.endOfLifeLicence',
	'myApp.licenceDetials',
	'myApp.customerSuppliedSoftware',
	'myApp.bundleAppication',
	'myApp.categoryGrouping',
])

.value('_', window._)

.constant('ApiEndpoint', {
	
 //url: 'http://allstate.adp.ind.in:8080/',
 url: 'http://localhost:8088/',
  //url: 'http://20.219.1.165:8088/',
	//url: 'http://10.195.36.182:8080/',
	//url: 'http://10.195.36.182:8082/',
	
	//url: 'http://INPUNINA024Q530:8082/',
//userKey : 'allState',
	headerKey : ''
})
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }
  
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }else if($location.path() == '/login' && $rootScope.globals.currentUser){
            	$location.path('/main/home');
            }
        });
    }])

/*.config(function($urlRouterProvider,$locationProvider) {
	 if none of the above states are matched, use this as the fallback
	$urlRouterProvider.otherwise('/main/home');
	 // use the HTML5 History API
	
	 $locationProvider.hashPrefix('');
}); */