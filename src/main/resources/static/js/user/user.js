$(function() {
	var zTreeObj;
	var setting = {
		check: {
			enable: true,
			chkStyle: "radio"
		},
		view: {
			selectedMulti: false
		},
		callback: {
			onClick: function(e, treeId, treeNode, clickFlag) {
				zTreeObj.checkNode(treeNode, !treeNode.checked, true);
			}
		}
	};
	var zTreeNodes = [];
	var data4Vue = {
		roles: [],
		users: [],
		user4Add: {id: 0, name: "", password: "", realname: "", phone: "", sex: "", roles: []},
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
			this.listRoles();
		},
		methods: {
			listRoles: function() {
				var _this = this;
				var url = "listRoles"
				axios.get(url).then(function(res) {
					_this.roles = res.data;
					zTreeNodes = res.data;
					zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
					zTreeObj.expandAll(true);
				});
			},
			save: function() {
				var _this = this;
				if (!this.user4Add.name || !this.user4Add.password || !this.user4Add.realname || !this.user4Add.phone || !this.user4Add.sex) {
					myzui._error("the param can not be empty");
					return;
				}
				if (zTreeObj.getCheckedNodes(true).length == 0) {
					myzui._error("please assign a role to the user");
					return;
				}
				var url = "users";
				_this.user4Add.roles = [];
				zTreeObj.getCheckedNodes(true).map(function(item) {
					_this.user4Add.roles.push({id: item.id});
				});
				if (_this.user4Add.id == 0) { //add
					axios.post(url, _this.user4Add).then(function(res) {
						if (res.data.code == 0) {
							_this.list(1);
							_this.user4Add = {id: 0, name: "", password: "", realname: "", phone: "", sex: "", roles: []};
							myzui._success(res.data.msg);
							_this.isEditShow = false;
						} else {
							myzui._error(res.data.msg);
						}
					});
				} else { //update
					axios.put(url, _this.user4Add).then(function(res) {
						_this.list(1);
						_this.user4Add = {id: 0, name: "", password: "", realname: "", phone: "", sex: "", roles: []};
						myzui._success(res.data);
						_this.isEditShow = false;
					});
				}
			},
			cancel: function() {
				this.isEditShow = false;
			},
			addEdit: function() {
				this.isEditShow = true;
				this.editTitle = "新增";
				this.user4Add = {id: 0, name: "", password: "", realname: "", phone: "", sex: "", roles: []};
				zTreeObj.checkNode(zTreeObj.getNodeByParam("name", "user", null), true ,false);
			},
			updateEdit: function(user) {
				this.isEditShow = true;
				this.editTitle = "修改";
				this.user4Add.id = user.id;
				this.user4Add.name = user.name;
				this.user4Add.password = user.password;
				this.user4Add.realname = user.realname;
				this.user4Add.phone = user.phone;
				this.user4Add.sex = user.sex;
				zTreeObj.checkNode(zTreeObj.getNodeByParam("id", user.roles.length > 0 ? user.roles[0].id : 0, null), true, false);
			},
			list: function(start) {
				var _this = this;
				_this.isLoading = true;
				var url = "users?start=" + start + "&keyword=" + _this.keyword + "&size=" + _this.size;
				axios.get(url).then(function(res) {
					_this.pagination = res.data;
					_this.users = res.data.list;
					_this.isLoading = false;
				});
			},
			add: function() {
				var _this = this;
				var url = "users";
				if (!this.user4Add.name || !this.user4Add.password || !this.user4Add.realname || !this.user4Add.phone || !this.user4Add.sex) {
					myzui._error("the param can not be empty");
					return;
				}
				axios.post(url, this.user4Add).then(function(res) {
					if (res.data.code == 0) {
						_this.list(1);
						_this.user4Add = {id: 0, name: "", password: "", realname: "", phone: "", sex: ""};
						myzui._success(res.data.msg);
					} else {
						myzui._error(res.data.msg);
					}
				});
			},
			deleteUser: function(id) {
				var _this = this;
				myzui.confirm("confirm delete？", function() {
					var url = "users/" + id;
					axios.delete(url).then(function(res) {
						_this.list(1);
					});
				});
			},
			edit: function(id) {
				location.href = "editUser?id=" + id;
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