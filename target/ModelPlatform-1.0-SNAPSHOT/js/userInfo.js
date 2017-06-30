/**
 * Created by Administrator on 2017/6/20.
 */

var uid = -1;
var serverUrl = "/model_platform/server";
var pageSize = 5;
var currentPage = 1;
var modelViewUrl = "obj.html"
var staticUrl = "http://sjtudalab.xyz:8080";
var modelNum = 0;

$(document).ready(function() {
    //countAllLoginDuringTime(0,new Date().getTime());
    var paras = window.location.search.substr(1).split("&");
    uid = paras[0];
    modelNum = getModelNumByUserId();

    var info = "<h1>" + "用户名：" + paras[1] +
        "&nbsp;&nbsp;&nbsp;&nbsp;模型数："+modelNum+
        "&nbsp;&nbsp;&nbsp;&nbsp;IP地址："+lastLoginIpByUserId(uid)+"</h1>";
    document.getElementById('user-header').innerHTML = info;

    setUserTable();
    setModelListTable();
});

function setUserTable() {
    var date = new Date();
    var d_start = new Date(date.getFullYear(), date.getMonth(), date.getDate(),0,0,0).getTime();
    var d_end = d_start+24*60*60*1000;

    var weekDataArray = new Array();
    var weekLabelArray = new Array();
    for(var k=0;k<3;k++) {
        weekDataArray[k] = new Array();
    }
    var monthDataArray = new Array();
    var monthLabelArray = new Array();
    for(k=0;k<3;k++) {
        monthDataArray[k] = new Array();
    }
    var yearDataArray = new Array();
    var yearLabelArray = new Array();
    for(k=0;k<3;k++) {
        yearDataArray[k] = new Array();
    }


    var str = "";
    for(var i=0; i<7; i++) {
        var d = new Date(d_start-24*60*60*1000*i).getFullYear()+"/"+
            (new Date(d_start-24*60*60*1000*i).getMonth()+1)+"/"+
            new Date(d_start-24*60*60*1000*i).getDate();
        var loginNum = countLoginByUserIdDuringTime(d_start-24*60*60*1000*i, d_end-24*60*60*1000*i);
        var uploadNum = countUploadModelByUserIdDuringTime(d_start-24*60*60*1000*i, d_end-24*60*60*1000*i);
        var downloadNum = countDownloadModelByUserIdDuringTime(d_start-24*60*60*1000*i, d_end-24*60*60*1000*i);
        //var deleteNum = countDeleteModelByUserIdDuringTime(d_start-24*60*60*1000*i, d_end-24*60*60*1000*i);

        var tmp_str = "<td>" + d + "</td>";
        tmp_str += "<td>" + loginNum + "</td>";
        tmp_str += "<td>" + uploadNum + "</td>";
        tmp_str += "<td>" + downloadNum + "</td>";
        //tmp_str += "<td>" + deleteNum + "</td>";

        weekLabelArray.push(d);
        weekDataArray[0].push(loginNum);
        weekDataArray[1].push(uploadNum);
        weekDataArray[2].push(downloadNum);

        str += "<tr>" + tmp_str + "</tr>";
    }
    //添加节点
    var tr = $(str);
    $("#week-tbody").append(tr);

    //画图
    //一周
    drawWeekChart(weekDataArray,weekLabelArray);
    //一月
    for(i=0; i<4; i++) {
        monthDataArray[0].push(countLoginByUserIdDuringTime(d_end-24*60*60*7000*(i+1), d_end-24*60*60*7000*i));
        monthDataArray[1].push(countUploadModelByUserIdDuringTime(d_end-24*60*60*7000*(i+1), d_end-24*60*60*7000*i));
        monthDataArray[2].push(countDownloadModelByUserIdDuringTime(d_end-24*60*60*7000*(i+1), d_end-24*60*60*7000*i));
    }
    monthLabelArray=["本周","过去第一周","过去第二周","过去第三周"];
    drawMonthChart(monthDataArray,monthLabelArray);
    //一年
    for(i=0; i<12; i++) {
        yearDataArray[0].push(countLoginByUserIdDuringTime(d_end-24*60*60*30000*(i+1), d_end-24*60*60*30000*i));
        yearDataArray[1].push(countUploadModelByUserIdDuringTime(d_end-24*60*60*30000*(i+1), d_end-24*60*60*30000*i));
        yearDataArray[2].push(countDownloadModelByUserIdDuringTime(d_end-24*60*60*30000*(i+1), d_end-24*60*60*30000*i));

        yearLabelArray.push(new Date(d_end-24*60*60*30000*i).getFullYear()+"年"+(new Date(d_end-24*60*60*30000*i).getMonth()+1)+"月");
    }
    drawYearChart(yearDataArray,yearLabelArray)
}
function setModelListTable() {
    var modelList = JSON.parse(getModelsByUserId());

    var str = "";
    for(var i = 0; i < modelList.length; i++){
        var tmp_str = "<td>"+modelList[i].id+"</td>";
        tmp_str += "<td>"+modelList[i].name+"</td>";
        tmp_str += "<td>"+"<a href='"+staticUrl+modelList[i].path+"' target='_blank' >"+modelList[i].path+"</a>"+"</td>";
        tmp_str += "<td><img src='"+staticUrl+modelList[i].thumbnail_path+"'></td>";

        str += "<tr onclick='modelOnClick("+"&quot;"+modelList[i].path+"&quot;"+")'>"+tmp_str+"</tr>";
    }

    //清空table
    var tb = document.getElementById('model-tbody');
    var rowNum=tb.rows.length;
    for (i=0;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
    //添加新节点
    var tr = $(str);
    $("#model-tbody").append(tr);
}
function drawWeekChart(dataArray,labelArray) {
    var ctx = document.getElementById("weekChart").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: labelArray,
            datasets: [{
                label: '登陆次数',
                data : dataArray[0],
                backgroundColor: [
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)'
                ],
                borderWidth: 1
            },{
                label: '上传模型次数',
                data : dataArray[1],
                backgroundColor: [
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)'
                ],
                borderWidth: 1
            },{
                label: '下载模型次数',
                data : dataArray[2],
                backgroundColor: [
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)'
                ],
                borderColor: [
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)'
                ],
                borderWidth: 1
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: '该用户一周内各类操作次数'
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
}
function drawMonthChart(dataArray,labelArray) {
    var ctx = document.getElementById("monthChart").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: labelArray,
            datasets: [{
                label: '登陆次数',
                data : dataArray[0],
                backgroundColor: [
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)'
                ],
                borderWidth: 1
            },{
                label: '上传模型次数',
                data : dataArray[1],
                backgroundColor: [
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)'
                ],
                borderWidth: 1
            },{
                label: '下载模型次数',
                data : dataArray[2],
                backgroundColor: [
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)'
                ],
                borderColor: [
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)'
                ],
                borderWidth: 1
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: '该用户一个月内各类操作次数'
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
}
function drawYearChart(dataArray,labelArray) {
    var ctx = document.getElementById("yearChart").getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: labelArray,
            datasets: [{
                label: '登陆次数',
                data : dataArray[0],
                backgroundColor: [
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(255, 206, 86, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(255, 206, 86, 1)'
                ],
                borderWidth: 1
            },{
                label: '上传模型次数',
                data : dataArray[1],
                backgroundColor: [
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)',
                    'rgba(255, 100, 123, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)',
                    'rgba(255, 100, 123, 1)'
                ],
                borderWidth: 1
            },{
                label: '下载模型次数',
                data : dataArray[2],
                backgroundColor: [
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)',
                    'rgba(106, 233, 106, 0.2)'
                ],
                borderColor: [
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)',
                    'rgba(106, 233, 106, 1)'
                ],
                borderWidth: 1
            }
            ]
        },
        options: {
            title: {
                display: true,
                text: '该用户一年内各类操作次数'
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero:true
                    }
                }]
            }
        }
    });
}

function countLoginByUserIdDuringTime(st,et) {
    var u1 = serverUrl+"/info/countLoginByUserIdDuringTime";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{user_id:uid, start_time:st,end_time:et},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}
function countUploadModelByUserIdDuringTime(st,et) {
    var u1 = serverUrl+"/info/countUploadModelByUserIdDuringTime";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{user_id:uid, start_time:st,end_time:et},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}
function countDownloadModelByUserIdDuringTime(st,et) {
    var u1 = serverUrl+"/info/countDownloadModelByUserIdDuringTime";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{user_id:uid, start_time:st,end_time:et},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}
function countDeleteModelByUserIdDuringTime(st,et) {
    var u1 = serverUrl+"/info/countDeleteModelByUserIdDuringTime";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{user_id:uid, start_time:st,end_time:et},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}

function lastLoginIpByUserId(uid) {
    var u1 = serverUrl+"/info/lastLoginIpByUserId";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{user_id:uid},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}

function getModelsByUserId() {
    var u1 = serverUrl+"/model/getListByUser?uid="+uid+"&startPage="+currentPage+"&pageSize="+pageSize;
    var result;
    $.ajax({
        type: "GET",
        url: u1,
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}
function getModelNumByUserId() {
    var u1 = serverUrl+"/model/getModelNumByUserId?id="+uid;
    var result;
    $.ajax({
        type: "GET",
        url: u1,
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}

function modelOnClick(path) {
    window.location.href = modelViewUrl+"?"+path;
}

function previousBtn() {
    if(currentPage>=2){
        currentPage -= 1;
        setModelListTable();
    }
}
function nextBtn() {
    if(currentPage<Math.ceil(modelNum/pageSize)){
        currentPage += 1;
        setModelListTable();
    }
}