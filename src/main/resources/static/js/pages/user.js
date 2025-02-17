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
    },


    // TODO: js 분리
    chat : {
        socket : null,
        stompClient : null,
        chatContainer : document.querySelector('#chatMessages'),
        invite : function (){
            const frm = $('#frm');
            frm.validate(jqueryValidOptions.user.chat.invite(async (frm) => {
                const { data } = await Post(frm.action, frm);
                if (data.result) {
                    location.href = '/user/chat/form';
                } else {
                    alert(data.message);
                }
            }))
        },


        init : async function (userId){
            user.chat.createSocket(userId);
            const frm = document.frm;
            const { data } = await Post('/user/chat/recv', frm);
            if (data.result) {
                data.res.forEach(message => {
                    console.log(message)
                    user.chat.createChat(message, userId);
                });
            }

            // 클릭 이벤트
            document.getElementById('message-btn').addEventListener('click', user.chat.send);
        },


        createSocket : function (userId){
                // WebSocket 연결: 서버의 IP, PORT, 엔드포인트(URL)를 지정
                this.socket = new SockJS('/ws-stomp'); // 전체 URL (ex: http://localhost:8080/ws)
                this.stompClient = Stomp.over(user.chat.socket);

                // WebSocket 연결 시작
                this.stompClient.connect({}, () => {

                // 특정 STOMP 경로 (topic/messages) 구독
                this.stompClient.subscribe('/room', (message) => {
                    document.querySelector('#chatMessages').innerHTML = ''
                    const receivedMessages = JSON.parse(message.body);

                    receivedMessages.body.res.forEach(message => {
                        user.chat.createChat(message, userId);
                    });
                });
            });
        },


        send : async function (){

            // 서버로 메시지 전송
            const frm = document.frm;

            if (frm.message.value) {
                if (user.chat.stompClient && user.chat.stompClient.connected) {
                    const reqObj = util.form.toJson(frm);
                    user.chat.stompClient.send('/app/send', {}, reqObj);
                    frm.message.value = '';
                }
            }
        },


        createChat: function (data, originUserId) {
            const { userId, message, regDate } = data;
            const chatContainer = document.querySelector('#chatMessages');

            // 메시지 Wrapper 생성
            const wrapper = user.chat.createElementWithClass('div', `message-row ${userId === originUserId ? 'mine' : 'other'}`);

            // 메시지 내용 컨테이너 생성
            const contentContainer = user.chat.createElementWithClass('div', 'message-content');

            // 메시지 텍스트 Content 생성
            const content = user.chat.createElementWithClass('div', 'message-bubble');
            content.innerText = message;

            // 메시지 시간 표시 Span 생성
            const timeSpan = user.chat.createElementWithClass('span', 'message-time');
            timeSpan.innerText = regDate;

            // 요소 조립
            contentContainer.append(timeSpan, content);
            wrapper.append(contentContainer);
            chatContainer.append(wrapper);
        },

        createElementWithClass: function (tagName, className) {
            const element = document.createElement(tagName);
            element.className = className;
            return element;
        }
    }
}