
var deviceManager = (function() {

	var progressMap = new Object();
	var devices = new Object();
	var deviceStatuses = new Object();
	var selectedDeivce = null;
	var bars = progressBars.getProgresBarArray;

	$(".device-container").append("<div class='no-device'>No connected devices<br><i class='fa fa-cog fa-3x'></i></div>");
	
	$('.my-progress').each(function( index ) {
		var data = $(this).attr('data-usage-type');
		progressMap[data] = bars[index];
	});
		
	function _addDevices(devices) {
		var tmp = Array.from(devices);
		if (devices.length == 0) {
			alert('EMPTY');
			return
		}
		$(".no-device").empty();
		var devices = JSON.parse(devices);
		this.devices = devices;
		for (var device of devices) { 
			addDevice(device);
		}
	};
			
	function addDevice(device) {
		var name = device.name;
		var id= device.id;
		var connectionId= device.connectionId;
		createDeviceButton(id,name,connectionId);
		var devId = '#device' + id;
		var that = this;
		$(devId).css('cursor','pointer');
		$('.device-container').on('click', devId, function(){
			$('.device-container div.activeDevice').removeClass('activeDevice');
	    	$(this).addClass('activeDevice'); 
	    	selectedDeivce = id;
	    	setClickedDevice();
		});
	};
	
	function createDeviceButton (devId,devName,devConnectionId) {
		var deviceButton = '<div id="device' + devId +
			'" class="device"><i class="fa fa-desktop fa-2x device-icon-text"></i> '+
			'<div class="device-icon-text">' + devName + '</div></div>';
			
		$('.device-container').append(deviceButton);
	};
			
	function removeDevice(id) {
		var devId = '#device' + id;
	};
	
	function setClickedDevice() {
		setDeviceStatus(deviceStatuses);
	};
			
	function _setProgress(type,value) {
		var bars = document.querySelectorAll('.my-progress');
		bars.forEach(function (bar,index) {
			var data = $(bar).attr('data-usage-type');
			if (type === data) {
				var progressBar = progressMap[data];
				progressBar.draw(value);
			}
		});
	};
	
	function setDeviceStatus(statuses) {
		deviceStatuses = statuses;
		jQuery.each(statuses, function(id, status) {
			if (selectedDeivce == id) {
				var cpuUsage = status.cpuUsage;
				var ramUsage = status.ramUsage;
				_setProgress("cpu",cpuUsage);
				_setProgress("ram",ramUsage);
			}
		});
	};
	
	function _receiveMessage(message) {
		alert(message);
		var statuses = JSON.parse(message.body);
		//setDeviceStatus(statuses)
	};
	
	return {
		setProgress: _setProgress,
		addDevices: _addDevices,
		receiveMessage : _receiveMessage
	}
	
})();
	