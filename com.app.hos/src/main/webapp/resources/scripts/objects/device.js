const DEVICE_TYPE = Object.freeze({
	PHONE: Symbol("PHONE"), 
	PC: Symbol("PC"), 
	TV: Symbol("TV"),
	UNKNOWN: Symbol("UNKNOWN")
});

function Device(id,name,serial,connection,icon) {
	this.id = id;
	this.name = name;
	this.serial = serial;
	this.connection = connection;
	this.icon = icon;
	this.type = DEVICE_TYPE.UNKNOWN;
}

Device.prototype.draw = function(content) {
	var deviceButton = '<div id="device' + this.id + '" class="device tooltips">' +
							'<i class="fa'+ this.icon +'fa-2x device-icon-text" style="float: left;"></i> '+
							'<div class="device-icon-text device-name">' + this.name + '</div>'+
							'<span class="tooltiptext">' + this.name + '</span>'+
						'</div>';
	
	content.append(deviceButton);		
}

Device.prototype.getType = function(content) {
	return this.type;
}

function PhoneDevice(name,serial,ip,port,connection) {
	Device.call(this,name,serial,ip,port,connection,"fa-mobile");
	this.type = DEVICE_TYPE.PHONE;
}

function ComputerDevice(name,serial,ip,port,connection) {
	Device.call(this,name,serial,ip,port,connection,"fa-desktop");
	this.type = DEVICE_TYPE.PC;
}

function TelevisionDevice(name,serial,ip,port,connection) {
	Device.call(this,name,serial,ip,port,connection,"fa-television");
	this.type = DEVICE_TYPE.TV;
}

PhoneDevice.prototype = Object.create(Device.prototype);
ComputerDevice.prototype = Object.create(Device.prototype);
TelevisionDevice.prototype = Object.create(Device.prototype);