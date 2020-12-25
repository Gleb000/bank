<#import "parts/common.ftlh" as c>

<@c.page>
    <h3>${username}</h3>
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Water:</label>
            <label class="col-sm-2 col-form-label">${water} $</label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Gas:</label>
            <label class="col-sm-2 col-form-label">${gas} $</label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Electricity:</label>
            <label class="col-sm-2 col-form-label">${electricity} $</label>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">To pay:</label>
            <label class="col-sm-2 col-form-label">${servicesSum} $</label>
        </div>
        <button class="btn btn-primary" type="submit">Enroll</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>