/**
 * vue组件-数据表格
 * @author zengmengyao(343722243@qq.com)
 * @date 2019-12-06
 */
Vue.component("component-table", {
	data: function() {
		return {
			list: this.list,
			ths: this.ths,
			checkboxAllFlag: false
		}
	},
	props: ['ths', 'list'],
	methods: {
		update(item) {
			this.$emit("update-item", item);
		},
		deleteItem(item) {
			this.$emit("delete-item", item.id);
		},
		checkboxAll() {
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
		checkbox(e) {
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
		}
	},
	template: "<div id='component-table' style='height:100%;'>" +
			"<div style='height:38px;'>" +
			"<table class='table table-hover table-striped' style='margin:0px;'>" +
			"<thead>" +
			"<tr>" +
			"<th><div @click='checkboxAll' class='checkbox-primary checkbox-parent' style='width:16px;'><label></label></div></th>" +
			"<th v-for='listTh in ths' :width='listTh.width'>{{listTh.name}}</th>" +
			"</tr>" +
			"</thead>" +
			"</table>" +
			"</div>" +
			"<div v-if='list && list.length > 0' style='height:calc(100% - 38px);overflow:auto;'>" +
			"<table class='table table-hover table-striped'>" +
			"<tbody>" +
			"<tr v-for='item in list'>" +
			"<td><div @click='checkbox' class='checkbox-primary checkbox-children' style='width:16px;' :data-id='item.id'><label></label></div></td>" +
			"<template v-for='th in ths'>" +
			"<td v-if='item[th.thname]' :width='th[\"width\"]'>{{item[th['thname']]}}</td>" +
			"<td v-if='!item[th.thname] && th.thname != \"operate\"'>{{item[th['thname']]}}</td>" +
			"<td v-if='!item[th.thname] && th.thname == \"operate\"' :width='th[\"width\"]'>" +
			"<button class='btn btn-xs btn-primary' @click='update(item)'><i class='icon icon-pencil'></i></button>" +
			"<button class='btn btn-xs btn-danger' style='margin-left:5px;' @click='deleteItem(item)'><i class='icon icon-trash'></i></button>" +
			"</td>" +
			"</template>" +
			"</tr>" +
			"</tbody>" +
			"</table>" +
			"</div>" +
			"<div v-else style='height:calc(100% - 38px);overflow:auto;'>" +
			"<div style='color:#999;padding:15px;line-height:100px;'>暂无数据</div>" +
			"</div>" +
			"</div>"
})