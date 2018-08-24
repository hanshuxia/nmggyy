 var oTab=document.getElementById("cen_right");

    var aH3=oTab.getElementsByTagName("h3");

    var aDiv=oTab.getElementsByClassName("di");

    for(var i=0;i<aH3.length;i++)

    {

        aH3[i].index=i;

        aH3[i].onclick=function()

        {

            for(var i=0;i<aH3.length;i++)

            {

                aH3[i].className="color_bg";
                // $(this).addClass("active").siblings().removeClass(""active"")
                aDiv[i].style.display="none";

            }

            this.className="active";

            aDiv[this.index].style.display="inline-block";

            $(".title").css("display","none");

            $(".placeholder").css("display","inline");

           $(".city-select .clearfix a").removeClass("active");

            var y=[];

            y=$(".city-select .clearfix a")



            for(var b=0;b<y.length;b++){



                if(y[b].getAttribute("class")=='active'){

                    y[b].className="";

                    console.log(y[b]);

                }

            }

            $(".select-item").html("");





            $("#qy").html("");

            $("#sj").html("");

            var map = new BMap.Map("l-map");

            var point = new BMap.Point(116.403963,39.915119);

            function myFun(result){

		var cityName = result.name;

		map.setCenter(cityName);

            }

            var myCity = new BMap.LocalCity();

            myCity.get(myFun);

            var navigationControl = new BMap.NavigationControl({

                // 靠左上角位置

                anchor: BMAP_ANCHOR_TOP_LEFT,

                // LARGE类型

                type: BMAP_NAVIGATION_CONTROL_LARGE,

                // 启用显示定位

                enableGeolocation: true

              });

            map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用

            map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

              map.addControl(navigationControl);

            map.centerAndZoom(point, 12);

//            var infoWindow = new BMap.InfoWindow(str);  // 创建信息窗口对象

//            map.openInfoWindow(infoWindow,point); //开启信息窗口

            

        }

    }

    $(function(){

        $('#dj01').removeAttr('onclick');//去掉a标签中的onclick事件

    });