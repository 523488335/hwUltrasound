# pubo

**2018-05-02** 896090c 分支
>	<u>解决视频拖动时，直接卡死的问题 </u>

>	<font color='red'>产生原因：</font> 之前为了控制左右两边视频同步播放，肯定是要监听视频播放的事件(注：我这里是有2个视频文件同步播放的，所以会有player2这个实例)
```js
	player.on("seeked",function() {
		// 直接赋值,当然我源代码中是有设立一个中间obj对象存储的，因为我还有其他的数据需要同步控制，这里意思是一样的
		player2.currentTime(this.currentTime());// 获取到当前的这个播放时间，也就是进度
	});
	// 监听上述事件，在拖动时，就一定会存在卡顿。
```
>	<font color='green'>解决办法：</font> 对 progress 进度条事件进行监听
```js
	player.on("progress",function() {
		player2.currentTime(this.currentTime());// 获取到当前的这个播放时间，也就是进度
	});
```
>   <u>解决删除图片存在的Bug,本来想删除左边视频的截图，但是却删除了右边的。</u>
>   
>   <font color='red'>产生原因：</font> 最初设计的时候，没有考虑到左右截图的问题，所以，所以的图片都放在一个imgArr数组中，根据点击的index，到数组中去imgArr.splice(index,1)。后面在布局的时候，已经将2个图片的盛放标签分开，之前就都方法right-content的div中，现在在这个div下面又分2个子容器img-content-left，img-content-right
>   
>   <font color='green'>解决办法：</font> 建立2个数组，imgLeftArr,imgRightArr,在截图时，captureImage，将left或者right这个参数传进去

```js
    if(position == "left") {
        imgLeftArr.push(obj);
    } else {
        imgRightArr.push(obj);
    }
```

>   删除时，比较关键的就是知道你点击了哪一个图片，所以，deleteObj对象在监听image的click就已经将index和left/right  2个信息保存了

```js
var index = deleteObj.index;
var deletePosition = deleteObj.isLeftOrRight;

if(deletePosition === "left") {
    $(".right-content .img-content-left").find("img")[index].remove();
    imgLeftArr.splice(index,1);
} else {
    $(".right-content .img-content-right").find("img")[index].remove();
    imgRightArr.splice(index,1);
}
```

>   <u>修复用户双击图片，放大后没任何操作，点击确定，仍然会向数组中push一张一模一样的图片</u>
>    <font color='green'>解决办法：</font> 只要有任何操作，.imageLabel-content中必定存在.imageLabel-drop-edit类

```js
    if($(".imageLabel-drop-edit").length<=0) {
        return true;
    }
    // 下面就是图片转canvas逻辑，然后canvas变成图片，再将图片push到页面和保存的数组中。
```



**2018-05-01** 7300d39 分支
>   添加截图后，对右侧缩略图二次编辑的功能，之前的下载功能不影响，删除功能目前还有一个bug，因为是left和right分开了，之前的写法是没分，所以删除imgArr容器没问题，现在分开展示，需要对imgArr中的数据进行过滤

**2018-04-28** b4782b5 分支

>	解决截图中，重复触发截图事件,其实就是click事件嵌套，事件重复注册，可以取消冒泡，或者将事件由嵌套拆称2个独立的，都能解决问题

```js
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
});

```
    

>	参考链接： [http://blog.sina.com.cn/s/blog_4ffbe80f0101e6co.html](http://blog.sina.com.cn/s/blog_4ffbe80f0101e6co.html)

**2018-04-27** d291b26 分支
>   解决左右2个视频同步控制Bug
>   
>   解决动态修改视频源src报错问题,之前的测试src都直接写死在source标签中，实际开发必定不是这样  
>   参考链接： [https://segmentfault.com/q/1010000006979553](https://segmentfault.com/q/1010000006979553)
```js
    var options = {}; // 这里就有很多扩展的属性，详细参考
    var player1 =  videojs("videoTag-left",options);
    player1.ready(function(){
        

        this.src("http://www.example.com/path/to/video.mp4");

        this.play(); // 调用这里，视频ready，就会直接播放，如果想单独通过一个按钮控制，就不加这句，将逻辑移出去

        // 我的情景是单独控制，所以注释this.play()
        handleVideoOperate(this,"videoTag-left");
    });

// 单独控制播放，我的页面上是有个2个视频的，所以下面会有player1,player2
// _player 就是另一个视频，我的有2个视频同时播放，如果你的需求只有一个，就特别简单，忽略相关的_player即可
// 参数 __this 有2个下划线，注意
function handleVideoOperate(__this,className) {

    var _this = __this;

    var _player = videojs(className);
    

    // 获取当前播放的时间和速率
    // 当改变播放时间，拖动播放轴时--同时快进和后退
    _this.on("seeked",function() {
        videoOpt.currentTime = _this.currentTime();
        _player.currentTime(videoOpt.currentTime);
    })
    _this.on("waiting",function() {
        //alert("视频正在缓冲");
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
        alert("结束");
    
        // 提示医生，切换病人信息吧，ajax
        videojs.log('视频结束');
    })
    //
    _this.on("error",function(error) {
        _this.
        console.log(error);
    })
}

```

>   
>   新增快速截取单帧视频功能-集成到暂停和播放一个按钮上，暂停就截图，与医院系统做成一致的实现
>   
>   解决快速点击截图，1秒钟点击40-50下，报错 Uncaught (in promise) DOMException: The play() request was interrupted by a call to pause(). https://goo.gl/LdLk22   当然正常的医生也不会这样操作  
>   参考链接：[https://www.cnblogs.com/zzsdream/p/6125223.html](https://www.cnblogs.com/zzsdream/p/6125223.html)
>   

**2018-04-25** 1755eb2 分支
>   实现视频的同步快进，后退，音量同步，同步播放，暂停

**2018-04-23 21:21** f9f32a8 分支

>   完成sm，md,lg 3种适配问题

**2018-04-23**  1632969 分支

>   解决bootstrap下面多列row高度不一致问题

*参考链接*：[https://blog.csdn.net/jewely/article/details/53006123](https://blog.csdn.net/jewely/article/details/53006123)

