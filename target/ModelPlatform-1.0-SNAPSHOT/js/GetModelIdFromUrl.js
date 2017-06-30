/**
 * Created by Administrator on 2017/4/10.
 */
function getModelIdFromUrl() {
    alert(1);
    var id = window.location.search.substr(1);
    var u1 = "/model_platform/server/model/path?id="+id;
    var path;
    $.ajax({
        type: "GET",
        url: u1,
        async: false,
        success: function(data){
            path = data;
        }
    });
    return path;
}