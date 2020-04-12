$(function() {
	$('#calendar').calendar({
		data: {
			events: [
				{title: '吃饭了', desc: '要吃更多', allDay: true, start: new Date(2020,03,02)}
			]
		}
	});
	
	/* 增加多个事件 */
	var calendar = $('#calendar').data('zui.calendar');
	var newEvents =
	[
	  {title: '吃饭了', desc: '要吃更多', start: new Date(2020,03,02), end: new Date(2020,03,02)},
	  {title: '学习了', desc: '要学更多', start: new Date(2020,03,02), end: new Date(2020,03,02)}
	];
	calendar.addEvents(newEvents);
});