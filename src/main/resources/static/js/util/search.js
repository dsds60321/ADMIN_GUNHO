$(function () {
  search.init();
})
const search = {
    init : function () {
        const frm = document.getElementById('search-form');

        if (!frm) {
            console.log('frm not found');
            return false;
        }


        const paging = document.getElementById('paging');
        const searchBtn = frm.querySelector('#search-btn');
        const size = document.getElementById('page-size');


        util.evt.click(searchBtn, async () => {
            const { data } = await Get(`${frm.action}?page=0&size=${size.value}`);
            paging.innerHTML = data;
        })

        // click 이벤트
        util.evt.trigger(searchBtn, 'click');
    },
    submit : async function (elem ,action, page ,size) {
        const { data } = await Get(`${action}?page=${page}&size=${size}`);
        elem.querySelector('#paging').innerHTML = data;
    }
}