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
	
	var zTreeObjBatch;
	var settingBatch = {
		check: {
			enable: true,
			chkStyle: "radio"
		},
		view: {
			selectedMulti: false
		},
		callback: {
			onClick: function(e, treeId, treeNode, clickFlag) {
				zTreeObjBatch.checkNode(treeNode, !treeNode.checked, true);
			}
		}
	};
	var zTreeNodesBatch = [];
	
	var data4Vue = {
		roles: [],
		users: [],
		btnPermissions: [],
		user4Add: {id: 0, name: "", password: "", realname: "", phone: "", sex: "", roles: []},
		pagination: {},
		keyword: "",
		isEditShow: false,
		isLoading: false,
		editTitle: "",
		size: 15,
		checkboxAllFlag: false
	};
	
	var vue = new Vue({
		el: "#app",
		data: data4Vue,
		mounted: function() {
			this.list(1);
			this.listRoles();
			this.initNav();
		},
		methods: {
			initNav: function() {
				var _this = this;
				var url ="permissionsByUser";
				axios.get(url).then(function(res) {
					var data = res.data;
					if (data.pers == null && data.btns == null) {
						return;
					}
					_this.btnPermissions = data.btns;
				});
			},
			getCheck() {
				var str = $("tbody .checked").map(function(item, ele) {
					console.log($(ele).data("id"));
					return $(ele).data("id");
				}).get().join(",");
				console.log(str);
			},
			checkboxAll: function() {
				if (!this.checkboxAllFlag) {
					$(".checkbox-parent").addClass("checked");
					$(".checkbox-children").addClass("checked");
					this.checkboxAllFlag = true;
				} else {
					$(".checkbox-parent").removeClass("checked");
					$(".checkbox-children").removeClass("checked");
					this.checkboxAllFlag = false;
				}
			},
			checkbox: function(e) {
				var el = e.target;
				$(el).parent(".checkbox-primary").toggleClass("checked");
				var allFlag = true;
				$(".checkbox-children").map(function(item, ele) {
					if (!$(ele).hasClass("checked")) {
						allFlag = false;
					}
				});
				if (allFlag) {
					$(".checkbox-parent").addClass("checked");
				} else {
					$(".checkbox-parent").removeClass("checked");
				}
			},
			listRoles: function() {
				var _this = this;
				var url = "listRoles"
				axios.get(url).then(function(res) {
					_this.roles = res.data;
					zTreeNodesBatch = res.data;
					zTreeNodes = res.data;
					zTreeObjBatch = $.fn.zTree.init($("#treeBatch"), settingBatch, zTreeNodesBatch);
					zTreeObjBatch.checkNode(zTreeObjBatch.getNodeByParam("name", "用户", null), true ,false);
					zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
					zTreeObjBatch.expandAll(true);
					zTreeObj.expandAll(true);
				});
			},
			saveBatch() {
				var _this = this;
				var nodes = zTreeObjBatch.transformToArray(zTreeObjBatch.getNodes());
				var arr = [];
				for (var i = 0, l = nodes.length; i < l; i++) {
					if(nodes[i].checked && nodes[i].id != "pid"){
						arr.push(nodes[i].id);
					}
				}
				var roleIds = arr.join(",");
				var userIds = $("tbody .checked").map(function(item, ele) {
					return $(ele).data("id");
				}).get().join(",");
				if (!userIds || !roleIds) {
					myzui._error("所选用户或角色不能为空");
					return;
				}
				axios.post("usersBatch", {userIds: userIds, roleIds: roleIds}).then(function(res) {
					myzui._success("角色分配成功");
					zTreeObjBatch.checkNode(zTreeObjBatch.getNodeByParam("name", "用户", null), true ,false);
					_this.list(1);
					_this.checkboxAllFlag = true;
					_this.checkboxAll();
				});
			},
			save: function() {
				var _this = this;
				if (!this.user4Add.name || !this.user4Add.password || !this.user4Add.realname || !this.user4Add.phone || !this.user4Add.sex) {
					myzui._error("必填参数不能为空");
					return;
				}
				if (zTreeObj.getCheckedNodes(true).length == 0) {
					myzui._error("请给用户分配一个角色");
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
				zTreeObj.checkNode(zTreeObj.getNodeByParam("name", "用户", null), true ,false);
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
					myzui._error("必填参数不能为空");
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
				myzui.confirm("确认删除？", function() {
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