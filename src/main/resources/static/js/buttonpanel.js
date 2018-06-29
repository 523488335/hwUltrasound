$(function() {
			let btn3Group1 = $(".btn3-group-1");
			let btn3Group2 = $(".btn3-group-2");
			let btn3Group3 = $(".btn3-group-3");
			
			// 切换按钮
			$(".shift-btn3 .left").click(function() {
				btn3Group1.show();
				btn3Group2.hide();
				btn3Group3.hide();
				$(".shift-btn .right").removeClass("activeBtn");
				$(".shift-btn .mid").removeClass("activeBtn");
				$(this).addClass("activeBtn");
			});
			
			$(".shift-btn .mid").click(function() {
				btn3Group1.hide();
				btn3Group2.show();
				btn3Group3.hide();
				$(".shift-btn .right").removeClass("activeBtn");
				$(".shift-btn .left").removeClass("activeBtn");
				$(this).addClass("activeBtn");
			});
				
			$(".shift-btn .right").click(function() {
				btn3Group1.hide();
				btn3Group2.hide();
				btn3Group3.show();
				$(".shift-btn .left").removeClass("activeBtn");
				$(".shift-btn .mid").removeClass("activeBtn");
				$(this).addClass("activeBtn");
			});
});
