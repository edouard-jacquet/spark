$(document).ready(function() {
	if(isFileAPIEnabled() && isEventSupported('dragstart') && isEventSupported('drop')) {
		$('#drop-notsupported').css('display', 'none');
		dropToUpload('/resource/xhr-upload');
	}
	else {
		$('#drop-supported').css('display', 'none');
	}
});

