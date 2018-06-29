scale = function (btn, bar, title) {
	this.btn = document.getElementById(btn);
	this.bar = document.getElementById(bar);
	this.title = document.getElementById(title);
	this.step = this.bar.getElementsByTagName("DIV")[0];
	this.init();
};
scale.prototype = {
	init: function () {
		var f = this, g = document, b = window, m = Math;
		f.btn.onmousedown = function (e) {
			var x = (e || b.event).clientX;
			var l = this.offsetLeft;
			var max = f.bar.offsetWidth - this.offsetWidth;
			g.onmousemove = function (e) {
				var thisX = (e || b.event).clientX;
				var to = m.min(max, m.max(-2, l + (thisX - x)));
				f.btn.style.left = to + 'px';
				f.ondrag(m.round(m.max(0, to / max) * 100), to);
				b.getSelection ? b.getSelection().removeAllRanges() : g.selection.empty();
			};
			g.onmouseup = new Function('this.onmousemove=null');
		};
	},
	ondrag: function (pos, x) {
		this.step.style.width = Math.max(0, x) + 'px';
		this.title.innerHTML = pos / 10 + '';
	}
}
new scale('btn0', 'bar0', 'title0');
new scale('btn1', 'bar1', 'title1');
new scale('btn2', 'bar2', 'title2');
new scale('btn3', 'bar3', 'title3');
new scale('btn4', 'bar4', 'title4');
new scale('btn5', 'bar5', 'title5');
new scale('btn6', 'bar6', 'title6');
new scale('btn7', 'bar7', 'title7');
new scale('btn8', 'bar8', 'title8');