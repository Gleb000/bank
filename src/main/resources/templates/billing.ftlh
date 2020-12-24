<#import "parts/common.ftlh" as c>

<@c.page>
    <h3>${username}</h3>
    <div class="form-group row">
        <label class="col col-form-label">Cash account:</label>
        <label class="col-sm-10 col-form-label">${userMoney} $</label>
    </div>
    <div>
        <a class="ui-icon-link" href="/user/cashReplenishment">Balance replenishment</a>
    </div>
    <div>
        <a class="ui-icon-link" href="/user/cashTransfer">Transfer of funds</a>
    </div>
    <div>
        <a class="ui-icon-link" href="/user/mobilePay">Mobile payment</a>
    </div>
    <div>
        <a class="ui-icon-link" href="/user/servicesPay">Payment of utility services</a>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</@c.page>