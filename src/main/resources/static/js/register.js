$(function() {

	var vm = new ViewModel();
	ko.applyBindings(vm);
});



function ViewModel() {
	const self = this;

	self.idNumber = ko.observable();
	self.name = ko.observable();
	self.selectedIdentity = ko.observable();
	self.phoneNumber = ko.observable();
	self.email = ko.observable();
	self.identityList = ko.observableArray([
		{ identity: "请选择"},
		{ identity: "医生"},
		{ identity: "管理员"},
		{ identity: "超级管理员"}
	]); // 性别
	self.submit = function() {
		//提交用户表单
		alert(self.selectedIdentity());
	}
}
