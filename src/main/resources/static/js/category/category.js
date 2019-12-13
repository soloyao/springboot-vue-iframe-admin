$(function() {
		var data4Vue = {
//			categories: [],
			itemList: [],
			listThs: [
				{name: '编号', width: 249, thname: 'id'},
				{name: '名称', width: 829, thname: 'name'},
				{name: '操作', width: 580, thname: 'operate'}
			],
			category4Add: {id: 0, name: "", image: ""},
			pagination: {},
			keyword: "",
			isEditShow: false,
			isLoading: false,
			editTitle: "",
			size: 15
		};
		
		var vue = new Vue({
			el: "#app",
			data: data4Vue,
			mounted: function() {
				this.list(1);
				
				$("#lightBoxToggle").lightbox();
			},
			methods: {
				showImage: function() {
					var myLightBox = $("#lightBoxToggle").data("zui.lightbox");
					myLightBox.show("upload/" + this.category4Add.image);
				},
				deleteItemImage: function() {
					this.category4Add.image = "";
				},
				upload: function() {
					var _this = this;
					$.uploader("../upload", function(data) {
						_this.category4Add.image = data;
					}, {
						filters: {
							mime_types: [
								{title: '图片', extensions: 'jpg,png'}
							]
						},
						limitFilesCount: 1
					}, true);
				},
				save: function() {
					var _this = this;
					if (!_this.category4Add.name) {
						myzui._error("必填参数不能为空");
						return;
					}
					var url = "categories";
					if (_this.category4Add.id == 0) { //add
						axios.post(url, this.category4Add).then(function(res) {
							_this.list(1);
							_this.category4Add = {id: 0, name: "", image: ""};
							_this.isEditShow = false;
						});
					} else { //update
						axios.put(url, _this.category4Add).then(function(res) {
							_this.list(1);
							_this.category4Add = {id: 0, name: "", image: ""};
							_this.isEditShow = false;
						});
					}
				},
				cancel: function() {
					this.isEditShow = false;
				},
				addEdit: function() {
					this.isEditShow = true;
					this.editTitle = "新建";
					this.category4Add = {id: 0, name: "", image: ""};
				},
				getCheck() {
					var str = $("tbody .checked").map(function(item, ele) {
						console.log($(ele).data("id"));
						return $(ele).data("id");
					}).get().join(",");
					console.log(str);
				},
				updateEdit: function(category) {
					this.isEditShow = true;
					this.editTitle = "修改";
					this.category4Add.id = category.id;
					this.category4Add.name = category.name;
					this.category4Add.image = category.image;
				},
				list: function(start) {
					var _this = this;
					_this.isLoading = true;
					var url = "categories?start=" + start + "&keyword=" + _this.keyword + "&size=" + _this.size;
					axios.get(url).then(function(res) {
						_this.pagination = res.data;
						_this.itemList = res.data.list;
						_this.isLoading = false;
					});
				},
				deleteItem: function(id) {
					var _this = this;
					myzui.confirm("确认删除？", function() {
						var url = "categories/" + id;
						axios.delete(url).then(function(res) {
							_this.list(1);
						});
					});
				},
				reset: function() {
					var _this = this;
					$("#keyword").val("");
					_this.keyword = $("#keyword").val();
					_this.list(1);
				},
				search: function() {
					var _this = this;
					_this.keyword = $("#keyword").val();
					if (_this.keyword) {
						_this.list(1);
					}
				}
			}
		});
});