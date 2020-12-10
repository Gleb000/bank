<#import "parts/common.ftlh" as c>

<@c.page>
    <h3>${username}</h3>
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Enter the money:</label>
            <div class="col-sm-6">
                <label>
                    <input type="number" name="cash" class="form-control" placeholder="100"/>
                </label>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Enroll</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>