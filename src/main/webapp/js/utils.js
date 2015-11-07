var _ajax = null;


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
=> suggest
\* ============================== */
function suggest(url, query) {
	if(!isEmpty(query) && !isBlank(query)) {
		if(_ajax != null) {
			_ajax.abort();
			_ajax = null;
		}
		
		_ajax = $.ajax({
			url: _context + url,
			method: 'get',
			data: {query: query},
			dataType: 'json'
		});
		
		_ajax.done(function(response) {
			if(response.error) {
				return false;
			}
			
			$('#suggestions').css('display', 'none').removeClass('open').empty();
			
			for(var i = 0 ; i < response.data.length ; i++) {	
				$('#suggestions').append("\
					<a class='list-group__item' \
						href='"+ _context +"/search?query="+ response.data[i].label +"'>\
						"+ response.data[i].label.replace(query, '<strong>'+ query +'</strong>') +"\
					</a>\
				");
			}
			
			$('#suggestions').css('display', 'block').addClass('open');
		});
		
		_ajax.fail(function(xhr, textStatus) {
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
		
		for(var i = 0 ; i < json.notifications.length ; i++) {
			$('#notifications').append("\
				<div class='row'>\
					<div class='column small-12'>\
						<div class='notification notification--"+ json.notifications[i].type +"'>\
							"+ json.notifications[i].message +"\
							<button class='button notification__close'><span class='bootypo bootypo--remove-2'></span></button>\
						</div>\
					</div>\
				</div>\
			");
		}
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
	
	xhr.open('post', _context + url, true);
	xhr.send(formData);
}


/* ============================== *\
=> dropToUpload
\* ============================== */
function dropToUpload(url) {
	$('#notifications').css('display', 'none');
	
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
				//for multiple upload
				//for(var i = 0 ; i < data.files.length ; i++) {
				//	upload(data.files, i);
				//}
			}
		}
	});
}