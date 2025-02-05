const util = {
    init : async function () {

        // select2 설정
        const selectPrefix = 'select-';
        const selectList = document.querySelectorAll('select');

        if (selectList.length > 0) {
            for (const select of selectList) {
                const id = select.id;

                switch (id) {
                    case `${selectPrefix}symbol`:
                        const { data } = await Get('/stock/symbols');
                        util.select2Util.init(`#${selectPrefix}symbol`, data.res);

                        break;
                }

            }
        }

        util.display.dropdown.init();
    },

    form : {
        toJson : function (frm, isDisabled = false ) {
            let finalData = {};
            const formElements = frm.elements;

            for (let element of formElements) {
                if (element.name && (isDisabled || !element.disabled)) {
                    // 체크박스인 경우
                    if (element.type === 'checkbox') {
                        if (element.checked) {
                            // 체크된 체크박스의 값만 추가
                            finalData[element.name] = element.value; // 마지막 체크된 값만 저장
                        }
                    } else {
                        // 체크박스가 아닌 경우
                        finalData[element.name] = element.value;
                    }

                }
            }

            return JSON.stringify(finalData);
        },
    },

    select2Util : {
        /**
         * Select2 초기화
         */
        init: function (selector, data, options = {}) {
            // HTML data-key와 data-value 읽기
            const selectElem = document.querySelector(selector);
            if (!selectElem) {
                return;
            }

            const selectedValue = selectElem.getAttribute('data-selected');

            // 데이터를 Select2 형식으로 변환
            const transformedData = [
                { text: '', id: '' }, // 기본 비어있는 값을 추가
                ...Object.entries(data).map(([key, value]) => {
                    return {
                        text: value,
                        id : key,
                        original : {key, value}
                    };
                })
            ];

            // 기본 옵션
            const defaultOptions = {
                data: transformedData,
                placeholder: "선택하세요",
                allowClear: true,
            };

            // Select2 초기화
            $(selector).select2({
                ...defaultOptions,
                ...options,
            });

            if (selectedValue) {
                this.setSelected(selector, selectedValue);
            }
        },

        /**
         * 선택 값 설정
         * @param {string} selector - Select2 셀렉터
         * @param {Array|string} value - 설정할 값
         */
        setSelected: function (selector, value) {
            const select2Elem = $(selector);
            if (!select2Elem.data("select2")) {
                console.error("Select2가 초기화되지 않았습니다.");
                return;
            }

            select2Elem.val(value).trigger("change");
        }
    },



    // 갖에 이벤트 실행
    evt : {
        trigger: function (elem, type) {
            const currentEvent = new Event(type); // 커스텀 이벤트 생성
            elem.dispatchEvent(currentEvent); // 강제로 change 이벤트 호출
        },
        click : function (elem, fn) {
            elem.addEventListener('click', fn);
        },
        change : function (elem, fn) {
            elem.addEventListener('change', fn);
        },
        input : function (elem, fn) {
            elem.addEventListener('input', fn)
        },
        focusOut: function (elem, fn, isSync = false) {
            elem.addEventListener('focusout', isSync ?  (async () => fn) : fn)
        },
    },



    display : {
        selectedSidebar : function (sidenavId, subSideNo) {
            const sidenav = document.getElementById(sidenavId);
            if (sidenav) {
                sidenav.classList.add('active');
            }

            if (subSideNo) {
                sidenav.querySelectorAll('ul > li')[subSideNo - 1].classList.add('active');
            }
        },

        dropdown : {
            init : function () {
                document.addEventListener("click", function (event) {
                    const isDropdown = event.target.closest(".dropdown-container");
                    const isMenu = event.target.closest(".dropdown-menu");
                    if (!isDropdown && !isMenu) {
                        document.querySelectorAll(".dropdown-menu").forEach((menu) => {
                            menu.classList.remove("show");
                            menu.removeAttribute("style");
                        });
                    }
                });
            },
            toggleDropdown : function (trigger) {
                const dropdownMenu = trigger.nextElementSibling; // 드롭다운 메뉴 선택
                const isShown = dropdownMenu.classList.contains("show");

                // 기존에 열려 있는 모든 드롭다운 닫기
                document.querySelectorAll(".dropdown-menu").forEach((menu) => {
                    menu.classList.remove("show");
                    menu.removeAttribute("style"); // 기존 스타일 제거
                });

                if (!isShown) {
                    // 버튼 위치 가져오기
                    const rect = trigger.getBoundingClientRect(); // 화면에서의 버튼 위치 가져오기
                    const top = rect.bottom + window.scrollY; // 버튼 하단의 Y 축 절대 위치
                    const left = rect.left + window.scrollX; // 버튼의 왼쪽 X 축 절대 위치

                    // 드롭다운 메뉴 스타일 설정
                    dropdownMenu.style.position = "absolute"; // 절대 위치
                    dropdownMenu.style.top = `${top}px`; // 버튼 하단에 위치
                    dropdownMenu.style.left = `${left}px`; // 버튼의 왼쪽과 일치
                    dropdownMenu.style.minWidth = `${rect.width}px`; // 버튼 폭과 동일

                    // 드롭다운 메뉴 활성화
                    dropdownMenu.classList.add("show");
                }
            }
        }
    },


    modal : {
        open : async function (url, size = 'medium') {
            const modalContainer = document.getElementById('modal-container');
            const modalBody = document.getElementById('modal-body');

            modalContainer.classList.remove('small', 'medium', 'large');
            modalContainer.classList.add(size);

            const { data } = await Get(url);
            if (data) {
                modalBody.innerHTML = data;
                modalContainer.classList.remove('hidden');
                modalContainer.classList.add('visible');

                modalContainer.addEventListener('click', (e) => {
                    if (e.target === modalContainer) {
                        this.close();
                    }
                })
            }
        },
        close : function () {
            const modalContainer = document.getElementById('modal-container');
            const modalBody = document.getElementById('modal-body');

            modalContainer.classList.remove('visible');
            setTimeout(() => {
                modalContainer.classList.add('hidden');
                modalBody.innerHTML = '';
            },300);
        }
    }

}