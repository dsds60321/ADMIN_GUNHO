const stock = {
    add : function (){

        $('#select-symbol').on('select2:select', async function (e) {
            const {id} = e.params.data; // 선택된 데이터 객체
            console.log(id);
            const {data} = await Get('/stock/info', {symbol: id});
            if (data.result) {
                document.getElementById('averagePrice').value = data.res.lastSale;
            }
        });


        const frm = $('#frm');
        frm.validate(jqueryValidOptions.stock.add(async (frm) => {
            const { data } = await Post(frm.action, frm);
            if (data.result) {
                location.href = '/';
            } else {
                alert(data.message);
            }
        }))
    }
}