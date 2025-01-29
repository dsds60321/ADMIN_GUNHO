const rule = {
    add : function (){
        const frm = $('#frm');
        frm.validate(jqueryValidOptions.rule.add(async (frm) => {
            const { data } = await Post(frm.action, frm);
            if (data.result) {
                location.href = '/';
            } else {
                alert(data.message);
            }
        }))
    }
}