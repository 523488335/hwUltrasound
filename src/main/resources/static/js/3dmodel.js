/**
 * Created by meifan on 2018/7/7.
 */

var renderer;



function initRender() {

    renderer = new THREE.WebGLRenderer({antialias: true});

    //renderer.setClearColor(new THREE.Color(0xEEEEEE, 1.0)); //设置背景颜色

    renderer.setSize(window.innerWidth, window.innerHeight);

    document.body.appendChild(renderer.domElement);

}



var camera;



function initCamera() {

    camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 10000);

    camera.position.set(0, 0, 200);

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



function initModel() {



    //轴辅助 （每一个轴的长度）

    var object = new THREE.AxesHelper(500);

    scene.add(object);



    //创建THREE.PointCloud粒子的容器

    var geometry = new THREE.Geometry();

    //创建THREE.PointCloud纹理

    var material = new THREE.PointCloudMaterial({size:5, vertexColors:true, color:0xffffff});



    //循环将粒子的颜色和位置添加到网格当中

    for (var x = -5; x <= 5; x++) {

        for (var y = -5; y <= 5; y++) {

            var particle = new THREE.Vector3(x * 10, y * 10, 0);

            geometry.vertices.push(particle);

            //geometry.colors.push(new THREE.Color(+randomColor()));
            //设置粒子颜色
            geometry.colors.push(new THREE.Color(0xffffff));

        }

    }



    //实例化THREE.PointCloud

    var cloud = new THREE.PointCloud(geometry, material);

    scene.add(cloud);



}



//随机生成颜色

function randomColor() {

    var arrHex = ["0","1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d","e","f"],

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

    document.body.appendChild(stats.dom);

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



    };

    var datGui = new dat.GUI();

    //将设置属性添加到gui当中，gui.add(对象，属性，最小值，最大值）

}



function render() {

    renderer.render(scene, camera);

}



//窗口变动触发的函数

function onWindowResize() {

    camera.aspect = window.innerWidth / window.innerHeight;

    camera.updateProjectionMatrix();

    render();

    renderer.setSize(window.innerWidth, window.innerHeight);



}



function animate() {

    //更新控制器

    controls.update();

    render();



    //更新性能插件

    stats.update();

    requestAnimationFrame(animate);

}



function draw() {

    initRender();

    initScene();

    initCamera();

    initLight();

    initModel();

    initControls();



    animate();

    window.onresize = onWindowResize;

}