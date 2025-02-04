const rule = {
    add : function() {
        const frm = $('#frm'); // 폼 요소를 선택
        frm.validate(jqueryValidOptions.rule.add(async (frm) => {
            const { data } = await Post(frm.action, frm);
            if (data.result) {
                alert(data.message);
                location.href = '/rule/form';
            } else {
                alert(data.message);
            }
        }));
    }
}