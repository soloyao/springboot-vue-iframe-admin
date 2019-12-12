$(function() {
	var data4Vue = {
		user4Login: {name: "", password: ""}
	};
	
	var vue = new Vue({
		el: "#app",
		data: data4Vue,
		mounted: function() {
			$("#login-name").get(0).select();
		},
		methods: {
			loginKeyDown: function(e) {
				if (e.keyCode == 13) {
					this.login();
				}
			},
			login: function() {
				var _this = this;
				var url = "login";
				if (!_this.user4Login.name || !_this.user4Login.password) {
					myzui._error1("name or password can not be empty");
					return;
				}
				axios.post(url, _this.user4Login).then(function(res) {
					var data = res.data;
					if (data.code == "0") {
						myzui._success1("login success");
						var user = data.user;
						sessionStorage.setItem("user", JSON.stringify(user));
						setTimeout(() => {
							location.href = "index";
						}, 1000);
					} else {
						myzui._error1("login fail,the name or the password is wrong");
					}
				});
			}
		}
	});
});