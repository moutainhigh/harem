var checks = {};
    $(".search").click(function() {
        pageUtils.request(1, $(".pageSize").val(),{tags:$("#itemName").val()});
    });
    function initTable() {
        $('table.resourceTable').datatable({
            checkable: true,
            checksChanged: function(event) {
                var oId = -1;
                if($(".resources").length > 0) {
                    oId = parseInt($(".resources").attr("rId"));
                }
                var nResources = [];
                $(event.checks.checks).each(function() {
                    var str = this.split("_");
                    var rId = parseInt(str[1]);
                    var rName = str[2];
                    if(parseInt(str[1]) != oId) {
                        nResources.push({rId:rId,rName:rName});
                    }
                });
                $(".checked-resource").empty();
                if(nResources.length > 0) {
                    var rId = nResources[0].rId;
                    var rName = nResources[0].rName;
                    var btn = $("<button class='btn btn-info resources'></button>");
                    // check
                    btn.click(function(){
                        $("tr[data-id^='resource_"+rId+"_']").removeClass("active");
                        $(this).remove();
                    });
                    btn.attr("id","r_"+rId);
                    btn.attr("rId",rId);
                    btn.attr("rName",rName);
                    btn.text(rName);
                    $(".checked-resource").append(btn);
                }
                $("tr[data-id^='resource_"+oId+"_']").removeClass("active");
            }
        });
    }
    var resource_table = {};
    function showData(itemList) {
        $(".selectResourceTable").empty();
        var table = $("<table class='table table-striped table-hover resourceTable'/>");
        table.append("<thead><tr><th>编号</th><th>主图</th><th>标题</th><th>类别</th><th>商家名称</th><th>目的地</th><th>单价</th><th>状态</th><th>发布时间</th></tr></thead>");
        var tbody = $("<tbody/>");
        table.append(tbody);
        var i= 0 ;
        for(i in itemList) {
            var item = itemList[i];
            console.log("xxxxxxxxxxx="+JSON.stringify(item));
            resource_table[item.id] = item;
            var tr = $("<tr id='resource_"+item.id+"_"+item.name+"'></tr>");
            tr.append($("<td>"+item.id+"</td>"));
            tr.append($("<td><img src='$utilWebconfig.getTfsRootPath()"+item.imgUrl+"' width='100' height='100'></td>"));
            if(null == item.name || item.name.length==0){
                tr.append($("<td>"+ ""+"</td>"));
            }else{
                tr.append($("<td>"+ item.name.replace(/<[^<>]+?>/g,'')+"</td>"));
            }
            tr.append($("<td>"+ item.showType+"</td>"));

            if(null == item.salerName || item.salerName.length==0){
                tr.append($("<td>"+ item.salerName+"</td>"));
            }else{
                tr.append($("<td>"+ item.salerName.replace(/<[^<>]+?>/g,'')+"</td>"));
            }
            tr.append($("<td>"+item.destination+"</td>"));
            tr.append($("<td>"+ item.price+"</td>"));
            tr.append($("<td>"+item.showStatus+"</td>"));
            tr.append($("<td>"+item.pushDate+"</td>"));
            tbody.append(tr);
        }
        $(".selectResourceTable").append(table);
        // 初始化特效
        initTable();
        // 已经选择的打上标记
        $(".resources").each(function(){
            var rId = $(this).attr("rId");
            var rName = $(this).attr("rName");
            $("input[id='radio_"+rId+"']").attr("checked", "checked");
        });
    }
    // 获得最终结果
    function getItems() {
        if($(".resources").length > 0) {
            var rId = $(".resources").attr("rId");
            return resource_table[rId];
        } else {
            return null;
        }
    }