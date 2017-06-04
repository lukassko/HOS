function Device(id,name,serial,connection,icon) {
	this.id = id;
	this.name = name;
	this.serial = serial;
	this.connection = connection;
	this.icon = icon;
}

Device.prototype.draw = function(content) {
	var deviceButton = '<div id="device' + this.id +
	'" class="device"><i class="'+ this.icon +' fa-2x device-icon-text"></i> '+
	'<div class="device-icon-text">' + this.name + '</div></div>';
	content.append(deviceButton);		
}

function PhoneDevice(name,serial,ip,port,connection) {
	this.icon = "";
	Device.call(this,name,serial,ip,port,connection,icon);

}

PhoneDevice.prototype = Object.create(Device.prototype);