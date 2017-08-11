<#--
  自定义的分页指令 (powered by zhumg)
   属性：
   pageNo      当前页号(int类型)
   pageSize    每页要显示的记录数(int类型)
   toURL       点击分页标签时要跳转到的目标URL(string类型)
   recordCount 总记录数(int类型)
 -->
<#macro page_helper pageNo=1 pageSize=2 recordCount=2>

<div class="dataTables_paginate paging_simple_numbers" id="datatable-editable_paginate">
    <ul class="pagination">
        <li class="paginate_button previous disabled" tabindex="0"
            id="datatable-editable_previous"><a href="#">上一页</a></li>

        <li class="paginate_button active" ><a href="#">1</a></li>
        <li class="paginate_button "><a href="#">2</a></li>
        <li class="paginate_button "><a href="#">3</a></li>
        <li class="paginate_button "><a href="#">4</a></li>
        <li class="paginate_button "><a href="#">5</a></li>
        <li class="paginate_button "><a href="#">6</a></li>
        <li class="paginate_button next" id="datatable-editable_next"><a
                href="#">下一页</a></li>
    </ul>
</div>

</#macro>