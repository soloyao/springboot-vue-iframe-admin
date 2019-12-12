$(function() {
		var data4Vue = {
			itemList: [],
			listThs: [
				{name: 'id', width: 249, thname: 'id'},
				{name: 'name', width: 829, thname: 'name'},
				{name: 'operate', width: 580, thname: 'operate'}
			],
			hotel4Add: {id: 0, name: "", phonenumber: "", address: "", location: "", image: ""},
			pagination: {},
			keyword: "",
			isEditShow: false,
			isLoading: false,
			editTitle: "",
			size: 15,
			map: null,
			mouseTool: null,
			marker: null,
			activeItem: null
		};
		
		var vue = new Vue({
			el: "#app",
			data: data4Vue,
			mounted: function() {
				$("[data-toggle='tooltip']").tooltip();
				this.list(1);
				this.map = new AMap.Map("mapContainer", {
					zoom: 16
				});
				this.mouseTool = new AMap.MouseTool(this.map);
				
				$("#lightBoxToggle").lightbox();
			},
			methods: {
				showImage: function() {
					var myLightBox = $("#lightBoxToggle").data("zui.lightbox");
					myLightBox.show("upload/" + this.hotel4Add.image);
				},
				deleteItemImage: function() {
					this.hotel4Add.image = "";
				},
				upload: function() {
					var _this = this;
					$.uploader("../upload", function(data) {
						_this.hotel4Add.image = data;
					}, {
						filters: {
							mime_types: [
								{title: '图片', extensions: 'jpg,png'}
							]
						},
						limitFilesCount: 1
					}, true);
				},
				addLocation: function() {
					if (this.marker) {
						this.map.remove(this.marker);
						this.marker = null;
					}
					this.map.setDefaultCursor("crosshair");
					this.mouseTool.marker();
					var _this = this;
					AMap.event.addListenerOnce(this.mouseTool, 'draw', function(obj) {
						_this.mouseTool.close(false);
						_this.marker = obj.obj;
						_this.hotel4Add.location = _this.marker.getPosition().toString();
						_this.map.setDefaultCursor();
					});
				},
				save: function() {
					var _this = this;
					if (!_this.hotel4Add.name || !_this.hotel4Add.location) {
						myzui._error("the params can not be empty");
						return;
					}
					var url = "hotels";
					if (_this.hotel4Add.id == 0) { //add
						axios.post(url, this.hotel4Add).then(function(res) {
							_this.list(1);
							_this.hotel4Add = {id: 0, name: "", phonenumber: "", address: "", location: "", image: ""};
							_this.isEditShow = false;
							if (_this.marker) {
								_this.map.remove(_this.marker);
							}
						});
					} else { //update
						axios.put(url, _this.hotel4Add).then(function(res) {
							_this.list(1);
							_this.hotel4Add = {id: 0, name: "", phonenumber: "", address: "", location: "", image: ""};
							_this.isEditShow = false;
							if (_this.marker) {
								_this.map.remove(_this.marker);
							}
						});
					}
				},
				cancel: function() {
					var _this = this;
					_this.activeItem = null;
					this.isEditShow = false;
					this.mouseTool.close(true);
					this.map.setDefaultCursor();
					if (this.marker) {
						_this.map.remove(_this.marker);
					}
				},
				addEdit: function() {
					this.isEditShow = true;
					this.activeItem = null;
					this.editTitle = "新建";
					this.hotel4Add= {id: 0, name: '', phonenumber: '', address: '', location: '', image: ""};
					if (this.marker) {
						this.map.remove(this.marker);
					}
				},
				updateEdit: function(hotel) {
					var _this = this;
					_this.activeItem = hotel;
					this.isEditShow = true;
					this.editTitle = "修改";
					this.hotel4Add.id = hotel.id;
					this.hotel4Add.name = hotel.name;
					this.hotel4Add.phonenumber = hotel.phonenumber;
					this.hotel4Add.address = hotel.address;
					this.hotel4Add.location = hotel.location;
					this.hotel4Add.image = hotel.image;
					if (this.marker) {
						_this.map.remove(_this.marker);
					}
					this.marker = new AMap.Marker({
						position: hotel.location.split(",")
					});
					this.map.add(this.marker);
					this.map.setZoomAndCenter(18, hotel.location.split(","));
				},
				list: function(start) {
					var _this = this;
					_this.isLoading = true;
					var url = "hotels?start=" + start + "&keyword=" + _this.keyword + "&size=" + _this.size;
					axios.get(url).then(function(res) {
						_this.pagination = res.data;
						_this.itemList = res.data.list;
						_this.isLoading = false;
					});
				},
				deleteItem: function(item) {
					var _this = this;
					_this.cancel();
					myzui.confirm("confirm delete " + item.name + "？", function() {
						var url = "hotels/" + item.id;
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