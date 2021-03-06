var patientId = getQueryVariable("patientId");
var patientDataId = getQueryVariable("patientDataId");

// 顶部自动走的时间
(function() {
	var time = $(".time");
	setInterval(function() {
		time.text(getCurrentTime());
	}, 1000);
})();

// 删除功能，指定删除对应图片的序号
let deleteObj = {
	index: 0,
	isLeftOrRight: 'left'
}
// 在每一次保存时，将img保存到这里
let imgLeftArr = []; // 左
let imgRightArr = []; // 右

$(function() {
	// 功能面板--左侧2组元素--夏
	let btnGroup1 = $(".btn-group-1");
	// 功能面板--右侧2组元素--夏
	let btnGroup2 = $(".btn-group-2");

	// 切换按钮
	$(".xia-btn-left").click(function() {
		btnGroup1.fadeIn().show();
		btnGroup2.fadeOut().hide();

		$(".xia-btn-right").find(".circle-middle").removeClass("activeBtn");
		$(this).find(".circle-middle").addClass("activeBtn");
	});
	$(".xia-btn-right").click(function() {
		btnGroup1.fadeOut().hide();
		btnGroup2.fadeIn().show();

		$(".xia-btn-left").find(".circle-middle").removeClass("activeBtn");
		$(this).find(".circle-middle").addClass("activeBtn");

	});
	
	// 获取所有的图片列表
	$(".right-content").on("click","img",function(event) {
		var imgParentClassName = $(this).parent().attr("class");
		var isLeftOrRight = imgParentClassName.split("-")[2];
		//console.log("当前点击的图片序号", $(this).index());
		// 获取图片序号
		deleteIndex = $(this).index();
		//console.log(isLeftOrRight,50);
		deleteObj = {
			index: deleteIndex,
			isLeftOrRight: isLeftOrRight
		}
		$(this).siblings().css({
			"border": "none"
		});
		// 保证每次点击，都只有一个高亮
		$(this).parent().siblings().children("img").css({
			"border": "none"
		});
		$(this).css({
			"border": "2px solid #FF0000"
		});
	});
	// 利用事件委托
	$(".right-content").on("dblclick","img",function() {
		// 放大功能
		
		var parentNode = $(this).parent();
		
		// 获取父元素的类名，通过类名 append图片
		var parentClassName = parentNode[0].className;
		
		var $imageLabel = imageLabel({
				img: $(this)[0].src,
				only: !1,
				editPop: true,// 禁用弹窗
				close: function(t) {
					return true;
					//return t.length && alert(JSON.stringify(t)), !0
				},
				clickArea: function() {},
				edit: function(t) {},
				startArea: function() {},
				confirm: function(t) {
					// 解决用户在没有任何编辑的情况下，点击保存，也会生成一张图片的问题
					if($(".imageLabel-drop-edit").length<=0) {
						return true;
					}
					html2canvas($(".imageLabel-jisuan").get(0),{
						backgroundColor: "#FFF" ,// 设置截图后的背景
						allowTaint: true // 关键点
					}).then((canvas) => {
						
						// 确保图层不会遮住 播放按钮--本来是准备直接移除的，但是移除后，你进一步的编辑，就会报元素找不到
						$(".imageLabel-jisuan").css("display","none");
						//console.log(canvas);
						if($(".imageLabel-drop-edit").length<=0) {
							return true;
						}
						// 将canvas转变成图片，append到图片列表中
						var img = convertCanvasToImage(canvas);
						
						parentNode.append(img);
						var position = parentClassName.indexOf("left")>0?"left":"right";
						var date = new Date();
						var obj = {
							id:date.getTime(),
							imgSource: img,
							captureTime: getCurrentTime(date),
							isLeftOrRight: position
						}
						if(position === "left") {
							imgLeftArr.push(obj);
						} else {
							imgRightArr.push(obj);
						}
						//imgArr.push(obj);
						// 做一次ajax请求，将图片都push到服务器
						$.ajax({
							url:"saveProcessedImg",
							data:{'imageId':obj.id,'path':img.src,'patientDataId':getQueryVariable("patientDataId"),'left':(position == 'left' ? "true" : "false")},
							type: "POST",
							async:true,
							dataType:"json"
						})
					});
					return true;
					//return t.length && alert(JSON.stringify(t)), !0
				}
		});
	});

	// 监控数据变化
	let vm = new ViewModel();
	ko.applyBindings(vm);
	
	$('#reportSure').click(function () {
		$.ajax({
			url:"saveReport",
			data:{'patientDataId':patientDataId,'status':'操作完成','breastFinding':$('#breastFinding').val(),
				'oxterFinding':$('#oxterFinding').val(),'hint':$('#hint').val()},
			type: "POST",
			async:true,
			dataType:"json"
		})
	})
	
	/**
	 * 获取指定的URL参数值
	 * URL:http://www.quwan.com/index?name=tyler
	 * 参数：variable URL参数
	 * 调用方法:getQueryVariable("name")
	 * 返回值:tyler
	 */


	/*填充数据进模态框*/
	$.ajax({
		url:"getPatientById",
		data:{'patientId':patientId},
		type: "POST",
		async:true,
		dataType:"json",
		success: function(data) {
			vm.height(data[0].height);
			vm.weight(data[0].width);
			vm.name(data[0].name);
			vm.patientID(data[0].patientId);
			vm.sex(data[0].sex);
			vm.birthday(data[0].birthday);
			$.ajax({
				url:"getViewModel",
				data:{'patientDataId':patientDataId},
				type: "POST",
				async:true,
				dataType:"json",
				success: function(data2) {
					vm.chekDesc(data2[0].description);
					vm.chiefComplaint(data2[0].principal);
					vm.pastHistory(data2[0].history);
					vm.age(data2[0].age);
					
					vm.deviceNo(data2[0].deviceId);
					vm.breastFinding(data2[0].breastFinding);
					vm.oxterFinding(data2[0].oxterFinding);
					vm.hint(data2[0].hint);
					
					vm.source(data2[0].source)
					vm.type(data2[0].type)
//					vm.deviceNo(data2[0].deviceId);
					console.log(data2[0])
					
					if(data2[0].status == '未操作'){
						$.ajax({
							url:"updatePatientData",
							data:{'patientDataId':patientDataId,'status':'已操作'},
							type: "POST",
							async:true,
							dataType:"json"
						})
					}
				}
			})
		},
	})
	
	$.ajax({
		url:"getPatientImage",
		data:{'patientDataId':patientDataId},
		type: "POST",
		async:true,
		dataType:"json",
		success: function(data) {
			for (var i = 0; i < data.length; i++) {
				showImage(data[i].imageId,data[i].left == true ? "left" : "right", data[i].path);
				if (data[i].report == true) {
					$('#reportImgs').append('<div id="' + data[i].imageId + '" class="col-xs-12 col-sm-4 col-lg-4"><div style="float: left;"><img src="/file/downloads?path=' + data[i].path + '" class="img-responsive center-block"/></div></div>');
				}
			}
		},
	})
});

//加载3D图像

function load3dmodel() {
	$.ajax({
		type:"post",
		url:"/info/pointSet",
		async:true,
		data:{"patientDataId":patientDataId},
		dataType:"json",
		success:function (data) {
//			console.log(data);
			draw();
			var renderer;
			function initRender() {

				renderer = new THREE.WebGLRenderer({
					antialias: true
				});

				//renderer.setClearColor(new THREE.Color(0xEEEEEE, 1.0)); //设置背景颜色

				renderer.setSize(868, 374);

				$(".model3d").append(renderer.domElement);

			}

			var camera;

			function initCamera() {

				camera = new THREE.PerspectiveCamera(45, 868 / 374, 1, 10000);

				camera.position.set(0, 0, 1000);

			}

			var scene;

			function initScene() {

				scene = new THREE.Scene();

			}

			var light;

			function initLight() {

				scene.add(new THREE.AmbientLight(0x404040));

				light = new THREE.DirectionalLight(0xffffff);

				light.position.set(1, 1, 1);

				scene.add(light);

			}

			var cloud;

			function initModel() {

				//轴辅助 （每一个轴的长度）

				// var object = new THREE.AxesHelper(500);

				// scene.add(object);

				//创建THREE.PointCloud粒子的容器

				var geometry = new THREE.Geometry();

				//创建THREE.PointCloud纹理

				var material = new THREE.PointCloudMaterial({
					size: 5,
					vertexColors: true,
					color: 0xffffff
				});


				//循环将粒子的颜色和位置添加到网格当中



				// for(var x = -5; x <= 5; x++) {
                //
				// 	for(var y = -5; y <= 5; y++) {
                //
				// 		var particle = new THREE.Vector3(x * 10, y * 10, 0);
                //
				// 		geometry.vertices.push(particle);
                //
				// 		geometry.colors.push(new THREE.Color(+randomColor()));
                //
				// 	}
                //
				// }

				//实例化THREE.PointCloud

				// cloud = new THREE.PointCloud(geometry, material);
                //
				// scene.add(cloud);

			}

			//随机生成颜色

			function randomColor() {

				var arrHex = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"],

					strHex = "0x",

					index;

				for(var i = 0; i < 6; i++) {

					index = Math.round(Math.random() * 15);

					strHex += arrHex[index];

				}

				return strHex;

			}

			//初始化性能插件

			var stats;

			function initStats() {

				stats = new Stats();

				$(".model3d").append(stats.dom);

			}

			//用户交互插件 鼠标左键按住旋转，右键按住平移，滚轮缩放

			var controls;

			function initControls() {

				controls = new THREE.OrbitControls(camera, renderer.domElement);

				// 如果使用animate方法时，将此函数删除

				//controls.addEventListener( 'change', render );

				// 使动画循环使用时阻尼或自转 意思是否有惯性

				controls.enableDamping = true;

				//动态阻尼系数 就是鼠标拖拽旋转灵敏度

				//controls.dampingFactor = 0.25;

				//是否可以缩放

				controls.enableZoom = true;

				//是否自动旋转

				controls.autoRotate = false;

				//设置相机距离原点的最远距离

				controls.minDistance = 20;

				//设置相机距离原点的最远距离

				controls.maxDistance = 10000;

				//是否开启右键拖拽

				controls.enablePan = true;

			}

			//生成gui设置配置项

			var gui;

			function initGui() {

				//声明一个保存需求修改的相关数据的对象

				gui = {

					"size": 4,

					"transparent": true,

					"opacity": 1,

					"vertexColors": true,

					"color": 0xffffff,

					"sizeAttenuation": true,

					"rotateSystem": false,

					redraw: function() {

						if(cloud) {

							scene.remove(cloud);

						}

						createParticles(gui.size, gui.transparent, gui.opacity, gui.vertexColors, gui.sizeAttenuation, gui.color);

						//设置是否自动旋转

						controls.autoRotate = gui.rotateSystem;

					}

				};

				//var datGui = new dat.GUI();

				//      //将设置属性添加到gui当中，gui.add(对象，属性，最小值，最大值）gui.add(controls, 'size', 0, 10).onChange(controls.redraw);
				//
				//      datGui.add(gui, 'transparent').onChange(gui.redraw);
				//
				//      datGui.add(gui, 'opacity', 0, 1).onChange(gui.redraw);
				//
				//      datGui.add(gui, 'vertexColors').onChange(gui.redraw);
				//
				//      datGui.addColor(gui, 'color').onChange(gui.redraw);
				//
				//      datGui.add(gui, 'sizeAttenuation').onChange(gui.redraw);
				//
				//      datGui.add(gui, 'rotateSystem').onChange(gui.redraw);

				gui.redraw();

			}

			//生成粒子的方法

			function createParticles(size, transparent, opacity, vertexColors, sizeAttenuation, color) {

				//存放粒子数据的网格

				var geom = new THREE.Geometry();

				//样式化粒子的THREE.PointCloudMaterial材质

				var material = new THREE.PointCloudMaterial({

					size: size,

					transparent: transparent,

					opacity: opacity,

					vertexColors: vertexColors,

					sizeAttenuation: sizeAttenuation,

					color: color

				});

				// for (var i = 0; i < data.light; i++){			alert(data.length);
                //
				// 	var particle = new THREE.Vector3(data[i].x, data[i].y, data[i].z);
				// 	geom.vertices.push(particle);
				// 	var color = new THREE.Color(+randomColor());
				// 	color.setHSL(color.getHSL().h, color.getHSL().s, Math.random() * color.getHSL().l);
                //
				// 	geom.colors.push(color);
				// }

				// var range = 500;

				for(var i = 0; i < data.length; i++) {

					var particle = new THREE.Vector3(data[i].x, data[i].y, data[i].z);

					geom.vertices.push(particle);

					var color = new THREE.Color(+randomColor());

					//.setHSL ( h, s, l ) h — 色调值在0.0和1.0之间 s — 饱和值在0.0和1.0之间 l — 亮度值在0.0和1.0之间。 使用HSL设置颜色。

					//随机当前每个粒子的亮度

					color.setHSL(1, 1, 1);

					geom.colors.push(color);

				}

				//生成模型，添加到场景当中

				cloud = new THREE.PointCloud(geom, material);

				scene.add(cloud);

			}

			function render() {

				renderer.render(scene, camera);

			}

			//窗口变动触发的函数

			function onWindowResize() {

				camera.aspect = 868 / 374;

				camera.updateProjectionMatrix();

				render();

				renderer.setSize(868, 374);

			}

			function animate() {

				//更新控制器

				controls.update();

				render();

				//更新性能插件

				//stats.update();

				requestAnimationFrame(animate);

			}

			function draw() {

				initRender();

				initScene();

				initCamera();

				initLight();

				//initModel();

				initControls();

				//initStats();

				initGui();

				animate();

				window.onresize = onWindowResize;

			}
		}
	});
}

// 双向数据绑定
function ViewModel() {
	const self = this;

	self.checkTitlte = ko.observable("C5-2Fs"); // 顶部标题
	self.checkPosition = ko.observable("腹部"); // 检查位置
	self.chekDesc = ko.observable(); // 检查描述
	self.chiefComplaint = ko.observable(); // 主诉
	self.pastHistory = ko.observable(); // 既往史
	self.height = ko.observable(); // 身高
	self.weight = ko.observable(); // 体重奶
	self.isEnable = ko.observable(false);
	self.patientID = ko.observable();
	self.birthday = ko.observable();
	self.source = ko.observable();
	
	//报表字段
	self.name = ko.observable();
	self.sex = ko.observable();
	self.age = ko.observable();
	self.source = ko.observable();
	self.deviceNo = ko.observable();
	self.type = ko.observable();
	self.breastFinding = ko.observable();
	self.oxterFinding = ko.observable();
	self.hint = ko.observable();
	
	
	// 该方法应该再没有用到---别删除
	self.save = function() {
		if($(".leftImage").attr("src") == "") {
			console.log("左边没有图像");
			return;
		}
		if($(".rightImage").attr("src") == "") {
			console.log("右边没有图像");
			return;
		}
		//拿到图片盒子
		html2canvas($(".image-online")[0]).then((canvas) => {
			//Canvas2Image.saveAsPNG(canvas,500,500);
			//console.log(canvas);
			var image = new Image();
			image.src = canvas.toDataURL("image/png", 1.0);
			// 将这个图片数据传出去
			let obj = {
				imgSource: image,
				currentTime: getCurrentTime()
			}
			imgArr.push(obj);
			console.log(imgArr[0].imgSource.src);
			//document.write(imgArr[0].imgSource.src)
			/*
			 参考链接地址： https://www.cnblogs.com/GoTing/p/6206466.html
			 使用canvas2image.js 会出现图片没有后缀名的情况
			 * */

			//console.log(convertCanvasToImage(canvas));
			// 将canvas转变成图片
			var img = convertCanvasToImage(canvas);
			//document.body.appendChild(convertCanvasToImage(canvas));
			$(".right-content")[0].append(img);

			// 获取所有的图片列表
			$(".right-content img").click(function(event) {
				console.log("当前点击的图片序号", $(this).index());
				// 获取图片序号
				deleteIndex = $(this).index();
				$(this).siblings().css({
					"border": "none"
				});
				$(this).css({
					"border": "4px solid blue"
				});
			});
		});
	}
	/*废弃上面的 save*/
	
	
	self.deleteImg = function() {
		if(isExitImg()) {
			//console.log(deleteObj,204);
			var index = deleteObj.index;
			var deletePosition = deleteObj.isLeftOrRight;
			
			if(deletePosition === "left") {
				$(".right-content .img-content-left").find("img")[index].remove();
				obj = imgLeftArr.splice(index,1)[0];
			} else {
				$(".right-content .img-content-right").find("img")[index].remove();
				obj = imgRightArr.splice(index,1)[0];
			}
			console.log(obj)
			$.ajax({
				url:"deleteImageById",
				data:{'imageId':obj.id},
				type: "POST",
				async:true,
				dataType:"json"
			})
		} else {
			console.log("右侧没有缩略图");
		}
	}
	/*下载*/
	self.download = function() {
		/*
		 	下载的思路：
		 	1、在上面self.save 保存的时候，我们就需要将图片的信息 （保存的时间，图片数据）--push到全局imgArr
		 	2、在点击 self.download时,操作的并不是缩略图的图片，而是imgArr中保存的
		 	3、右侧缩略图是调用self.save方法，每张图片都会对应一个时间，如果连续点击相同图片下载，本地文件名是相同的。
		 * */
		var _fixType = function(type) {
			type = type.toLowerCase().replace(/jpg/i, 'jpeg');
			var r = type.match(/png|jpeg|bmp|gif/)[0];
			return 'image/' + r;
		};
		
		var index = deleteObj.index;
		var deletePosition = deleteObj.isLeftOrRight;
		let img = "";
		let imgData = "";
		if(deletePosition === "left") {
			img = imgLeftArr[index];
			imgData = img.imgSource.src.replace(_fixType("png"), 'image/octet-stream');
		} else {
			img = imgRightArr[index];
			imgData = img.imgSource.src.replace(_fixType("png"), 'image/octet-stream');
		}
		
		var saveFile = function(data, filename) {
			var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
			save_link.href = data;
			save_link.download = filename;

			var event = document.createEvent('MouseEvents');
			event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
			save_link.dispatchEvent(event);
		};
		//  img.captureTime --是截屏时间或者截屏后修改的标注时间
		var filename = 'scann-' + img.captureTime + '.' + "png";

		// 下载图片
		saveFile(imgData, filename);

	}
	self.cancel = function() {
		layer.open({
			title: "确认",
			content: "确定要取消检查吗？",
			btn: ["确认", "取消"],
			yes: function(index) {
				// 做ajax请求，保存数据

				// layer.close(index);
			},
			btn2: function() {
				//alert(false);
			},
			btnAlign: "c",
		});
	}
	// 滚动到顶部
	self.scrollToTop = function() {
		let obj = getImage(deleteObj);
		$.ajax({
			url:"rmReport",
			data:{'id':obj.id},
			type: "POST",
			async:true,
			dataType:"json"
		})
		if($('#reportImgs').find('#' + obj.id)){
			$('#reportImgs').find('#' + obj.id).remove();
		}
	}
	// 滚动到底部
	self.scrollToBottom = function() {
		let obj = getImage(deleteObj);
		$.ajax({
			url:"addReport",
			data:{'id':obj.id},
			type: "POST",
			async:true,
			dataType:"json"
		})
		if ($('#reportImgs').find('#' + obj.id).length > 0) {
			alert("重复添加")
			return;
		}
		$('#reportImgs').append('<div id="' + obj.id + '" class="col-xs-12 col-sm-4 col-lg-4"><div style="float: left;"><img src="' + obj.imgSource.src + '" class="img-responsive center-block"/></div></div>');
	}
}

function getImage(deleteObj) {
	if(isExitImg()) {
		//console.log(deleteObj,204);
		var index = deleteObj.index;
		var deletePosition = deleteObj.isLeftOrRight;
		
		if(deletePosition === "left") {
			obj = imgLeftArr[index];
		} else {
			obj = imgRightArr[index];
		}
		return obj;
	} else {
		console.log("右侧没有缩略图");
		return null;
	}
}
/*将canvas转为图片*/
function convertCanvasToImage(canvas) {
	var image = new Image();
	image.src = canvas.toDataURL("image/png", 1.0);
	return image;
}

function isExitImg() {
	let imgContent = $(".right-content");
	// 判断缩略图容器中是否有图片
	if(imgContent.has("img").length === 0) {
		console.log("右侧没有缩略图");
		return false;
	}
	return true;
}

function getCurrentTime() {
	let now = new Date();
	let year = now.getFullYear();
	let month = now.getMonth() + 1;
	month = month < 10 ? ("0" + month) : month;
	let day = now.getDate();
	day = day < 10 ? ("0" + day) : day;
	let hour = now.getHours();
	hour = hour < 10 ? ("0" + hour) : hour;
	let mimutes = now.getMinutes();
	mimutes = mimutes < 10 ? ("0" + mimutes) : mimutes;
	let seconds = now.getSeconds();
	seconds = seconds < 10 ? ("0" + seconds) : seconds;
	let downloadTime = year + "/" + month + "/" + day + " " + hour + "." + mimutes + "." + seconds;
	return downloadTime;
}

function getTime(date) {
	let now = date;
	let year = now.getFullYear();
	let month = now.getMonth() + 1;
	month = month < 10 ? ("0" + month) : month;
	let day = now.getDate();
	day = day < 10 ? ("0" + day) : day;
	let hour = now.getHours();
	hour = hour < 10 ? ("0" + hour) : hour;
	let mimutes = now.getMinutes();
	mimutes = mimutes < 10 ? ("0" + mimutes) : mimutes;
	let seconds = now.getSeconds();
	seconds = seconds < 10 ? ("0" + seconds) : seconds;
	let downloadTime = year + "/" + month + "/" + day + " " + hour + "." + mimutes + "." + seconds;
	return downloadTime;
}

var options = {
	language: 'zh-CN', // 默认是 en
	
	controls: true,
	muted: true, // 静音
	control: {
		/* CurrentTimeDisplay: false,
		 TimeDivider: false,
		 DurationDisplay: false*/
		/*captionsButton: false,
		playbackRateMenuButton: false*/
	},
	ControlBar: {
		
	},
	//poster: "https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=404a1782eb1190ef1efb94dffe1a9df7/3ac79f3df8dcd1007fde3f4e7e8b4710b9122f1b.jpg",
	preload: "auto",
	
	playbackRates:[0.5,1,1.5,1.75,2], // 控制播放速度，视频中会出一个控件
};


// 同时控制视频的速率和当前播发到哪一个时间点
var videoOpt = {
	currentTime: '',
	volume: '',// 音量
	playbackRate: 1,// 视频播放速度
	aspectRatio: "4:3"
	
}


/**
 * @param {videolEle} video元素 -类名  
 * 
 * 
 * */
function handleVideoOperate(__this,className) {

	var _this = __this;

	var _player = videojs(className);
	

	// 获取当前播放的时间和速率
	// 当改变播放时间，拖动播放轴时--同时快进和后退
	_this.on("progress",function() {
		videoOpt.currentTime = _this.currentTime();
		_player.currentTime(videoOpt.currentTime);
	})
	_this.on("waiting",function() {
		//layer.msg("视频正在缓冲！！！");
	});
	// 同步声音，这里可能没有必要，影像应该是没有声音的
	_this.on("volumechange",function() {
		
		
		videoOpt.volume = _this.volume();
		//console.log(videoOpt.volume,typeof videoOpt.volume)
		_player.volume(videoOpt.volume);
	});
	// 同步控制播放速度
	_this.on("ratechange",function() {
		videoOpt.playbackRate = _this.playbackRate();
		_player.playbackRate(videoOpt.playbackRate);
		
	});
	// 做事件监听
	_this.on('ended',function() {
		$(".playBtn").attr("disabled",true);
		layer.msg("结束");
	
		// 提示医生，切换病人信息吧，ajax
		layer.msg("结束");
	})
	//
	_this.on("error",function(error) {
		//_this.
		console.log(error);
	})

}
// 到时候 ajax请求，改变地址就可以

//var src = "http://zixuncr.cn/video/v2.mp4";
var src = "./video/oceans.mp4";
var player1 = videojs("videoTag-left",options);

player1.ready(function() {
	
	this.src(src);
	handleVideoOperate(this,"videoTag-right");


});
// 卸载播放事件
/*$(".video-js").on("click",function(event) {
	


})*/

var player2 = videojs("videoTag-right",options);
player2.ready(function() {
	this.src(src);
	handleVideoOperate(this,"videoTag-left");
});


var lastTime = Date.now();

// 播放操作
$(".playBtn").click(function() {
	var currentTime = Date.now();
	var protectTime = 100; // 设置保护性延时，单位毫秒
	var btnText = $(this).text();
	
	// 左右截屏按钮
	var btnCaptureLeft = $(".capture-left-image");
	var	btnCaptureRight = $(".capture-right-image");
	/**
	 *  防止用户点击过快，出现报错 1秒钟点40-50下
	 * 报错信息 Uncaught (in promise) DOMException: The play() request was interrupted by a call to pause(). https://goo.gl/LdLk22
	 * @param {https://www.cnblogs.com/zzsdream/p/6125223.html}
	 * */
	if((currentTime-lastTime) < protectTime) {
		return; // 直接退出
	}
	
	if(btnText === "播放") {
		$(this).text("暂停");
		player1.play();
		player2.play();

		btnCaptureLeft.attr('disabled',true);
		btnCaptureRight.attr('disabled',true);
		
	} else if(btnText === "暂停") {
		$(this).text("播放");
		player1.pause();
		player2.pause();
		
		btnCaptureLeft.attr('disabled',false);
		btnCaptureRight.attr('disabled',false);
	}
});

$(".capture-left-image").bind("click",function() {
	if($(this).attr("disabled")) {
		return;
	}
	captureImage($("#videoTag-left_html5_api").get(0),"left");
	
});
		
$(".capture-right-image").bind("click",function() {
	if($(this).attr("disabled")) {
		return;
	}
	captureImage($("#videoTag-right_html5_api").get(0),"right");
	$.ajax({
		
	})
});


// 截屏
function captureImage(video,position) {
	
	var canvas = document.createElement("canvas");
	console.log(canvas)
	var ctx = canvas.getContext("2d");
	canvas.width = 800;
	canvas.height = 800;
	
    var image = new Image();
    
    image.setAttribute("crossOrigin",'Anonymous')
    ctx.drawImage(video, 0, 0,800,800);
    image.src =  canvas.toDataURL("image/png", 1.0);
    
    /**
     * imgSource 图片源
     * captureTime 截图时间
     * isLeftOrRight 是截左边的图还是右边的
     * */
    var date = new Date();
    var obj = {
    	id:date.getTime(),	
		imgSource: image,
		captureTime: getCurrentTime(date),
		isLeftOrRight: position
	}
    
	if(position == "left") {
		imgLeftArr.push(obj);
	} else {
		imgRightArr.push(obj);
	}
	
	if(position === "left") {
    	$(".right-content .img-content-left").append(image).css("border-bottom","2px dashed blue");
    }
    if(position === "right") {
    	$(".right-content .img-content-right").append(image);
    }
    
    $.ajax({
		url:"saveImg",
		data:{'imageId':obj.id,'path':image.src,'patientDataId':getQueryVariable("patientDataId"),'left':(position == 'left' ? "true" : "false")},
		type: "POST",
		async:true,
		dataType:"json"
	})
}

//显示图片
function showImage(id,position,src) {
    var image = new Image();
    
    image.setAttribute("crossOrigin",'Anonymous')
    image.src = '/file/downloads?path=' + src;
  
    /**
     * imgSource 图片源
     * captureTime 截图时间
     * isLeftOrRight 是截左边的图还是右边的
     * */
    var obj = {
    	id:id,
		imgSource: image,
		captureTime: getCurrentTime(id),
		isLeftOrRight: position
	}
    
	if(position == "left") {
		imgLeftArr.push(obj);
	} else {
		imgRightArr.push(obj);
	}
	
	if(position === "left") {
    	$(".right-content .img-content-left").append(image).css("border-bottom","2px dashed blue");
    }
    if(position === "right") {
    	$(".right-content .img-content-right").append(image);
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

