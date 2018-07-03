$(function() {

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
