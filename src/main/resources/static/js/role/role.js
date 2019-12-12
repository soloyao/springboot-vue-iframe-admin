$(function() {
	var zTreeObj;
	var setting = {
		check: {
			enable: true,
			chkStyle: "checkbox"
		},
		view: {
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid",
				rootPId: 0
			},
			key: {
				url: "xUrl"
			}
		},
		callback: {
			onClick: function(e, treeId, treeNode, clickFlag) {
				zTreeObj.checkNode(treeNode, !treeNode.checked, true);
			}
		}
	};
	var zTreeNodes;
	var data4Vue = {
		permissions: [],
		roles: [],
		role4Add: {id: 0, name: "", desc: "", permissions: []},
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
			this.listPermissions();
		},
		methods: {
			listPermissions: function() {
				var _this = this;
				var url ="listPermissions";
				axios.get(url).then(function(res) {
					_this.permissions = res.data;
					zTreeNodes = res.data;
					zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
					zTreeObj.expandAll(true);
				});
			},
			save: function() {
				var _this = this;
				if (!_this.role4Add.name || !_this.role4Add.desc) {
					myzui._error("the params can not be empty");
					return;
				}
				var url = "roles";
				_this.role4Add.permissions = [];
				zTreeObj.getCheckedNodes(true).map(function(item) {
					_this.role4Add.permissions.push({id: item.id});
				});
				if (_this.role4Add.id == 0) { //add
					axios.post(url, _this.role4Add).then(function(res) {
						if (res.data.code == 0) {
							_this.list(1);
							_this.role4Add = {id: 0, name: "", desc: "", permissions: []};
							myzui._success(res.data.msg);
							_this.isEditShow = false;
						} else {
							myzui._error(res.data.msg);
						}
					});
				} else { //update
					axios.put(url, _this.role4Add).then(function(res) {
						_this.list(1);
						_this.role4Add = {id: 0, name: "", desc: "", permissions: []};
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
				this.role4Add = {id: 0, name: "", desc: "", permissions: []};
				zTreeObj.checkAllNodes(false);
			},
			updateEdit: function(role) {
				var _this = this;
				this.isEditShow = true;
				this.editTitle = "修改";
				this.role4Add.id = role.id;
				this.role4Add.name = role.name;
				this.role4Add.desc = role.desc;
				zTreeObj.checkAllNodes(false);
				role.permissions.map(function(item) {
					zTreeObj.checkNode(zTreeObj.getNodeByParam("id", item.id, null), true, false);
				});
			},
			list: function(start) {
				var _this = this;
				_this.isLoading = true;
				var url = "roles?start=" + start + "&keyword=" + _this.keyword + "&size=" + _this.size;
				axios.get(url).then(function(res) {
					_this.pagination = res.data;
					_this.roles = res.data.list;
					_this.isLoading = false;
				});
			},
			add: function() {
				var _this = this;
				var url = "roles";
				if (!this.role4Add.name || !this.role4Add.desc) {
					myzui._error("the param can not be empty");
					return;
				}
				axios.post(url, this.role4Add).then(function(res) {
					if (res.data.code == 0) {
						_this.list(1);
						_this.role4Add = {id: 0, name: "", desc: ""};
						myzui._success(res.data.msg);
					} else {
						myzui._error(res.data.msg);
					}
				});
			},
			deleteRole: function(id) {
				var _this = this;
				myzui.confirm("confirm delete？", function() {
					var url = "roles/" + id;
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