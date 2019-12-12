$(function() {
	$('#calendar').calendar({
		data: {
			events: [
				{title: '吃饭了', desc: '要吃更多', allDay: true, start: new Date(2019,11,12)}
			]
		}
	});
	
	var calendar = $('#calendar').data('zui.calendar');
});