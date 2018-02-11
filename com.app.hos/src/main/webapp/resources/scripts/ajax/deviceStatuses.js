	
// TODO:
// create factory method - create object first and later return 
function AjaxCall () {};

AjaxCall.prototype.get = function () {
	$.ajax({
		url         : this.url,
	    type        : this.type,
	    contentType : 'aplication/json', //data type which is send
	    dataType    : 'json', //expected data type
	    data        : {}
	})
	.done(function(response) {
		this.onSuccess();
	})
	.fail(function() {
		this.onFailed();
	})
	.always(function() {
		//console.log('ALWAYES block');
	});
};

function DeviceStatusAjax (serial,from, to) {
	AjaxCall.call(this);
	
	this.url = "devices/statuses/"+ serial +"?from=" + from + "&to=" + to;
	this.type = "get";
	
	this.onSuccess = function () {
		console.log("onSuccess");
	};
	
	this.onFailed = function () {
		console.log("onFailed");
	};
	
};


DeviceStatusAjax.prototype = Object.create(AjaxCall.prototype);

DeviceStatusAjax.prototype.get = function () {
    // Call the original version of getName that we overrode.
	AjaxCall.prototype.get.call(this);
}


var deviceStatus = new DeviceStatusAjax('serial_1',0,1518297087);
