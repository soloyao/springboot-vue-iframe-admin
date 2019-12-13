$(function() {
	var data4Vue = {
//			heros: [],
			itemList: [],
			listThs: [
				{name: '编号', width: 353, thname: 'id'},
				{name: '名称', width: 495, thname: 'name'},
				{name: '血量', width: 243, thname: 'hp'},
				{name: '操作', width: 567, thname: 'operate'}
			],
			hero4Add: {id: 0, name: "", hp: 0},
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
			},
			methods: {
				save: function() {
					var _this = this;
					if (!_this.hero4Add.name || _this.hero4Add.hp == 0) {
						myzui._error("必填参数不能为空");
						return;
					}
					var url = "heros";
					if (_this.hero4Add.id == 0) { //add
						axios.post(url, this.hero4Add).then(function(res) {
							_this.list(1);
							_this.hero4Add = {id: 0, name: "", hp: 0};
							_this.isEditShow = false;
						});
					} else { //update
						axios.put(url, _this.hero4Add).then(function(res) {
							_this.list(1);
							_this.hero4Add = {id: 0, name: "", hp: 0};
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
					this.hero4Add = {id: 0, name: '', hp: 0};
				},
				updateEdit: function(hero) {
					this.isEditShow = true;
					this.editTitle = "修改";
					this.hero4Add.id = hero.id;
					this.hero4Add.name = hero.name;
					this.hero4Add.hp = hero.hp;
				},
				list: function(start) {
					var _this = this;
					_this.isLoading = true;
					var url = "heros?start=" + start + "&keyword=" + _this.keyword + "&size=" + _this.size;
					axios.get(url).then(function(res) {
						_this.pagination = res.data;
						_this.itemList = res.data.list;
						_this.isLoading = false;
					});
				},
				deleteItem: function(id) {
					var _this = this;
					myzui.confirm("确认删除？", function() {
						var url = "heros/" + id;
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