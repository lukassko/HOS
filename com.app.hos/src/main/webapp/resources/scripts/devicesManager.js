var deviceManager = (function() {

	var progressMap = new Object();
	var devices = new Object();
	var deviceStatuses = new Object();
	var selectedDeivce = null;
	
	var bars = progressBars.getProgresBarArray;
	
	$('.my-progress').each(function( index ) {
		var data = $(this).attr('data-usage-type');
		progressMap[data] = bars[index];
	});
		
	function addDevices(devices) {
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
			
	function setProgress(type,value) {
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
				setProgress("cpu",cpuUsage);
				setProgress("ram",ramUsage);
			}
		});
	};
	
	function receiveMessage(message) {
		var statuses = JSON.parse(message.body);
		setDeviceStatus(statuses)
	};
	
	return {
		setProgress: setProgress,
		addDevices: addDevices,
		receiveMessage : receiveMessage
	}
})();
	