$(function() {
	var data4Vue = {
		user4Login: {name: "admin", password: "admin", code: ""},
		codeImg: "111.jpg",
		count: 0
	};
	
	var vue = new Vue({
		el: "#app",
		data: data4Vue,
		mounted: function() {
			this.getCount();
			this.getCode();
			$("#login-name").get(0).select();
		},
		methods: {
			getCode: function() {
				this.codeImg = this.timestamp("http://127.0.0.1:8181/verifyCode");
			},
			timestamp(url) {
				var getTimestamp = new Date().getTime();
				if (url.indexOf("?") > -1) {
					url = url + "&timestamp=" + getTimestamp
				} else {
					url = url + "?timestamp=" + getTimestamp
				}
				return url;
			},
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
				if (!_this.user4Login.code) {
					myzui._error1("验证码不能为空");
					return;
				}
				axios.post(url, _this.user4Login).then(function(res) {
					var data = res.data;
					var code = data.code;
					if (code == "0") {
						myzui._success1("登录成功");
						var user = data.user;
						sessionStorage.setItem("user", JSON.stringify(user));
						setTimeout(() => {
							location.href = "index";
						}, 1000);
					} else if (code == '1') {
						myzui._error1("登录失败，用户名或密码错误");
					} else if (code == '2') {
						myzui._error1("登录失败，验证码错误");
					}
				});
			}
		}
	});
});