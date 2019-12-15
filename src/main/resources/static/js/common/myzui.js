/**
 * 对ZUI的二次封装
 */
var myzui = {};

myzui._error=function(str) {
	try {
		new $.zui.Messager(str, {
			icon: 'exclamation-sign',
			type: 'danger'
		}).show();
	} catch (e) {
		alert(str);
	}
},
myzui._success=function(str){
	try {
		new $.zui.Messager(str,{
			icon: 'check',
			type: 'success'
		}).show();
	} catch (e) {
		alert(str);
	}
},
myzui._error1=function(str) {
	try {
		new $.zui.Messager(str, {
			type: 'danger'
		}).show();
	} catch (e) {
		alert(str);
	}
},
myzui._success1=function(str){
	try {
		new $.zui.Messager(str,{
			type: 'success'
		}).show();
	} catch (e) {
		alert(str);
	}
},

/**
 * alert
 */
myzui.alert = function(title, content) {
	//首次，初始化
	if(!myzui.alert_init) {
		myzui.alert_init = true;
		//init DOM
		var h = 
		'<div class="modal fade" id="myzui_alert">\
			<div class="modal-dialog modal-sm" style="width:500px;">\
				<div class="modal-content">\
					<div class="modal-header">\
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>\
						<h4 class="modal-title" id="myzui_alert_title">&nbsp;</h4>\
					</div>\
					<div class="modal-body" id="myzui_alert_content"></div>\
					<div class="modal-footer">\
						<button type="button" class="btn btn-primary" id="myzui_alert_ok">确定</button>\
					</div>\
				</div>\
			</div>\
		</div>';
		$("body").append(h);
		// $('#myzui_alert').modal({show:false, backdrop:"static"});
		$('#myzui_alert').modal({show:false});
		
		//bind events
		$("#myzui_alert_ok").click(function(){
			$('#myzui_alert').modal("hide");
		});
	}
	$("#myzui_alert_title").html(title);
	$("#myzui_alert_content").html(content);
	$('#myzui_alert').modal("show");
}

/**
 * 确认或取消
 */
myzui.confirm = function(question, callback, callback2) {
	myzui.confirm_callback = callback;
	myzui.confirm_callback2 = callback2;
	//首次，初始化
	if(!myzui.confirm_init) {
		myzui.confirm_init = true;
		//init DOM
		var h = 
		'<div class="modal fade" id="myzui_confirm">\
			<div class="modal-dialog modal-sm">\
				<div class="modal-content">\
					<div class="modal-header" style="border-bottom:none;">\
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>\
						<h4 class="modal-title" id="myzui_confirm_title">&nbsp;</h4>\
					</div>\
					<div class="modal-footer">\
						<button type="button" class="btn btn-default" data-dismiss="modal" id="myzui_confirm_cancel">取消</button>\
						<button type="button" class="btn btn-danger" id="myzui_confirm_ok">确认</button>\
					</div>\
				</div>\
			</div>\
		</div>';
		$("body").append(h);
		$('#myzui_confirm').modal({show:false});
		
		//bind events
		$("#myzui_confirm_ok").click(function(){
			if(myzui.confirm_callback) {
				myzui.confirm_callback();
			}
			$('#myzui_confirm').modal("hide");
		});
		
		$("#myzui_confirm_cancel").click(function() {
			if (myzui.confirm_callback2) {
				myzui.confirm_callback2();
			}
			$("#myzui_confirm").modal("hide");
		});
	}
	$("#myzui_confirm_title").html(question);
	$('#myzui_confirm').modal("show");
}

/**
 * 弹出输入框
 */
myzui.input = function(title, placeholder, callback, value) {
	myzui.input_callback = callback;
	//首次，初始化
	if(!myzui.init_input) {
		myzui.init_input = true;
		
		//init DOM
		var h = 
		'<div class="modal fade" id="myzui_input">\
			<div class="modal-dialog">\
				<div class="modal-content">\
					<div class="modal-header">\
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>\
						<h4 class="modal-title" id="myzui_input_title">xxx</h4>\
					</div>\
					<div class="modal-body"><input id="myzui_input_input" class="form-control"/></div>\
					<div class="modal-footer">\
						<button type="button" class="btn btn-primary" id="myzui_input_ok">确定</button>\
						<button type="button" class="btn btn-default" data-dismiss="modal" id="myzui_input_cancel">取消</button>\
					</div>\
				</div>\
			</div>\
		</div>';
		$("body").append(h);
		$('#myzui_input').modal({show:false});
		
		//bind events
		$("#myzui_input_ok").click(function(){
			var val = $("#myzui_input_input").val();
			if(val) {
				$("#myzui_input_input").val("");
				if(myzui.input_callback) {
					myzui.input_callback(val);
				}
				$('#myzui_input').modal("hide");
			} else {
				$("#myzui_input_input").focus();
			}
		});
		$("#myzui_input_cancel").click(function(){
			$("#myzui_input_input").val("");
		});
		
		//focus to input
		$('#myzui_input').on("shown.zui.modal",function(){
			$("#myzui_input_input").focus();
		});
	}
	
	if(value){
		$("#myzui_input_input").val(value);
	}else{
		$("#myzui_input_input").val('');
	};
	$("#myzui_input_input").attr("placeholder", placeholder ? placeholder : "");
	$("#myzui_input_title").html(title);
	$('#myzui_input').modal("show");
}