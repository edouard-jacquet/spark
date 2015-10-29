$(document).ready(function() {
	if(isFileAPIEnabled() && isEventSupported('dragstart') && isEventSupported('drop')) {
		$('#drop-notsupported').css('display', 'none');
		dropActive();
	}
	else {
		$('#drop-supported').css('display', 'none');
	}
});

function dropActive() {
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
				$('#notifications').empty().css('display', 'none');
				upload(data.files, 0);
				/*
				for(var i = 0 ; i < data.files.length ; i++) {
					upload(data.files, i);
				}
				*/
			}
		}
	});
}

function upload(files, index) {
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
			console.log(percent);
			$('#progress-upload').css('width', percent).html(percent);
		}
	}, false);
	
	$('#progress-upload').css('width', 0).html('0%').addClass('active');
	
	xhr.open('post','xhr-upload', true);
	xhr.send(formData);
}