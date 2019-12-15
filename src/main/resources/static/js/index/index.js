$(function() {
	var user = JSON.parse(window.sessionStorage.getItem("user"));
	
	var icon = ["icon-dashboard", "icon-book", "icon-cogs", "icon-tasks", "icon-th", "icon-envelope", "icon-user"]
	
	var data = {
		active: null, //用来确认当前展示的功能页面
		childrens: [], //所有子菜单
		name: user.name,
		user: user,
		isFullScreen: false,
		showNav: "block",
		items: [] //左边导航处所有的菜单
	};
	
	var vue = new Vue({
		el: "#app",
		data: data,
		mounted: function() {
			this.initNav();
			$("[data-toggle='tooltip']").tooltip();
			timeInit();
		},
		methods: {
			logout: function() {
				myzui.confirm("确认注销？", function() {
					var url = "logout";
					sessionStorage.clear();
					axios.get(url).then(function() {
						location.reload();
					});
				});
			},
			personalInfo: function() {
				var _this = this;
				$("#infoModal").modal("show");
			},
			initNav: function() {
				var _this = this;
				var url ="permissionsByUser";
				axios.get(url).then(function(res) {
					var data = res.data;
					_this.items = data;
					var i = 0;
					_this.items.map(function(item) {
						item.icon = icon[i++];
						/*item.children.map(function(it) {
							it.icon = icon[i++];
						});*/
					});
//					_this.childrens.push(_this.items[0].children[0]);
//					_this.active = _this.items[0].children[0];
				});
			},
			toggleNav: function() {
				this.showNav = this.showNav == "block" ? "none" : "block";
			},
			refreshNav: function() {
				if (typeof this.active != "undefined") {
					$("iframe[src='" + this.active.url + "']").attr("src", this.active.url).ready();
				}
			},
			fullScreen: function() {
				if (!this.isFullScreen) {
					this.isFullScreen = true;
					var elem = document.body;
				    if (elem.webkitRequestFullScreen) {
				        elem.webkitRequestFullScreen();
				    } else if (elem.mozRequestFullScreen) {
				        elem.mozRequestFullScreen();
				    } else if (elem.requestFullScreen) {
				        elem.requestFullscreen();
				    } else {
				    	this.isFullScreen = false;
				    	myzui._error("浏览器不支持全屏，请勿使用ie浏览器并升级浏览器为最新版本");
				    }
				} else {
					this.isFullScreen = false;
				    var elem = document;
				    if (elem.webkitCancelFullScreen) {
				        elem.webkitCancelFullScreen();
				    } else if (elem.mozCancelFullScreen) {
				        elem.mozCancelFullScreen();
				    } else if (elem.cancelFullScreen) {
				        elem.cancelFullScreen();
				    } else if (elem.exitFullscreen) {
				        elem.exitFullscreen();
				    } else {
				    	myzui._error("浏览器不支持全屏，请勿使用ie浏览器并升级浏览器为最新版本");
				    }
				}
			},
			deleteTabs: function(children) {
				var _this = this;
				var arr = [];
				for (var i = 0; i < this.childrens.length; i++) {
					if (_this.childrens[i] == children) {
						_this.childrens.splice(i, 1, null);
						break;
					}
				}
				for (var i = 0; i < this.childrens.length; i++) {
					if (_this.childrens[i]) {
						arr.push(_this.childrens[i]);
					}
				}
				if (children == _this.active) {
					_this.active = arr[arr.length - 1];
				}
			},
			tabsClick: function(children) {
				this.active = children;
				$("#tabsHome").removeClass("activeTabs");
				$("#ifrHome").removeClass("active");
				$("#ifrHome").addClass("ifr-hide");
			},
			tabsClickHome: function() {
				var home = {
					id: null,
					name: "home",
					url: "home"
				};
				this.active = home;
				$("#tabsHome").addClass("activeTabs");
				$("#ifrHome").addClass("active");
				$("#ifrHome").removeClass("ifr-hide");
			},
			childrenClick: function(e, children) {
				var flag = true;
				this.childrens.map(function(item) {
					if (item == children) {
						flag = false;
					}
				});
				if (flag) {
					this.childrens.push(children);
				}
				this.active = children;
				$("#tabsHome").removeClass("activeTabs");
				$("#ifrHome").removeClass("active");
				$("#ifrHome").addClass("ifr-hide");
				/*var el = e.target;
				$(".panel").find("li").removeClass("children");
				$(".panel").find("a").removeClass("bg");
				$(el).parent("li").addClass("children");
				$(el).addClass("bg");*/
			},
			parentClick: function(e) {
				var el = e.target;
				$(".panel").find("h4").removeClass("parent");
				$(el).parent("a").parent("h4").addClass("parent");
			},
		}
	});
	
});

function timeInit() {
	var Flipper = (function () {
	    function Flipper(node, currentTime, nextTime) {
	        this.isFlipping = false;
	        this.duration = 600;
	        this.flipNode = node;
	        this.frontNode = node.querySelector(".front");
	        this.backNode = node.querySelector(".back");
	        this.setFrontTime(currentTime);
	        this.setBackTime(nextTime);
	    }
	    Flipper.prototype.setFrontTime = function (time) {
	        this.frontNode.dataset.number = time;
	    };
	    Flipper.prototype.setBackTime = function (time) {
	        this.backNode.dataset.number = time;
	    };
	    Flipper.prototype.flipDown = function (currentTime, nextTime) {
	        var _this = this;
	        if (this.isFlipping) {
	            return false;
	        }
	        this.isFlipping = true;
	        this.setFrontTime(currentTime);
	        this.setBackTime(nextTime);
	        this.flipNode.classList.add("running");
	        setTimeout(function () {
	            _this.flipNode.classList.remove("running");
	            _this.isFlipping = false;
	            _this.setFrontTime(nextTime);
	        }, this.duration);
	    };
	    return Flipper;
	}());
	var getTimeFromDate = function (date) {
	    return date
	        .toTimeString()
	        .slice(0, 8)
	        .split(":")
	        .join("");
	};
	var flips = document.querySelectorAll(".flip");
	var now = new Date();
	var nowTimeStr = getTimeFromDate(new Date(now.getTime() - 1000));
	var nextTimeStr = getTimeFromDate(now);
	var flippers = Array.from(flips).map(function (flip, i) { return new Flipper(flip, nowTimeStr[i], nextTimeStr[i]); });
	setInterval(function () {
	    var now = new Date();
	    var nowTimeStr = getTimeFromDate(new Date(now.getTime() - 1000));
	    var nextTimeStr = getTimeFromDate(now);
	    for (var i = 0; i < flippers.length; i++) {
	        if (nowTimeStr[i] === nextTimeStr[i]) {
	            continue;
	        }
	        flippers[i].flipDown(nowTimeStr[i], nextTimeStr[i]);
	    }
	}, 1000);
}