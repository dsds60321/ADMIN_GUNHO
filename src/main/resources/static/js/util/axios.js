const axiosInstance = axios.create({
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json;utf-8',
        'X-FromAjax':1,
    },
});



axiosInstance.interceptors.response.use(
    response => response,
    async (error) => {
        if (error.response && error.response.status === 401) {
            // 세션 만료 시 새로고침 또는 재로그인 유도
            try {
                const res = await axiosInstance.post('/auth/refresh'); // 리프레시 토큰 요
                if (res.status === 200) {
                    location.reload(); // 토큰 갱신 후 새로고침
                }
            } catch (refreshError) {
                console.error('리프레시 토큰 갱신 실패:', refreshError);
                window.location.href = '/auth/sign-in'; // 로그인 페이지로 이동
            }
        }

        return Promise.reject(error);
    }
);

const PostWithBlob = async (url, data,_progressDiv) => {
    try {
        if (_progressDiv) {
            loading(_progressDiv);
        }

        const response = await axiosInstance.post(url, data, {
            headers: {
                searchType : 'download',
            },
            responseType: 'blob', // 파일 다운로드를 위한 blob 응답
        });

        const overlayElement = document.querySelector('.card-overlay');
        if (overlayElement) {
            overlayElement.remove();
        }

        return response;
    } catch (e) {
        console.error('오류 발생....', e);
        mikAlert('알림', '오류가 발생했습니다.', 'error');
    } finally {
        removeLoading();
    }
};

/**
 * Post 요청 함수
 */
const Post = async (url, data, _progressDiv) => {

    try {
        let finalData = data;

        if (data instanceof HTMLFormElement) {
            finalData = util.form.toJson(data);
        } else {
            finalData = data;
        }

        if (_progressDiv) {
            loading(_progressDiv);
        }

        const response = await axiosInstance.post(url, finalData, {
            validateStatus: (status) => status >= 200 && status <= 500
        });


        if (response.status === 401) {
            window.parent.location.href = "/";
        }


        return response;
    } catch (e) {
        console.error('오류 발생....' , e);
        alert('오류가 발생했습니다.')
    } finally {
        removeLoading();
    }
};

/**
 * Post Multipart Form 요청 함수
 * @param url {string} 요청 URL
 * @param data {object|FormData} 전송할 데이터 (FormData 및 일반 데이터)
 * @param _progressDiv {HTMLElement} 로딩 표시 엘리먼트
 * @returns {Promise<*>} 서버 응답
 */
const PostMultipartForm = async (url, formElement, _progressDiv) => {
    try {
        const formData = new FormData();

        // formElement가 HTMLFormElement인지 확인
        if (formElement instanceof HTMLFormElement) {
            // 폼의 모든 입력 요소를 가져와 처리
            const elements = formElement.elements;

            Array.from(elements).forEach(element => {
                const { name, type, value, checked, files, tagName } = element;

                // name이 없는 항목은 제외합니다.
                if (!name) return;

                switch (type) {
                    case "file":
                        // 파일 입력 처리 (다중 파일 지원)
                        if (files) {
                            Array.from(files).forEach(file => {
                                formData.append(name, file);
                            });
                        }
                        break;
                    case "checkbox":
                    case "radio":
                        // 체크박스나 라디오 버튼은 체크된 경우만 추가
                        if (checked) {
                            formData.append(name, value);
                        }
                        break;
                    default:
                        // 기본적으로 <input>, <textarea>, <select> 등의 값을 추가
                        if (tagName === "INPUT" || tagName === "TEXTAREA" || tagName === "SELECT") {
                            formData.append(name, value);
                        }
                        break;
                }
            });
        } else if (formElement instanceof Object) {
            // 일반 데이터를 전달받을 경우 JSON 데이터를 FormData에 추가
            for (const [key, value] of Object.entries(formElement)) {
                formData.append(key, value);
            }
        } else {
            throw new Error("formElement는 HTMLFormElement이거나 객체 형태여야 합니다.");
        }

        if (_progressDiv) {
            loading(_progressDiv); // 로딩 시작
        }

        // Axios 요청 (Content-Type: multipart/form-data 자동 설정)
        const response = await axiosInstance.post(url, formData, {
            headers: {
            },
        });

        // 로딩 완료 후 오버레이 제거
        const overlayElement = document.querySelector('.card-overlay');
        if (overlayElement) {
            overlayElement.remove();
        }

        return response; // 서버 응답 반환
    } catch (e) {
        console.error('오류 발생....', e);
        mikAlert('알림', '오류가 발생했습니다.', 'error');
    } finally {
        removeLoading(); // 로딩 제거
    }
};


const Get = async (url, params = {}, _progressDiv) => {
    try {
        if (_progressDiv) {
            loading(_progressDiv);
        }
        const response = await axiosInstance.get(url, {
            params,
            validateStatus: (status) => {
                // Accept all status codes to handle them manually
                return status >= 200 && status <= 500; // Accept 2xx and 3xx responses
            }
        });

        // Check if the response status is 302
        if (response.status === 401) {
            window.parent.location.href = "/";
            // Handle the redirect as needed
        }

        return response;

    } catch (e) {
        console.error('오류 발생....' , e);
    } finally {
        removeLoading();
    }
};

/**
 * 로딩바 유틸
 * @param div
 */
function loading(div) {
}


function removeLoading() {

}