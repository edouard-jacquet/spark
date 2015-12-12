var _INDEXER_ = {
	name: null,
	size: null,
	builded: null
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
		var max = $(container).data('max');
		var value = $(container).data('value');
		var unit = $(container).data('unit');
		
		renderGraphRounded(container, illustration, caption, color, max, value, unit, 1000);
	});
	
	$('#indexer-document').bind('click', function(event) {
		event.preventDefault();
		var trigger = $(this);
		
		openWebSocket("ws://localhost:8080"+ _JAVA_context +"/administration/indexer", receiveRebuildMessage, function() {
			rebuild('document');
		});
	});
	
	$('#indexer-suggestion').bind('click', function(event) {
		event.preventDefault();
		var trigger = $(this);
		
		openWebSocket("ws://localhost:8080"+ _JAVA_context +"/administration/indexer", receiveRebuildMessage, function() {
			rebuild('suggestion');
		});
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
		
		if(typeof unit == 'undefined') {
			caption.html((value * progress).toFixed());
		}
		else {
			caption.html((value * progress).toFixed() +' '+ unit);
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
function rebuild(indexName) {
	$('.indexer__rebuild').addClass('disabled');
	
	_INDEXER_.name = indexName;
	_INDEXER_.builded = 0;
	
	$('#indexer-progress').css('width', 0).html('0%').addClass('active');
	$('#indexer-progresscontainer').css('display', 'block');
	
	_WEBSOCKET_.send('{"action":"rebuild", "index":"'+ _INDEXER_.name +'"}');
}

function receiveRebuildMessage(message) {
	var response = JSON.parse(message.data);
	
	switch(response.operation) {
		case 'addToTotalCount':
			_INDEXER_.size = response.count;
			break;
		case 'documentsBuilt':
			_INDEXER_.builded += response.number;
			
			var percent = Math.round((_INDEXER_.builded / _INDEXER_.size) * 100) +'%';
			$('#indexer-progress').css('width', percent).html(percent);
			break;
		case 'indexingCompleted':
			closeWebSocket();
			
			$('#indexer-progress').removeClass('active')
			$('#indexer-progresscontainer').css('display', 'none');
			$('.indexer__rebuild').removeClass('disabled');
			$('#notifications').css('display', 'none').empty();
			displayNotifications([response.notifications]);
			$('#notifications').slideDown(500);
			break;
		
	}
}