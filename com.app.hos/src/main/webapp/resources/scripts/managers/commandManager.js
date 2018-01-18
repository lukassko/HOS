
var commandManager = (function() {

	function _receiveCommand(command) {
		var type = command.type;
		var message = command.message;
		var cmd = createCommandObject(type);
		cmd.setMessage(message);
		cmd.execute();
	};
	
	function createCommandObject(type) {
		if (type == "GET_ALL_DEVICES") {
			return new GetAllDeviceCommand();
		} else {
			// unknown command type
		}
	}
	
	return {
		receiveCommand : _receiveCommand
	}
	
})();

var GetAllDeviceCommand = function () {
	
	var devices;	

	this.execute = function(){
		deviceManager.setDevices(devices);
	}
	
	this.setMessage = function(message){
		devices = JSON.parse(message);
	}
}

var RemoveDeviceCommand = function () {
	
	var deviceToRemove;	

	this.execute = function(){
		//deviceManager.removeDevice(deviceToRemove);
	}
	
	this.setMessage = function(device){
		deviceToRemove = device;
	}
}