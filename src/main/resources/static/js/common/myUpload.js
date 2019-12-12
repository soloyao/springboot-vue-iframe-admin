;(function($){
	$(function(){
		$('body').append('\
			<link rel="stylesheet" href="./static/css/common/zui.uploader.css"/>\
			<script type="text/javascript" src="./static/js/common/zui.uploader.js"></script>\
			<div class="modal fade" id="my_uploader" style="z-index:1998">\
			    <div class="modal-dialog">\
			        <div class="modal-content">\
			            <div class="my-modal-body">\
			            </div>\
			        </div>\
			    </div>\
			</div>\
		')
	})
	$.uploader = function(url, callback, option, autoHide, uploadcallback){
		var option = option || {}
		option.url = url
		option.chunk_size = 0
		// console.log(option)
		var h = '\
			<div id="myUploader" class="uploader" style="margin-bottom: 0;">\
			    <div class="uploader-message text-center">\
			        <div class="content"></div>\
			        <button type="button" class="close">×</button>\
			    </div>\
			    <div class="uploader-files file-list file-list-lg" data-drag-placeholder="请拖拽文件到此处" style="height: 266px;overflow-y: auto;"></div>\
			    <div class="uploader-actions">\
			        <div class="uploader-status pull-right text-muted"></div>\
			        <button type="button" class="btn btn-link uploader-btn-browse"><i class="icon icon-plus"></i> 选择文件</button>\
			        '+ (option.autoUpload ? '' : '<button type="button" class="btn btn-link uploader-btn-start"><i class="icon icon-cloud-upload"></i> 开始上传</button>') +'\
			    </div>\
			</div>\
		'
		$('#my_uploader').find('.my-modal-body').html(h)
		$('#myUploader').uploader(option);
		$('#myUploader').uploader().on('onFileUploaded', function(file, responseObject, msg) {
			var _data
			try{
				_data = JSON.parse(msg.response);
        	} catch(err) {
				_data = msg.response
		    }
		    callback && callback(_data)
		});
		if(autoHide){
			$('#myUploader').uploader().on('onUploadComplete', function(file, responseObject, msg) {
				$('#my_uploader').modal('hide');
			});
		}

		if(uploadcallback){
			$('.uploader-btn-start').click(function(){
				var files = $('#myUploader').data('zui.uploader').getFiles()
				uploadcallback(files)
				$('#my_uploader').modal('hide');
				return false
			})
		}

		$('#my_uploader').modal('show');
	}
})(jQuery);


