<#import "parts/common.ftlh" as c>

<@c.page>
    <h3>${username}</h3>
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Enter the phone number:</label>
            <div>
                <label>
                    <input type="text" name="number" class="form-control" placeholder="29-111-11-11"/>
                </label>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Enter the money:</label>
            <div>
                <label>
                    <input type="number" name="cash" class="form-control" placeholder="100"/>
                </label>
            </div>
        </div>
        <button class="btn btn-primary" type="submit">Enroll</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>
</@c.page>