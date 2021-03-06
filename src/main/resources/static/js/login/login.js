$(function() {
	var data4Vue = {
		user4Login: {name: "admin", password: "admin"},
		count: 0
	};
	
	var vue = new Vue({
		el: "#app",
		data: data4Vue,
		mounted: function() {
			this.getCount();
			$("#login-name").get(0).select();
		},
		methods: {
			getCount: function() {
				var url = "count";
				axios.get(url).then(function(res) {
					var data = res.data;
					if (data.code == 0) {
						vue.count = data.data;
					} else {
						vue.count = 0;
					}
				});
			},
			loginKeyDown: function(e) {
				if (e.keyCode == 13) {
					this.login();
				}
			},
			login: function() {
				var _this = this;
				var url = "login";
				if (!_this.user4Login.name || !_this.user4Login.password) {
					myzui._error1("用户名或密码不能为空");
					return;
				}
				axios.post(url, _this.user4Login).then(function(res) {
					var data = res.data;
					if (data.code == "0") {
						myzui._success1("登录成功");
						var user = data.user;
						sessionStorage.setItem("user", JSON.stringify(user));
						setTimeout(() => {
							location.href = "index";
						}, 1000);
					} else {
						myzui._error1("登录失败，用户名或密码错误");
					}
				});
			}
		}
	});
});