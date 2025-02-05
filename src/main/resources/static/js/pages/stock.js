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
    },
    noti : async function (evt, isBuy){
        const symbol = evt.dataset.symbol;
        if (!symbol) {
            alert('선택하신 주식이 올바르게 선택되지 않았습니다. 관리자에게 문의해주세요.');
            return;
        }

        if (confirm(`${isBuy ? '매수' : '매도'} 알림을 보내시겠습니까?`)) {
            const { data } = await Post('/stock/noti', {isBuy, symbol});
            if (data.result) {
                alert(data.message);
            } else {
                alert(data.message);
            }
        }
    }
}