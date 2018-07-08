

$(function() {
	$.ajax({
		url:"/info/data",
		data:{'dataPath':getQueryVariable("dataPath")},
		type: "POST",
		async:true,
		dataType:"json",
		success: function(data) {
			let path = getQueryVariable("dataPath")
			for (let i = 0; i<data.length; i++){
				$(".palist").append("<tr><td>"+data[i]+"</td><td class=''><a href='/file/open?path=" + path + "/" + data[i]+"'>浏览</a></td><td class=''><a href='/file/downloads?path=" + path + "/" + data[i]+"'>下载</a></td></tr>");
			};
		}
	})
	
	var vm = new ViewModel();
	ko.applyBindings(vm);
});



function ViewModel() {
	const self = this;

	
	self.command = ko.observable();
	
	self.submit = function() {
		$.ajax({
			url:"/socket/send",
			data:{'mode':self.command()},
			type: "POST",
			async:true,
			dataType:"json",
			success: function(data) {
				alert(data)
			}
		})
	}
}
function getQueryVariable(variable)
{
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i=0;i<vars.length;i++) {
		var pair = vars[i].split("=");
		if(pair[0] == variable){return pair[1];}
	}
	return(false);
}
