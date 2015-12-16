var _INDEXER_ = {
	timeLeft: null,
	timer: null
};

var _UNITS_ = {
	"B": 0,
	'kB': 1,
	'MB': 2,
	'GB': 3,
	'TB': 4
};


$(document).ready(function() {
	
	$('.graph--rounded').each(function() {
		var container = $(this);
		$(container).append("<h4 class='graph__title'>"+ $(container).data('title') +"</h4>");
		$(container).append("<span class='graph__caption'></span>");
		$(container).append("<canvas class='graph__background' width='200px' height='200px'></canvas>");
		$(container).append("<canvas class='graph__illustration' width='200px' height='200px'></canvas>");
		
		var background = $(container).find('.graph__background')[0].getContext('2d');
		background.beginPath();
		background.arc(100, 100, 85, 0, 2 * Math.PI);
		background.lineWidth = 20;
		background.strokeStyle = '#FFF';
		background.shadowOffsetX = 2;
		background.shadowBlur = 5;
		background.shadowColor = 'rgba(0, 0, 0, 0.2)';
		background.stroke();
		
		var illustration = $(container).find('.graph__illustration')[0].getContext('2d');
		illustration.beginPath();
		var caption = $(container).find('.graph__caption');
		var color = $(container).data('color');
		var unit = $(container).data('unit');
		
		if(typeof unit != 'undefined' && unit.length > 0 && _UNITS_[unit] != 'undefined') {
			var max = Math.ceil($(container).data('max') / Math.pow(1024, _UNITS_[unit]));
			var value = Math.ceil($(container).data('value') / Math.pow(1024, _UNITS_[unit]));
		}
		else {
			var max = $(container).data('max');
			var value = $(container).data('value');
		}
		
		renderGraphRounded(container, illustration, caption, color, max, value, unit, 1000);
	});
	
	$('#indexer-document').bind('click', function(event) {
		event.preventDefault();
		var trigger = $(this);
		
		openWebSocket("ws://localhost:8080"+ _JAVA_context +"/administration/indexer",
			rebuildIndexError,
			rebuildIndexOpen,
			rebuildIndexMessage,
			function() {
				rebuildIndex('document');
			}
		);
	});
	
	$('#indexer-suggestion').bind('click', function(event) {
		event.preventDefault();
		var trigger = $(this);
		
		openWebSocket("ws://localhost:8080"+ _JAVA_context +"/administration/indexer",
			rebuildIndexError,
			rebuildIndexOpen,
			rebuildIndexMessage,
			function() {
				rebuildIndex('suggestion');
			}
		);
	});
	
});

/* ============================== *\
   => Statistic
\* ============================== */
function renderGraphRounded(container, illustration, caption, color, max, value, unit, duration) {
	var start = new Date().getTime();
	var end = start + duration;
	var current = 0;
	var distance = value - current;
	
	var step = function() {
		var timestamp = new Date().getTime();
		var progress = Math.min((duration - (end - timestamp)) / duration, 1);
		current = current + (distance * progress);

		var ratio = (value / max) * progress;
		illustration.clearRect(0, 0, 200, 200);
		illustration.beginPath();
		illustration.arc(100, 100, 85, (-(1 / 2) * Math.PI), (ratio * 2 * Math.PI - (1 / 2) * Math.PI));
		illustration.lineWidth = 20;
		illustration.strokeStyle = color;
		illustration.stroke();
		
		if(typeof unit != 'undefined') {
			caption.html((value * progress).toFixed() +' '+ unit);
		}
		else {
			caption.html((value * progress).toFixed());
		}
		
		if(progress < 1) {
			setTimeout(function() {
				step();
			}, 1);
		}
	};
	
	return step();
}


/* ============================== *\
   => Indexer
\* ============================== */
function rebuildIndex(indexName) {
	$('.indexer__rebuild').addClass('disabled');
	
	_WEBSOCKET_.send('{"action":"rebuildIndex", "index":"'+ indexName +'"}');
}

function rebuildTimeLeft() {
	if(_INDEXER_.timeLeft >= 1000) {
		_INDEXER_.timeLeft -= 1000;
		$('#indexer-left').html(formatTime(_INDEXER_.timeLeft));
	}
	else if(_INDEXER_.timeLeft != null && _INDEXER_.timer != null) {
		clearInterval(_INDEXER_.timer);
	}
}

function rebuildIndexError() {
	
}

function rebuildIndexOpen(event) {
	
}

function rebuildIndexMessage(event) {
	var response = JSON.parse(event.data);
	
	switch(response.type) {
		case 'rebuildIndexAccept':
			$('#notifications').css('display', 'none').empty();
			displayNotifications([response.notifications]);
			$('#notifications').slideDown(500);
			
			$('#indexer-progress').css('width', 0).html('0%').addClass('active');
			$('#indexer-left').css('display', 'inline-block').html('--:--:--');
			$('#indexer-progresscontainer').css('display', 'block');
			
			_INDEXER_.timer = setInterval(rebuildTimeLeft, 1000);
			break;
			
		case 'rebuildIndexProgress':
			_INDEXER_.timeLeft = response.timeLeft;
			var percent = (response.progress * 100) +'%';
			$('#indexer-progress').css('width', percent).html(percent);
			break;
			
		case 'rebuildIndexCompleted':
			closeWebSocket();
			
			$('#indexer-progress').removeClass('active');
			$('#indexer-left').css('display', 'none');
			
			if(_INDEXER_.timer != null) {
				clearInterval(_INDEXER_.timer);
			}
			
			_INDEXER_ = {
				timeLeft: null,
				timer: null
			};
			
			$('#indexer-progresscontainer').css('display', 'none');
			$('.indexer__rebuild').removeClass('disabled');
			
			$('#notifications').css('display', 'none').empty();
			displayNotifications([response.notifications]);
			$('#notifications').slideDown(500);
			break;
	}
}