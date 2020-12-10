<#include "security.ftlh">

<#macro ads>
    <#if isAdmin>
        <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
            Add a new ad
        </a>
        <div class="collapse <#if message??>show</#if>" id="collapseExample">
            <div class="form-group mt-3">
                <form method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                               value="<#if message??>${message.text}</#if>" name="text" placeholder="Введите сообщение" />
                        <#if textError??>
                            <div class="invalid-feedback">
                                ${textError}
                            </div>
                        </#if>
                    </div>
                    <div class="form-group">
                        <div class="custom-file">
                            <input type="file" name="file" id="customFile">
                            <label class="custom-file-label" for="customFile">Choose file</label>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Place</button>
                    </div>
                </form>
            </div>
        </div>
    </#if>
</#macro>