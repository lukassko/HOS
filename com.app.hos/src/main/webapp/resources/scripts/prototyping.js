
if (typeof window.daterangepicker !== "undefined") {
	window.daterangepicker.prototype.setRange = function () {
		this.startDate = moment().startOf('day');
	    this.endDate = moment().endOf('day');
	    this.callback(this.startDate, this.endDate);
	}
}

