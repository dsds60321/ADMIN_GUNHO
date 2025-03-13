const stock = {
    tableNoti : async () => {
        for (const row of document.querySelectorAll('tr[data-symbol]')) {
            console.log('11')
            const symbol = row.dataset.symbol;
            const marketPriceElem = row.querySelector('#market-price');

            // 시장가 데이터 가져오기
            const { data } = await Get(`/stock/daily/${symbol}`);
            if (!data.result) {
                console.error(`Failed to fetch market price for symbol: ${symbol}`);
                continue;
            }

            const marketPrice = parseFloat(data.res); // 시장가
            if (!marketPrice) {
                console.warn(`Invalid market price for symbol: ${symbol}`);
                continue;
            }

            marketPriceElem.innerText = marketPrice.toFixed(2);

            // 매수/매도 가격 가져오기
            const sellPrice = parseFloat(row.querySelector('#sell-price').innerText);
            const buyPrice = parseFloat(row.querySelector('#buy-price').innerText);

            // 유효하지 않은 데이터가 있으면 처리 스킵
            if (isNaN(sellPrice) || isNaN(buyPrice)) {
                console.warn(`Invalid sell/buy price for symbol: ${symbol}`);
                continue;
            }

            // 매수/매도 조건에 따른 CSS 클래스 적용
            if (marketPrice >= sellPrice) {
                row.classList.add('buy-highlight'); // 매도 조건 충족
            } else {
                row.classList.remove('buy-highlight');
            }

            if (marketPrice <= buyPrice) {
                row.classList.add('sell-highlight'); // 매수 조건 충족
            } else {
                row.classList.remove('sell-highlight');
            }

        }
    },

    init : function (){
        stock.tableNoti();

        // INTERVAL 1분
        setInterval(async () => {
            // 테이블의 모든 주식 데이터를 가져오기
            stock.tableNoti();
        }, 6000); // 1분(60초)

    },
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