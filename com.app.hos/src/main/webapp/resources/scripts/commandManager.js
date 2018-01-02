
var commandManager = (function() {

	var devicesMap = null;
	
	function _receiveCommand(command) {
		var type = command.type;
		var message = command.message;
		var devices = JSON.parse(message);	
		console.log(devices);
	};
	
	function setDevicesMap(devices) {
		devices = devicesMap;
	}
	
	return {
		receiveCommand : _receiveCommand
	}
	
})();
	