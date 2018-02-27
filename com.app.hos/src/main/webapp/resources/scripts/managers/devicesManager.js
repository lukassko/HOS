
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
			} 
		});
		if (selectedDeivce != null) {
			refreshDeviceStatus();
		}
	}
	
	function addDevice(device) {
		var name = device.name;
		var id= device.id;
		createDeviceButton(id,name);
		var devId = getDivDeviceId(id);
		$(devId).css('cursor','pointer');
		$('.device-container').on('click', devId, function(){
			$('.device-container div.activeDevice').removeClass('activeDevice');
	    	selectedDeivce = id;
	    	setClickedDevice();
		});
	};
	
	function refreshDeviceStatus() {
		setDeviceData();
		setDeviceStatus();
	}
	
	function setClickedDevice() {
		statusDatePicker.setRange();
		$(getDivDeviceId(selectedDeivce)).addClass('activeDevice'); 
		refreshDeviceStatus();
	};
	
	function setDeviceData() {
		var data = connectedDevices.get(selectedDeivce);
		var connection = data.connection;
		var ip = connection.ip;
		var port = connection.remotePort;	
		var name = data.name;
		var serial = data.serial;
		var connectionTime = getConnectionTimeAsString(connection);
		deviceControlsManager.setData('ip-address',ip);
		deviceControlsManager.setData('port-number',port);
		deviceControlsManager.setData('connection-time',connectionTime);
	}
	
	function getConnectionTimeAsString(connection){
		var connectionTime = connection.connectionTime;
		var day = addZero(connectionTime.day);
		var month = addZero(connectionTime.month);
		var year = connectionTime.year;
		var hour = addZero(connectionTime.hour);
		var minutes = addZero(connectionTime.minutes);
		var seconds = addZero(connectionTime.seconds);
		return getTimeAsString(year,month,day,hour,minutes,seconds);
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
		var deviceButton = '<div id="device' + id + '" class="device tooltips">' +
								'<i class="fa fa-desktop fa-2x device-icon-text" style="float: left;"></i> '+
								'<div class="device-icon-text device-name">' + name + '</div>'+
								'<span class="tooltiptext">' + name + '</span>'+
							'</div>';
			
		$('.device-container').append(deviceButton);
	};
		
	function _removeDevice() {
		
	}
	
	function _getActiveDevice() {
		var device = connectedDevices.get(selectedDeivce);
		if (device == undefined) {
			return null
		}
		return device;
	}
	
	function _selectFirst() {
		if (connectedDevices.size > 0) {
			var id = connectedDevices.keys().next().value;
			selectedDeivce = id;
			setClickedDevice();
		}
	}
	
	return {
		setDevices: _setDevices,
		drawDevices: _drawDevices,
		removeDevice: _removeDevice,
		getActiveDevice: _getActiveDevice,
		selectFirst: _selectFirst
	}
	
})();
	