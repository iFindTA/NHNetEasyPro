/**
 * core
 * @authors sjd@gongshidai.com
 * @date    2015-07-24 09:15:48
 */
/**
* 构造html
* @params {data} object 内容对象
*/
function build(data) {
	var $el = document.getElementById('builder');
	$el.innerHTML = doT.template(window.template)(data);
}
/**
* 替换图片
* @params {string} s 源文本
* @params {array} o 图片集合
* @params {string} d 默认图片地址
* @params {boolean} b 是否是有图模式
*/
function replaceImage(s, o, d, b) {
	for(var i in o) {
		var oi = o[i];
		s = s.replace(oi.ref, '<img src="' + d + '" id="delayLoadImageFuckuman'+i+'" '+ (!b ? '': 'nopic="1"')+' onclick="imageClicked(this, ' + i + ')" alt="' + oi.alt + '">');
	}
	return s;
}
/**
* 动态加载模板
* @params {string} url 模板地址
*/
function loadScript(url, cb) {
	var script = document.createElement('script');
	script.src = url;
	script.type = 'text/javascript';
	(document.head || document.documentElement).appendChild(script);
	script.onload = function() {
		cb();
	}
	
}
/**
* 点击图片
* @params {object} e 图片对象
* @params {int} idx 图片索引
*/
function imageClicked(e, idx) {
	var data = {index: idx, type: 'img'};
	if(e.getAttribute('nopic')) {
		data.loadImg = 1;
	}
	window.WebViewJavascriptBridge.send(data, function(response) {
        if(response.src)
            e.src = response.src;
		e.removeAttribute('nopic');
	})
}
/**
* 阅读相关文章
*/
function getRelateDetail(id) {
	window.WebViewJavascriptBridge.send({id: id, type: 'relate'});
}
