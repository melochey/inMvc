	//living comments
	 var ze = liveStartTime, Be = 0,He=0,intervalId;
	$(function(){
		var st=ck();
		if(st==0){
			intervalId=setInterval(ck,5e3);
		}
		 ColorPicker(
                 document.getElementById('slide'),
                 document.getElementById('picker'),
                 function(hex, hsv, rgb) {
                	 document.getElementById('liveComment').style.backgroundColor = hex;
         });
		 $(".bkpic").mouseover(function(){
			 $(".colorPanel").show();
		 });
		 $(".bkpic").mouseout(function(){
			 $(".colorPanel").hide();
		 });
	});
    function bt(e, n) {
        if (e - Be > 3e3) {
            Be = e;
            var r = ze;
            $.ajax({
            	url: "comment",
                method: "get",
                data: {
                    liveId: liveId,
                    start: r,
                    count: n
                },
                success:function(data){
                	var result=eval("("+data+")");
                	if (0 === result.result) {
                        var c = result.comments;
                        ze = Math.max(ze, c.end);
                        wt(c.list);
                    }
                }
            });
        }
    }
    function wt(list) {
        for(var i=0;i<list.length;i++){
        	var item='<div><div class="bubble"><div class="avatar"><img src="'+list[i].portrait+'" alt="'+list[i].name+'"></div><div class="name">'+
        	list[i].userName+'</div>'+list[i].comment+'</div></div>';
        	$("#liveComment").append(item);
        }
    }
    function xt(e) {
        e - He > 5e3 && (He = e,
        $.ajax({
        	url: "info",
            method: "get",
            data: {
                liveId: liveId,
                uId: uId
            },
            success:function(d){
            	var result=eval("("+d+")");
            	if (1 === result.live.liveStatus) {
            		var onlineCount=result.live.onlineCount;
            		var totalCount=result.live.totalCount;
            		$("#onlineUser").html(onlineCount);
            		$("#onlineTotal").html(totalCount);
                 }
            }
        })
        )
    }
    //check job start?
    function ck(){
    	var status=0;
    	$.ajax({
        	url: "info",
            method: "get",
            async:false,
            data: {
                liveId: liveId,
                uId: uId
            },
            success:function(d){
            	 var result=eval("("+d+")");
            	 if (1 === result.live.liveStatus) {
            		 status=1;
            		 if(intervalId!=undefined){
            			 clearInterval(intervalId);
            		 }
            		 var oc=result.live.onlineCount;
             		var ot=result.live.totalCount;
             		$("#onlineUser").html(oc);
             		$("#onlineTotal").html(ot);
            		 bt((new Date).getTime(), 30);//init comments list
         		     setInterval(function() {
         		        var t = (new Date).getTime();
         		        xt(t);
         		        bt(t, 20);
         		     }, 1e3);
                 }
            }
        })
        return status;
    }
    
    
    
    