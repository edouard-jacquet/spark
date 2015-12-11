var _INDEXER_ = {
	name: null,
	size: null,
	builded: null
};

$(document).ready(function() {
	
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
   => Index
\* ============================== */
function rebuild(indexName) {
	$('.indexer__rebuild').addClass('disabled');
	_INDEXER_.name = indexName;
	_INDEXER_.builded = 0;
	$('#indexer-progresscontainer').css('display', 'block');
	_WEBSOCKET_.send('{"action":"rebuild", "index":"'+ _INDEXER_.name +'"}');
}

function receiveRebuildMessage(message) {
	var response = JSON.parse(message.data);
	
	switch(response.operation) {
		case 'addToTotalCount':
			_INDEXER_.size = response.count;
			
			$('#indexer-progress').css('width', 0).html('0%').addClass('active');
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