/**
 * 通用方法封装处理
 * Copyright (c) 2018
 */

$(function(){
    // 复选框事件绑定
    if ($.fn.select2 !== undefined) {
        $("select.form-control:not(.noselect2)").each(function () {
            $(this).select2().on("change", function () {
                $(this).valid();
            })
        })
    }
    if ($(".i-checks").length > 0) {
        $(".i-checks").iCheck({
            checkboxClass: "icheckbox_square-green",
            radioClass: "iradio_square-green",
        })
    }
    if ($(".time").length > 0) {
        layui.use('laydate', function() {
            var laydate = layui.laydate;
            laydate.render({ elem: '#startTime', theme: 'molv' });
            laydate.render({ elem: '#endTime', theme: 'molv' });
        });
    }
});



/** 创建选项卡 */
function createMenuItem(dataUrl, menuName) {
    dataIndex = $.common.random(1,100),
        flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .J_iframe', topWindow).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.J_iframe').hide().parents('.mainContent').append(str1);

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}


//除法函数，用来得到精确的除法结果
//说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
//调用：accDiv(arg1,arg2)
//返回值：arg1除以arg2的精确结果
function accDiv(arg1,arg2){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""))
        r2=Number(arg2.toString().replace(".",""))
        return (r1/r2)*pow(10,t2-t1);
    }
}

//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg){
    return accDiv(this, arg);
}

//乘法函数，用来得到精确的乘法结果
//说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
//调用：accMul(arg1,arg2)
//返回值：arg1乘以 arg2的精确结果
function accMul(arg1,arg2)
{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{m+=s1.split(".")[1].length}catch(e){}
    try{m+=s2.split(".")[1].length}catch(e){}
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg){
    return accMul(arg, this);
}

function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
    try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""))
        r2 = Number(arg2.toString().replace(".", ""))
        return (r1 / r2) * pow(10, t2 - t1);
    }
}
//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function(arg) {
    return accDiv(this, arg);
}
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try { m += s1.split(".")[1].length } catch (e) { }
    try { m += s2.split(".")[1].length } catch (e) { }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
Number.prototype.mul = function(arg) {
    return accMul(arg, this);
}
function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
    try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2))
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m
}
Number.prototype.add = function(arg) {
    return accAdd(arg, this);
}

//排序方法
function compareInt(x, y){
    var iNum1 = parseInt(x[0]);//强制转换成int型;
    var iNum2 = parseInt(y[0]);
    if(iNum1 < iNum2){
        return -1;
    }else if(iNum1 > iNum2){
        return 1;
    }else{
        return 0;
    }
}
