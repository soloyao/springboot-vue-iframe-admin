$(function() {
	var data4Vue = {
		active: null,
		parentPermissions: [],
		parentPermission: {},
//		permissions: [],
		itemList: [],
		listThs: [
			{name: '编号', width: 94, thname: 'id'},
			{name: '名称', width: 310, thname: 'name'},
			{name: '描述', width: 311, thname: 'desc'},
			{name: 'url', width: 382, thname: 'url'},
			{name: '操作', width: 284, thname: 'operate'}
		],
		permissionParent4Add: {id: 0, name: "", desc: "", pid: 0, url: "", pid: 0},
		permission4Add: {id: 0, name: "", desc: "", url: "", pid: 0},
		pagination: {},
		keyword: "",
		isEditShow: false,
		isLoading: false,
		editTitle: "",
		parentEditTitle: "",
		pid: 0,
		size: 15
	};
	
	var vue = new Vue({
		el: "#app",
		data: data4Vue,
		mounted: function() {
			this.listParentPermission();
			//this.list(1);
			$("[data-toggle='tooltip']").tooltip();
		},
		methods: {
			saveParent() {
				var _this = this;
				if (!_this.permissionParent4Add.name || !_this.permissionParent4Add.desc) {
					myzui._error("必填参数不能为空");
					return;
				}
				var url = "permissions";
				if (_this.permissionParent4Add.id == 0) { //add
					axios.post(url, this.permissionParent4Add).then(function(res) {
						if (res.data.code == 0) {
							_this.listParentPermission();
							_this.permissionParent4Add = {id: 0, name: "", desc: "", pid: 0, url: "", pid: 0};
							myzui._success(res.data.msg);
							$("#parentEditModal").modal("hide");
						} else {
							myzui._error(res.data.msg);
						}
					});
				} else { //update
					axios.put(url, this.permissionParent4Add).then(function(res) {
						_this.listParentPermission();
						_this.permissionParent4Add = {id: 0, name: "", desc: "", pid: 0, url: "", pid: 0};
						myzui._success(res.data.msg);
						$("#parentEditModal").modal("hide");
					});
				}
			},
			save: function() {
				var _this = this;
				if (!_this.permission4Add.name || !_this.permission4Add.desc || !_this.permission4Add.url) {
					myzui._error("必填参数不能为空");
					return;
				}
				var url = "permissions";
				this.permission4Add.pid = _this.pid;
				if (_this.permission4Add.id == 0) { //add
					axios.post(url, this.permission4Add).then(function(res) {
						if (res.data.code == 0) {
							_this.list(1);
							_this.permission4Add = {id: 0, name: "", desc: "", url: "", pid: 0};
							myzui._success(res.data.msg);
							_this.isEditShow = false;
						} else {
							myzui._error(res.data.msg);
						}
					});
				} else { //update
					axios.put(url, this.permission4Add).then(function(res) {
						_this.list(1);
						_this.permission4Add = {id: 0, name: "", desc: "", url: "", pid: 0};
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
				this.editTitle = "新增子菜单";
				this.permission4Add = {id: 0, name: "", desc: "", url: "", pid: 0};
			},
			addEditParent: function() {
				$("#parentEditModal").modal({
					show: true
				});
				this.parentEditTitle = "新增父菜单";
				this.permissionParent4Add = {id: 0, name: "", desc: "", pid: 0, url: "", pid: 0};
			},
			updateEditParent: function() {
				this.permissionParent4Add.id = this.parentPermission.id;
				this.permissionParent4Add.name = this.parentPermission.name;
				this.permissionParent4Add.desc = this.parentPermission.desc;
				$("#parentEditModal").modal({
					show: true
				});
				this.parentEditTitle = "修改父菜单";
			},
			updateEdit: function(permission) {
				this.isEditShow = true;
				this.editTitle = "修改子菜单";
				this.permission4Add.id = permission.id;
				this.permission4Add.name = permission.name;
				this.permission4Add.desc = permission.desc;
				this.permission4Add.url = permission.url;
				this.permission4Add.pid = permission.pid;
			},
			listParentPermission: function() {
				var url = "parentPermissions";
				var _this = this;
				axios.get(url).then(function(res) {
					_this.parentPermissions = res.data;
					_this.pid = _this.parentPermissions[0].id;
					_this.parentPermission = _this.parentPermissions[0];
					_this.active = _this.parentPermissions[0];
					_this.list(1);
				});
			},
			parentClick: function(p, $index) {
//				this.active = $index;
				this.active = p;
				this.parentPermission = p;
				this.pid = p.id;
				this.list(1);
			},
			list: function(start) {
				var _this = this;
				_this.isLoading = true;
				var url = "permissions?start=" + start + "&keyword=" + _this.keyword + "&pid=" + _this.pid + "&size=" + _this.size;
				axios.get(url).then(function(res) {
					_this.pagination = res.data;
					_this.itemList = res.data.list;
					_this.isLoading = false;
				});
			},
			deletePermissionParent: function() {
				var id = this.parentPermission.id;
				var _this = this;
				myzui.confirm("确认删除?", function() {
					var url = "permissions/" + id;
					axios.delete(url).then(function(res) {
						_this.listParentPermission();
					});
				});
			},
			deleteItem: function(id) {
				var _this = this;
				myzui.confirm("确认删除?", function() {
					var url = "permissions/" + id;
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