$(function() {
		var data4Vue = {
			itemList: [],
			listThs: [
				{name: '编号', width: 172, thname: 'id'},
				{name: '用户名', width: 198, thname: 'username'},
				{name: '操作', width: 502, thname: 'operation'},
				{name: 'ip', width: 270, thname: 'ip'},
				{name: '创建时间', width: 415, thname: 'createtime'}
			],
			pagination: {},
			isLoading: false,
			keyword: "",
			size: 15
		};
		
		var vue = new Vue({
			el: "#app",
			data: data4Vue,
			mounted: function() {
				this.list(1);
			},
			methods: {
				list: function(start) {
					var _this = this;
					_this.isLoading = true;
					var url = "systemLogs?start=" + start + "&keyword=" + _this.keyword + "&size=" + _this.size;
					axios.get(url).then(function(res) {
						_this.pagination = res.data;
						_this.itemList = res.data.list;
						_this.isLoading = false;
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