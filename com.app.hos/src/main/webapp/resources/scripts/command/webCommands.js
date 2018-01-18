
function WebCommand () {
	
	this.type = null;
	this.message = null;
	
	this.setType = function(type) {
		this.type = type;
	}
	
	this.setMessage = function(message) {
		this.message = message;
	}
}

function WebCommandBuilder () {
	this.construct = function(builder) {
        builder.createCommand();
        builder.setMessage();
        return builder.getCommand();
    }
}

function GetAllDeviceCommandBuilder () {
	
	this.command = null;

	this.createCommand = function() {
		this.command = new WebCommand();
		this.command.setType('GET_ALL_DEVICES');
	}
	
	this.setMessage = function () {
		this.command.setMessage(null);
	}
	
	this.getCommand = function () {
		return this.command;
	}
}

