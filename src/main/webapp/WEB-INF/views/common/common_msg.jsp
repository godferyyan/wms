<script type="text/javascript">
    $(function () {
        <s:if test="hasActionErrors()">
        var errorMsg = "<s:property value="actionErrors[0]"/>";
        showDialogMsg("",errorMsg,"","");
        </s:if>
    });
    $(function(){
        <s:if test="hasActionMessages()">
        var actionMsg = "<s:property value="actionMessages[0]"/>";
        showDialogMsg("",actionMsg,"","");
        </s:if>
    })
</script>
