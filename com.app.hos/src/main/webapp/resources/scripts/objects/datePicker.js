function DatePicker(id, callback) {

	$(id).datepicker(callback);
	
	var picker = $(id).data('daterangepicker');
	
	this.setRange = function () {
		picker.setRange();
	}
};

