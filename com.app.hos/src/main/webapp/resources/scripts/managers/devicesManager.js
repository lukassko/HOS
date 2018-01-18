
var deviceManager = (function() {

	var connectedDevices = new Map();
	var deviceStatuses = new Map();
	var selectedDeivce = null;
	
	function _setDevices(devices) {
		devices.forEach(function(entry){
			var device = entry.webdevice;
			var status = entry.devicestatus;
			var id = device.id;
			connectedDevices.set(id,device);
			deviceStatuses.set(id,status);
		}) ;
		if (activePage == 'devices') {
			_drawDevices();
		} 
	}
	
	function _drawDevices() {
		connectedDevices.forEach(function(device,id, map) {
			if (!isDeviceDraw(id)) {
				addDevice(device);
			} else {
				if (selectedDeivce == id) {
					setDeviceStatus();
				}
			} 
		});
	}
	
	function addDevice(device) {
		var name = device.name;
		var id= device.id;
		createDeviceButton(id,name);
		var devId = getDivDeviceId(id);
		$(devId).css('cursor','pointer');
		$('.device-container').on('click', devId, function(){
			$('.device-container div.activeDevice').removeClass('activeDevice');
	    	$(this).addClass('activeDevice'); 
	    	selectedDeivce = id;
	    	setClickedDevice();
		});
	};
	
	function setClickedDevice() {
		setDeviceData();
		setDeviceStatus();
	};
	
	function setDeviceData() {
		var data = connectedDevices.get(selectedDeivce);
		console.log(data);
		var connection = data.connection;
		var ip = connection.ip;
		var port = connection.remotePort;	
		var name = data.name;
		var serial = data.serial;
		deviceControlsManager.setData('ip-address',ip);
		deviceControlsManager.setData('port-number',port);
	}
	
	function setDeviceStatus() {
		var status = deviceStatuses.get(selectedDeivce);
		deviceControlsManager.setProgress('cpu',status.cpuUsage);
		deviceControlsManager.setProgress('ram',status.ramUsage);
	}
	
	function isDeviceDraw(id) {
		return $(getDivDeviceId(id)).length;
	}
	
	function getDivDeviceId(id) {
		return '#device' + id;
	}
	
	function createDeviceButton (id,name) {
		var deviceButton = '<div id="device' + id +
			'" class="device"><i class="fa fa-desktop fa-2x device-icon-text"></i> '+
			'<div class="device-icon-text">' + name + '</div></div>';
			
		$('.device-container').append(deviceButton);
	};
	
	function _removeDevice() {
		
	}
	
	return {
		setDevices: _setDevices,
		drawDevices: _drawDevices,
		removeDevice: _removeDevice
	}
	
})();
	