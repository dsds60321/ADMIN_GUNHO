const util = {
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
    }
}