(function ( $ ) {

    $.fn.datepicker = function(callback) {
    	
    	var start = moment().subtract(29, 'days');
        var end = moment();
        
        return this.each(function() {
        	var datePicker = $(this);

            function onPeriodSelect(start, end) {
            	callback(datePicker.attr("data-type"),start,end);
            	setSelectedPeriod(start,end);
            }

        	function setSelectedPeriod (start, end) {
            	$('span', datePicker).html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        	} 
        	
        	datePicker.daterangepicker({
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

            datePicker.addClass("form-control");
            datePicker.css("color", "black");
            datePicker.css("width", "300px");
            datePicker.html("<i class='glyphicon glyphicon-calendar fa fa-calendar'></i>&nbsp;<span></span> <b class='caret'></b>");
            
            setSelectedPeriod(start,end);
        });
    };
 
    $.fn.chart = function(options) {
    	
    	var chart = $(this);
    	
        var settings = $.extend({
            title: "CPU/RAM usage",
            yAxis : "Percent usage"
        }, options );
        
        Highcharts.chart(chart.attr('id'), {
    	    chart: {
    	        type: 'area',
    	        backgroundColor:'#2e353d',
    		    style: {fontFamily: 'Verdana, Geneva, sans-serif'},
    		    plotBorderColor: '#606063'
    	    },
    	    title: {
    	        text: settings.title,
    	        style: {
    		        color: '#efffff',
    		        fontSize: '20px'
    		    }
    	    },
    	    xAxis: {
    	        allowDecimals: false,
    	        labels: {
    	            formatter: function () {
    	                return this.value; // clean, unformatted number for year
    	            },
    	            style: {color: '#efffff'}
    	        },
    	        title: {
    		         style: {color: '#efffff'}
    		    }
    	    },
    	    yAxis: {
    	    	min: 0,
    	    	max: 100,
    	        title: {
    	            text: settings.yAxis
    	        },
    	        labels: {
    	            style: {color: '#efffff'}
    	        },
    	        title: {
    		         style: {color: '#efffff'}
    		     }
    	    },
    	    tooltip: {
    	        pointFormat: '{series.name} produced <b>{point.y:,.0f}</b><br/>warheads in {point.x}'
    	    },
    	    plotOptions: {
    	        area: {
    	            marker: {
    	                enabled: false,
    	                symbol: 'circle',
    	                radius: 2,
    	                states: {hover: { enabled: true}}
    	            }
    	        }
    	    },
    	    legend: {
    		      itemStyle: {color: '#efffff'},
    		      itemHoverStyle: {color: '#FFF'},
    		      itemHiddenStyle: {color: '#606063'}
    		},
    		series: [{
    			data:[null,null]     
    	    }]
    	});
        
        return this;
    }
}( jQuery ));