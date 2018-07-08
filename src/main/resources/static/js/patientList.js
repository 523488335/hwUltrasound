$(function() {

//	/**
//	 *	获取病人信息
//	 * */
//	$.ajax({
//		type:"post",
//		url:"getAllPatient",
//		async:true,
//		dataType:"json",
//		success:function (data) {
//			for (let i = 0; i<data.length; i++){
//				$(".palist").append("<tr><td>"+data[i].patientId+"</td><td class=''><a class='updated' data-id='"+data[i].patientId+"'>"+data[i].name+"</a></td><td>"+data[i].sex+"</td><td>"+data[i].birthday+"</td><td>"+data[i].phone+"</td><td>"+data[i].address+"</td></tr>");
//			};
//		}
//	});
	function patientList(data) {
		vm.pageIndex(data.pageIndex);
		vm.pageCount(data.pageCount);
		vm.pageSize(data.pageSize);
		vm.totalCount(data.totalCount);
		for (let i = 0; i<data.list.length; i++){
			$(".palist").append("<tr><td>"+data.list[i].patientId+"</td><td class=''><a class='updated' data.list-id='"+data.list[i].patientId+"'>"+data.list[i].name+"</a></td><td>"+data.list[i].sex+"</td><td>"+data.list[i].birthday+"</td><td>"+data.list[i].phone+"</td><td>"+data.list[i].address+"</td></tr>");
		};
	}
	/**
	 *	获取病人信息
	 * */
	$.ajax({
		type:"post",
		url:"getCurrPage",
		data:{'pageNum':1},
		async:true,
		dataType:"json",
		success:function (data) {
			patientList(data);
		}  
	});


	/**
	 * 	条件检索
	 * */
	$(".bt-search").click(function () {
		let name = vm.name();
		let sex = vm.selectedSex();
		let patientId = vm.doctor();
		// let st = vm.startTime();
		// let et = vm.endTime();
		$.ajax({
			type:"get",
			url:"getPatientByCondition",
			data:{"name":name,"sex":sex,"patintId":patientId},
			dataType:"json",
			async:true,
			success:function (data) {
				$(".palist").empty();
				$(".history").empty();
				patientList(data);
			},
		})
	})
	
	//分页
	self.last = function() {
		$.ajax({
			type:"post",
			url:"getLastPage",
			async:true,
			dataType:"json",
			success:function (data) {
				$(".palist").empty();
				$(".history").empty();
				patientList(data);
			}
		});
	}
	self.next = (function () {
		$.ajax({
			type:"post",
			url:"getNextPage",
			async:true,
			dataType:"json",
			success:function (data) {
				$(".palist").empty();
				$(".history").empty();
				patientList(data);
			}
		});
	})
	self.previous = function() {
		$.ajax({
			type:"post",
			url:"getPreviousPage",
			async:true,
			dataType:"json",
			success:function (data) {
				$(".palist").empty();
				$(".history").empty();
				patientList(data);
			}
		});
	}
	self.first = function() {
		$.ajax({
			type:"post",
			url:"getFristPage",
			async:true,
			dataType:"json",
			success:function (data) {
				$(".palist").empty();
				$(".history").empty();
				patientList(data);
			}
		});
	}
	
	/**
	 *	获取患者诊断记录
	 * */
	// function updata() {
	$("body").on("click",'.updated',function(){
		let patientId = $(this).attr("data.list-id");
		$.ajax({
			type:"get",
			url:"getHistoryPatientById",
			data:{"patientId":patientId},
			async:true,
			dataType:"json",
			success:function (data) {
				$(".history-tr").remove();
				console.log(data);
				for (var i = 0; i<data.length; i++){
					$(".history").append("<tr class='history-tr'><td>"+(i+1)+"</td><td>"+data[i].description+"</td><td>"+data[i].principal+"</td><td>"+data[i].history+"</td><td>"+data[i].date+"</td><td>"+data[i].source+"</td><td>"+data[i].type+"</td><td><a href='index.html?patientDataId="+data[i].patientDataId+"&patientId=" + data[i].patientId + "'>"+data[i].status+"</a></td><td><a href='data.html?dataPath="+data[i].dataPath+"'>查看</a></td></tr>");
				}
				$(".patient-history").css("display","block");
				console.log(data)
			}
		})
	});
	// }


	/**
	 *	获取当前登陆用户信息
	 * */
	$.ajax({
		type:"get",
		url:"getSession",
		async:true,
		dataType:"json",
		success:function(data){
			$(".panel-in").append(data[0].name);
		}
	})


	/**
	 *	注销当前用户
	 * */
	$(".panel-right-a").click(function(){
		layer.open({
			title: '警告'
			,content: '是否要注销当前用户？'
			,btn:['确定','取消']
			,yes:function(index, layero){
				$.ajax({
					type:"GET",
					url:"LogOut",
					async:true,
					success:function(){
						window.location.href = 'Login.html';
					}
				});
			}
		});
	})


	$("#startTime input").datetimepicker({
		format: "Y-m-d",
		hours24: 'true',
		step: 10,
		lang: 'ch',
		timepicker: false,    //关闭时间选项
		onShow: function (ct) {
			this.setOptions({
				maxDate: jQuery('#end').val() ? jQuery('#end').val() : false
			});
		}
	});
	$("#startTime span").click(function() {
		$("#startTime input").datetimepicker("show");
	});

	$("#endTime input").datetimepicker({
		format: "Y-m-d",
		hours24: 'true',
		step: 10,
		lang: 'ch',
		timepicker: false,    //关闭时间选项
		onShow: function (ct) {
			this.setOptions({
				maxDate: jQuery('#end').val() ? jQuery('#end').val() : false
			});
		}
	});
	$("#endTime span").click(function() {
		$("#endTime input").datetimepicker("show");
	});


	var vm = new ViewModel();
	ko.applyBindings(vm);
});



function ViewModel() {
	const self = this;
	self.name = ko.observable();// 姓名
	self.doctor = ko.observable(); // 医生
	self.startTime = ko.observable();// 开始时间
	self.endTime = ko.observable();// 开始时间
	self.selectedSex = ko.observable(); // 选中的性别
	self.keywords = ko.observable();// 选中的关键字

	self.sexList = ko.observableArray([
		{ sex: "请选择", sexCode: "0" },
		{ sex: "男", sexCode: "1" },
		{ sex: "女", sexCode: "2" }
	]); // 性别
	self.keywordsList = ko.observableArray([
		{name: "请选择",type: "0"},
		{name: "检查提示",type: "1"},
		{name: "检查所见",type: "2"},
		{name: "病人标记",type: "3"},
		{name: "临床诊断",type: "4"},
		{name: "临床科室",type: "5"},
		{name: "临床医生",type: "6"},
		{name: "住院号",type: "7"}
	]); // 关键字

	// 分页
	self.pageSize = ko.observable(10); // 每页呈现条数
	self.pageIndex = ko.observable(); // 要访问的页面
	self.pageCount = ko.observable(); // 总页数
	self.allPages = ko.observableArray([]); // 页码
	self.totalCount = ko.observable(1); // 总共多少条数据
	
	self.refresh = function() {
		// 获取所有的查询参数
		var searchParameter = "?name="+self.name()+"&sex="+self.selectedSex()+"&doctor="+self.doctor()+
			"&keyword="+self.keywords()+"&startTime="+self.startTime()+"&endTime="+self.endTime();

		if(self.pageIndex()>self.pageCount()) {
			self.pageIndex(self.pageCount());
		}
		if(self.pageIndex() < 1) {
			self.pageIndex(1);
		}
	}

	self.search = function() {
		self.pageIndex(1);
		self.refresh();
	}
	// 查看详细信息
	self.detailInfo = function() {

	}
	// 进入到诊断界面
	self.edit = function() {

	}
}
