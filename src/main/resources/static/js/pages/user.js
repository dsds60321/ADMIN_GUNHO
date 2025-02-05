const user = {
    invite : function (){
        const frm = $('#frm');
        frm.validate(jqueryValidOptions.user.invite(async (frm) => {
            const { data } = await Post(frm.action, frm);
            if (data.result) {
                location.href = '/user/friends/form';
            } else {
                alert(data.message);
            }
        }))
    }
}