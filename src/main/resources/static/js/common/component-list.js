/**
 * vue组件-列表（地图数据维护使用）
 * @author zengmengyao(343722243@qq.com)
 * @date 2019-12-13
 */
Vue.component("component-list", {
	data: function() {
		return {
			
		}
	},
	props: [],
	methods: {
		update(item) {
			
		}
	},
	template: "<div class='col-md-3 content'>" +
			"<div class='panel' id='listPanel' style='border:none;'>" +
			"<div class='panel-body' id='listPanelBody'>" +
			"<div class='list list-condensed'>" +
			"<div class='items items-hover'>" +
			"<div v-if='itemList && itemList.length != 0' v-for='item in itemList' class='item' :class='{activeItem:item==activeItem}' @click='update(item)'>" +
			"<div class='item-heading'>" +
			"<div class='pull-right'><i class='icon-trash' data-toggle='tooltip' data-placement='bottom' title='删除' @click.stop='deleteItem(item)'>删除</i></div>" +
			"<h4><a href='javascript:void(0)'>{{item.name}}</h4>" +
			"</div>" +
			"<hr>" +
			"<div class='item-content'></div>" +
			"</div>" +
			"</div>" +
			"</div>" +
			"</div>" +
			"</div>" +
			"</div>"
})