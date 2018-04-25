	
// TODO:
// create factory method - create object first and later return 
function AjaxCall () {};

AjaxCall.prototype.send = function () {
	var that = this;
	$.ajax({
		url         : this.url,
	    type        : this.type,
	    contentType : this.contentType, //data type which is send
	    dataType    : this.dataType, //expected data type
	    data        : this.contentData
	})
	.done(function(response) {
		that.onSuccess(response.status,response);
	})
	.fail(function(response) {
		that.onFailed(response.status,response);
	});
};

function GetViewCall (page,callback) {
	AjaxCall.call(this);
	this.url = page;
	this.type = "GET";
	this.contentData = {};
	this.contentType = {};
	this.dataType = {};
	
	this.onSuccess = function (status,response) {
		callback(status, response);
	};
	
	this.onFailed = function (status, response) {
		callback(status, response);
	};
};

function DeviceStatusCall (serial,from,to,callback) {
	AjaxCall.call(this);
	this.url = "devices/statuses/"+ serial +"?from=" + from + "&to=" + to;
	this.type = "GET";
	this.contentData = {};
	this.contentType = 'aplication/json';
	this.dataType = 'json';
	
	this.onSuccess = function (status,response) {
		callback(status, response);
	};
	
	this.onFailed = function (status, response) {
		callback(status, response);
	};
};

function DeleteDeviceCall (serial,callback) {
	AjaxCall.call(this);
	
	this.url = "devices/statuses/"+ serial;
	this.type = "DELETE";
	this.contentData = {};
	this.contentType = 'aplication/json';
	this.dataType = 'json';
	
	this.onSuccess = function (response) {
		callback(200, response);
	};
	
	this.onFailed = function (status, response) {
		callback(status, response);
	};
};

function GetActiveUserCall (callback) {
	AjaxCall.call(this);
	
	this.url = "user/getactive";
	this.type = "GET";
	this.contentData = {};
	this.contentType = 'aplication/json';
	this.dataType = 'json';
	
	this.onSuccess = function (response) {
		callback(200, response);
	};
	
	this.onFailed = function (status, response) {
		callback(status, response);
	};
};

function LogoutUserCall (callback) {
	AjaxCall.call(this);
	
	this.url = "user/logout";
	this.type = "GET";
	this.contentData = {};
	this.contentType = 'aplication/json';
	this.dataType = 'json';
	
	this.onSuccess = function (response) {
		callback(200, response);
	};
	
	this.onFailed = function (status, response) {
		callback(status, response);
	};
};

GetViewCall.prototype = Object.create(AjaxCall.prototype);
DeviceStatusCall.prototype = Object.create(AjaxCall.prototype);
DeleteDeviceCall.prototype = Object.create(AjaxCall.prototype);
GetActiveUserCall.prototype = Object.create(AjaxCall.prototype);
LogoutUserCall.prototype = Object.create(AjaxCall.prototype);
