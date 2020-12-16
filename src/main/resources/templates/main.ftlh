<#import "parts/common.ftlh" as c>
<#import "parts/main.ftlh" as m>

<@c.page>
    <@m.ads/>
    <div class="card-columns">
        <#list messages as message>
            <div class="card my-3">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No ads
        </#list>
    </div>
</@c.page>