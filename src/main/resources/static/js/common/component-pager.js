/**
 * vue组件-分页条
 * @author zengmengyao(343722243@qq.com)
 * @date 2019-07-05
 */
Vue.component('component-pager', {
	data: function() {
		return {
			pageNum: this.pageNum,
			hasPreviousPage: this.hasPreviousPage,
			navigatepageNums: this.navigatepageNums,
			hasNextPage: this.hasNextPage,
			pages: this.pages,
			prePage: this.prePage,
			nextPage: this.nextPage,
			choosePage: 1,
			total: this.total,
			startRow: this.startRow,
			endRow: this.endRow,
			pageSize: this.pageSize
		}
	},
	props: ['pageNum', 'hasPreviousPage', 'navigatepageNums', 'hasNextPage', 'pages', 'prePage', 'nextPage', 'total', 'startRow', 'endRow', 'pageSize', 'size'],
	methods: {
		jump: function(page) {
			var _this = this;
			if ('first' == page && 1 != _this.pageNum) { //如果点击首页按钮并且当前不为首页
				_this.$emit("list", 1);
				_this.choosePage = 1;
			} else if ('pre' == page && _this.hasPreviousPage ) { //如果点击上一页按钮并且存在上一页
				_this.$emit("list", _this.prePage);
				_this.choosePage = _this.prePage;
			} else if ('next' == page && _this.hasNextPage) { //如果点击下一页按钮并且存在下一页
				_this.$emit("list", _this.nextPage);
				_this.choosePage = _this.nextPage;
			} else if ('last' == page && _this.pageNum != _this.pages) { //如果点击末页按钮并且当前页不为末页
				_this.$emit("list", _this.pages);
				_this.choosePage = _this.pages;
			}
		},
		jumpByNumber: function(start) {
			this.choosePage = start;
			var _this = this;
			if (start != _this.pageNum) {
				_this.$emit("list", start);
			}
		},
		jumpByGoTo: function() {
			if (this.choosePage > this.pages) {
				this.$emit("list", this.pages);
				this.choosePage = this.pages;
			} else if (this.choosePage < 0) {
				this.$emit("list", 1);
				this.choosePage = 1;
			} else {
				this.$emit("list", this.choosePage);
			}
		},
		changePageSize: function(newPageSize) {
			if (this.pageSize == newPageSize) {
				return;
			}
			this.$parent.size = newPageSize;
			this.$emit("list", this.pageNum);
			var _this = this;
			setTimeout(function() {
				_this.choosePage = _this.$parent.pagination.pageNum;
			}, 100);
		}
	},
	template: "<div id='component-pager'>" +
			"<ul class='pager' style='position:absolute;left:15px;'>" +
			"<li :class='{disabled:pageNum==1}'><a href='javascript:void(0);' @click='jump(\"first\")'><i class='icon icon-step-backward'></i></a></li>" +
			"<li :class='{disabled:!hasPreviousPage}'><a href='javascript:void(0);' @click='jump(\"pre\")'>«</a></li>" +
			"<li :class='{active:pageNum==i}' v-for='i in navigatepageNums'>" +
			"<a href='javascript:void(0);' @click='jumpByNumber(i)'>{{i}}</a>" +
			"</li>" +
			"<li :class='{disabled:!hasNextPage}'><a href='javascript:void(0);' @click='jump(\"next\")'>»</a></li>" +
			"<li :class='{disabled:pageNum==pages}'><a href='javascript:void(0);' @click='jump(\"last\")'><i class='icon icon-step-forward'></i></a></li>" +
			"<li>" +
			"<div class='input-group pager-goto'>" +
			"<input v-model='choosePage' type='number' min='1' :max='pages' placeholder='1' class='form-control pager-goto-input' style='width:70px;'>" +
			"<span class='input-group-btn'>" +
			"<button class='btn pager-goto-btn' type='button' @click='jumpByGoTo'>跳转</button>" +
			"</span>" +
			"</div>" +
			"</li>" +
			"<li><div class='pager-label'>第<strong>{{pageNum}}</strong>/<strong>{{pages}}</strong>页</div></li>" +
			"<li><div class='pager-label'>第<strong>{{startRow}}</strong>&nbsp;~&nbsp;<strong>{{endRow}}</strong>条</div></li>" +
			"<li><div class='pager-label'>共<strong>{{total}}</strong>条记录</div></li>" +
			"<li><div class='btn-group dropup pager-size-menu dropdown'>" +
			"<button class='btn dropdown-toggle' data-toggle='dropdown'>每页<strong>{{pageSize}}</strong>条</button>" +
			"<ul class='dropdown-menu'>" +
			"<li :class='{active:pageSize==15}' @click='changePageSize(15)'><a href='javascript:void(0)'>15</a></li>" +
			"<li :class='{active:pageSize==20}' @click='changePageSize(20)'><a href='javascript:void(0)'>20</a></li>" +
			"<li :class='{active:pageSize==30}' @click='changePageSize(30)'><a href='javascript:void(0)'>30</a></li>" +
			"<li :class='{active:pageSize==50}' @click='changePageSize(50)'><a href='javascript:void(0)'>50</a></li>" +
			"<li :class='{active:pageSize==100}' @click='changePageSize(100)'><a href='javascript:void(0)'>100</a></li>" +
			/*"<li :class='{active:pageSize==100}' @click='changePageSize(\"all\")'><a href='javascript:void(0)'>all</a></li>" +*/
			"</ul>" +
			"</div></li>" +
			"</ul>" +
			"</div>"
});