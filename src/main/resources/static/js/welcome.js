$(document).ajaxSuccess(function(event, xhr, settings){
	if(xhr.responseJSON.code == 1002){
		top.location.href="/";
	}
});
