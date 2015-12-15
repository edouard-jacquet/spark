var _AJAX_ = null;
var _WEBSOCKET_ = null;


/* ============================== *\
   => isFileAPIEnabled
\* ============================== */
function isFileAPIEnabled() {
	return !!window.FileReader;
}


/* ============================== *\
   => isEventSupported
\* ============================== */
function isEventSupported(eventName, element) {
	element = element || document.createElement('div');
	eventName = 'on'+ eventName;
	
	var isSupported = eventName in element;
	
	if(!isSupported) {
		if(!element.setAttribute) {
			element = document.createElement('div');
		}
		if(element.setAttribute && element.removeAttribute) {
			element.setAttribute(eventName, '');
			isSupported = typeof element[eventName] == 'function';
			
			if(typeof element[eventName] != 'undefined') {
				element[eventName] == undefined;
			}
			element.removeAttribute(eventName);
		}
	}
	
	return isSupported;
}


/* ============================== *\
   => isEmpty
\* ============================== */
function isEmpty(string) {
	return (!string || string.length === 0);
}


/* ============================== *\
   => isBlank
\* ============================== */
function isBlank(string) {
	return (!string || /^[\s].*$/.test(string));
}


/* ============================== *\
=> unserialize
\* ============================== */
function unserialize(string) {
	var entrys = string.split("&");
	var parameters = [];
	
	for(var i = 0 ; i < entrys.length ; i++) {
		entry = entrys[i].split("=");
		parameters[i] = [
			decodeURIComponent(entry[0].replace(/\+/g, '%20')),
			decodeURIComponent(entry[1].replace(/\+/g, '%20'))
		];
	}
	
	return parameters;
}


/* ============================== *\
   => displayNotifications
\* ============================== */
function displayNotifications(notifications) {
	if(notifications != null) {
		for(var i = 0 ; i < notifications.length ; i++) {
			$('#notifications').append("\
				<div class='row'>\
					<div class='column small-12'>\
						<div class='notification notification--"+ notifications[i].type +"'>\
							"+ notifications[i].message +"\
							<button class='button notification__close'><span class='bootypo bootypo--remove-2'></span></button>\
						</div>\
					</div>\
				</div>\
			");
		}
	}
}

/* ============================== *\
   => suggest
\* ============================== */
function suggest(url, query) {
	if(!isEmpty(query) && new RegExp(_JAVA_regexQuery).test(query)) {
		if(_AJAX_ != null) {
			_AJAX_.abort();
			_AJAX_ = null;
		}
		
		_AJAX_ = $.ajax({
			url: _JAVA_context + url,
			method: 'get',
			data: {query: query},
			dataType: 'json'
		});
		
		_AJAX_.done(function(response) {
			if(response.error) {
				return false;
			}
			
			$('#suggestions').css('display', 'none').removeClass('open').empty();
			
			for(var i = 0 ; i < response.data.length ; i++) {	
				var suggestion = response.data[i].query;
				
				if(suggestion != query.toLowerCase()) {
					var idx = suggestion.indexOf(query);
					suggestion = '<strong>'+ suggestion +'</strong>';
					
					if(idx == 0) {
						suggestion = suggestion.replace('<strong>'+ query, query +'<strong>');
					}
					else if(idx == -1) {
						var subQuery = query;
						var matchSpace = subQuery.match(new RegExp(' ', 'g'));
						
						if(matchSpace != null) {
							var countSpace = matchSpace.length;
							
							for(var j = 0 ; j < countSpace ; j++) {
								subQuery = subQuery.substring(0, subQuery.lastIndexOf(' '));
								var idx = suggestion.indexOf(subQuery);
								
								if(idx == 8) {
									suggestion = suggestion.replace('<strong>'+ subQuery, subQuery +'<strong>');
									break;
								}
							}
						}
					}
				}
				
				$('#suggestions').append("\
					<a class='list-group__item' \
						href='"+ _JAVA_context +"/search?query="+ response.data[i].query +"'>\
						<span class='suggestion__label'>"+ suggestion +"</span>\
					</a>\
				");
			}
			
			$('#suggestions').css('display', 'block').addClass('open');
		});
		
		_AJAX_.fail(function(xhr, textStatus) {
			$('#suggestions').css('display', 'none').removeClass('open');
		});
	}
	else {
		$('#suggestions').css('display', 'none').removeClass('open');
	}
}


/* ============================== *\
   => xhrUpload
\* ============================== */
function xhrUpload(url, file, parameters) {
	var formData = new FormData();
	
	formData.append('file', file);
	
	if(parameters != null) {
		params = unserialize(parameters);
		for(var i = 0 ; i < params.length ; i++) {
			formData.append(params[i][0], params[i][1]);
		}
	}
	
	var xhr = new XMLHttpRequest();
	
	xhr.addEventListener('load', function(event) {
		var json = jQuery.parseJSON(event.target.responseText);
		$('#drop').removeClass('dropped');
		$('#upload-progress').removeClass('active');
		$('#upload-progresscontainer').css('display', 'none');
		
		displayNotifications(json.notifications);
		$('#notifications').slideDown(500);
		
		if(json.error) {
			return false;
		}
	}, false);
	
	xhr.upload.addEventListener('progress',function(event) {
		if(event.lengthComputable) {
			var percent = Math.round((event.loaded / event.total) * 100) +'%';
			$('#upload-progress').css('width', percent).html(percent);
		}
	}, false);
	
	$('#upload-progresscontainer').css('display', 'block');
	$('#upload-progress').css('width', 0).html('0%').addClass('active');
	
	xhr.open('post', _JAVA_context + url, true);
	xhr.send(formData);
}


/* ============================== *\
   => dropToUpload
\* ============================== */
function dropToUpload(url, parameters) {
	$('#drop').on('dragover', function(event) {
		event.preventDefault();
		var trigger = $(this);
		$(trigger).addClass('dropped');
	});
	
	$('#drop').on('dragleave', function(event) {
		event.preventDefault();
		var trigger = $(this);
		$(trigger).removeClass('dropped');
	});
	
	$('#drop').on('drop', function(event) {
		event.preventDefault();
		var data = event.originalEvent.dataTransfer;
		
		if(data) {
			if(data.files.length) {
				$('#notifications').css('display', 'none').empty();
				var file = data.files[0];

				if($('#drop-modal').length > 0) {
					$('#drop-modal').fadeIn(500, function() {
						$('#drop-form')[0].reset();
						
						$('#drop-confirm').on('click', function(event) {
							event.preventDefault();
							var trigger = $(this);
							
							$('#drop-confirm').off('click');
							$('#drop-cancel').off('click');
							
							$('#drop-modal').fadeOut(500, function() {
								xhrUpload(url, file, $('#drop-form').serialize());
								$('#drop-form')[0].reset();
							});
						});
						
						$('#drop-cancel').on('click', function(event) {
							event.preventDefault();
							var trigger = $(this);
							
							$('#drop-confirm').off('click');
							$('#drop-cancel').off('click');
							
							$('#drop-modal').fadeOut(500, function() {
								$('#drop-form')[0].reset();
								$('#drop').removeClass('dropped');
							});
						});
					});
				}
				else {
					xhrUpload(url, file, parameters);
				}
			}
		}
	});
}


/* ============================== *\
   => openWebSocket
\* ============================== */
function openWebSocket(url, onErrorCallback, onOpenCallback, onMessageCallback, callback) {
	_WEBSOCKET_ = new WebSocket(url);
	_WEBSOCKET_.onerror = onErrorCallback;
	_WEBSOCKET_.onopen = onOpenCallback;
	_WEBSOCKET_.onmessage = onMessageCallback;
	waitWebSocketIsReady(callback);
}


/* ============================== *\
   => waitWebSocketIsReady
\* ============================== */
function waitWebSocketIsReady(callback) {
	setTimeout(function() {
		if(_WEBSOCKET_.readyState == 1) {
			if(callback != null) {
				callback();
			}
		}
		else {
			waitWebSocketIsReady(callback);
		}
	}, 5);
}


/* ============================== *\
   => closeWebSocket
\* ============================== */
function closeWebSocket() {
	_WEBSOCKET_.onclose = function() {};
	_WEBSOCKET_.close();
}

/* ============================== *\
   => formatTime
\* ============================== */
function formatTime(milliseconds) {
	var formated = '';
	
	var date = new Date(milliseconds);
	
	var hours = date.getUTCHours().toString();
	formated += ('0'+ hours).slice(-2) +':';
	
	var minutes = date.getUTCMinutes().toString();
	formated += ('0'+ minutes).slice(-2) +':';
	
	var seconds = date.getUTCSeconds().toString();
	formated += ('0'+ seconds).slice(-2);
	
	return formated;
}