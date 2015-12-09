var _GLOBAL_ajax = null;


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
=> displayNotification
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
		if(_GLOBAL_ajax != null) {
			_GLOBAL_ajax.abort();
			_GLOBAL_ajax = null;
		}
		
		_GLOBAL_ajax = $.ajax({
			url: _JAVA_context + url,
			method: 'get',
			data: {query: query},
			dataType: 'json'
		});
		
		_GLOBAL_ajax.done(function(response) {
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
		
		_GLOBAL_ajax.fail(function(xhr, textStatus) {
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
function xhrUpload(url, files, index) {
	var file = files[index];
	var formData = new FormData();
	formData.append('file', file);
	var xhr = new XMLHttpRequest();
	
	xhr.addEventListener('load', function(event) {
		var json = jQuery.parseJSON(event.target.responseText);
		$('#drop').removeClass('dropped');
		$('#progress-upload').removeClass('active');
		
		displayNotifications(json.notifications);
		$('#notifications').slideDown(500);
		
		if(json.error) {
			return false;
		}
	}, false);
	
	xhr.upload.addEventListener('progress',function(event) {
		if(event.lengthComputable) {
			var percent = Math.round((event.loaded / event.total) * 100) +'%';
			$('#progress-upload').css('width', percent).html(percent);
		}
	}, false);
	
	$('#progress-upload').css('width', 0).html('0%').addClass('active');
	
	xhr.open('post', _JAVA_context + url, true);
	xhr.send(formData);
}


/* ============================== *\
   => dropToUpload
\* ============================== */
function dropToUpload(url) {
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
				xhrUpload(url, data.files, 0);
			}
		}
	});
}