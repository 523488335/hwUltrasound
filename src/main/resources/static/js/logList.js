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
	$.ajax({
		type:"post",
		url:"/info/pointSet",
		async:true,
		data:{'path':'F:/3d_cloud^5.xyz'},
		dataType:"json",
		success:function (data) {
			console.log(data)
		}
	});
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
