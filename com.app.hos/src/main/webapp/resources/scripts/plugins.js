(function ( $ ) {

	$.fn.showModal = function () {
		$(this).find(".modal").modal("show");
		$(this).css("position","relative");
		$('.modal-backdrop').appendTo($(this));  
		$('body').removeClass("modal-open");
		$('body').css("padding-right",""); 
		return this;
	}
	
	$.fn.disable = function() {
		return this.each(function() {
			$(this).css('opacity', '0.6');
			$(this).css('pointer-events', 'none');
		});
	}
	
	$.fn.enable = function() {
		return this.each(function() {
			$(this).css('opacity', '1');
			$(this).css('pointer-events', 'auto');
		});
	}
	
    $.fn.datepicker = function(callback) {
    	
    	var start = moment().startOf('day');
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
            title: "",
            series: [],
            type: 'area'
        }, options );
        
        var series = [];
        $.each(settings.series , function(index, serie) {
        	series.push({
        		name: serie,
        		data: []
        	});
        });
        
        Highcharts.chart(chart.attr('id'), {
    	    chart: {
    	        type: settings.type,
    	        backgroundColor:'#2e353d',
    		    style: {fontFamily: 'Verdana, Geneva, sans-serif'},
    		    plotBorderColor: '#606063'
    	    },
    	    colors: ["#d19b3d","#7cb5ec", "#90ed7d", "#f7a35c", "#8085e9", "#f15c80", "#2b908f", "#f45b5b","#e4d354", "#91e8e1","#434348"],
    	    credits: {
    	        enabled: false
    	    },
    	    legend: {
    	        enabled: false
    	    },
    	    title: {
    	        text: settings.title,
    	        style: {
    		        color: '#efffff',
    		        fontSize: '20px'
    		    }
    	    },
    	    xAxis: {
    	        type: 'datetime',
    	        labels: {
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
    	            style: {color: '#efffff'},
    	        },
    	        title: {
    	        	 text: '%',
    		         style: {color: '#efffff'}
    		    },
    		    gridLineWidth: 0
    		    
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
    		series: series
    	});
        
        return this;
    }
}( jQuery ));
