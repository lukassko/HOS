var datePicker = (function () {

    var start = moment().subtract(29, 'days');
    var end = moment();

    function onPeriodSelect(start, end) {
    	console.log(start);
    	console.log(end);
    	setSelectedPeriod(start,end);
    }

    function setSelectedPeriod (start, end) {
	   	 $('#report-range span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	} 
    
    $('#report-range').daterangepicker({
        startDate: start,
        endDate: end,
        ranges: {
           'Today': [moment(), moment()],
           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
           'This Month': [moment().startOf('month'), moment().endOf('month')],
           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }, onPeriodSelect);

    setSelectedPeriod(start,end);

})();

