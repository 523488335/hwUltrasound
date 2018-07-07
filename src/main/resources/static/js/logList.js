$(function() {

	/**
	 *	获取病人信息
	 * */
	$.ajax({
		type:"post",
		url:"getAllPatient",
		async:true,
		dataType:"json",
		success:function (data) {
			for (let i = 0; i<data.length; i++){
				$(".palist").append("<tr><td>"+data[i].patientId+"</td><td class=''><a class='updated' data-id='"+data[i].patientId+"'>"+data[i].name+"</a></td><td>"+data[i].sex+"</td><td>"+data[i].birthday+"</td><td>"+data[i].phone+"</td><td>"+data[i].address+"</td></tr>");
			};
		}
	});
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
	 *	获取患者检查历史
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
					$(".history").append("<tr class='history-tr'><td>"+(i+1)+"</td><td>"+data[i].description+"</td><td>"+data[i].principal+"</td><td>"+data[i].history+"</td><td>"+data[i].date+"</td><td>"+data[i].source+"</td><td>"+data[i].type+"</td><td><a href='index.html?patientDataId="+data[i].patientDataId+"&patientId=" + data[i].patientId + "'>"+data[i].status+"</a></td></tr>");
				}
				$(".patient-history").css("display","block");
				console.log(data)
			}
		})
	});
	// }


	var vm = new ViewModel();
	ko.applyBindings(vm);
});



function ViewModel() {
	const self = this;
	
	self.path = ko.observable();
	
	self.submit = function() {
		$.ajax({
			url:"/info/log",
			data:{'mode':self.path()},
			type: "POST",
			async:true,
			dataType:"json",
			success: function(data) {
				alert(data)
			}
		})
	}
}
