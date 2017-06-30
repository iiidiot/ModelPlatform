/**
 * Created by Administrator on 2017/6/20.
 */

var singleUserInfoUrl = "userInfo.html";
var serverUrl = "/model_platform/server";
var pageSize = 5;
var currentRow = 0;
var userNum = -1;
var modelNum = -1;
var uploadNum = -1;
var downloadNum = -1;

$(document).ready(function(){
    $.ajaxSetup({async: false});

});
function initMainPage() {
    userNum = countAllUsers();
    modelNum = countAllModels();
    uploadNum = countAllUploadModelDuringTime(0,new Date().getTime());
    downloadNum = countAllDownloadModelDuringTime(0,new Date().getTime());

    var info =  "<h3>"+
        "总注册用户数:"+userNum+
        " &nbsp;&nbsp;&nbsp;&nbsp;总模型数:"+modelNum+
        " &nbsp;&nbsp;&nbsp;&nbsp;总上传模型次数:"+uploadNum+
        " &nbsp;&nbsp;&nbsp;&nbsp;总下载模型次数:"+downloadNum+
        "</h3>";
    document.getElementById('general-info').innerHTML = info;

    setUserTable(currentRow,pageSize);

    //画图
    var date = new Date();
    var d_start = new Date(date.getFullYear(), date.getMonth(), date.getDate(),0,0,0);
    var d_end = new Date(d_start.getTime()+24*60*60*1000);
    var month = date.getMonth()+1;
    var day = date.getDate();

    //一周
    var weekCtx = document.getElementById("weekChart").getContext('2d');
    var weekChartData  = [countAllLoginDuringTime(d_start.getTime(),d_end.getTime()),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*1000,d_start.getTime()),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*2000,d_start.getTime()-24*60*60*1000),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*3000,d_start.getTime()-24*60*60*2000),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*4000,d_start.getTime()-24*60*60*3000),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*5000,d_start.getTime()-24*60*60*4000),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*6000,d_start.getTime()-24*60*60*5000)
    ];
    var weekChart = new Chart(weekCtx, {
        type: 'line',
        data: {

            labels: [month+"/"+day,month+"/"+(day-1),month+"/"+(day-2),month+"/"+(day-3),month+"/"+(day-4),month+"/"+(day-5),month+"/"+(day-6)],
            datasets: [{
                data : weekChartData,
                borderColor:"rgba(255, 206, 86, 1)",
                backgroundColor: 'rgba(255, 206, 86, 0.2)',
                lineTension:0.23
            }]
        },
        options: {
            title: {
                display: true,
                text: '一周内总登陆次数'
            },
            legend: {
                display: false
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
    //一月
    var monthCtx = document.getElementById("monthChart").getContext('2d');
    var monthChartData  = [countAllLoginDuringTime(d_start.getTime()-24*60*60*6000,d_end.getTime()),//本周
        countAllLoginDuringTime(d_start.getTime()-24*60*60*13000,d_start.getTime()-24*60*60*6000),//过去第一周
        countAllLoginDuringTime(d_start.getTime()-24*60*60*20000,d_start.getTime()-24*60*60*13000),//过去第二周
        countAllLoginDuringTime(d_start.getTime()-24*60*60*27000,d_start.getTime()-24*60*60*20000)//过去第三周
    ];
    var monthChart = new Chart(monthCtx, {
        type: 'line',
        data: {

            labels: ["本周","过去第一周","过去第二周","过去第三周"],
            datasets: [{
                data : monthChartData,
                borderColor:"rgba(255, 206, 86, 1)",
                backgroundColor: 'rgba(255, 206, 86, 0.2)',
                lineTension:0.23
            }]
        },
        options: {
            title: {
                display: true,
                text: '一月内总登陆次数'
            },
            legend: {
                display: false
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
    //一年
    var yearCtx = document.getElementById("yearChart").getContext('2d');
    var yearChartData  = [countAllLoginDuringTime(d_start.getTime()-24*60*60*30000,d_end.getTime()),//本月
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*2,d_start.getTime()-24*60*60*30000),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*3,d_start.getTime()-24*60*60*30000*2),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*4,d_start.getTime()-24*60*60*30000*3),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*5,d_start.getTime()-24*60*60*30000*4),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*6,d_start.getTime()-24*60*60*30000*5),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*7,d_start.getTime()-24*60*60*30000*6),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*8,d_start.getTime()-24*60*60*30000*7),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*9,d_start.getTime()-24*60*60*30000*8),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*10,d_start.getTime()-24*60*60*30000*9),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*11,d_start.getTime()-24*60*60*30000*10),
        countAllLoginDuringTime(d_start.getTime()-24*60*60*30000*12,d_start.getTime()-24*60*60*30000*11)
    ];
    var yearChart = new Chart(yearCtx, {
        type: 'line',
        data: {

            labels: [
                date.getFullYear()+"年"+month+"月",
                new Date(date.getTime()-24*60*60*30000).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*2).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*2).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*3).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*3).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*4).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*4).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*5).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*5).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*6).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*6).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*7).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*7).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*8).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*8).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*9).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*9).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*10).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*10).getMonth()+1)+"月",
                new Date(date.getTime()-24*60*60*30000*11).getFullYear()+"年"+(new Date(date.getTime()-24*60*60*30000*11).getMonth()+1)+"月"
            ],
            datasets: [{
                data : yearChartData,
                borderColor:"rgba(255, 206, 86, 1)",
                backgroundColor: 'rgba(255, 206, 86, 0.2)',
                lineTension:0.23
            }]
        },
        options: {
            title: {
                display: true,
                text: '一年内总登陆次数'
            },
            legend: {
                display: false
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
function setUserTable(startRow, pageSize) {
    var userList = JSON.parse(getAllUsers(startRow,pageSize));
    var str = "";
    for(var i = 0; i < userList.length; i++){
        var num = getModelNumByUserId(userList[i].id);
        var tmp_str = "<td>"+userList[i].id+"</td>";
        tmp_str += "<td>"+userList[i].username+"</td>";
        tmp_str += "<td>"+num+"</td>";

        var ip = lastLoginIpByUserId(userList[i].id);
        tmp_str += "<td>"+ip+"</td>";

        str += "<tr onclick='rowOnClick("+userList[i].id+","+"&quot;"+userList[i].username+"&quot;"+")'>"+tmp_str+"</tr>";
    }

    //清空table
    var tb = document.getElementById('all-user-tbody');
    var rowNum=tb.rows.length;
    for (i=0;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
    //添加新节点
    var tr = $(str);
    $("#all-user-tbody").append(tr);
}

function countAllLoginDuringTime(st, et) {
    var u1 = serverUrl+"/info/countAllLoginDuringTime";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{start_time:st,end_time:et},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}
function countAllUploadModelDuringTime(st, et) {
    var u1 = serverUrl+"/info/countAllUploadModelDuringTime";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{start_time:st,end_time:et},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}
function countAllDownloadModelDuringTime(st, et) {
    var u1 = serverUrl+"/info/countAllDownloadModelDuringTime";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{start_time:st,end_time:et},
        async: false,
        success: function(data){
            result = data;
        }
    });
    return result;
}

function countAllUsers() {
    var u1 = serverUrl+"/user/countAllUsers";
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
function countAllModels() {
    var u1 = serverUrl+"/model/countAllModels";
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

function getAllUsers(startRow, pageSize) {
    var u1 = serverUrl+"/user/getAllUsers?startRow="+startRow+"&pageSize="+pageSize;
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

function getModelNumByUserId(uid) {
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

function previousBtn() {
    if(currentRow>=pageSize){
        currentRow -= pageSize;
        setUserTable(currentRow,pageSize);
    }
}
function nextBtn() {
    if(currentRow<userNum-pageSize){
        currentRow += pageSize;
        setUserTable(currentRow,pageSize);
    }
}
function rowOnClick(id,username) {
    window.open(singleUserInfoUrl+"?"+id+"&"+username);
}
function loginOnClick() {
    //alert($("#adminName").val());
    //alert($("#passowrd").val());
    var u1 = serverUrl+"/user/admin";
    var result;
    $.ajax({
        type: "POST",
        url: u1,
        data:{adminName:$("#adminName").val(),password:$("#password").val()},
        async: false,
        success: function(data){
            if(data==0){
                alert("用户名或密码错误");
            }
            else {
                result = data;
            }
        }
    });

    if(result==1) {
        $("#mainPage").load("adminMain.html", function (responseTxt, statusTxt, xhr) {
            if (statusTxt == "success") {
                initMainPage();
            }
            if (statusTxt == "error")
                alert("Error: " + xhr.status + ": " + xhr.statusText);
        });
    }
}